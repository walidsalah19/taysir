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
import com.example.taysir.Broker.Adapter.BrokerNewOrdersAdapter;
import com.example.taysir.Models.AcceptedOrdersModel;
import com.example.taysir.Models.NewOrderModel;
import com.example.taysir.Models.OrderDetailsModel;
import com.example.taysir.R;
import com.example.taysir.SweetDialog;
import com.example.taysir.databinding.FragmentBrokerDisplayCurrentOrderBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BrokerDisplayCurrentOrder extends Fragment {
    private FragmentBrokerDisplayCurrentOrderBinding mBinding;
    private DatabaseReference orderDatabase;
    private String userId;
    private ArrayList<AcceptedOrdersModel>order;
    private ArrayList<OrderDetailsModel>orderDetails;
    private BrokerAcceptedOrderAdapter adapter;
    private SweetAlertDialog loading;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentBrokerDisplayCurrentOrderBinding.inflate(inflater,container,false);
        orderDatabase= FirebaseDatabase.getInstance().getReference("currentOrders");
        userId= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
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
        order=new ArrayList<>();
        orderDetails=new ArrayList<>();
        adapter=new BrokerAcceptedOrderAdapter(order,this);
        mBinding.BrokerCurrentOrders.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.BrokerCurrentOrders.setAdapter(adapter);
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
                        String brokerId = data.child("brokerId").getValue().toString();
                        if (userId.equals(brokerId)) {
                            String WebSitLink = data.child("webSitLink").getValue().toString();
                            String WebSitName = data.child("webSitName").getValue().toString();
                            String clintId = data.child("clintId").getValue().toString();
                            String orderId = data.child("orderId").getValue().toString();
                            String orderStat = data.child("orderStat").getValue().toString();
                            String OrderDate = data.child("orderDate").getValue().toString();
                            String clintName = data.child("clintName").getValue().toString();
                            String clintLocation = data.child("clintLocation").getValue().toString();
                            String rating = data.child("rating").getValue().toString();
                            String TotalCost = data.child("totalCost").getValue().toString();

                            int orderNum = Integer.parseInt(data.child("orderNum").getValue().toString());
                            for (DataSnapshot snap2 : data.getChildren()) {
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
                            AcceptedOrdersModel newOrderModel = new AcceptedOrdersModel(WebSitLink, WebSitName, clintId, clintName, clintLocation
                                    , orderId, orderStat, OrderDate, orderNum, orderDetails, brokerId,TotalCost, rating);
                            order.add(newOrderModel);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
                loading.dismiss();
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
                NavHostFragment.findNavController(BrokerDisplayCurrentOrder.this)
                        .navigate(R.id.goToHome);
            }
        });
    }
}