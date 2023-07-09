package com.app.taysir.Support;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.taysir.Models.NewComplaintModel;
import com.app.taysir.R;
import com.app.taysir.databinding.FragmentComplaintWaitingBinding;


public class ComplaintWaiting extends Fragment {
    private FragmentComplaintWaitingBinding mBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= FragmentComplaintWaitingBinding.inflate(inflater,container,false);
        NewComplaintModel model=new NewComplaintModel(
                getArguments().getString("username").toString(),
                getArguments().getString("complaint").toString(),
                getArguments().getString("complaintId").toString(),
                getArguments().getString("userId").toString(),
                getArguments().getInt("num"));
        mBinding.complaintNumber.setText(model.getInquireNum()+" ");
        mBinding.edittextComplaintText.setText(model.getInquire());
        back();
        return mBinding.getRoot();
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(ComplaintWaiting.this)
                        .navigate(R.id.goTosupportTechnical);
            }
        });
    }
}