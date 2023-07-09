package com.app.taysir.Broker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.taysir.Models.OfferModel;
import com.app.taysir.R;
import com.app.taysir.SweetDialog;
import com.app.taysir.databinding.FragmentBrokerSendOfferBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BrokerSendOffer extends Fragment {
     private FragmentBrokerSendOfferBinding mBinding;

     private DatabaseReference database;
     private String brokerId,clintId,orderId;
     private String brokerName;
     private float orderCost;
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
        orderCost=getArguments().getFloat("orderCost");
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
        String offerId= UUID.randomUUID().toString();
        float commission=Float.parseFloat(mBinding.commission.getText().toString());
        float totalCost= orderCost+commission;
        String date=getDateTime();
        OfferModel offerModel=new OfferModel(offerId,brokerId,brokerName,clintId,orderId,date,totalCost,orderCost,commission);
         database.child("offers").child(offerId).setValue(offerModel).addOnCompleteListener(new OnCompleteListener<Void>() {
             @Override
             public void onComplete(@NonNull Task<Void> task) {
                 loading.dismiss();
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
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH.mm", Locale.US);
        Date date = new Date();
        return dateFormat.format(date);
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