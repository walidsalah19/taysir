package com.example.taysir.Broker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taysir.Customer.Offers.OfferNotification;
import com.example.taysir.R;
import com.example.taysir.databinding.FragmentOrderNotificationBinding;

public class OrderNotification extends Fragment {

  private FragmentOrderNotificationBinding mBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentOrderNotificationBinding.inflate(inflater,container,false);
        showOffers();
        return mBinding.getRoot();
    }
    private void showOffers()
    {
        mBinding.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(OrderNotification.this)
                        .navigate(R.id.newOrders);
            }
        });
    }
}