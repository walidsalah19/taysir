package com.example.taysir.UserAccess;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taysir.UserType;
import com.example.taysir.R;
import com.example.taysir.databinding.FragmentSelectAccessTypeBinding;


public class SelectAccessType extends Fragment {

    private FragmentSelectAccessTypeBinding mBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentSelectAccessTypeBinding.inflate(inflater,container,false);
        funLogin();
        funCreateAccount();
        back();
        return mBinding.getRoot();
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(SelectAccessType.this)
                        .navigate(R.id.goToSelectAccessType);
            }
        });
    }
    private void funLogin()
    {
        mBinding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(SelectAccessType.this)
                        .navigate(R.id.loginAction);
            }
        });
    }
    private void funCreateAccount()
    {
        mBinding.registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserType.type.equals("customer")) {
                    NavHostFragment.findNavController(SelectAccessType.this)
                            .navigate(R.id.customerProfileAction);
                }
                else
                {
                    NavHostFragment.findNavController(SelectAccessType.this)
                            .navigate(R.id.brokerProfileAction);
                }

            }
        });
    }
}