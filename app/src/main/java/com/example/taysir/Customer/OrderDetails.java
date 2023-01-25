package com.example.taysir.Customer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taysir.Broker.Adapter.BrokerCurrentOrderDetailsAdapter;
import com.example.taysir.Models.AcceptedOrdersModel;
import com.example.taysir.Models.OrderDetailsModel;
import com.example.taysir.R;
import com.example.taysir.SweetDialog;
import com.example.taysir.databinding.FragmentOrderDetailsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class OrderDetails extends Fragment {
    private FragmentOrderDetailsBinding mBinding;
    private String orderType,orderId;
    private DatabaseReference orderDatabase;
    private SweetAlertDialog loading;
    private ArrayList<OrderDetailsModel> orderDetails;
    private  AcceptedOrdersModel newOrderModel;
    private BrokerCurrentOrderDetailsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentOrderDetailsBinding.inflate(inflater,container,false);
        orderType=getArguments().getString("orderType");
        orderId=getArguments().getString("orderId");
        orderDatabase= FirebaseDatabase.getInstance().getReference("currentOrders");
        startLoading();
        recyclerViewComponent();
        if (orderType.equals("current")) {
            mBinding.text.setText("الطلبات الحالية");
        }
        back();
        getOrderData();
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

                    adapter.notifyDataSetChanged();
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
        mBinding.orderNumber1.setText(newOrderModel.getOrderNum());
        mBinding.customerName.setText(newOrderModel.getBrokerName());
        mBinding.webSiteName.setText(newOrderModel.getWebSitName());
        mBinding.orderDate.setText(newOrderModel.getOrderDate());
        mBinding.totalCost.setText(newOrderModel.getTotalCost()+"");

       loading.dismiss();
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderType.equals("current")) {
                    NavHostFragment.findNavController(OrderDetails.this)
                            .navigate(R.id.goTocurrentlyOrders);
                }
                else
                {
                    NavHostFragment.findNavController(OrderDetails.this)
                            .navigate(R.id.goToPreviousOrders);
                }
            }
        });
    }
}