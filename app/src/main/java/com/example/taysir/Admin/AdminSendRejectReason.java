package com.example.taysir.Admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taysir.R;
import com.example.taysir.databinding.FragmentAdminSendRejectReasonBinding;


public class AdminSendRejectReason extends Fragment {
    private FragmentAdminSendRejectReasonBinding mBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentAdminSendRejectReasonBinding.inflate(inflater,container,false);

        endFragment();
        return mBinding.getRoot();
    }
    private void endFragment()
    {
        mBinding.end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(AdminSendRejectReason.this).commit();
            }
        });
    }


}
