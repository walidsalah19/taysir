package com.example.taysir.Customer.Adapters;

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
import com.example.taysir.R;

import java.util.ArrayList;

public class CustomerViewOrderAdapter extends RecyclerView.Adapter<CustomerViewOrderAdapter.help>{

    private ArrayList<AcceptedOrdersModel> arrayList;
    private Fragment fragment;
    private String status;
    public CustomerViewOrderAdapter(ArrayList<AcceptedOrdersModel> arrayList, Fragment fragment,String status) {
        this.arrayList = arrayList;
        this.fragment=fragment;
        this.status=status;
    }

    @NonNull
    @Override
    public help onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.display_orders,parent,false);
        return new help(view);
    }

    @Override
    public void onBindViewHolder(@NonNull help holder, @SuppressLint("RecyclerView") int position) {
        holder.userName.setText(arrayList.get(position).getClintName());
        holder.orderNum.setText(arrayList.get(position).getOrderNum()+"");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b=new Bundle();
                b.putString("orderId",arrayList.get(position).getOrderId());
                if (status.equals("current")) {
                    NavHostFragment.findNavController(fragment)
                            .navigate(R.id.goToCurrentlyOrders, b);
                }
                else
                {
                    NavHostFragment.findNavController(fragment)
                            .navigate(R.id.goToPreviousOrderss, b);
                }
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
