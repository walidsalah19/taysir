package com.app.taysir.Broker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.taysir.R;
import com.app.taysir.databinding.FragmentStatusNotificationBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class StatusNotification extends Fragment {

    private FragmentStatusNotificationBinding mBinding;
    private String userId;
    private DatabaseReference brokerDatabase;
    private FirebaseAuth auth;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentStatusNotificationBinding.inflate(inflater,container,false);
         initFirebaseTool();
         getRejectReason();

        return mBinding.getRoot();
    }
    private void initFirebaseTool()
    {
        brokerDatabase=FirebaseDatabase.getInstance().getReference();
        auth= FirebaseAuth.getInstance();
        userId=auth.getCurrentUser().getUid().toString();
    }
    private void getRejectReason()
    {
        DatabaseReference database= FirebaseDatabase.getInstance().getReference("BrokerRejectReason");
        database.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    String reason=snapshot.child(userId).getValue().toString();
                    mBinding.text3.setText(reason);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}