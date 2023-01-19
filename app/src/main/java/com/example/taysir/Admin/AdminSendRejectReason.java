package com.example.taysir.Admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taysir.R;
import com.example.taysir.SweetDialog;
import com.example.taysir.databinding.FragmentAdminSendRejectReasonBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class AdminSendRejectReason extends Fragment {
    private FragmentAdminSendRejectReasonBinding mBinding;
    private String BId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentAdminSendRejectReasonBinding.inflate(inflater,container,false);
        BId=getArguments().getString("BID");
        endFragment();
        sendRejectReason();
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
    private void sendRejectReason()
    {
        mBinding.adminSendRejectReason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reason=mBinding.edittextRejectReason.getText().toString();
                if (TextUtils.isEmpty(reason))
                {
                    mBinding.edittextRejectReason.setError("من فضلك قم بإضافة سبب الرفض");
                }
                else {
                    setRejectReason(reason);
                }
            }
        });
    }
    private void setRejectReason(String reason)
    {
        DatabaseReference database= FirebaseDatabase.getInstance().getReference("BrokerRejectReason");
        database.child(BId).setValue(reason).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    updateStatus();
                }
                else
                {
                    SweetAlertDialog dialog= SweetDialog.failed(getContext(),"فشل الإرسال");
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
    private void updateStatus()
    {
        DatabaseReference database= FirebaseDatabase.getInstance().getReference("Brokers");
        database.child(BId).child("status").setValue("rejected").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    SweetAlertDialog dialog= SweetDialog.success(getContext(),"تم إرسال سبب الرفض");
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
                    SweetAlertDialog dialog= SweetDialog.failed(getContext(),"فشل الإرسال");
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

}
