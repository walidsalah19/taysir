package com.app.taysir.Broker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.taysir.Broker.Adapter.BrokerNewOrderDetailsAdapter;
import com.app.taysir.Models.OrderDetailsModel;
import com.app.taysir.Models.NewOrderModel;
import com.app.taysir.R;
import com.app.taysir.SweetDialog;
import com.app.taysir.databinding.FragmentBrokerNewOrderBinding;
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
   private float orderCost=0.0f;
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
                        orderDetails.clear();
                        String WebSitLink=snapshot.child("webSitLink").getValue().toString();
                        String WebSitName=snapshot.child("webSitName").getValue().toString();
                        clintId=snapshot.child("clintId").getValue().toString();
                        String orderStat=snapshot.child("orderStat").getValue().toString();
                        String OrderDate=snapshot.child("orderDate").getValue().toString();
                        String clintName=snapshot.child("clintName").getValue().toString();
                        String clintLocation=snapshot.child("clintLocation").getValue().toString();

                        int orderNum=Integer.parseInt(snapshot.child("orderNum").getValue().toString());
                        for(DataSnapshot snap2:snapshot.child("orderDetails").getChildren()) {
                            String productLink = snap2.child("productLink").getValue().toString();
                            String productColor = snap2.child("productColor").getValue().toString();
                            String productPhoto = snap2.child("productPhoto").getValue().toString();
                            String productNotes = snap2.child("productNotes").getValue().toString();
                            String productQuantity = snap2.child("productQuantity").getValue().toString();
                            String productCode = snap2.child("productCode").getValue().toString();
                            String productSize = snap2.child("productSize").getValue().toString();
                            String productCost = snap2.child("productCost").getValue().toString();
                            orderCost+=Float.parseFloat(productCost);
                            OrderDetailsModel detailsModel = new OrderDetailsModel(productLink, productColor, productPhoto, productNotes, Integer.parseInt(productQuantity),
                                    Integer.parseInt(productCode), Integer.parseInt(productSize),Float.parseFloat(productCost));

                            orderDetails.add(detailsModel);
                        }
                        newOrderModel =new NewOrderModel(WebSitLink,WebSitName,clintId,clintName,clintLocation,orderId,orderStat,OrderDate,orderNum,orderDetails);
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
                b.putFloat("orderCost",orderCost);
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
        mBinding.orderNumber.setText(newOrderModel.getOrderNum()+"");
        mBinding.webSiteName.setText(newOrderModel.getWebSitName());
        mBinding.webSiteLink.setText(newOrderModel.getWebSitLink());

    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(BrokerNewOrderDetails.this)
                        .navigate(R.id.goToDisplayNewOrders);
            }
        });
    }
}