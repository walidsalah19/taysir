package com.example.taysir.Broker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taysir.Admin.AdminAnswerComplaint;
import com.example.taysir.Models.OfferModel;
import com.example.taysir.R;
import com.example.taysir.SweetDialog;
import com.example.taysir.databinding.FragmentBrokerSendOfferBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BrokerSendOffer extends Fragment {
     private FragmentBrokerSendOfferBinding mBinding;

     private DatabaseReference database;
     private String brokerId,clintId,orderId;
     private String brokerName;
     private int orderCost;
    private SweetAlertDialog loading;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentBrokerSendOfferBinding.inflate(inflater,container,false);
        database= FirebaseDatabase.getInstance().getReference();
        brokerId= FirebaseAuth.getInstance().getCurrentUser().getUid();
        orderId=getArguments().getString("orderId");
        clintId=getArguments().getString("clintId");
        endFragment();
        clickToSendOffer();
        return mBinding.getRoot();
    }
    private void clickToSendOffer()
    {
        mBinding.sendOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mBinding.commission.getText().toString()))
                {
                    mBinding.commission.setError("من فضلك قم بإدخال العمولة");
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
        database.child("Brokers").child(brokerId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                brokerName=snapshot.child("userName").getValue().toString();
                sendOffer();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendOffer() {
        int commission=Integer.parseInt(mBinding.commission.getText().toString());
        int totalCost= orderCost+commission;
        OfferModel offerModel=new OfferModel(brokerId,brokerName,clintId,orderId,totalCost,orderCost,commission);
         database.child(clintId).child(orderId).setValue(offerModel).addOnCompleteListener(new OnCompleteListener<Void>() {
             @Override
             public void onComplete(@NonNull Task<Void> task) {
                 if (task.isSuccessful())
                 {
                     SweetAlertDialog dialog= SweetDialog.success(getContext(),"تم إرسال العرض بنجاح");
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
                     SweetAlertDialog dialog= SweetDialog.failed(getContext(),"فشل إرسال العرض");
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

    private void startLoading()
    {
        loading= SweetDialog.loading(getContext());
        loading.show();
    }
    private void endFragment()
    {
        mBinding.end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(BrokerSendOffer.this).commit();
            }
        });
    }
}