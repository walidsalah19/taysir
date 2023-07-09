package com.app.taysir.Admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.taysir.Models.OldComplaintModel;
import com.app.taysir.R;
import com.app.taysir.databinding.FragmentAdminOldComplaintDetailsBinding;

public class AdminOldComplaintDetails extends Fragment {

    private FragmentAdminOldComplaintDetailsBinding mBinding;
/*
    private AdminOldComplaintDetailsArgs args;
*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentAdminOldComplaintDetailsBinding.inflate(inflater,container,false);
        back();
        details();
        return mBinding.getRoot();
    }
    private void details()
    {
        OldComplaintModel model=new OldComplaintModel(
                getArguments().getString("username"),
                getArguments().getString("complaint"),
                getArguments().getString("complaintId"),
                getArguments().getString("userId"),
                getArguments().getInt("num"),
                getArguments().getString("answer"));
        mBinding.complaintNumber.setText(model.getInquireNum()+"");
        mBinding.complaintAnswer.setText(model.getAnswer());
        mBinding.complaint.setText(model.getInquire());
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(AdminOldComplaintDetails.this)
                        .navigate(R.id.goToOldComplaint);
            }
        });
    }
}