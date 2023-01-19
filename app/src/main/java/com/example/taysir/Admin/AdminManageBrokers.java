package com.example.taysir.Admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taysir.Admin.Adapter.OldComplaintAdapter;
import com.example.taysir.Admin.Adapter.displayBrokersAdapter;
import com.example.taysir.Broker.BrokerCurrentOrder;
import com.example.taysir.Models.BrokerModel;
import com.example.taysir.Models.OldComplaintModel;
import com.example.taysir.R;
import com.example.taysir.databinding.FragmentAdminManageBrokersBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AdminManageBrokers extends Fragment {

    private FragmentAdminManageBrokersBinding mBinding;
    private DatabaseReference database;
    private ArrayList<BrokerModel> arrayList;
    private displayBrokersAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentAdminManageBrokersBinding.inflate(inflater,container,false);
        database= FirebaseDatabase.getInstance().getReference("Brokers");
        back();
        recyclerViewComponent();
        getBrokerRequests();
        return mBinding.getRoot();
    }

    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(AdminManageBrokers.this)
                        .navigate(R.id.goToAdminHome);
            }
        });
    }
    private void recyclerViewComponent()
    {
        arrayList=new ArrayList<>();
        adapter=new displayBrokersAdapter(arrayList,this);
        mBinding.adminDisplayBroker.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.adminDisplayBroker.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void getBrokerRequests()
    {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    for (DataSnapshot data:snapshot.getChildren()) {
                        String Status = data.child("status").getValue().toString();
                        if (Status.equals("unKnown") && Status.equals("reject")) {
                            String BID = data.child("bid").getValue().toString();
                            String UserName = data.child("userName").getValue().toString();
                            String FullName = data.child("fullName").getValue().toString();
                            String Email = data.child("email").getValue().toString();
                            String PhoneNum = data.child("phoneNum").getValue().toString();
                            String Gender = data.child("gender").getValue().toString();
                            String DOB = data.child("dob").getValue().toString();
                            int NID = Integer.parseInt(data.child("nid").getValue().toString());
                            int MaroOfNum = Integer.parseInt(data.child("maroOfNum").getValue().toString());
                            String FreeWorkDocumentCode = data.child("freeWorkDocumentCode").getValue().toString();
                            BrokerModel broker = new BrokerModel(BID, UserName, FullName, Email, PhoneNum, Gender, Status, DOB, NID, MaroOfNum, FreeWorkDocumentCode);
                            arrayList.add(broker);
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