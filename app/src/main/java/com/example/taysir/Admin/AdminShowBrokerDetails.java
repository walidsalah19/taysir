package com.example.taysir.Admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taysir.Models.BrokerModel;
import com.example.taysir.R;
import com.example.taysir.SweetDialog;
import com.example.taysir.databinding.FragmentAdminShowBrokerDetailsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AdminShowBrokerDetails extends Fragment {


    private FragmentAdminShowBrokerDetailsBinding mBinding;
    private BrokerModel broker;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentAdminShowBrokerDetailsBinding.inflate(inflater,container,false);
        getData();
        insertDataToView();
        sendRejectReason();
        back();
        AcceptBroker();
        return mBinding.getRoot();
    }
    private void getData()
    {
         broker=new BrokerModel(getArguments().getString("BID"),
                                           getArguments().getString("UserName"),
                                           getArguments().getString("FullName"),
                                            getArguments().getString("Email"),
                                            getArguments().getString("PhoneNum"),
                                            getArguments().getString("Gender"),
                                            getArguments().getString("Status"),
                                            getArguments().getString("DOB"),
                                            getArguments().getInt("NID"),
                                            getArguments().getInt("MaroOfNum"),
                                            getArguments().getString("FreeWorkDocumentCode"));
    }
    private void insertDataToView()
    {
        mBinding.name.setText(broker.getUserName());
        mBinding.email.setText(broker.getEmail());
        mBinding.edittextUserName.setText(broker.getUserName());
        mBinding.edittextEmail.setText(broker.getEmail());
        mBinding.edittextUserNameAr.setText(broker.getFullName());
        mBinding.edittextId.setText(broker.getNID()+"");
        mBinding.edittextMaroOfNum.setText(broker.getMaroOfNum()+"");
        mBinding.FreeWorkDocumentCode.setText(broker.getFreeWorkDocumentCode());
        mBinding.edittextGender.setText(broker.getGender());
    }
    private void sendRejectReason() {
        mBinding.btnRejectBroker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b=new Bundle();
                b.putString("BID",broker.getBID());
                AdminSendRejectReason s=new AdminSendRejectReason();
                s.setArguments(b);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.FrameLayout,s).commit();
            }
        });
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(AdminShowBrokerDetails.this)
                        .navigate(R.id.goToManageBrokers);
            }
        });
    }
    private void AcceptBroker()
    {
        mBinding.btnAcceptBroker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStatus();
            }
        });
    }
    private void updateStatus()
    {
        broker.setStatus("accepted");
        DatabaseReference database= FirebaseDatabase.getInstance().getReference("Brokers");

        database.child(broker.getBID()).setValue(broker).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    SweetAlertDialog dialog= SweetDialog.success(getContext(),"تم قبول الوسيط بنجاح");
                    dialog.show();
                    dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            dialog.dismiss();
                        }
                    });
                }
                else
                {
                    SweetAlertDialog dialog= SweetDialog.failed(getContext(),"فشل");
                    dialog.show();
                    dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            dialog.dismiss();
                        }
                    });
                }
            }
        });
    }
}