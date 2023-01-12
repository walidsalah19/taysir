package com.example.taysir.Customer.Offers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.taysir.R;
import com.example.taysir.databinding.FragmentPaymentBinding;
import com.example.taysir.databinding.FragmentSelectPaymentTypeBinding;

import java.util.ArrayList;

public class PaymentFragment extends Fragment {
     private FragmentPaymentBinding mBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= FragmentPaymentBinding.inflate(inflater,container,false);
        back();
        yearsSpinner();
        daysSpinner();
        return mBinding.getRoot();
    }
    private void yearsSpinner()
    {
        ArrayList<String > array=new ArrayList<>();
        for(int i=2023;i<=2033;i++)
        {
            array.add(i+"");
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), R.layout.item_dropdwon,array);
        mBinding.yearsSpinner.setAdapter(adapter);
    }
    private void daysSpinner()
    {
        ArrayList<String > array=new ArrayList<>();
        for(int i=1;i<=30;i++)
        {
            array.add(i+"");
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), R.layout.item_dropdwon,array);
        mBinding.daysSpinner.setAdapter(adapter);
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(PaymentFragment.this)
                        .navigate(R.id.goToselectPayment);
            }
        });
    }
}