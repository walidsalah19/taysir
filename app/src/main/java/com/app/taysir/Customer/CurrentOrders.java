package com.app.taysir.Customer;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.app.taysir.Models.AcceptedOrdersModel;
import com.app.taysir.Models.OrderDetailsModel;
import com.app.taysir.R;
import com.app.taysir.SweetDialog;
import com.app.taysir.databinding.FragmentCurrentOrdersBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CurrentOrders extends Fragment {

    private FragmentCurrentOrdersBinding mBinding;
    private String orderId;
    private DatabaseReference orderDatabase;
    private SweetAlertDialog loading;
    private ArrayList<OrderDetailsModel> orderDetails;
    private  AcceptedOrdersModel newOrderModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= FragmentCurrentOrdersBinding.inflate(inflater,container,false);
        orderId=getArguments().getString("orderId");
        orderDatabase= FirebaseDatabase.getInstance().getReference("currentOrders");
        startLoading();
        back();
        showOrderDetails();
        goToChat();
        getOrderData();
        return mBinding.getRoot();
    }
    private void startLoading()
    {
        loading= SweetDialog.loading(getContext());
        loading.show();
    }
    private void getOrderData()
    {
        orderDetails=new ArrayList<>();
        orderDatabase.child(orderId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                        orderDetails.clear();
                        String WebSitLink = snapshot.child("webSitLink").getValue().toString();
                        String WebSitName = snapshot.child("webSitName").getValue().toString();
                        String clintId = snapshot.child("clintId").getValue().toString();
                        String orderId = snapshot.child("orderId").getValue().toString();
                        String orderStat = snapshot.child("orderStat").getValue().toString();
                        String OrderDate = snapshot.child("orderDate").getValue().toString();
                        String clintName = snapshot.child("clintName").getValue().toString();
                        String clintLocation = snapshot.child("clintLocation").getValue().toString();
                        String brokerId = snapshot.child("brokerId").getValue().toString();
                        String totalCost = snapshot.child("totalCost").getValue().toString();
                        String rating = snapshot.child("rating").getValue().toString();
                        String brokerName = snapshot.child("brokerName").getValue().toString();
                        int orderNum = Integer.parseInt(snapshot.child("orderNum").getValue().toString());
                        for (DataSnapshot snap2 : snapshot.child("orderDetails").getChildren()) {
                            String productLink = snap2.child("productLink").getValue().toString();
                            String productColor = snap2.child("productColor").getValue().toString();
                            String productPhoto = snap2.child("productPhoto").getValue().toString();
                            String productNotes = snap2.child("productNotes").getValue().toString();
                            String productQuantity = snap2.child("productQuantity").getValue().toString();
                            String productCode = snap2.child("productCode").getValue().toString();
                            String productSize = snap2.child("productSize").getValue().toString();
                            String productCost = snap2.child("productCost").getValue().toString();

                            OrderDetailsModel detailsModel = new OrderDetailsModel(productLink, productColor, productPhoto, productNotes, Integer.parseInt(productQuantity),
                                    Integer.parseInt(productCode), Integer.parseInt(productSize), Float.parseFloat(productCost));

                            orderDetails.add(detailsModel);
                        }
                        newOrderModel = new AcceptedOrdersModel(WebSitLink, WebSitName, clintId, clintName, clintLocation, orderId,
                                orderStat, OrderDate, orderNum, orderDetails, brokerId, rating,brokerName,Float.parseFloat(totalCost));
                    addDataToView();
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void addDataToView()
    {
        mBinding.orderNumber.setText(newOrderModel.getOrderNum()+"");
        mBinding.brokerName.setText(newOrderModel.getBrokerName());
        mBinding.websiteName.setText(newOrderModel.getWebSitName());
        addOrderStatus();
        loading.dismiss();

    }
    @SuppressLint("ResourceAsColor")
    private void addOrderStatus()
    {
        if(newOrderModel.getOrderStat().equals("payed"))
        {

        }
        else if(newOrderModel.getOrderStat().equals("charge"))
        {
            Glide.with(CurrentOrders.this).load(R.drawable.thumb).centerCrop().into(mBinding.image2);
            mBinding.view2.setBackgroundResource(R.color.Red_Orange);
        }
        else if (newOrderModel.getOrderStat().equals("arrived"))
        {
            Glide.with(CurrentOrders.this).load(R.drawable.thumb).centerCrop().into(mBinding.image1);
            mBinding.view1.setBackgroundResource(R.color.Red_Orange);
        }
        loading.dismiss();
    }
    private void goToChat()
    {
        mBinding.goToChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b=new Bundle();
                b.putString("id",newOrderModel.getBrokerId());
                b.putString("name",newOrderModel.getBrokerName());
                NavHostFragment.findNavController(CurrentOrders.this)
                        .navigate(R.id.goToChat,b);
            }
        });
    }
    private void showOrderDetails()
    {
        mBinding.showOrderDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b=new Bundle();
                b.putString("orderType","current");
                b.putString("orderId",newOrderModel.getOrderId());
                NavHostFragment.findNavController(CurrentOrders.this)
                        .navigate(R.id.goTopreviousOrdersDetails,b);
            }
        });
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(CurrentOrders.this)
                        .navigate(R.id.goToHome);
            }
        });
    }
}