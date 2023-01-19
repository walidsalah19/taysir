package com.example.taysir.Broker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taysir.Broker.Adapter.BrokerOrderNotificationAdapter;
import com.example.taysir.Broker.Adapter.BrokerRatingAdapter;
import com.example.taysir.Customer.Offers.OfferNotification;
import com.example.taysir.Models.OrderDetailsModel;
import com.example.taysir.Models.OrderModel;
import com.example.taysir.Models.RatingModel;
import com.example.taysir.R;
import com.example.taysir.databinding.FragmentOrderNotificationBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderNotification extends Fragment {

  private FragmentOrderNotificationBinding mBinding;
    private DatabaseReference orderDatabase;
    private ArrayList<OrderModel>order;
    private ArrayList<OrderDetailsModel>orderDetails;
    private BrokerOrderNotificationAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentOrderNotificationBinding.inflate(inflater,container,false);
        orderDatabase= FirebaseDatabase.getInstance().getReference("newOrders");
        recyclerViewComponent();
        return mBinding.getRoot();
    }
    private void recyclerViewComponent()
    {
        order=new ArrayList<>();
        orderDetails=new ArrayList<>();
        adapter=new BrokerOrderNotificationAdapter (order,this);
        mBinding.BrokerNewOrders.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.BrokerNewOrders.setAdapter(adapter);
        getNewOrders();
    }

    private void getNewOrders() {
        orderDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    order.clear();
                    for(DataSnapshot data:snapshot.getChildren()) {
                        orderDetails.clear();
                        String WebSitLink=data.child("webSitLink").getValue().toString();
                        String WebSitName=data.child("webSitName").getValue().toString();
                        String userId=data.child("userId").getValue().toString();
                        String orderId=data.child("orderId").getValue().toString();
                        String orderStat=data.child("orderStat").getValue().toString();
                        String OrderDate=data.child("orderDate").getValue().toString();
                        String userName=data.child("userName").getValue().toString();

                        int orderNum=Integer.parseInt(data.child("orderNum").getValue().toString());
                        for(DataSnapshot snap2:data.getChildren()) {
                            String productLink = data.child("productLink").getValue().toString();
                            String productColor = data.child("productColor").getValue().toString();
                            String productPhoto = data.child("productPhoto").getValue().toString();
                            String productNotes = data.child("productNotes").getValue().toString();
                            String productQuantity = data.child("productQuantity").getValue().toString();
                            String productCode = data.child("productCode").getValue().toString();
                            String productSize = data.child("productSize").getValue().toString();
                            OrderDetailsModel detailsModel=new OrderDetailsModel(productLink,productColor,productPhoto,productNotes,Integer.parseInt(productQuantity),
                                    Integer.parseInt(productCode),Integer.parseInt(productSize));
                            orderDetails.add(detailsModel);
                        }
                        OrderModel orderModel=new OrderModel(WebSitLink,WebSitName,userId,userName,orderId,orderStat,OrderDate,orderNum,orderDetails);
                        order.add(orderModel);
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