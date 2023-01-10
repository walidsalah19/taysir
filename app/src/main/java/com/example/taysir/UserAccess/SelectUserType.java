package com.example.taysir.UserAccess;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taysir.UserType;
import com.example.taysir.R;
import com.example.taysir.databinding.FragmentSelectUserTypeBinding;

public class SelectUserType extends Fragment {

    private FragmentSelectUserTypeBinding mBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentSelectUserTypeBinding.inflate(inflater,container,false);
        customerType();
        brokerType();
        return mBinding.getRoot();
    }
    private void customerType()
    {
        mBinding.customerType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserType.type="customer";
                NavHostFragment.findNavController(SelectUserType.this)
                        .navigate(R.id.accessTypeAction);
            }
        });
    }
    private void brokerType()
    {
        mBinding.brokerType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserType.type="broker";
                NavHostFragment.findNavController(SelectUserType.this)
                        .navigate(R.id.accessTypeAction);
            }
        });
    }
}