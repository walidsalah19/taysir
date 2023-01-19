package com.example.taysir.UserAccess;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taysir.Admin.AdminMainActivity;
import com.example.taysir.Broker.BrokerMainActivity;
import com.example.taysir.Customer.CustomerMainActivity;
import com.example.taysir.R;
import com.example.taysir.UserType;
import com.example.taysir.databinding.FragmentLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {


    private FragmentLoginBinding mBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentLoginBinding.inflate(inflater,container,false);
        forgotPassword();
        login();
        back();
        return mBinding.getRoot();
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.selectAccessType);
            }
        });
    }
    private void login()
    {
        mBinding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=mBinding.Email.getText().toString();
                String password=mBinding.password.getText().toString();
                if(TextUtils.isEmpty(email))
                {
                    mBinding.Email.setError("من فضلك قم بإدخال البريد الإلكتروني الخاص بك");
                }
                else if(TextUtils.isEmpty(password))
                {
                    mBinding.password.setError("من فضلك قم بإدخال كلمة المرور الخاص بك");
                }
                else if(mBinding.Email.getText().toString().equals("admin@gmail.com"))
                {
                    startActivity(new Intent(getActivity(), AdminMainActivity.class));
                }
                else
                {
                    checkAccount(email,password);
                }

            }
        });
    }

    private void checkAccount(String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if (task.isSuccessful())
               {
                   if (UserType.type.equals("customer"))
                   {
                       startActivity(new Intent(getActivity(), CustomerMainActivity.class));
                   }
                   else
                   {
                       startActivity(new Intent(getActivity(), BrokerMainActivity.class));
                   }
               }
            }
        });
    }
    private void forgotPassword()
    {
        mBinding.forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.forgetPasswordAction);
            }
        });
    }
}