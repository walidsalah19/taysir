package com.app.taysir.Customer;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.taysir.Customer.Adapters.CreateNewOrderAdapter;
import com.app.taysir.Models.NewOrderModel;
import com.app.taysir.Models.OrderDetailsModel;
import com.app.taysir.R;
import com.app.taysir.SweetDialog;
import com.app.taysir.databinding.FragmentCreateNewOfferBinding;
import com.app.taysir.databinding.FragmentCurrentOrdersBinding;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class CreateNewOrder extends Fragment {
    private FragmentCreateNewOfferBinding mBinding;
    private SweetAlertDialog loading;
    private ArrayList<String> items;
    private CreateNewOrderAdapter adapter;
    private  DatabaseReference database;
    private String clintId,clintName,clintLocation;
    private int orderNum,imagePosition;
    public static final int PICK_IMAGE = 1;
    UploadTask uploadTask;
    StorageReference storageReference;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= FragmentCreateNewOfferBinding.inflate(inflater,container,false);
        initializeFirebaseTools();
        recyclerViewComponents();
        funAddNewItem();
        sendOrder();
        getOrderNumber();
        back();
        cancel();
        return mBinding.getRoot();
    }
    private void initializeFirebaseTools()
    {
        database= FirebaseDatabase.getInstance().getReference();
        clintId= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
    }
    private void startLoading()
    {
        loading= SweetDialog.loading(getContext());
        loading.show();
    }
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH.mm", Locale.US);
        Date date = new Date();
        return dateFormat.format(date);
    }
    private void recyclerViewComponents()
    {
        items=new ArrayList<>();
        items.add("1");
        adapter=new CreateNewOrderAdapter(items);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(new CreateNewOrderAdapter.OnItemClickListener() {
            @Override
            public void onItemSelected(int position) {
                imagePosition=position;
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }

            @Override
            public void onItemDelete(int position) {
              items.remove(position);
              adapter.notifyDataSetChanged();
            }
        });
        mBinding.addOrderDetails.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.addOrderDetails.setAdapter(adapter);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1&& resultCode== Activity.RESULT_OK)
        {
            if (data !=null)
            {
                startLoading();
                saveImage(data.getData());
            }
        }
    }

    private void saveImage(Uri data) {
        storageReference= FirebaseStorage.getInstance().getReference("images");
        StorageReference reference=storageReference.child(UUID.randomUUID().toString());
        uploadTask= reference.putFile(data);

        Task<Uri> uriTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if(!task.isSuccessful())
                {
                    throw task.getException();
                }
                return reference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri=task.getResult();
                    CreateNewOrderAdapter.orderDetails.get(imagePosition).setProductPhoto(downloadUri.toString());
                    loading.dismiss();
                }
            }
        });

    }

    private void funAddNewItem()
    {
        mBinding.linear4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.add("1");
                adapter.notifyDataSetChanged();
            }
        });
    }
    private void getOrderNumber()
    {
        database.child("ordersNumber").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    orderNum=Integer.parseInt(snapshot.child("number").getValue().toString())+1;
                }
                else
                {
                 orderNum=1;
                    database.child("ordersNumber").child("number").setValue("1");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void sendOrder()
    {
        mBinding.btnSendOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mBinding.edittextWebsiteName.getText().toString()))
                {
                    mBinding.edittextWebsiteName.setError("قم بإدخال اسم الموقع");
                }
                else if (TextUtils.isEmpty(mBinding.edittextWebsiteLink.getText().toString()))
                {
                    mBinding.edittextWebsiteLink.setError("قم بإدخال رابط الموقع");
                }
                else {
                   startLoading();
                   getClintData();
                }
            }
        });
    }
    private void getClintData()
    {
        database.child("Customers").child(clintId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    clintName = snapshot.child("userName").getValue().toString();
                    clintLocation  = snapshot.child("address").getValue().toString();
                    setOrderData();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void setOrderData() {
        String orderId= UUID.randomUUID().toString();
        String webSiteLink=mBinding.edittextWebsiteLink.getText().toString();
        String webSiteName=mBinding.edittextWebsiteName.getText().toString();
        String orderDate=getDateTime();
        ArrayList<OrderDetailsModel> orderDetails=new ArrayList<>();
        for(OrderDetailsModel model:CreateNewOrderAdapter.orderDetails)
        {
            if (! TextUtils.isEmpty(model.getProductLink()) || model.getProductQuantity()!=0)
            {
                orderDetails.add(model);
            }
        }
        NewOrderModel model=new NewOrderModel(webSiteLink,webSiteName,clintId,clintName,clintLocation,orderId,"new",orderDate,orderNum,orderDetails);
        addToDatabase(orderId,model);

    }
    private void addToDatabase(String orderId,NewOrderModel model)
    {
        database.child("newOrders").child(orderId).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                loading.dismiss();
                if (task.isSuccessful())
                {
                    database.child("ordersNumber").child("number").setValue(orderNum+1);
                    funSuccessfully("تم إضافة طلب بنجاح");
                }
                else
                {
                    funFailed("فشل إرسال الطلب ");
                }
            }
        });
    }
    private void funSuccessfully(String title)
    {
        SweetAlertDialog success=SweetDialog.success(getContext(),title);
        success.show();
        success.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                success.dismiss();
                items.clear();
                items.add("1");
                CreateNewOrderAdapter.orderDetails.clear();
            }
        });
    }
    private void funFailed(String title)
    {
        SweetAlertDialog fail=SweetDialog.failed(getContext(),title);
        fail.show();
        fail.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                fail.dismiss();
            }
        });
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(CreateNewOrder.this)
                        .navigate(R.id.goToHome);

            }
        });
    }
    private void cancel()
    {
        mBinding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavHostFragment.findNavController(CreateNewOrder.this)
                        .navigate(R.id.goToHome);
            }
        });
    }
}