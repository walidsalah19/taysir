package com.app.taysir.Customer.Offers;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.taysir.Customer.Adapters.CustomerGetBrokerRating;
import com.app.taysir.Models.RatingModel;
import com.app.taysir.R;
import com.app.taysir.SweetDialog;
import com.app.taysir.databinding.FragmentBrokerRanksBinding;
import com.app.taysir.databinding.FragmentOfferNotificationBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BrokerRanks extends Fragment {

    private FragmentBrokerRanksBinding mBinding;
    private SweetAlertDialog loading;
    private String brokerId;
    private DatabaseReference brokerDatabase;
    private ArrayList<RatingModel> rating;
    private CustomerGetBrokerRating adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= FragmentBrokerRanksBinding.inflate(inflater,container,false);
        brokerDatabase= FirebaseDatabase.getInstance().getReference();
        brokerId=getArguments().getString("id");
        startLoading();
        recyclerViewComponent();
        back();
        return mBinding.getRoot();
    }
    private void startLoading()
    {
        loading= SweetDialog.loading(getContext());
        loading.show();
    }
    private void recyclerViewComponent()
    {
        rating=new ArrayList<>();
        adapter=new CustomerGetBrokerRating(rating);
        mBinding.brokerRating.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.brokerRating.setAdapter(adapter);
        getRating();
    }
    private void getRating()
    {

        brokerDatabase.child("rating").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data:snapshot.getChildren())
                {
                    String id=data.child("bid").getValue().toString();
                    if(id.equals(brokerId))
                    {
                        String customerName=data.child("customerName").getValue().toString();
                        String ratingText=data.child("ratingText").getValue().toString();
                        String ratingNum=data.child("ratingNum").getValue().toString();
                        String brokerName=data.child("brokerName").getValue().toString();
                        RatingModel ratingModel=new RatingModel(id,brokerName,customerName,ratingText,Integer.parseInt(ratingNum));
                        rating.add(ratingModel);
                    }
                }
                adapter.notifyDataSetChanged();
                loading.dismiss();
                if (rating.isEmpty())
                {
                    funFailed("لا توجد تقييمات");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                NavHostFragment.findNavController(BrokerRanks.this)
                        .navigate(R.id.goToshowOffers);
            }
        });
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(BrokerRanks.this)
                        .navigate(R.id.goToshowOffers);
            }
        });
    }
}