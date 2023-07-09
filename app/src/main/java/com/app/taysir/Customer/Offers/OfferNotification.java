package com.app.taysir.Customer.Offers;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.taysir.Customer.Adapters.CustomerViewOfferNotification;
import com.app.taysir.Models.OfferModel;
import com.app.taysir.R;
import com.app.taysir.databinding.FragmentAcceptOffersBinding;
import com.app.taysir.databinding.FragmentOfferNotificationBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OfferNotification extends Fragment {


   private FragmentOfferNotificationBinding mBinding;
    private DatabaseReference offerDatabase;
    private ArrayList<OfferModel>offerModel;
    private CustomerViewOfferNotification adapter;
    private  String userId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= FragmentOfferNotificationBinding.inflate(inflater,container,false);
        userId= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        recyclerViewComponent();
        return mBinding.getRoot();
    }
    private void recyclerViewComponent()
    {
        offerModel=new ArrayList<>();
        adapter=new CustomerViewOfferNotification(offerModel,this);
        mBinding.showOffersNotification.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.showOffersNotification.setAdapter(adapter);
        getNewOffers();
    }

    private void getNewOffers() {
        offerDatabase= FirebaseDatabase.getInstance().getReference("offers");
        offerDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    offerModel.clear();
                    for(DataSnapshot data:snapshot.getChildren()) {
                        String clintId=data.child("clintId").getValue().toString();
                        if (userId.equals(clintId)) {
                            String brokerId = data.child("brokerId").getValue().toString();
                            String brokerName = data.child("brokerName").getValue().toString();
                            String orderId = data.child("orderId").getValue().toString();
                            String orderDate = data.child("orderDate").getValue().toString();
                            String totalCost = data.child("totalCost").getValue().toString();
                            String orderCost = data.child("orderCost").getValue().toString();
                            String commission = data.child("commission").getValue().toString();
                            String offerId = data.child("offerId").getValue().toString();


                            OfferModel model = new OfferModel(offerId,brokerId, brokerName, clintId, orderId, orderDate, Integer.parseInt(totalCost)
                                    , Integer.parseInt(orderCost), Integer.parseInt(commission));
                            offerModel.add(model);
                        }

                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}