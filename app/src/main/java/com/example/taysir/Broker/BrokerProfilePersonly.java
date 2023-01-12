package com.example.taysir.Broker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taysir.R;
import com.example.taysir.databinding.FragmentBrokerProfileBinding;
import com.example.taysir.databinding.FragmentBrokerProfilePersonlyBinding;

public class BrokerProfilePersonly extends Fragment {
   private FragmentBrokerProfilePersonlyBinding mBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= FragmentBrokerProfilePersonlyBinding.inflate(inflater,container,false);
        back();
        editProfile();
        showRejectReason();
        return mBinding.getRoot();
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavHostFragment.findNavController(BrokerProfilePersonly.this)
                        .navigate(R.id.goToHome);
            }
        });
    }
    private void editProfile()
    {
        mBinding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavHostFragment.findNavController(BrokerProfilePersonly.this)
                        .navigate(R.id.goToBrokerProfile);
            }
        });
    }
    private void showRejectReason()
    {
        mBinding.showRejectReason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout,new StatusNotification()).addToBackStack(null).commit();
            }
        });
    }
}