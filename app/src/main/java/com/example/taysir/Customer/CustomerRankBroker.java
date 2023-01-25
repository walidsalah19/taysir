package com.example.taysir.Customer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.taysir.Admin.AdminSendRejectReason;
import com.example.taysir.Models.RatingModel;
import com.example.taysir.R;
import com.example.taysir.SweetDialog;
import com.example.taysir.databinding.FragmentCustomerRankBrokerBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CustomerRankBroker extends Fragment {

    private FragmentCustomerRankBrokerBinding mBinding;
    private String brokerId,customerName,brokerName,orderId;
    private DatabaseReference orderDatabase;
    private SweetAlertDialog loading;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentCustomerRankBrokerBinding.inflate(inflater,container,false);
        orderDatabase= FirebaseDatabase.getInstance().getReference();
        brokerId=getArguments().getString("brokerId");
        brokerName=getArguments().getString("brokerName");
        customerName=getArguments().getString("customerName");
        orderId=getArguments().getString("orderId");

        rating();
        endFragment();
        return mBinding.getRoot();
    }
    private void startLoading()
    {
        loading= SweetDialog.loading(getContext());
        loading.show();
    }
    private void rating()
    {
        mBinding.btRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBinding.rating.getRating()==0)
                {
                    Toast.makeText(getContext(), "قم بإضافة تقييم", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty( mBinding.edittextComment.getText().toString()))
                {
                    mBinding.edittextComment.setError("قم بإضافة تقييم");
                }
                else
                {
                    startLoading();
                    sendToDatabese();
                }
            }
        });
    }

    private void sendToDatabese() {
        RatingModel rating=new RatingModel(brokerId,brokerName,customerName,mBinding.edittextComment.getText().toString(), (int) mBinding.rating.getRating());
        orderDatabase.child("oldOrders").child(orderId).child("rating").setValue(rating.getRatingNum());
        orderDatabase.child("rating").push().setValue(rating).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                loading.dismiss();
                if (task.isSuccessful())
                {

                    funSuccessfully("تم التقييم بنجاح");
                }
                else
                {
                  funFailed("فشل التقييم");
                }
            }
        });
    }


    private void funSuccessfully(String title)
    {
        SweetAlertDialog success= SweetDialog.success(getContext(),title);
        success.show();
        success.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                success.dismiss();
            }
        });
    }
    private void funFailed(String title)
    {
        SweetAlertDialog fail=SweetDialog.failed(getContext(),title);
        fail.show();
        fail.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                fail.dismiss();
            }
        });
    }
    private void endFragment()
    {
        mBinding.end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(CustomerRankBroker.this).commit();
            }
        });
    }

}