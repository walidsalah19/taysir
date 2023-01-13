package com.example.taysir.Admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taysir.Broker.BrokerCurrentOrder;
import com.example.taysir.R;
import com.example.taysir.databinding.FragmentAdminManageBrokersBinding;


public class AdminManageBrokers extends Fragment {

    private FragmentAdminManageBrokersBinding mBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentAdminManageBrokersBinding.inflate(inflater,container,false);
        showBrokerDetails();
        back();
        return mBinding.getRoot();
    }

    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(AdminManageBrokers.this)
                        .navigate(R.id.goToAdminHome);
            }
        });
    }
    private void showBrokerDetails()
    {
        mBinding.card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(AdminManageBrokers.this)
                        .navigate(R.id.goToBrokerDetails);
            }
        });
    }
}