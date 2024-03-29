package com.app.taysir.Admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.taysir.Admin.Adapter.OldComplaintAdapter;
import com.app.taysir.Models.OldComplaintModel;
import com.app.taysir.R;
import com.app.taysir.databinding.FragmentAdminShowOldComplaintBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AdminShowOldComplaint extends Fragment {
   private FragmentAdminShowOldComplaintBinding mBinding;
   private DatabaseReference database;
   private ArrayList<OldComplaintModel> arrayList;
   private OldComplaintAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentAdminShowOldComplaintBinding.inflate(inflater,container,false);
         database=FirebaseDatabase.getInstance().getReference("OldEnquire");
        back();
        recyclerViewComponent();
        getOldComplaint();
        return mBinding.getRoot();
    }

    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(AdminShowOldComplaint.this)
                        .navigate(R.id.goToAdminHome);
            }
        });
    }
    private void recyclerViewComponent()
    {
        arrayList=new ArrayList<>();
        adapter=new OldComplaintAdapter(arrayList,this);
        mBinding.showOldComplaint.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.showOldComplaint.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void getOldComplaint()
    {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    for (DataSnapshot data:snapshot.getChildren())
                    {
                        String id=data.getKey();
                        database.child(id).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists())
                                {
                                    for (DataSnapshot snap:snapshot.getChildren()) {
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}