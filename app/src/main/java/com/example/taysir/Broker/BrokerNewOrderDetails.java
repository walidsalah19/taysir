package com.example.taysir.Broker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taysir.Admin.AdminAnswerComplaint;
import com.example.taysir.Broker.Adapter.BrokerNewOrderDetailsAdapter;
import com.example.taysir.Models.OrderDetailsModel;
import com.example.taysir.Models.NewOrderModel;
import com.example.taysir.R;
import com.example.taysir.SweetDialog;
import com.example.taysir.databinding.FragmentBrokerNewOrderBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class BrokerNewOrderDetails extends Fragment {

   private FragmentBrokerNewOrderBinding mBinding;
   private String orderId,clintId;
   private NewOrderModel newOrderModel;
   private DatabaseReference orderDatabase;
   private SweetAlertDialog loading;
   private ArrayList<OrderDetailsModel> orderDetails;
   private BrokerNewOrderDetailsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentBrokerNewOrderBinding.inflate(inflater,container,false);
        orderId=getArguments().getString("orderId");
        orderDatabase= FirebaseDatabase.getInstance().getReference("newOrders");
        back();
        startLoading();
        recyclerViewComponent();
        getOrderData();
        sendOffer();
        return mBinding.getRoot();
    }
    private void startLoading()
    {
        loading= SweetDialog.loading(getContext());
        loading.show();
    }
    private void getOrderData() {
        orderDatabase.child(orderId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    for(DataSnapshot data:snapshot.getChildren()) {
                        orderDetails.clear();
                        String WebSitLink=data.child("webSitLink").getValue().toString();
                        String WebSitName=data.child("webSitName").getValue().toString();
                        clintId=data.child("clintId").getValue().toString();
                        String orderStat=data.child("orderStat").getValue().toString();
                        String OrderDate=data.child("orderDate").getValue().toString();
                        String clintName=data.child("clintName").getValue().toString();
                        String clintLocation=data.child("clintLocation").getValue().toString();

                        int orderNum=Integer.parseInt(data.child("orderNum").getValue().toString());
                        for(DataSnapshot snap2:data.getChildren()) {
                            String productLink = snap2.child("productLink").getValue().toString();
                            String productColor = snap2.child("productColor").getValue().toString();
                            String productPhoto = snap2.child("productPhoto").getValue().toString();
                            String productNotes = snap2.child("productNotes").getValue().toString();
                            String productQuantity = snap2.child("productQuantity").getValue().toString();
                            String productCode = snap2.child("productCode").getValue().toString();
                            String productSize = snap2.child("productSize").getValue().toString();
                            OrderDetailsModel detailsModel=new OrderDetailsModel(productLink,productColor,productPhoto,productNotes,Integer.parseInt(productQuantity),
                                    Integer.parseInt(productCode),Integer.parseInt(productSize));
                            orderDetails.add(detailsModel);
                        }
                        newOrderModel =new NewOrderModel(WebSitLink,WebSitName,clintId,clintName,clintLocation,orderId,orderStat,OrderDate,orderNum,orderDetails);
                    }
                    loading.dismiss();
                    addDataToView();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void sendOffer() {
        mBinding.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b=new Bundle();
                b.putString("orderId",orderId);
                b.putString("clintId",clintId);
                BrokerSendOffer a=new BrokerSendOffer();
                a.setArguments(b);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.FrameLayout,a).commit();
            }
        });
    }
    private void recyclerViewComponent()
    {
        orderDetails=new ArrayList<>();
        adapter=new BrokerNewOrderDetailsAdapter(orderDetails,this);
        mBinding.newProductDetails.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.newProductDetails.setAdapter(adapter);
    }
    private void addDataToView()
    {
        mBinding.clintName.setText(newOrderModel.getClintName());
        mBinding.clintLocation.setText(newOrderModel.getClintLocation());
        mBinding.orderNumber.setText(newOrderModel.getOrderNum());
        mBinding.webSiteName.setText(newOrderModel.getWebSitName());
        mBinding.webSiteLink.setText(newOrderModel.getWebSitLink());

    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(BrokerNewOrderDetails.this)
                        .navigate(R.id.goToHome);
            }
        });
    }
}