package com.example.taysir.Admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taysir.Admin.Adapter.ComplaintAdapter;
import com.example.taysir.Models.ComplaintModel;
import com.example.taysir.R;
import com.example.taysir.databinding.FragmentAdminShowOldComplaintBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AdminShowOldComplaint extends Fragment {
   private FragmentAdminShowOldComplaintBinding mBinding;
   private DatabaseReference database;
   private ArrayList<ComplaintModel> arrayList;
   private ComplaintAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentAdminShowOldComplaintBinding.inflate(inflater,container,false);
         database=FirebaseDatabase.getInstance().getReference("Enquire");
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
        adapter=new ComplaintAdapter(arrayList,this);
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
                         String UserName=data.child("UserName").getValue().toString();
                        String Inquire=data.child("Inquire").getValue().toString();
                        String InquireId=data.child("InquireId").getValue().toString();
                        String userId=data.child("UserId").getValue().toString();
                        int InquireNum=Integer.parseInt(data.child("InquireNum").getValue().toString());
                        ComplaintModel complaint=new ComplaintModel(UserName,Inquire,InquireId,userId,InquireNum);
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