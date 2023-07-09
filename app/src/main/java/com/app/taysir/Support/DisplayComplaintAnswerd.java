package com.app.taysir.Support;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.taysir.Models.OldComplaintModel;
import com.app.taysir.R;
import com.app.taysir.Support.Adapter.AnsweredComplaintAdapter;
import com.app.taysir.databinding.FragmentDisplayComplaintAnswerdBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class DisplayComplaintAnswerd extends Fragment {
    private FragmentDisplayComplaintAnswerdBinding mBinding;
    private DatabaseReference database;
    private ArrayList<OldComplaintModel> arrayList;
    private AnsweredComplaintAdapter adapter;
    private String userId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       mBinding=FragmentDisplayComplaintAnswerdBinding.inflate(inflater,container,false);
        database= FirebaseDatabase.getInstance().getReference("OldEnquire");
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
                NavHostFragment.findNavController(DisplayComplaintAnswerd.this)
                        .navigate(R.id.goTosupportTechnical);
            }
        });
    }
    private void recyclerViewComponent()
    {
        arrayList=new ArrayList<>();
        adapter=new AnsweredComplaintAdapter(arrayList,this);
        mBinding.showOldComplaint.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.showOldComplaint.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void getNewComplaint()
    {
        database.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        String UserName = snap.child("userName").getValue().toString();
                        String Inquire = snap.child("inquire").getValue().toString();
                        String InquireId = snap.child("inquireId").getValue().toString();
                        String userId = snap.child("userId").getValue().toString();
                        String Answer = snap.child("answer").getValue().toString();
                        int InquireNum = Integer.parseInt(snap.child("inquireNum").getValue().toString());
                        OldComplaintModel complaint = new OldComplaintModel(UserName, Inquire, InquireId, userId, InquireNum, Answer);
                        arrayList.add(complaint);
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