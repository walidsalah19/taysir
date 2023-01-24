package com.example.taysir.Customer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taysir.Customer.Adapters.CreateNewOrderAdapter;
import com.example.taysir.Models.OrderDetailsModel;
import com.example.taysir.R;
import com.example.taysir.databinding.FragmentCreateNewOfferBinding;
import com.example.taysir.databinding.FragmentCurrentOrdersBinding;

import java.util.ArrayList;


public class CreateNewOffer extends Fragment {
    private FragmentCreateNewOfferBinding mBinding;
    private ArrayList<Integer> items;
    private CreateNewOrderAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= FragmentCreateNewOfferBinding.inflate(inflater,container,false);
        recyclerViewComponents();
        funAddNewItem();
        sendOrder();
        back();
        return mBinding.getRoot();
    }
    private void recyclerViewComponents()
    {
        items=new ArrayList<>();
        items.add(1);
        adapter=new CreateNewOrderAdapter(items);
        adapter.notifyDataSetChanged();
        mBinding.addOrderDetails.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.addOrderDetails.setAdapter(adapter);

    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(CreateNewOffer.this)
                        .navigate(R.id.goToHome);

            }
        });
    }
    private void funAddNewItem()
    {
        mBinding.linear4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.add(1);
                adapter.notifyDataSetChanged();
            }
        });
    }
    private void sendOrder()
    {
        mBinding.btnSendOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOrderData();
            }
        });
    }

    private void getOrderData() {
        for (OrderDetailsModel model:adapter.orderDetails)
        {
            Log.e("order",model.getProductLink());
        }
    }
}