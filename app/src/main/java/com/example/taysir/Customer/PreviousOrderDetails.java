package com.example.taysir.Customer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taysir.R;
import com.example.taysir.databinding.FragmentPreviousOrderDetailsBinding;

public class PreviousOrderDetails extends Fragment {
    private FragmentPreviousOrderDetailsBinding mBinding;
    private String orderType;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentPreviousOrderDetailsBinding.inflate(inflater,container,false);
        orderType=getArguments().getString("orderType");
        if (orderType.equals("current")) {
            mBinding.text.setText("الطلبات الحالية");
        }
        back();
        return mBinding.getRoot();
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderType.equals("current")) {
                    NavHostFragment.findNavController(PreviousOrderDetails.this)
                            .navigate(R.id.goTocurrentlyOrders);
                }
                else
                {
                    NavHostFragment.findNavController(PreviousOrderDetails.this)
                            .navigate(R.id.goToPreviousOrders);
                }
            }
        });
    }
}