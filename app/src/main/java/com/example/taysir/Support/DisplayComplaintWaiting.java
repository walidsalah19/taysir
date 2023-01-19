package com.example.taysir.Support;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taysir.Admin.Adapter.NewComplaintAdapter;
import com.example.taysir.Admin.AdminShowNewComplaint;
import com.example.taysir.Models.NewComplaintModel;
import com.example.taysir.R;
import com.example.taysir.Support.Adapter.WaitingComplaintAdapter;
import com.example.taysir.databinding.FragmentDisplayComplaintWaitingBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayComplaintWaiting extends Fragment {
    private FragmentDisplayComplaintWaitingBinding mBinding;
    private DatabaseReference database;
    private ArrayList<NewComplaintModel> arrayList;
    private WaitingComplaintAdapter adapter;
    private String userId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentDisplayComplaintWaitingBinding.inflate(inflater,container,false);
        database= FirebaseDatabase.getInstance().getReference("newEnquire");
        userId= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        recyclerViewComponent();
        getNewComplaint();
        back();
        return mBinding.getRoot();
    }

    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(DisplayComplaintWaiting.this)
                        .navigate(R.id.goTosupportTechnical);
            }
        });
    }
    private void recyclerViewComponent()
    {
        arrayList=new ArrayList<>();
        adapter=new WaitingComplaintAdapter(arrayList,this);
        mBinding.showWaitingComplaint.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.showWaitingComplaint.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void getNewComplaint()
    {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    arrayList.clear();
                    for (DataSnapshot data:snapshot.getChildren())
                    {
                        String uId=data.child("userId").getValue().toString();
                        if (userId.equals(uId)) {
                            String UserName = data.child("userName").getValue().toString();
                            String Inquire = data.child("inquire").getValue().toString();
                            String InquireId = data.child("inquireId").getValue().toString();
                            int InquireNum = Integer.parseInt(data.child("inquireNum").getValue().toString());
                            NewComplaintModel complaint = new NewComplaintModel(UserName, Inquire, InquireId, uId, InquireNum);
                            arrayList.add(complaint);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}