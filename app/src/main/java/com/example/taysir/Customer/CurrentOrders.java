package com.example.taysir.Customer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taysir.R;
import com.example.taysir.databinding.FragmentCurrentOrdersBinding;

public class CurrentOrders extends Fragment {

    private FragmentCurrentOrdersBinding mBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentCurrentOrdersBinding.inflate(inflater,container,false);
        steps();
        return mBinding.getRoot();
    }
    private void steps()
    {

    }
}