package com.example.taysir.Customer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

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
        mBinding= FragmentCurrentOrdersBinding.inflate(inflater,container,false);
        back();
        showOrderDetails();
        return mBinding.getRoot();
    }
    private void showOrderDetails()
    {
        mBinding.showOrderDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b=new Bundle();
                b.putString("orderType","current");
                NavHostFragment.findNavController(CurrentOrders.this)
                        .navigate(R.id.goTopreviousOrdersDetails,b);
            }
        });
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(CurrentOrders.this)
                        .navigate(R.id.goToHome);
            }
        });
    }
}