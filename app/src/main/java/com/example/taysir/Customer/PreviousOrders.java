package com.example.taysir.Customer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taysir.R;
import com.example.taysir.databinding.FragmentPreviousOrdersBinding;


public class PreviousOrders extends Fragment {
  private FragmentPreviousOrdersBinding mBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentPreviousOrdersBinding.inflate(inflater,container,false);
        showRating();
        back();
        showOrderDetails();
        return mBinding.getRoot();
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavHostFragment.findNavController(PreviousOrders.this)
                        .navigate(R.id.goToHome);
            }
        });
    }
    private void showOrderDetails()
    {
        mBinding.showOrderDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b=new Bundle();
                b.putString("orderType","old");
                NavHostFragment.findNavController(PreviousOrders.this)
                        .navigate(R.id.goTopreviousOrdersDetails,b);
            }
        });
    }
    private void showRating()
    {
        mBinding.rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.FrameLayout,new CustomerRankBroker()).commit();
            }
        });
    }
}