package com.app.taysir.Support;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.taysir.Models.OldComplaintModel;
import com.app.taysir.R;
import com.app.taysir.databinding.FragmentComplaintAnsweredBinding;


public class ComplaintAnswered extends Fragment {


    private FragmentComplaintAnsweredBinding mBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= FragmentComplaintAnsweredBinding.inflate(inflater,container,false);
        back();
        details();
        return mBinding.getRoot();
    }
    private void details()
    {
        OldComplaintModel model=new OldComplaintModel(
                getArguments().getString("username").toString(),
                getArguments().getString("complaint").toString(),
                getArguments().getString("complaintId").toString(),
                getArguments().getString("userId").toString(),
                getArguments().getInt("num"),
                getArguments().getString("answer").toString());
        mBinding.complaintAnswer.setText(model.getAnswer());
        mBinding.complaint.setText(model.getInquire());
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(ComplaintAnswered.this)
                        .navigate(R.id.goTosupportTechnical);
            }
        });
    }
}