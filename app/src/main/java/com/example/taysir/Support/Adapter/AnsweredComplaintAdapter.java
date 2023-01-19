package com.example.taysir.Support.Adapter;

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

import com.example.taysir.Models.OldComplaintModel;
import com.example.taysir.R;

import java.util.ArrayList;

public class AnsweredComplaintAdapter extends RecyclerView.Adapter<AnsweredComplaintAdapter.help> {
    private ArrayList<OldComplaintModel> arrayList;
    private Fragment fragment;
    public AnsweredComplaintAdapter(ArrayList<OldComplaintModel> arrayList, Fragment fragment) {
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
    public void onBindViewHolder(@NonNull help holder, @SuppressLint("RecyclerView") int position) {
        holder.userName.setText(arrayList.get(position).getUserName());
        holder.ComplaintNum.setText(arrayList.get(position).getInquireNum()+" ");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b=new Bundle();
                b.putString("complaint",arrayList.get(position).getInquire());
                b.putString("username",arrayList.get(position).getUserName());
                b.putString("complaintId",arrayList.get(position).getInquireId());
                b.putString("userId",arrayList.get(position).getUserId());
                b.putInt("num",arrayList.get(position).getInquireNum());
                b.putString("answer",arrayList.get(position).getAnswer());

                NavHostFragment.findNavController(fragment)
                        .navigate(R.id.goToComplaintAnswer,b);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class help extends RecyclerView.ViewHolder {
        private TextView userName, ComplaintNum;

        public help(@NonNull View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.complaintUserName);
            ComplaintNum = (TextView) itemView.findViewById(R.id.complaintNum);

        }
    }
}
