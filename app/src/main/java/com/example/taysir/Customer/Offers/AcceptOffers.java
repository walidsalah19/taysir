package com.example.taysir.Customer.Offers;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.taysir.Models.OfferModel;
import com.example.taysir.R;
import com.example.taysir.databinding.FragmentAcceptOffersBinding;
import com.example.taysir.databinding.FragmentPaymentBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AcceptOffers extends Fragment {

    private FragmentAcceptOffersBinding mBinding;
    private DatabaseReference offerDatabase;
    private OfferModel model;
    private String orderId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= FragmentAcceptOffersBinding.inflate(inflater,container,false);
        orderId=getArguments().getString("id");
        back();
        accept();
        reject();
        getNewOffers();
        return mBinding.getRoot();
    }
    private void getNewOffers() {
        offerDatabase= FirebaseDatabase.getInstance().getReference("offers");
        offerDatabase.child(orderId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    for(DataSnapshot data:snapshot.getChildren()) {
                            String clintId=data.child("clintId").getValue().toString();
                            String brokerId = data.child("brokerId").getValue().toString();
                            String brokerName = data.child("brokerName").getValue().toString();
                            String orderId = data.child("orderId").getValue().toString();
                            String orderDate = data.child("orderDate").getValue().toString();
                            String totalCost = data.child("totalCost").getValue().toString();
                            String orderCost = data.child("orderCost").getValue().toString();
                            String commission = data.child("commission").getValue().toString();
                            String offerId=data.child("offerId").getValue().toString();
                            model = new OfferModel(offerId,brokerId, brokerName, clintId, orderId, orderDate, Integer.parseInt(totalCost)
                                    , Integer.parseInt(orderCost), Integer.parseInt(commission));
                            addToView(model);
                        }

                    }
                }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void addToView(OfferModel model)
    {
        mBinding.brokerName.setText(model.getBrokerName());
        mBinding.cost.setText(model.getOrderCost());
        mBinding.commission.setText(model.getCommission());
        mBinding.totalCost.setText(model.getTotalCost());

    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(AcceptOffers.this)
                        .navigate(R.id.goToshowOffers);
            }
        });
    }
    private void accept()
    {
        mBinding.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b=new Bundle();
                b.putString("id", model.getOfferId());
                b.putString("orderId", model.getOrderId());
                NavHostFragment.findNavController(AcceptOffers.this)
                        .navigate(R.id.goToselectPayment,b);
            }
        });
    }
    private void reject()
    {
        mBinding.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference offerDatabase= FirebaseDatabase.getInstance().getReference("offers");
                offerDatabase.child(model.getOfferId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                   if (task.isSuccessful())
                   {
                       NavHostFragment.findNavController(AcceptOffers.this)
                               .navigate(R.id.goToshowOffers);
                   }
                    }
                });
            }
        });
    }
}