package com.example.taysir.UserAccess;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taysir.R;
import com.example.taysir.databinding.FragmentForgotPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ForgotPassword extends Fragment {
   private FragmentForgotPasswordBinding mBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentForgotPasswordBinding.inflate(inflater,container,false);
        clickToChangePassword();
        back();
        return mBinding.getRoot();
    }
    private void clickToChangePassword () {
        mBinding.forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });
    }

    private void changePassword() {
        if (mBinding.email.getText().toString().equals(""))
        {
           mBinding.email.setError("قم بإدخال البريد الإلكتروني الخاص بك");
        }
        else
        {
            FirebaseAuth auth= FirebaseAuth.getInstance();
            auth.sendPasswordResetEmail(mBinding.email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                    {
                        SweetAlertDialog pDialogSuccess= new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE);
                        pDialogSuccess.setConfirmText("تم");
                        pDialogSuccess.setConfirmClickListener(sweetAlertDialog -> {
                            pDialogSuccess.dismiss();
                        });
                        pDialogSuccess.setCancelable(true);
                        pDialogSuccess.setTitleText("تم إرسال رابط تغير كلمة المرور ");
                        pDialogSuccess.show();
                    }
                }
            });
        }
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(ForgotPassword.this)
                        .navigate(R.id.goToLogin);
            }
        });
    }
}