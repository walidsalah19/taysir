package com.example.taysir.Admin.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taysir.Admin.AdminShowOldComplaint;
import com.example.taysir.Models.ComplaintModel;
import com.example.taysir.R;

import java.util.ArrayList;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.help> {
    private ArrayList<ComplaintModel> arrayList;
    private Fragment fragment;
    public ComplaintAdapter(ArrayList<ComplaintModel> arrayList,Fragment fragment) {
        this.arrayList = arrayList;
       this.fragment=fragment;
    }

    @NonNull
    @Override
    public help onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_complaint_layout,parent,false);
        return new help(view);
    }

    @Override
    public void onBindViewHolder(@NonNull help holder, int position) {
          holder.userName.setText(arrayList.get(position).getUserName());
          holder.ComplaintNum.setText(arrayList.get(position).getInquireNum());
          holder.itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  NavHostFragment.findNavController(fragment)
                          .navigate(R.id.goToOldComplaintDetails);
              }
          });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class help extends RecyclerView.ViewHolder
    {
         private TextView userName,ComplaintNum;
        public help(@NonNull View itemView) {
            super(itemView);
            userName=(TextView) itemView.findViewById(R.id.complaintUserName);
            ComplaintNum=(TextView) itemView.findViewById(R.id.complaintNum);

        }
    }
}
