package com.app.taysir.Broker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.taysir.Models.AcceptedOrdersModel;
import com.app.taysir.Models.OrderDetailsModel;
import com.app.taysir.R;
import com.app.taysir.SweetDialog;
import com.app.taysir.databinding.FragmentBrokerCurrentOrderBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BrokerCurrentOrder extends Fragment {

    private FragmentBrokerCurrentOrderBinding mBinding;
    private DatabaseReference Database;
    private ArrayList<OrderDetailsModel>orderDetails;
    private SweetAlertDialog loading;
    private String orderId;
    private AcceptedOrdersModel OrderModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentBrokerCurrentOrderBinding.inflate(inflater,container,false);
        orderId=getArguments().getString("orderId");
        Database= FirebaseDatabase.getInstance().getReference();
        showOrderDetails();
        startLoading();
        getOrderData();
        chargeOrder();
        finishOrder();
        goToChat();
        back();
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
        Database.child("currentOrders").child(orderId).addValueEventListener(new ValueEventListener() {
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
                    OrderModel = new AcceptedOrdersModel(WebSitLink, WebSitName, clintId, clintName, clintLocation, orderId,
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
        mBinding.orderNumber.setText(OrderModel.getOrderNum()+"");
        mBinding.brokerName.setText(OrderModel.getBrokerName());
        if(OrderModel.getOrderStat().equals("payed"))
        {
         mBinding.checkbox1.setChecked(true);
        }
        else if(OrderModel.getOrderStat().equals("charge"))
        {
            mBinding.checkbox1.setChecked(true);
            mBinding.checkbox2.setChecked(true);

        }
        loading.dismiss();

    }
    private void chargeOrder()
    {
        mBinding.checkbox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if (mBinding.checkbox2.isChecked())
                 {
                     Database.child("currentOrders").child(orderId).child("orderStat").setValue("charge").addOnCompleteListener(new OnCompleteListener<Void>() {
                         @Override
                         public void onComplete(@NonNull Task<Void> task) {
                             if (task.isSuccessful())
                             {
                                 funSuccessfully("تم شحن الطلب بنجاح");
                             }
                             else
                             {
                                 funFailed("فشل شحن الطلب ");
                             }
                         }
                     });
                 }
                 else
                 {
                     Database.child("currentOrders").child(orderId).child("orderStat").setValue("payed").addOnCompleteListener(new OnCompleteListener<Void>() {
                         @Override
                         public void onComplete(@NonNull Task<Void> task) {
                             if (task.isSuccessful())
                             {
                                 funSuccessfully("تم التراجع في  شحن الطلب ");
                             }
                             else
                             {
                                 funFailed("فشل التراجع ");
                             }
                         }
                     });
                 }
            }
        });

    }
    private void finishOrder()
    {
        mBinding.checkbox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBinding.checkbox3.isChecked()) {
                    sendToOld();
                }
            }
        });
    }
    private void sendToOld()
    {
        Database.child("oldOrders").child(orderId).setValue(OrderModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Database.child("currentOrders").child(orderId).removeValue();
                    funSuccessfully("تم توصيل الطلب بنجاح");
                } else {
                    funFailed("فشل توصيل الطلب ");
                }
            }
        });
    }
    private void funSuccessfully(String title)
    {
        SweetAlertDialog success=SweetDialog.success(getContext(),title);
        success.show();
        success.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                success.dismiss();
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
            }
        });
    }
    private void goToChat()
    {
        mBinding.brokerGoToChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b=new Bundle();
                b.putString("id",OrderModel.getClintId());
                b.putString("name",OrderModel.getClintName());
                NavHostFragment.findNavController(BrokerCurrentOrder.this)
                        .navigate(R.id.goToChat,b);
            }
        });
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(BrokerCurrentOrder.this)
                        .navigate(R.id.goToHome);
            }
        });
    }
    private void showOrderDetails()
    {
        mBinding.showOrderDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b=new Bundle();
                b.putString("orderId",OrderModel.getOrderId());
                NavHostFragment.findNavController(BrokerCurrentOrder.this)
                        .navigate(R.id.OrderDetails,b);
            }
        });
    }
}