package com.app.taysir.Broker.Adapter;

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

import com.app.taysir.Models.NewOrderModel;
import com.app.taysir.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        calculateTime(order.get(position).getOrderDate(),holder);
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
    private void  calculateTime(String time,@NonNull help holder)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH.mm");
        try {

            Date oldDate = dateFormat.parse(time);
            System.out.println(oldDate);

            Date currentDate = new Date();

            long diff = currentDate.getTime() - oldDate.getTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;
            long month=days/30;
            if (month !=0) {
                holder.time.setText(" منذ "+month+" ش ");
            }
            else if (days !=0)
            {
                holder.time.setText(" منذ "+days+" ي ");
            }
            else if (hours !=0)
            {
                holder.time.setText(" منذ "+hours+" س ");
            }
            else if (minutes !=0)
            {
                holder.time.setText("منذ "+minutes+" د ");
            }


        } catch ( ParseException e) {

            e.printStackTrace();
        }

    }

}
