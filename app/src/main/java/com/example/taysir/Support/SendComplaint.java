package com.example.taysir.Support;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taysir.Models.NewComplaintModel;
import com.example.taysir.Models.OldComplaintModel;
import com.example.taysir.R;
import com.example.taysir.SweetDialog;
import com.example.taysir.databinding.FragmentSendComplaintBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class SendComplaint extends Fragment {

    private FragmentSendComplaintBinding mBinding;
    private DatabaseReference database;
    private String userId,userName;
    private long complaintNum;
    private SweetAlertDialog loading;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= FragmentSendComplaintBinding.inflate(inflater,container,false);
        database= FirebaseDatabase.getInstance().getReference();
        userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
        clickToSendComplaint();
        back();
        cancel();
        return mBinding.getRoot();
    }
    private void cancel()
    {
        mBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(SendComplaint.this)
                        .navigate(R.id.goTosupportTechnical);
            }
        });
    }
    private void startLoading()
    {
        loading= SweetDialog.loading(getContext());
        loading.show();
    }
    private void clickToSendComplaint()
    {
        mBinding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String complaint=mBinding.edittextComplaint.getText().toString();
                if (TextUtils.isEmpty(complaint))
                {
                    mBinding.edittextComplaint.setError("من فضلك قم بإدخال الشكوى");
                }
                else
                {
                    startLoading();
                    getUserData();
                }
            }
        });
    }
    private void getUserData() {

        database.child("Brokers").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userName=snapshot.child("userName").getValue().toString();
                getComplaintNumber();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getComplaintNumber()
    {
        database.child("inquireNum").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    complaintNum = Integer.parseInt(dataSnapshot.child("number").getValue().toString());
                    addToDatabase();
                }
                else
                {
                    database.child("inquireNum").child("number").setValue("1");
                    complaintNum=1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void addToDatabase()
    {
        String complaintId= UUID.randomUUID().toString();
        String complaint=mBinding.edittextComplaint.getText().toString();
        NewComplaintModel model=new NewComplaintModel(userName,complaint,complaintId,userId, (int) complaintNum);
        database.child("newEnquire").child(model.getInquireId()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    loading.dismiss();
                    database.child("inquireNum").child("number").setValue(complaintNum+"");
                    SweetAlertDialog dialog= SweetDialog.success(getContext(),"تم إرسال الشكوى بنجاح");
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
                    loading.dismiss();
                    SweetAlertDialog dialog= SweetDialog.failed(getContext(),"فشل إرسال الشكوى");
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
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(SendComplaint.this)
                        .navigate(R.id.goTosupportTechnical);
            }
        });
    }
}