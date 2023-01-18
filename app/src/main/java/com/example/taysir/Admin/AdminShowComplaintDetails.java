package com.example.taysir.Admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taysir.Customer.CustomerRankBroker;
import com.example.taysir.Models.NewComplaintModel;
import com.example.taysir.Models.OldComplaintModel;
import com.example.taysir.R;
import com.example.taysir.databinding.FragmentAdminShowComplaintDetailsBinding;


public class AdminShowComplaintDetails extends Fragment {

   private FragmentAdminShowComplaintDetailsBinding mBinding;
   //private AdminShowComplaintDetailsArgs args;
   private NewComplaintModel data;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentAdminShowComplaintDetailsBinding.inflate(inflater,container,false);
        NewComplaintModel model=new NewComplaintModel(
                getArguments().getString("username").toString(),
                getArguments().getString("complaint").toString(),
                getArguments().getString("complaintId").toString(),
                getArguments().getString("userId").toString(),
                getArguments().getInt("num"));
        mBinding.complaintNumber.setText(data.getInquireNum()+" ");
        mBinding.edittextComplaintText.setText(data.getInquire());
        sendAnswer();
        back();
        return mBinding.getRoot();
    }

    private void sendAnswer() {
        mBinding.btnAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b=new Bundle();
                b.putString("complaint",data.getInquire());
                b.putString("username",data.getUserName());
                b.putString("complaintId",data.getInquireId());
                b.putString("userId",data.getUserId());
                b.putInt("num",data.getInquireNum());
                AdminAnswerComplaint a=new AdminAnswerComplaint();
                a.setArguments(b);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.FrameLayout,a).commit();
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