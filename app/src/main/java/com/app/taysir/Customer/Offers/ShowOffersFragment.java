package com.app.taysir.Customer.Offers;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.taysir.Customer.Adapters.CustomerViewOffersAdapter;
import com.app.taysir.Models.OfferModel;
import com.app.taysir.R;
import com.app.taysir.databinding.FragmentCreateNewOfferBinding;
import com.app.taysir.databinding.FragmentShowOffersBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowOffersFragment extends Fragment {
   private FragmentShowOffersBinding mBinding;
    private DatabaseReference offerDatabase;
    private ArrayList<OfferModel>offerModel;
    private CustomerViewOffersAdapter adapter;
    private  String userId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= FragmentShowOffersBinding.inflate(inflater,container,false);
        back();
        userId= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        recyclerViewComponent();
        rejectOffer();
        return mBinding.getRoot();
}
    private void recyclerViewComponent()
    {
        offerModel=new ArrayList<>();
        adapter=new CustomerViewOffersAdapter(offerModel,this);
        mBinding.customerViewOffers.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.customerViewOffers.setAdapter(adapter);
        getNewOffers();
    }
    private void rejectOffer()
    {
        adapter.setOnItemClickListener(new CustomerViewOffersAdapter.OnItemClickListener() {
            @Override
            public void onItemDelete(String OfferId) {
                removeOffer( OfferId);
            }
        });
    }
    private void removeOffer(String id)
    {
        offerDatabase.child(id).removeValue();
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
                            OfferModel model = new OfferModel(offerId,brokerId, brokerName, clintId, orderId, orderDate, Float.parseFloat(totalCost)
                                    , Float.parseFloat(orderCost), Float.parseFloat(commission));
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
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(ShowOffersFragment.this)
                        .navigate(R.id.goToHome);
            }
        });
    }
}