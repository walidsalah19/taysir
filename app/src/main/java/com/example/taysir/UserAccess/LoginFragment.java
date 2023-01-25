package com.example.taysir.UserAccess;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.taysir.Admin.AdminMainActivity;
import com.example.taysir.Broker.BrokerMainActivity;
import com.example.taysir.Customer.CustomerMainActivity;
import com.example.taysir.R;
import com.example.taysir.SweetDialog;
import com.example.taysir.UserType;
import com.example.taysir.databinding.FragmentLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginFragment extends Fragment {


    private FragmentLoginBinding mBinding;
    private SweetAlertDialog loading;
    private int pStatus=0;
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
        showPassword();
        createAccount();
        return mBinding.getRoot();
    }
    private void createAccount()
    {
        mBinding.createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserType.type.equals("customer")) {
                    NavHostFragment.findNavController(LoginFragment.this)
                            .navigate(R.id.customerProfile);
                }
                else
                {
                    NavHostFragment.findNavController(LoginFragment.this)
                            .navigate(R.id.brokerProfile);
                }
            }
        });
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
    private void funLoading()
    {
        loading= SweetDialog.loading(getContext());
        loading.show();
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
                    funLoading();
                    checkAccount(email,password);
                }

            }
        });
    }

    private void checkAccount(String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                loading.dismiss();
               if (task.isSuccessful())
               {
                   if (UserType.type.equals("customer")) {
                       checkCustomerCorrectly(task.getResult().getUser().getUid().toString());
                   }
                   else
                   {
                       checkBrokerrCorrectly(task.getResult().getUser().getUid().toString());

                   }
               }
               else
               {
                   funLoginField();
               }
            }
        });
    }

    private void checkBrokerrCorrectly(String id) {
        DatabaseReference Brokers= FirebaseDatabase.getInstance().getReference("Brokers");
        Brokers.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    funLoginSuccessfully();
                }
                else
                {
                    funLoginField();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void checkCustomerCorrectly(String id) {
      DatabaseReference clintDatabase= FirebaseDatabase.getInstance().getReference("Customers");
      clintDatabase.child(id).addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
              if (snapshot.exists())
              {
                  funLoginSuccessfully();
              }
              else
              {
                  funLoginField();
              }
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }
      });
    }

    private void funLoginSuccessfully()
    {
        SweetAlertDialog success=SweetDialog.success(getContext(),"تم تسجيل الدخول بنجاح");
        success.show();
        success.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                success.dismiss();
                if (UserType.type.equals("customer"))
                {
                    startActivity(new Intent(getActivity(), CustomerMainActivity.class));
                }
                else
                {
                    startActivity(new Intent(getActivity(), BrokerMainActivity.class));
                }
            }
        });
    }
    private void funLoginField()
    {
        FirebaseAuth.getInstance().signOut();
        SweetAlertDialog field=SweetDialog.failed(getContext(),"فشل تسجيل الدخول الرجاء المحاولة مرة أخري");
        field.show();
        field.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
              field.dismiss();
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
    private void showPassword() {
        mBinding.showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pStatus==0)
                {
                    Glide.with(LoginFragment.this)
                            .load(R.drawable.ic_baseline_visibility_off_24)
                            .centerCrop()
                            .into(mBinding.showPassword);
                    pStatus=1;
                    mBinding.password.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                else
                {
                    Glide.with(LoginFragment.this)
                            .load(R.drawable.ic_baseline_visibility_24)
                            .centerCrop()
                            .into(mBinding.showPassword);
                    pStatus=0;
                    mBinding.password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }
}