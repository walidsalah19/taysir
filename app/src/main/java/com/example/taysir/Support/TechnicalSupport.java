package com.example.taysir.Support;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taysir.R;
import com.example.taysir.databinding.FragmentTechnicalSupportBinding;


public class TechnicalSupport extends Fragment {


    private FragmentTechnicalSupportBinding mBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= FragmentTechnicalSupportBinding.inflate(inflater,container,false);
        back();
        answeredComplaint();
        waitAnswer();
        createComplaint();
        return mBinding.getRoot();
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(TechnicalSupport.this)
                        .navigate(R.id.goToHome);
            }
        });
    }
    private void createComplaint()
    {
        mBinding.linear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(TechnicalSupport.this)
                        .navigate(R.id.goTosendComplaint);
            }
        });
    }
    private void waitAnswer()
    {
        mBinding.linear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(TechnicalSupport.this)
                        .navigate(R.id.goTowaitComplaint);
            }
        });
    }
    private void answeredComplaint()
    {
        mBinding.linear3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(TechnicalSupport.this)
                        .navigate(R.id.goTocomplaintAnswer);
            }
        });
    }
}