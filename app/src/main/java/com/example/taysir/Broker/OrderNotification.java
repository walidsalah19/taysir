package com.example.taysir.Broker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taysir.Broker.Adapter.BrokerOrderNotificationAdapter;
import com.example.taysir.Models.OrderDetailsModel;
import com.example.taysir.Models.NewOrderModel;
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
    private DatabaseReference Database;
    private ArrayList<NewOrderModel>order;
    private ArrayList<OrderDetailsModel>orderDetails;
    private BrokerOrderNotificationAdapter adapter;
    private String userId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentOrderNotificationBinding.inflate(inflater,container,false);
        Database= FirebaseDatabase.getInstance().getReference();
        userId= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        checkAccepted();
        return mBinding.getRoot();
    }
    private void checkAccepted()
    {
        Database.child("Brokers").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    String status=snapshot.child("status").getValue().toString();
                    if (status.equals("accepted"))
                    {
                        recyclerViewComponent();
                    }
                    else
                    {
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
        Database.child("newOrders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    order.clear();
                    for(DataSnapshot data:snapshot.getChildren()) {
                        orderDetails.clear();
                        String WebSitLink=data.child("webSitLink").getValue().toString();
                        String WebSitName=data.child("webSitName").getValue().toString();
                        String clintId=data.child("clintId").getValue().toString();
                        String orderId=data.child("orderId").getValue().toString();
                        String orderStat=data.child("orderStat").getValue().toString();
                        String OrderDate=data.child("orderDate").getValue().toString();
                        String clintName=data.child("clintName").getValue().toString();
                        String clintLocation=data.child("clintLocation").getValue().toString();

                        int orderNum=Integer.parseInt(data.child("orderNum").getValue().toString());
                        for(DataSnapshot snap2:data.child("orderDetails").getChildren()) {
                            String productLink = snap2.child("productLink").getValue().toString();
                            String productColor = snap2.child("productColor").getValue().toString();
                            String productPhoto = snap2.child("productPhoto").getValue().toString();
                            String productNotes = snap2.child("productNotes").getValue().toString();
                            String productQuantity = snap2.child("productQuantity").getValue().toString();
                            String productCode = snap2.child("productCode").getValue().toString();
                            String productSize = snap2.child("productSize").getValue().toString();
                            String productCost = snap2.child("productCost").getValue().toString();

                            OrderDetailsModel detailsModel = new OrderDetailsModel(productLink, productColor, productPhoto, productNotes, Integer.parseInt(productQuantity),
                                    Integer.parseInt(productCode), Integer.parseInt(productSize),Float.parseFloat(productCost));

                            orderDetails.add(detailsModel);
                        }
                        NewOrderModel newOrderModel =new NewOrderModel(WebSitLink,WebSitName,clintId,clintName,clintLocation,orderId,orderStat,OrderDate,orderNum,orderDetails);
                        order.add(newOrderModel);
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