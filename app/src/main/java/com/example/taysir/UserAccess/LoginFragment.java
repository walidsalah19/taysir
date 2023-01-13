package com.example.taysir.UserAccess;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taysir.Admin.AdminMainActivity;
import com.example.taysir.Broker.BrokerMainActivity;
import com.example.taysir.Customer.CustomerMainActivity;
import com.example.taysir.R;
import com.example.taysir.UserType;
import com.example.taysir.databinding.FragmentLoginBinding;

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
                if(mBinding.Email.getText().toString().equals("customer"))
                {
                    UserType.type="customer";
                   startActivity(new Intent(getActivity(), CustomerMainActivity.class));
                }
                else if(mBinding.Email.getText().toString().equals("broker"))
                {
                    UserType.type="broker";
                    startActivity(new Intent(getActivity(), BrokerMainActivity.class));
                }
                else if(mBinding.Email.getText().toString().equals("admin"))
                {
                    startActivity(new Intent(getActivity(), AdminMainActivity.class));
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