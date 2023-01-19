package com.example.taysir.Broker.Adapter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taysir.Models.AcceptedOrdersModel;
import com.example.taysir.Models.NewOrderModel;
import com.example.taysir.R;

import java.util.ArrayList;

public class BrokerAcceptedOrderAdapter extends RecyclerView.Adapter<BrokerAcceptedOrderAdapter.help>{

    private ArrayList<AcceptedOrdersModel> arrayList;
    private Fragment fragment;
    public BrokerAcceptedOrderAdapter(ArrayList<AcceptedOrdersModel> arrayList, Fragment fragment) {
        this.arrayList = arrayList;
        this.fragment=fragment;
    }

    @NonNull
    @Override
    public help onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.broker_orders,parent,false);
        return new help(view);
    }

    @Override
    public void onBindViewHolder(@NonNull help holder, @SuppressLint("RecyclerView") int position) {
        holder.userName.setText(arrayList.get(position).getClintName());
        holder.orderNum.setText(arrayList.get(position).getOrderNum());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b=new Bundle();
                b.putString("orderId",arrayList.get(position).getOrderId());
                NavHostFragment.findNavController(fragment)
                        .navigate(R.id.goToOrderDetails,b);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class help extends RecyclerView.ViewHolder
    {
        private TextView userName,orderNum;
        public help(@NonNull View itemView) {
            super(itemView);
            userName=(TextView) itemView.findViewById(R.id.UserName);
            orderNum=(TextView) itemView.findViewById(R.id.orderNum);

        }
    }
}