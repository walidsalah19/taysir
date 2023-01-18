package com.example.taysir.Admin.Adapter;

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

import com.example.taysir.Admin.AdminShowNewComplaintDirections;
import com.example.taysir.Models.BrokerModel;
import com.example.taysir.Models.NewComplaintModel;
import com.example.taysir.R;

import java.util.ArrayList;

public class displayBrokersAdapter  extends RecyclerView.Adapter<displayBrokersAdapter.help> {
    private ArrayList<BrokerModel> arrayList;
    private Fragment fragment;
    public displayBrokersAdapter(ArrayList<BrokerModel> arrayList, Fragment fragment) {
        this.arrayList = arrayList;
        this.fragment=fragment;
    }

    @NonNull
    @Override
    public help onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_broker_layout,parent,false);
        return new help(view);
    }

    @Override
    public void onBindViewHolder(@NonNull help holder, @SuppressLint("RecyclerView") int position) {
       holder.userName.setText(arrayList.get(position).getUserName());
        holder.email.setText(arrayList.get(position).getEmail());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b=new Bundle();
                b.putString("BID",arrayList.get(position).getBID());
                b.putString("UserName",arrayList.get(position).getUserName());
                b.putString("FullName",arrayList.get(position).getFullName());
                b.putString("Email",arrayList.get(position).getEmail());
                b.putString("PhoneNum",arrayList.get(position).getPhoneNum());
                b.putString("Gender",arrayList.get(position).getGender());
                b.putString("Status",arrayList.get(position).getStatus());
                b.putString("DOB",arrayList.get(position).getDOB());
                b.putInt("NID",arrayList.get(position).getNID());
                b.putInt("MaroOfNum",arrayList.get(position).getMaroOfNum());
                b.putString("FreeWorkDocumentCode",arrayList.get(position).getFreeWorkDocumentCode());
                NavHostFragment.findNavController(fragment).navigate(R.id.goToBrokerDetails,b);


            }
        });

    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class help extends RecyclerView.ViewHolder
    {
        private TextView userName,email;
        public help(@NonNull View itemView) {
            super(itemView);
            userName=(TextView) itemView.findViewById(R.id.brokerName);
            email=(TextView) itemView.findViewById(R.id.email);

        }
    }
}
