package com.example.taysir.UserAccess;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.taysir.Customer.CustomerMainActivity;
import com.example.taysir.R;
import com.example.taysir.databinding.FragmentCustomerProfileBinding;

import java.util.ArrayList;

public class CustomerProfile extends Fragment {


    private FragmentCustomerProfileBinding mBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentCustomerProfileBinding.inflate(inflater,container,false);

        funGenderSpinner();
        funCities();
        funLocation();
        startCustomer();
        return mBinding.getRoot();
    }
    private void startCustomer()
    {
        mBinding.btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CustomerMainActivity.class));
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