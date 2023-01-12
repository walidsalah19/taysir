package com.example.taysir.Customer.Offers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.taysir.R;
import com.example.taysir.databinding.FragmentAcceptOffersBinding;
import com.example.taysir.databinding.FragmentPaymentBinding;

import java.util.ArrayList;

public class AcceptOffers extends Fragment {

    private FragmentAcceptOffersBinding mBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= FragmentAcceptOffersBinding.inflate(inflater,container,false);
        back();
        accept();
        return mBinding.getRoot();
    }

    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(AcceptOffers.this)
                        .navigate(R.id.goToshowOffers);
            }
        });
    }
    private void accept()
    {
        mBinding.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(AcceptOffers.this)
                        .navigate(R.id.goToselectPayment);
            }
        });
    }
}