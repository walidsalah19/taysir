package com.example.taysir.Customer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taysir.Customer.Offers.OfferNotification;
import com.example.taysir.R;
import com.example.taysir.databinding.FragmentCustomerHomeBinding;

public class CustomerHome extends Fragment {
    private FragmentCustomerHomeBinding mBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentCustomerHomeBinding.inflate(inflater,container,false);
        showNotificationOffers();
        return mBinding.getRoot();
    }
    private void showNotificationOffers()
    {
        mBinding.displayNotificationOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framlayout,new OfferNotification()).addToBackStack(null).commit();
            }
        });
    }
}