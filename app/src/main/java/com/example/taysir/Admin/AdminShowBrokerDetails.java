package com.example.taysir.Admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taysir.R;
import com.example.taysir.databinding.FragmentAdminShowBrokerDetailsBinding;

public class AdminShowBrokerDetails extends Fragment {


    private FragmentAdminShowBrokerDetailsBinding mBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentAdminShowBrokerDetailsBinding.inflate(inflater,container,false);
        sendRejectReason();
        back();
        return mBinding.getRoot();
    }
    private void sendRejectReason() {
        mBinding.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.FrameLayout,new AdminSendRejectReason()).commit();
            }
        });
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(AdminShowBrokerDetails.this)
                        .navigate(R.id.goToManageBrokers);
            }
        });
    }
}