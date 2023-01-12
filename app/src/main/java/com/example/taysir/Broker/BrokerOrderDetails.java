package com.example.taysir.Broker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taysir.Customer.PreviousOrderDetails;
import com.example.taysir.R;
import com.example.taysir.databinding.FragmentBrokerOrderDetailsBinding;


public class BrokerOrderDetails extends Fragment {
    private FragmentBrokerOrderDetailsBinding mBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentBrokerOrderDetailsBinding.inflate(inflater,container,false);
        back();
        return mBinding.getRoot();
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    NavHostFragment.findNavController(BrokerOrderDetails.this)
                            .navigate(R.id.currentOrders);
            }
        });
    }
}