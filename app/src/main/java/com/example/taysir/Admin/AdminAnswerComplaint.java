package com.example.taysir.Admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taysir.Customer.CustomerRankBroker;
import com.example.taysir.Models.OldComplaintModel;
import com.example.taysir.R;
import com.example.taysir.SweetDialog;
import com.example.taysir.databinding.FragmentAdminAnswerComplaintBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AdminAnswerComplaint extends Fragment {

  private FragmentAdminAnswerComplaintBinding mBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentAdminAnswerComplaintBinding.inflate(inflater,container,false);
        endFragment();
        clickToSendAnswer();
        return mBinding.getRoot();
    }
    private void clickToSendAnswer()
    {
        mBinding.sendAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer=mBinding.edittextComplaintAnswer.getText().toString();
                if (TextUtils.isEmpty(answer))
                {
                    mBinding.edittextComplaintAnswer.setError("من فضلك قم بإدخال إجابة الشكوى");
                }
                else
                {
                    OldComplaintModel model=new OldComplaintModel(
                            getArguments().getString("username").toString(),
                            getArguments().getString("complaint").toString(),
                            getArguments().getString("complaintId").toString(),
                            getArguments().getString("userId").toString(),
                            getArguments().getInt("num"),
                            answer);
                    addToDatabase(model);
                }
            }
        });
    }
    private void addToDatabase(OldComplaintModel model)
    {
        DatabaseReference database=FirebaseDatabase.getInstance().getReference("OldEnquire");
        database.child(model.getInquireId()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    SweetAlertDialog dialog= SweetDialog.success(getContext(),"تم إرسال الرد بنجاح");
                    dialog.show();
                    dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            dialog.dismiss();
                        }
                    });
                }
                else
                {
                    SweetAlertDialog dialog= SweetDialog.failed(getContext(),"فشل إرسال الرد");
                    dialog.show();
                    dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            dialog.dismiss();
                        }
                    });
                }
            }
        });
    }
    private void endFragment()
    {
        mBinding.end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(AdminAnswerComplaint.this).commit();
            }
        });
    }
}