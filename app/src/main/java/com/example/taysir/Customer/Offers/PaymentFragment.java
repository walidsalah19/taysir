package com.example.taysir.Customer.Offers;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.taysir.Models.AcceptedOrdersModel;
import com.example.taysir.Models.NewOrderModel;
import com.example.taysir.Models.OfferModel;
import com.example.taysir.Models.OrderDetailsModel;
import com.example.taysir.R;
import com.example.taysir.SweetDialog;
import com.example.taysir.databinding.FragmentPaymentBinding;
import com.example.taysir.databinding.FragmentSelectPaymentTypeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PaymentFragment extends Fragment {
     private FragmentPaymentBinding mBinding;
     private String offerId,orderId,brokerId,totalCost;
    private SweetAlertDialog loading;
    private DatabaseReference offerDatabase;
    private ArrayList<OrderDetailsModel>orderDetails;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= FragmentPaymentBinding.inflate(inflater,container,false);
        startLoading();
        offerId=getArguments().getString("id");
        orderId=getArguments().getString("orderId");
        offerDatabase= FirebaseDatabase.getInstance().getReference();
        getNewOffers();
        pay();
        back();
        yearsSpinner();
        daysSpinner();
        return mBinding.getRoot();
    }
    private void startLoading()
    {
        loading= SweetDialog.loading(getContext());
        loading.show();
    }
    private void pay()
    {
        mBinding.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              checkCardData();
            }
        });
    }
    private void getNewOffers() {
        offerDatabase.child("offers").child(orderId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    for(DataSnapshot data:snapshot.getChildren()) {
                        String totalCost = data.child("totalCost").getValue().toString();
                        String orderCost = data.child("orderCost").getValue().toString();
                        String commission = data.child("commission").getValue().toString();
                        brokerId = data.child("brokerId").getValue().toString();
                        totalCost = data.child("totalCost").getValue().toString();

                        mBinding.cost.setText(orderCost);
                        mBinding.commission.setText(commission);
                        mBinding.totalCost.setText(totalCost);
                        loading.dismiss();
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }    private void checkCardData()
    {
        if (TextUtils.isEmpty(mBinding.edittextCardNumber.getText().toString()))
        {
            mBinding.edittextCardNumber.setError("قم بإدخال رقم البطاقة ");
        }
       else if (TextUtils.isEmpty(mBinding.cardOwner.getText().toString()))
        {
            mBinding.cardOwner.setError("قم بإدخال اسم مالك البطاقة ");
        }
        else if (TextUtils.isEmpty(mBinding.cardOwner.getText().toString()))
        {
            mBinding.cardOwner.setError("قم بإدخال اسم مالك البطاقة ");
        }
        else if (TextUtils.isEmpty(mBinding.cvc.getText().toString()))
        {
            mBinding.cvc.setError("قم بإدخال cvc ");
        }
        else
        {
            startLoading();
            sendOrderToCurrentOrders();
        }
    }

    private void sendOrderToCurrentOrders() {
        orderDetails=new ArrayList<>();
        offerDatabase.child("newOrders").child(orderId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    for(DataSnapshot data:snapshot.getChildren()) {
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
                        sendOrderToCurrentOrders(newOrderModel);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void sendOrderToCurrentOrders( NewOrderModel newOrderModel)
    {
        AcceptedOrdersModel model=new AcceptedOrdersModel(newOrderModel.getWebSitLink(),newOrderModel.getWebSitName(), newOrderModel.getClintId(),
                newOrderModel.getClintName(),newOrderModel.getClintLocation(),newOrderModel.getOrderId(), newOrderModel.getOrderStat(), newOrderModel.getOrderDate(), newOrderModel.getOrderNum(), newOrderModel.getOrderDetails()
                ,brokerId,Float.parseFloat(totalCost),"no");
        offerDatabase.child("currentOrders").child(orderId).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                  if (task.isSuccessful())
                  {

                  }
            }
        });
    }

    private void yearsSpinner()
    {
        ArrayList<String > array=new ArrayList<>();
        for(int i=2023;i<=2033;i++)
        {
            array.add(i+"");
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), R.layout.item_dropdwon,array);
        mBinding.yearsSpinner.setAdapter(adapter);
    }
    private void daysSpinner()
    {
        ArrayList<String > array=new ArrayList<>();
        for(int i=1;i<=30;i++)
        {
            array.add(i+"");
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), R.layout.item_dropdwon,array);
        mBinding.daysSpinner.setAdapter(adapter);
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(PaymentFragment.this)
                        .navigate(R.id.goToselectPayment);
            }
        });
    }

}