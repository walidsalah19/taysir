package com.example.taysir.UserAccess;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.taysir.Broker.BrokerMainActivity;
import com.example.taysir.Customer.CustomerMainActivity;
import com.example.taysir.Models.BrokerModel;
import com.example.taysir.Models.CustomerModel;
import com.example.taysir.R;
import com.example.taysir.SweetDialog;
import com.example.taysir.UserType;
import com.example.taysir.databinding.FragmentCustomerCompleteProfileBinding;
import com.example.taysir.databinding.FragmentCustomerProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CustomerCompleteProfile extends Fragment {


    private FragmentCustomerCompleteProfileBinding mBinding;
    private int pStatus=0;
    private SweetAlertDialog loading;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentCustomerCompleteProfileBinding.inflate(inflater,container,false);
        funGenderSpinner();
        clickCreateAccount();
        showPassword();
        funCities();
        funLocation();
        goToLogin();
        back();
        return mBinding.getRoot();
    }
    private void clickCreateAccount()
    {
        mBinding.btnCreateAccount.setOnClickListener(new View.OnClickListener() {
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
        String password=mBinding.edittextPassword.getText().toString();
        String city=mBinding.cities.getText().toString();
        String street=mBinding.street.getText().toString();
        String postCode=mBinding.PostCode.getText().toString();
        String address=mBinding.edittextAddress.getText().toString();
        if (TextUtils.isEmpty(userName))
        {
            mBinding.edittextUserName.setError("قم بإدخال اسم المستخدم");
        }

        else if (TextUtils.isEmpty(email))
        {
            mBinding.edittextEmail.setError("قم بإدخال البريد الإلكتروني بشكل صحيح");
        }
        else if (! validatePassword(password) || password.length()<8)
        {
            mBinding.edittextPassword.setError("قم بإدخال الرقم السري مكون من 8 حروف وأرقام");
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
            mBinding.PostCode.setError("قم بإدخال رقم الرمز البريدي");
        }
        else {
            loading = SweetDialog.loading(getContext());
            loading.show();
            CustomerModel customer=new CustomerModel("",userName,fullName,email,phoneNum,gender,bob,address,city,street,123454,Integer.parseInt(postCode));
            createAccount(customer,email,password);
        }
    }
    private void createAccount( CustomerModel customer,String email,String password)
    {
        FirebaseAuth auth=FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    String id=task.getResult().getUser().getUid().toString();
                    customer.setCID(id);
                    sendRequest(customer,id);
                }
                else
                {
                    loading.dismiss();
                    funField();
                }
            }
        });
    }
    private void sendRequest(CustomerModel customer,String id)
    {
        DatabaseReference database= FirebaseDatabase.getInstance().getReference("Customers");
        database.child(id).setValue(customer).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                loading.dismiss();
                if (task.isSuccessful())
                {
                    funSuccessfully();
                }
                else
                {
                    funField();
                }
            }
        });
    }
    private void funSuccessfully()
    {
        SweetAlertDialog dialog= SweetDialog.success(getContext(),"تم التسجسل بنجاح");
        dialog.show();
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                dialog.dismiss();
                startActivity(new Intent(getActivity(), CustomerMainActivity.class));
            }
        });
    }
    private void funField()
    {
        SweetAlertDialog dialog= SweetDialog.failed(getContext(),"فشل في التسجيل الرجاء تغيير البريد الإلكتروني");
        dialog.show();
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                dialog.dismiss();
            }
        });
    }
    private Boolean validatePassword(String password)
    {
        String passwordPattern= "(?=.*[0-9])" +"(?=.*[a-zA-Z])";
        Pattern pattern = Pattern.compile(passwordPattern);
        Matcher matcher = pattern.matcher(password);
        if (matcher.find())
            return true;
        else
            return false;
    }
    private void showPassword() {
        mBinding.showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pStatus==0)
                {
                    Glide.with(CustomerCompleteProfile.this)
                            .load(R.drawable.ic_baseline_visibility_off_24)
                            .centerCrop()
                            .into(mBinding.showPassword);
                    pStatus=1;
                    mBinding.edittextPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                else
                {
                    Glide.with(CustomerCompleteProfile.this)
                            .load(R.drawable.ic_baseline_visibility_24)
                            .centerCrop()
                            .into(mBinding.showPassword);
                    pStatus=0;
                    mBinding.edittextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(CustomerCompleteProfile.this)
                        .navigate(R.id.goToSelectUserType);
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
        mBinding.street.setAdapter(adapter);
    }
    private void goToLogin()
    {
        mBinding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(CustomerCompleteProfile.this)
                        .navigate(R.id.loginAction);
            }
        });
    }
}