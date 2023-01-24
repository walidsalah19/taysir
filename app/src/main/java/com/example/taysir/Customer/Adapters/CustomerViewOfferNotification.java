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

import com.example.taysir.Broker.Adapter.BrokerOrderNotificationAdapter;
import com.example.taysir.Customer.CustomerHome;
import com.example.taysir.Models.NewOrderModel;
import com.example.taysir.Models.OfferModel;
import com.example.taysir.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CustomerViewOfferNotification extends RecyclerView.Adapter<CustomerViewOfferNotification.help>{
    private ArrayList<OfferModel> offer;
    private Fragment fragment;

    public CustomerViewOfferNotification(ArrayList<OfferModel> offer, Fragment fragment) {
        this.offer = offer;
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
        calculateTime(offer.get(position).getOrderDate(),holder);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b=new Bundle();
                b.putString("Id",offer.get(position).getOrderId());
                NavHostFragment.findNavController(fragment)
                        .navigate(R.id.goToshowOffers);
            }
        });
    }

    @Override
    public int getItemCount() {
        return offer.size();
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
                holder.time.setText(" شهر"+month+"منذ ");
            }
            else if (days !=0)
            {
                holder.time.setText(" يوم"+days+"منذ ");
            }
            else if (hours !=0)
            {
                holder.time.setText(" ساعة"+hours+"منذ ");
            }
            else if (minutes !=0)
            {
                holder.time.setText(" دقيقة"+minutes+"منذ ");
            }


        } catch ( ParseException e) {

            e.printStackTrace();
        }

    }
}
