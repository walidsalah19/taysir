package com.app.taysir.UserAccess;

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
import com.app.taysir.Broker.BrokerMainActivity;
import com.app.taysir.Models.BrokerModel;
import com.app.taysir.R;
import com.app.taysir.SweetDialog;
import com.app.taysir.databinding.FragmentBrokerCompleteProfileBinding;
import com.app.taysir.databinding.FragmentBrokerProfileBinding;
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

public class BrokerCompleteProfile extends Fragment {

   private FragmentBrokerCompleteProfileBinding mBinding;
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
        mBinding=FragmentBrokerCompleteProfileBinding.inflate(inflater,container,false);
        funGenderSpinner();
        back();
      //  startBroker();
        clickCreateAccount();
        showPassword();
        goToLogin();
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
       String MaroOfNum= mBinding.edittextMaroOfNum.getText().toString();
       String FreeWorkDocumentCode=mBinding.FreeWorkDocumentCode.getText().toString();
       String password=mBinding.edittextPassword.getText().toString();
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
       else if (TextUtils.isEmpty(gender)||gender.equals("الجنس"))
       {
           Toast.makeText(getActivity(), "قم بإختيار الجنس ", Toast.LENGTH_SHORT).show();
       }
       else if (TextUtils.isEmpty(NID))
       {
           mBinding.edittextId.setError("قم بإدخال اسم المستخدم");
       }
       else if (TextUtils.isEmpty(MaroOfNum) && (MaroOfNum.length()<=6 &&MaroOfNum.length()>=5))
       {
           mBinding.edittextMaroOfNum.setError("قم بأدخل رقم معروف مكون من 5 الي 6 أرقام");
       }
       else if (TextUtils.isEmpty(FreeWorkDocumentCode))
       {
           mBinding.FreeWorkDocumentCode.setError("قم بإدخال رقم وثيقة العمل الحر ");
       }
       else if (FreeWorkDocumentCode.length() !=8)
       {
           mBinding.FreeWorkDocumentCode.setError("قم بإدخال رقم وثيقة العمل الحر مكون من 8 حروف وأرقام");
       }
       else {
           loading=SweetDialog.loading(getContext());
           loading.show();
           BrokerModel broker=new BrokerModel("234567",userName,fullName,email,phoneNum,gender,"unKnown",bob,Integer.parseInt(NID),Integer.parseInt(MaroOfNum),FreeWorkDocumentCode);
           createAccount(broker,email,password);
       }
    }
    private void createAccount(BrokerModel broker,String email,String password)
    {
        FirebaseAuth auth=FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    String id=task.getResult().getUser().getUid().toString();
                    broker.setBID(id);
                    sendRequest(broker,id);
                }
                else
                {
                    loading.dismiss();
                    funField("الرجاء تغيير البريد الإلكتروني");
                }
            }
        });
    }
    private void sendRequest(BrokerModel broker,String id)
    {
        DatabaseReference database= FirebaseDatabase.getInstance().getReference("Brokers");
        database.child(id).setValue(broker).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                loading.dismiss();
                if (task.isSuccessful())
                {
                 funSuccessfully();
                }
                else
                {
                    funField("فشل إرسال الطلب");
                }
            }
        });
    }
    private void funSuccessfully()
    {
        SweetAlertDialog dialog= SweetDialog.success(getContext(),"تم إرسال الطلب بنجاح");
        dialog.show();
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                dialog.dismiss();
                startActivity(new Intent(getActivity(), BrokerMainActivity.class));
            }
        });
    }
    private void funField(String title)
    {
        SweetAlertDialog dialog= SweetDialog.failed(getContext(),title);
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
                    Glide.with(BrokerCompleteProfile.this)
                            .load(R.drawable.ic_baseline_visibility_off_24)
                            .centerCrop()
                            .into(mBinding.showPassword);
                    pStatus=1;
                    mBinding.edittextPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                else
                {
                    Glide.with(BrokerCompleteProfile.this)
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
                NavHostFragment.findNavController(BrokerCompleteProfile.this)
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
    private void goToLogin()
    {
        mBinding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(BrokerCompleteProfile.this)
                        .navigate(R.id.loginAction);
            }
        });
    }

}