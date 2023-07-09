package com.app.taysir.Admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.taysir.Models.OldComplaintModel;
import com.app.taysir.R;
import com.app.taysir.SweetDialog;
import com.app.taysir.databinding.FragmentAdminAnswerComplaintBinding;
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
        database.child(model.getUserId()).child(model.getInquireId()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
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
                            deleteFromNewInquire(model.getInquireId());
                            end();
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
                           end();
                        }
                    });
                }
            }
        });
    }
    private void deleteFromNewInquire(String id)
    {
        DatabaseReference database=FirebaseDatabase.getInstance().getReference("newEnquire");
        database.child(id).removeValue();
    }
    private void endFragment()
    {
        mBinding.end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     end();
            }
        });
    }
    private void end()
    {
        getActivity().getSupportFragmentManager().beginTransaction().remove(AdminAnswerComplaint.this).commit();
    }
}