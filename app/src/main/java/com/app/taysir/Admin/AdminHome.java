package com.app.taysir.Admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.taysir.R;
import com.app.taysir.UserAccess.AccessMainActivity;
import com.app.taysir.databinding.FragmentAdminHomeBinding;


public class AdminHome extends Fragment {

    private FragmentAdminHomeBinding mBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentAdminHomeBinding.inflate(inflater,container,false);
         manageBrokers();
         newComplaint();
         oldComplaint();
         logout();

        return mBinding.getRoot();
    }
    private void manageBrokers()
    {
        mBinding.manageBrokers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(AdminHome.this)
                        .navigate(R.id.goToManageBrokers);
            }
        });
    }
    private void newComplaint()
    {
        mBinding.newComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(AdminHome.this)
                        .navigate(R.id.goToNewComplaint);
            }
        });
    }
    private void oldComplaint()
    {
        mBinding.oldComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(AdminHome.this)
                        .navigate(R.id.goToOldComplaint);
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
}