package com.app.taysir.Broker.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.app.taysir.Models.OrderDetailsModel;
import com.app.taysir.R;

import java.util.ArrayList;

public class BrokerNewOrderDetailsAdapter extends RecyclerView.Adapter<BrokerNewOrderDetailsAdapter.help>{

    private ArrayList<OrderDetailsModel> arrayList;
    private Fragment fragment;
    public BrokerNewOrderDetailsAdapter(ArrayList<OrderDetailsModel> arrayList, Fragment fragment) {
        this.arrayList = arrayList;
        this.fragment=fragment;
    }

    @NonNull
    @Override
    public help onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.broker_new_orders_products,parent,false);
        return new help(view);
    }

    @Override
    public void onBindViewHolder(@NonNull help holder, @SuppressLint("RecyclerView") int position) {
        holder.color.setText(arrayList.get(position).getProductColor());
        holder.link.setText(arrayList.get(position).getProductLink());
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
            link=(TextView) itemView.findViewById(R.id.productLink);
            quantity=(TextView) itemView.findViewById(R.id.quantityNum);
            color=(TextView) itemView.findViewById(R.id.colorName);
            productNum=(TextView) itemView.findViewById(R.id.productNum);

        }
    }
}
