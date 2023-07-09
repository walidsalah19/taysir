package com.app.taysir.Broker;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.taysir.R;
import com.app.taysir.UserAccess.AccessMainActivity;
import com.app.taysir.databinding.FragmentBrokerHomeBinding;
import com.app.taysir.databinding.FragmentCustomerHomeBinding;
import com.google.firebase.auth.FirebaseAuth;

public class BrokerHome extends Fragment {

     private FragmentBrokerHomeBinding mBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding= FragmentBrokerHomeBinding.inflate(inflater,container,false);
        oldOrders();
        showNewOrder();
        currentlyOrders();
        logout();
        showProfile();
        supportTechnical();
        showNotificationOffers();
        removeNotification();
        return mBinding.getRoot();
    }
    private void removeNotification()
    {
        mBinding.home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.FrameLayout.setVisibility(View.INVISIBLE);
            }
        });
    }
    private void showProfile()
    {
        mBinding.displayProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(BrokerHome.this)
                        .navigate(R.id.goToBrokerPersonly);
            }
        });
    }
    private void showNewOrder()
    {
        mBinding.showNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(BrokerHome.this)
                        .navigate(R.id.goToNewOrders);
            }
        });
    }
    private void currentlyOrders()
    {
        mBinding.currentlyOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(BrokerHome.this)
                        .navigate(R.id.goToCurrentOrders);
            }
        });
    }
    private void oldOrders()
    {
        mBinding.oldOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(BrokerHome.this)
                        .navigate(R.id.displayOldOrders);
            }
        });
    }
    private void supportTechnical()
    {
        mBinding.supportTechnical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(BrokerHome.this)
                        .navigate(R.id.goTosupportTechnical);
            }
        });
    }
    private void logout()
    {
        mBinding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), AccessMainActivity.class));
            }
        });
    }
    private void showNotificationOffers()
    {
        mBinding.displayNotificationOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.FrameLayout.setVisibility(View.VISIBLE);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout,new OrderNotification()).addToBackStack(null).commit();
            }
        });
    }
}