package com.app.taysir.Broker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.taysir.Broker.Adapter.BrokerRatingAdapter;
import com.app.taysir.Models.RatingModel;
import com.app.taysir.R;
import com.app.taysir.SweetDialog;
import com.app.taysir.databinding.FragmentBrokerViewRatingBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BrokerViewRating extends Fragment {

   private FragmentBrokerViewRatingBinding mBinding;
    private ArrayList<RatingModel>rating;
    private BrokerRatingAdapter adapter;
    private String userId;
    private SweetAlertDialog loading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentBrokerViewRatingBinding.inflate(inflater,container,false);
        userId= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        Log.e("rate",userId);

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
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavHostFragment.findNavController(BrokerViewRating.this)
                        .navigate(R.id.goToHome);
            }
        });
    }
    private void recyclerViewComponent()
    {
        rating=new ArrayList<>();
        adapter=new BrokerRatingAdapter(rating);
        mBinding.brokerRating.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.brokerRating.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        getRating();
    }
    private void getRating()
    {
        DatabaseReference rate= FirebaseDatabase.getInstance().getReference("rating");
        rate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("rate4","4");
                if (snapshot.exists()) {
                    Log.e("rate4","4");

                    rating.clear();
                    for (DataSnapshot data : snapshot.getChildren()) {
                        String id = data.child("bid").getValue().toString();
                        Log.e("rate3",id);
                        if (id.equals(userId)) {
                            Log.e("rate1",id);
                            String customerName = data.child("customerName").getValue().toString();
                            String ratingText = data.child("ratingText").getValue().toString();
                            String ratingNum = data.child("ratingNum").getValue().toString();
                            String brokerName = data.child("brokerName").getValue().toString();
                            RatingModel ratingModel = new RatingModel(id, brokerName, customerName, ratingText, Integer.parseInt(ratingNum));
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void funFailed(String title)
    {
        SweetAlertDialog fail= SweetDialog.failed(getContext(),title);
        fail.show();
        fail.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                fail.dismiss();
                NavHostFragment.findNavController(BrokerViewRating.this)
                        .navigate(R.id.goToHome);
            }
        });
    }
}