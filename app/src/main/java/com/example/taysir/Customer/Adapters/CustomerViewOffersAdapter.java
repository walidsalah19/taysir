package com.example.taysir.Customer.Adapters;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.telephony.BarringInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taysir.Customer.Offers.ShowOffersFragment;
import com.example.taysir.Models.OfferModel;
import com.example.taysir.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CustomerViewOffersAdapter  extends RecyclerView.Adapter<CustomerViewOffersAdapter.help>{
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemDelete(String OfferId);

    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }

    private ArrayList<OfferModel> offer;
    private Fragment fragment;

    public CustomerViewOffersAdapter(ArrayList<OfferModel> offer, Fragment fragment) {
        this.offer = offer;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public help onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_show_offeres,parent,false);
        return new help(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull help holder, @SuppressLint("RecyclerView") int position) {
        holder.offerCost.setText(offer.get(position).getTotalCost()+" ");
        holder.brokerName.setText(offer.get(position).getBrokerName());
        holder.showBrokerInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b=new Bundle();
                b.putString("id",offer.get(position).getBrokerId());
                NavHostFragment.findNavController(fragment)
                        .navigate(R.id.barkerRank,b);
            }
        });
        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              mListener.onItemDelete(offer.get(position).getOfferId());
            }
        });
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b=new Bundle();
                b.putString("id",offer.get(position).getOfferId());
                NavHostFragment.findNavController(fragment)
                        .navigate(R.id.goToacceptOffer,b);
            }
        });
    }

    @Override
    public int getItemCount() {
        return offer.size();
    }

    public class help extends RecyclerView.ViewHolder
    {
        TextView brokerName,showBrokerInfo,offerCost;
        Button accept,reject;
        public help(@NonNull View itemView) {
            super(itemView);

            brokerName =(TextView) itemView.findViewById(R.id.brokerName);
            showBrokerInfo=(TextView) itemView.findViewById(R.id.showBrokerInfo);
            offerCost=(TextView) itemView.findViewById(R.id.offerCost);
            accept=(Button) itemView.findViewById(R.id.btnAccept);
            reject=(Button) itemView.findViewById(R.id.btnReject);

        }
    }
}
