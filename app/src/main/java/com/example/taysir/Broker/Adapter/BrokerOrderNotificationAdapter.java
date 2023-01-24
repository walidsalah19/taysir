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

import com.example.taysir.Models.NewOrderModel;
import com.example.taysir.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BrokerOrderNotificationAdapter extends RecyclerView.Adapter<BrokerOrderNotificationAdapter.help>{
    private ArrayList<NewOrderModel> order;
    private Fragment fragment;

    public BrokerOrderNotificationAdapter(ArrayList<NewOrderModel> order, Fragment fragment) {
        this.order = order;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public help onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.show_notification,parent,false);
        return new help(v);
    }

    @Override
    public void onBindViewHolder(@NonNull help holder, @SuppressLint("RecyclerView") int position) {
             holder.time.setText(order.get(position).getOrderDate());
             holder.itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Bundle b=new Bundle();
                     b.putString("orderId",order.get(position).getOrderId());
                     NavHostFragment.findNavController(fragment)
                             .navigate(R.id.goToNewOrders,b);
                 }
             });
    }

    @Override
    public int getItemCount() {
        return order.size();
    }

    public class help extends RecyclerView.ViewHolder
    {
        TextView time;
        public help(@NonNull View itemView) {
            super(itemView);

            time =(TextView) itemView.findViewById(R.id.time);

        }
    }
    private String  calculateTime(String time)
    {
        String minusTime = null;
        try{
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH.mm" );
            Calendar cal = Calendar.getInstance();
            Date d = dateFormat2.parse(time);
            cal.setTime(d);
            minusTime=dateFormat2.format(cal.getTime());
        }catch (Exception e)
        {

        }
        return minusTime;
    }
}
