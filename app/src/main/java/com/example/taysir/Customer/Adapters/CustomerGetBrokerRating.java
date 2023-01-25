package com.example.taysir.Customer.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taysir.Broker.Adapter.BrokerRatingAdapter;
import com.example.taysir.Models.RatingModel;
import com.example.taysir.R;

import java.util.ArrayList;

public class CustomerGetBrokerRating extends RecyclerView.Adapter<CustomerGetBrokerRating.help>{

    private ArrayList<RatingModel> rating;

    public CustomerGetBrokerRating(ArrayList<RatingModel> rating) {
        this.rating = rating;
    }

    @NonNull
    @Override
    public help onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_view_broker_ratings,parent,false);

        return new help(view);
    }

    @Override
    public void onBindViewHolder(@NonNull help holder, int position) {
        holder.name.setText(rating.get(position).getCustomerName());
        holder.ratingNum.setText(rating.get(position).getRatingNum()+" ");
        holder.ratingText.setText(rating.get(position).getRatingText());
    }

    @Override
    public int getItemCount() {
        return rating.size();
    }

    protected class help extends RecyclerView.ViewHolder
    {
        TextView name,ratingNum,ratingText;
        public help(@NonNull View itemView) {
            super(itemView);
            name=(TextView) itemView.findViewById(R.id.name);
            ratingNum=(TextView) itemView.findViewById(R.id.ratingNum);
            ratingText=(TextView) itemView.findViewById(R.id.ratingText);
        }
    }
}
