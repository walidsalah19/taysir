package com.example.taysir.Customer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.taysir.Broker.BrokerProfile;
import com.example.taysir.Models.BrokerModel;
import com.example.taysir.Models.CustomerModel;
import com.example.taysir.R;
import com.example.taysir.SweetDialog;
import com.example.taysir.databinding.FragmentCustomerProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CustomerProfile extends Fragment {

   private FragmentCustomerProfileBinding mBinding;
    private SweetAlertDialog loading;
    private String userId;
    private DatabaseReference clintDatabase;
    private FirebaseAuth auth;
    private CustomerModel customer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= FragmentCustomerProfileBinding.inflate(inflater,container,false);
        startLoading();
        initFirebaseTool();
        getBrokerData();
        updateAccount();
        funCities();
        funLocation();
        funGenderSpinner();
        back();
        cancel();
        return mBinding.getRoot();
    }
    private void cancel()
    {
        mBinding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavHostFragment.findNavController(CustomerProfile.this)
                        .navigate(R.id.goToHome);
            }
        });
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(CustomerProfile.this)
                        .navigate(R.id.goToHome);
            }
        });
    }
    private void initFirebaseTool()
    {
        clintDatabase= FirebaseDatabase.getInstance().getReference("Customers");
        auth= FirebaseAuth.getInstance();
        userId=auth.getCurrentUser().getUid().toString();
    }
    private void startLoading()
    {
        loading= SweetDialog.loading(getContext());
        loading.show();
    }
    private void getBrokerData()
    {
        clintDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    String CID = snapshot.child("ciD").getValue().toString();
                    String UserName = snapshot.child("userName").getValue().toString();
                    String FullName = snapshot.child("fullName").getValue().toString();
                    String Email = snapshot.child("email").getValue().toString();
                    String PhoneNum = snapshot.child("phoneNum").getValue().toString();
                    String Gender = snapshot.child("gender").getValue().toString();
                    String DOB = snapshot.child("dob").getValue().toString();
                    String address = snapshot.child("address").getValue().toString();
                    String city = snapshot.child("city").getValue().toString();
                    String street = snapshot.child("street").getValue().toString();
                    int NID = Integer.parseInt(snapshot.child("nid").getValue().toString());
                    int postCode = Integer.parseInt(snapshot.child("postCode").getValue().toString());
                    customer=new CustomerModel(CID,UserName,FullName,Email,PhoneNum,Gender,DOB,address,city,street,NID,postCode);
                    insertDataToView();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void insertDataToView()
    {
        mBinding.name.setText(customer.getUserName());
        mBinding.email.setText(customer.getEmail());
        mBinding.edittextUserName.setText(customer.getUserName());
        mBinding.edittextEmail.setText(customer.getEmail());
        mBinding.edittextUserNameAr.setText(customer.getFullName());
        mBinding.edittextId.setText(customer.getNID()+"");
        mBinding.edittextAddress.setText(customer.getAddress());
        mBinding.cities.setText(customer.getCity());
        mBinding.location.setText(customer.getStreet());
        mBinding.genderSpinner.setText(customer.getGender());
        mBinding.postCode.setText(customer.getPostCode());
        loading.dismiss();
    }
    private void updateAccount()
    {
        mBinding.btnUpdateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEnteredData();
            }
        });
    }

    private void checkEnteredData() {
        String userName= mBinding.edittextUserName.getText().toString();
        String email= mBinding.edittextEmail.getText().toString();
        String fullName= mBinding.edittextUserNameAr.getText().toString();
        String gender= mBinding.genderSpinner.getText().toString();
        String phoneNum="12345678";
        String bob="1/1/2000";
        String NID=mBinding.edittextId.getText().toString();
        String city=mBinding.cities.getText().toString();
        String street=mBinding.location.getText().toString();
        String postCode=mBinding.postCode.getText().toString();
        String address=mBinding.edittextAddress.getText().toString();
        if (TextUtils.isEmpty(userName))
        {
            mBinding.edittextUserName.setError("قم بإدخال اسم المستخدم");
        }

        else if (TextUtils.isEmpty(email))
        {
            mBinding.edittextEmail.setError("قم بإدخال البريد الإلكتروني بشكل صحيح");
        }
        else if (TextUtils.isEmpty(fullName))
        {
            mBinding.edittextUserNameAr.setError("قم بإدخال اسم كامل ");
        }
        else if (TextUtils.isEmpty(NID))
        {
            mBinding.edittextId.setError("قم بإدخال رقم الهوية");
        }
        else if (TextUtils.isEmpty(address))
        {
            mBinding.edittextAddress.setError("قم بإدخال العنوان بالتفصيل ");
        }
        else if (TextUtils.isEmpty(city) || city.equals("المدينة"))
        {
            Toast.makeText(getContext(), "من فضلك قم بإختيار المدينة", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(street) || street.equals("المنطقة"))
        {
            Toast.makeText(getContext(), "من فضلك قم بإختيار المنطقة", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(gender)||gender.equals("الجنس"))
        {
            Toast.makeText(getActivity(), "قم بإختيار الجنس ", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(postCode))
        {
            mBinding.postCode.setError("قم بإدخال رقم الرمز البريدي");
        }
        else {
            startLoading();
            CustomerModel customer=new CustomerModel("",userName,fullName,email,phoneNum,gender,bob,address,city,street,123454,Integer.parseInt(postCode));
            updateDatabase(customer);
        }
    }
    private void updateDatabase(CustomerModel customer)
    {
        DatabaseReference database= FirebaseDatabase.getInstance().getReference("Customers");
        database.child(userId).setValue(customer).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                loading.dismiss();
                if (task.isSuccessful())
                {
                    SweetAlertDialog dialog= SweetDialog.success(getContext(),"تم تحديث البيانات بنجاح");
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
                    SweetAlertDialog dialog= SweetDialog.failed(getContext(),"فشل التحديث");
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
    private void funGenderSpinner()
    {
        ArrayList<String > array=new ArrayList<>();
        array.add("ذكر");
        array.add("أنثي");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), R.layout.item_dropdwon,array);
        mBinding.genderSpinner.setAdapter(adapter);

    }
    private void funCities()
    {
        ArrayList<String > array=new ArrayList<>();
        array.add(" منطقة مكة المكرمة");
        array.add(" منطقة المدينة");
        array.add("منطقة الرياض");
        array.add(" المنطقةالشرقية");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), R.layout.item_dropdwon,array);
        mBinding.cities.setAdapter(adapter);
    }
    private void funLocation()
    {
        ArrayList<String > array=new ArrayList<>();
        array.add(" مكة المكرمة");
        array.add(" جدة");
        array.add("الطائف");
        array.add("ينبع");
        array.add("الليث");
        array.add("القنفذة");
        array.add("رابغ");

        array.add("المدينةالمنورة");
        array.add("ينبع");
        array.add("العال");

        array.add("الرياض");
        array.add("الدرعية");
        array.add("الخرج");
        array.add("المجمعة");
        array.add("الدوادمي");
        array.add("الزلفى");

        array.add("الدمام");
        array.add("الخبر");
        array.add("الظهران");
        array.add("االحساء");
        array.add("حفر الباطن");
        array.add("الخفجي");
        array.add("الجبيل");
        array.add("القطيف");

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), R.layout.item_dropdwon,array);
        mBinding.location.setAdapter(adapter);
    }
}