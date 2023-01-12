package com.example.taysir.Customer.Offers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taysir.Customer.CreateNewOffer;
import com.example.taysir.R;
import com.example.taysir.databinding.FragmentCreateNewOfferBinding;
import com.example.taysir.databinding.FragmentShowOffersBinding;

public class ShowOffersFragment extends Fragment {
   private FragmentShowOffersBinding mBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= FragmentShowOffersBinding.inflate(inflater,container,false);
        back();
        showBrokerInfo();
        accept();
        return mBinding.getRoot();
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(ShowOffersFragment.this)
                        .navigate(R.id.goToHome);
            }
        });
    }
    private void showBrokerInfo()
    {
        mBinding.showBrokerInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(ShowOffersFragment.this)
                        .navigate(R.id.barkerRank);
            }
        });
    }
    private void accept()
    {
        mBinding.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(ShowOffersFragment.this)
                        .navigate(R.id.goToacceptOffer);
            }
        });
    }

}