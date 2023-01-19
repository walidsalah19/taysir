package com.example.taysir.Broker;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.taysir.Models.BrokerModel;
import com.example.taysir.R;
import com.example.taysir.SweetDialog;
import com.example.taysir.databinding.FragmentBrokerOrderDetailsBinding;
import com.example.taysir.databinding.FragmentBrokerProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BrokerProfile extends Fragment {
    private FragmentBrokerProfileBinding mBinding;
    private SweetAlertDialog loading;
    private  BrokerModel broker;
    private String userId;
    private DatabaseReference brokerDatabase;
    private FirebaseAuth auth;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= FragmentBrokerProfileBinding.inflate(inflater,container,false);
        startLoading();
        initFirebaseTool();
        getBrokerData();
        back();
        updateAccount();
        cancel();
        return mBinding.getRoot();
    }
    private void cancel()
    {
        mBinding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavHostFragment.findNavController(BrokerProfile.this)
                        .navigate(R.id.goToBrokerPersonly);
            }
        });
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavHostFragment.findNavController(BrokerProfile.this)
                        .navigate(R.id.goToBrokerPersonly);
            }
        });
    }
    private void initFirebaseTool()
    {
        brokerDatabase= FirebaseDatabase.getInstance().getReference("Brokers");
        auth=FirebaseAuth.getInstance();
        userId=auth.getCurrentUser().getUid().toString();
    }
    private void startLoading()
    {
        loading= SweetDialog.loading(getContext());
        loading.show();
    }
    private void getBrokerData()
    {
        brokerDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                        String Status = snapshot.child("status").getValue().toString();
                            String BID = snapshot.child("bid").getValue().toString();
                            String UserName = snapshot.child("userName").getValue().toString();
                            String FullName = snapshot.child("fullName").getValue().toString();
                            String Email = snapshot.child("email").getValue().toString();
                            String PhoneNum = snapshot.child("phoneNum").getValue().toString();
                            String Gender = snapshot.child("gender").getValue().toString();
                            String DOB = snapshot.child("dob").getValue().toString();
                            int NID = Integer.parseInt(snapshot.child("nid").getValue().toString());
                            int MaroOfNum = Integer.parseInt(snapshot.child("maroOfNum").getValue().toString());
                            String FreeWorkDocumentCode = snapshot.child("freeWorkDocumentCode").getValue().toString();
                            broker = new BrokerModel(BID, UserName, FullName, Email, PhoneNum, Gender, Status, DOB, NID, MaroOfNum, FreeWorkDocumentCode);
                            insertDataToView();
                            }
                    }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void insertDataToView()
    {
        mBinding.name.setText(broker.getUserName());
        mBinding.email.setText(broker.getEmail());
        mBinding.edittextUserName.setText(broker.getUserName());
        mBinding.edittextEmail.setText(broker.getEmail());
        mBinding.edittextUserNameAr.setText(broker.getFullName());
        mBinding.edittextId.setText(broker.getNID()+"");
        mBinding.edittextMaroOfNum.setText(broker.getMaroOfNum()+"");
        mBinding.FreeWorkDocumentCode.setText(broker.getFreeWorkDocumentCode());
        mBinding.edittextGender.getEditText().setText(broker.getGender());
        loading.dismiss();
    }
    private void updateAccount()
    {
        mBinding.btnUpdateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEnteredData();
            }
        });
    }

    private void checkEnteredData() {
        String userName= mBinding.edittextUserName.getText().toString();
        String email= mBinding.edittextEmail.getText().toString();
        String fullName= mBinding.edittextUserNameAr.getText().toString();
        String gender= mBinding.genderSpinner.getText().toString();
        String phoneNum="12345678";
        String bob="1/1/2000";
        String NID=mBinding.edittextId.getText().toString();
        String MaroOfNum= mBinding.edittextMaroOfNum.getText().toString();
        String FreeWorkDocumentCode=mBinding.FreeWorkDocumentCode.getText().toString();
        if (TextUtils.isEmpty(userName))
        {
            mBinding.edittextUserName.setError("قم بإدخال اسم المستخدم");
        }

        else if (TextUtils.isEmpty(email))
        {
            mBinding.edittextEmail.setError("قم بإدخال البريد الإلكتروني بشكل صحيح");
        }
        else if (TextUtils.isEmpty(fullName))
        {
            mBinding.edittextUserNameAr.setError("قم بإدخال اسم كامل ");
        }
        else if (TextUtils.isEmpty(gender)||gender.equals("الجنس"))
        {
            Toast.makeText(getActivity(), "قم بإختيار الجنس ", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(NID))
        {
            mBinding.edittextId.setError("قم بإدخال اسم المستخدم");
        }
        else if (TextUtils.isEmpty(MaroOfNum) && (MaroOfNum.length()<=6 &&MaroOfNum.length()>=5))
        {
            mBinding.edittextMaroOfNum.setError("قم بأدخل رقم معروف مكون من 5 الي 6 أرقام");
        }
        else if (TextUtils.isEmpty(FreeWorkDocumentCode))
        {
            mBinding.FreeWorkDocumentCode.setError("قم بإدخال رقم وثيقة العمل الحر ");
        }
        else if (FreeWorkDocumentCode.length() !=8)
        {
            mBinding.FreeWorkDocumentCode.setError("قم بإدخال رقم وثيقة العمل الحر مكون من 8 حروف وأرقام");
        }
        else {
            startLoading();
            BrokerModel broker=new BrokerModel("234567",userName,fullName,email,phoneNum,gender,"unKnown",bob,Integer.parseInt(NID),Integer.parseInt(MaroOfNum),FreeWorkDocumentCode);
            updateDatabase(broker);
        }
    }
    private void updateDatabase(BrokerModel broker)
    {
        DatabaseReference database= FirebaseDatabase.getInstance().getReference("Brokers");
        database.child(userId).setValue(broker).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                loading.dismiss();
                if (task.isSuccessful())
                {
                    SweetAlertDialog dialog= SweetDialog.success(getContext(),"تم تحديث البيانات بنجاح");
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
                    SweetAlertDialog dialog= SweetDialog.failed(getContext(),"فشل التحديث");
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