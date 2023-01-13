package com.example.taysir.Broker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taysir.Customer.CurrentOrders;
import com.example.taysir.R;
import com.example.taysir.databinding.FragmentBrokerCurrentOrderBinding;

public class BrokerCurrentOrder extends Fragment {

    private FragmentBrokerCurrentOrderBinding mBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentBrokerCurrentOrderBinding.inflate(inflater,container,false);
        showOrderDetails();
        back();
        goToChat();
        return mBinding.getRoot();
    }
    private void goToChat()
    {
        mBinding.brokerGoToChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(BrokerCurrentOrder.this)
                        .navigate(R.id.goToChat);
            }
        });
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(BrokerCurrentOrder.this)
                        .navigate(R.id.goToHome);
            }
        });
    }
    private void showOrderDetails()
    {
        mBinding.showOrderDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(BrokerCurrentOrder.this)
                        .navigate(R.id.OrderDetails);
            }
        });
    }
}