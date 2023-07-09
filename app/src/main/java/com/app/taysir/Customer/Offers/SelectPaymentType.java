package com.app.taysir.Customer.Offers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.taysir.R;
import com.app.taysir.databinding.FragmentSelectPaymentTypeBinding;
import com.app.taysir.databinding.FragmentShowOffersBinding;

public class SelectPaymentType extends Fragment {
     private FragmentSelectPaymentTypeBinding mBinding;
     private String offerId,orderId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= FragmentSelectPaymentTypeBinding.inflate(inflater,container,false);
        offerId=getArguments().getString("id");
        orderId=getArguments().getString("orderId");
        back();
        next();
        return mBinding.getRoot();
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(SelectPaymentType.this)
                        .navigate(R.id.goToacceptOffer);
            }
        });
    }
    private void next()
    {
        mBinding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b=new Bundle();
                b.putString("id", offerId);
                b.putString("orderId",orderId);
                NavHostFragment.findNavController(SelectPaymentType.this)
                        .navigate(R.id.goToPayment,b);
            }
        });
    }
}