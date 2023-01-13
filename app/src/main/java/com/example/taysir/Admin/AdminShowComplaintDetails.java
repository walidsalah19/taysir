package com.example.taysir.Admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taysir.Customer.CustomerRankBroker;
import com.example.taysir.R;
import com.example.taysir.databinding.FragmentAdminShowComplaintDetailsBinding;


public class AdminShowComplaintDetails extends Fragment {

   private FragmentAdminShowComplaintDetailsBinding mBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentAdminShowComplaintDetailsBinding.inflate(inflater,container,false);
        sendAnswer();
        back();
        return mBinding.getRoot();
    }

    private void sendAnswer() {
        mBinding.btnAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.FrameLayout,new AdminAnswerComplaint()).commit();
            }
        });
    }

    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(AdminShowComplaintDetails.this)
                        .navigate(R.id.goToNewComplaint);
            }
        });
    }

}