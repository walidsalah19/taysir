package com.example.taysir.Broker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taysir.Broker.Adapter.BrokerAcceptedOrderAdapter;
import com.example.taysir.Broker.Adapter.BrokerCurrentOrderDetailsAdapter;
import com.example.taysir.Models.OrderDetailsModel;
import com.example.taysir.R;
import com.example.taysir.SweetDialog;
import com.example.taysir.databinding.FragmentBrokerOrderDetailsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class BrokerCurrentOrderDetails extends Fragment {
    private FragmentBrokerOrderDetailsBinding mBinding;
    private String orderId;
    private DatabaseReference orderDatabase;
    private ArrayList<OrderDetailsModel>orderDetails;
    private BrokerCurrentOrderDetailsAdapter adapter;
    private SweetAlertDialog loading;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentBrokerOrderDetailsBinding.inflate(inflater,container,false);
        orderId=getArguments().getString("orderId");
        orderDatabase= FirebaseDatabase.getInstance().getReference("currentOrders");
        back();
        startLoading();
        recyclerViewComponent();
        return mBinding.getRoot();
    }
    private void startLoading()
    {
        loading= SweetDialog.loading(getContext());
        loading.show();
    }
    private void recyclerViewComponent()
    {
        orderDetails=new ArrayList<>();
        adapter=new BrokerCurrentOrderDetailsAdapter(orderDetails,this);
        mBinding.currentOrderDetails.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.currentOrderDetails.setAdapter(adapter);
        getNewOrders();
    }

    private void getNewOrders() {
        orderDatabase.child(orderId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                        orderDetails.clear();
                            String WebSitName = snapshot.child("webSitName").getValue().toString();
                            String OrderDate = snapshot.child("orderDate").getValue().toString();
                            String clintName = snapshot.child("clintName").getValue().toString();
                            String clintLocation = snapshot.child("clintLocation").getValue().toString();
                            String TotalCost = snapshot.child("totalCost").getValue().toString();

                            int orderNum = Integer.parseInt(snapshot.child("orderNum").getValue().toString());
                            for (DataSnapshot snap2 : snapshot.getChildren()) {
                                String productLink = snap2.child("productLink").getValue().toString();
                                String productColor = snap2.child("productColor").getValue().toString();
                                String productPhoto = snap2.child("productPhoto").getValue().toString();
                                String productNotes = snap2.child("productNotes").getValue().toString();
                                String productQuantity = snap2.child("productQuantity").getValue().toString();
                                String productCode = snap2.child("productCode").getValue().toString();
                                String productSize = snap2.child("productSize").getValue().toString();
                                OrderDetailsModel detailsModel = new OrderDetailsModel(productLink, productColor, productPhoto, productNotes, Integer.parseInt(productQuantity),
                                        Integer.parseInt(productCode), Integer.parseInt(productSize));
                                orderDetails.add(detailsModel);
                            }
                            adapter.notifyDataSetChanged();
                            loading.dismiss();
                            addToView(WebSitName,OrderDate,clintName,clintLocation,TotalCost,orderNum);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void addToView(String webSitName, String orderDate, String clintName, String clintLocation, String totalCost, int orderNum)
    {
        mBinding.orderNumber.setText(orderNum+"");
        mBinding.clintName.setText(clintName);
        mBinding.clintLocation.setText(clintLocation);
        mBinding.orderDate.setText(orderDate);
        mBinding.totalCost.setText(totalCost);
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    NavHostFragment.findNavController(BrokerCurrentOrderDetails.this)
                            .navigate(R.id.currentOrders);
            }
        });
    }
}