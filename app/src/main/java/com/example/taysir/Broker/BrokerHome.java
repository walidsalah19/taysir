package com.example.taysir.Broker;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taysir.Customer.CustomerHome;
import com.example.taysir.Customer.Offers.OfferNotification;
import com.example.taysir.R;
import com.example.taysir.UserAccess.AccessMainActivity;
import com.example.taysir.databinding.FragmentBrokerHomeBinding;
import com.example.taysir.databinding.FragmentCustomerHomeBinding;

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
        /*showNotificationOffers();
        displayOffers();
        oldOrders();
        createNewOrder();
        currentlyOrders();
        logout();
        showProfile();
        supportTechnical();*/
        return mBinding.getRoot();
    }
   /* private void displayOffers()
    {
        mBinding.displayOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(CustomerHome.this)
                        .navigate(R.id.goToshowOffers);
            }
        });
    }
    private void showProfile()
    {
        mBinding.displayProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(CustomerHome.this)
                        .navigate(R.id.goToCustomerProfile);
            }
        });
    }
    private void createNewOrder()
    {
        mBinding.createNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(CustomerHome.this)
                        .navigate(R.id.goTocreateNewOrder);
            }
        });
    }
    private void currentlyOrders()
    {
        mBinding.currentlyOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(CustomerHome.this)
                        .navigate(R.id.goTocurrentlyOrders);
            }
        });
    }
    private void oldOrders()
    {
        mBinding.oldOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(CustomerHome.this)
                        .navigate(R.id.goTopreviousOrders);
            }
        });
    }
    private void supportTechnical()
    {
        mBinding.supportTechnical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(CustomerHome.this)
                        .navigate(R.id.goTosupportTechnical);
            }
        });
    }
    private void logout()
    {
        mBinding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AccessMainActivity.class));
            }
        });
    }
    private void showNotificationOffers()
    {
        mBinding.displayNotificationOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout,new OfferNotification()).addToBackStack(null).commit();
            }
        });
    }*/
}