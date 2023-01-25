package com.example.taysir.Broker.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taysir.Models.OrderDetailsModel;
import com.example.taysir.R;

import java.util.ArrayList;

public class BrokerCurrentOrderDetailsAdapter extends RecyclerView.Adapter<BrokerCurrentOrderDetailsAdapter.help>{

    private ArrayList<OrderDetailsModel> arrayList;
    private Fragment fragment;
    public BrokerCurrentOrderDetailsAdapter(ArrayList<OrderDetailsModel> arrayList, Fragment fragment) {
        this.arrayList = arrayList;
        this.fragment=fragment;
    }

    @NonNull
    @Override
    public help onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.broker_current_order_details,parent,false);
        return new help(view);
    }

    @Override
    public void onBindViewHolder(@NonNull help holder, @SuppressLint("RecyclerView") int position) {
        holder.quantity.setText(arrayList.get(position).getProductQuantity()+"");
        holder.productNum.setText(arrayList.get(position).getProductCode()+"");
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class help extends RecyclerView.ViewHolder
    {
        private TextView link,quantity,color,productNum;
        public help(@NonNull View itemView) {
            super(itemView);
            quantity=(TextView) itemView.findViewById(R.id.productQuantity);
            productNum=(TextView) itemView.findViewById(R.id.productNum);

        }
    }
}
