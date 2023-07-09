package com.app.taysir.Broker;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.app.taysir.Models.BrokerModel;
import com.app.taysir.R;
import com.app.taysir.SweetDialog;
import com.app.taysir.databinding.FragmentBrokerProfileBinding;
import com.app.taysir.databinding.FragmentBrokerProfilePersonlyBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BrokerProfilePersonly extends Fragment {
   private FragmentBrokerProfilePersonlyBinding mBinding;
   private String userId;
   private DatabaseReference brokerDatabase;
   private FirebaseAuth auth;
   private BrokerModel broker;
   private SweetAlertDialog loading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= FragmentBrokerProfilePersonlyBinding.inflate(inflater,container,false);
        startLoading();
        initFirebaseTool();
        getBrokerData();
        back();
        editProfile();
        showRejectReason();
        removeNotification();
        goToRating();
        return mBinding.getRoot();
    }

    private void goToRating() {
        mBinding.card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(BrokerProfilePersonly.this)
                        .navigate(R.id.goToRating);
            }
        });
    }

    private void removeNotification()
    {
        mBinding.home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mBinding.FrameLayout.setVisibility(View.INVISIBLE);
            }
        });
    }
    private void startLoading()
    {
        loading= SweetDialog.loading(getContext());
        loading.show();
    }
    private void getBrokerData()
    {
        brokerDatabase.child("Brokers").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
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
                    addRequestStatus();
                    addPersonalInfo();
                    loading.dismiss();
                }
                }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void addPersonalInfo()
    {
        mBinding.name.setText(broker.getUserName());
        mBinding.email.setText(broker.getEmail());
    }
    @SuppressLint("ResourceAsColor")
    private void addRequestStatus()
    {
        if(broker.getStatus().equals("unKnown"))
        {

        }
        else if(broker.getStatus().equals("accepted"))
        {
            Glide.with(BrokerProfilePersonly.this).load(R.drawable.thumb).centerCrop().into(mBinding.image1);
            mBinding.view1.setBackgroundResource(R.color.Red_Orange);
            mBinding.text1.setText("تم قبولك");
        }
        else
        {
            Glide.with(BrokerProfilePersonly.this).load(R.drawable.thumb).centerCrop().into(mBinding.image1);
            mBinding.view1.setBackgroundColor(R.color.Red_Orange);
            mBinding.text1.setText("تم رفضك");
        }
    }
    private void initFirebaseTool()
    {
        brokerDatabase=FirebaseDatabase.getInstance().getReference();
        auth=FirebaseAuth.getInstance();
        userId=auth.getCurrentUser().getUid().toString();
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavHostFragment.findNavController(BrokerProfilePersonly.this)
                        .navigate(R.id.goToHome);
            }
        });
    }
    private void editProfile()
    {
        mBinding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavHostFragment.findNavController(BrokerProfilePersonly.this)
                        .navigate(R.id.goToBrokerProfile);
            }
        });
    }
    private void showRejectReason()
    {
        mBinding.showRejectReason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.FrameLayout.setVisibility(View.VISIBLE);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.FrameLayout,new StatusNotification()).commit();
            }
        });
    }

}