package com.example.taysir.UserAccess;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.taysir.R;
import com.example.taysir.databinding.FragmentBrokerCompleteProfileBinding;
import com.example.taysir.databinding.FragmentBrokerProfileBinding;

import java.util.ArrayList;

public class BrokerCompleteProfile extends Fragment {

   private FragmentBrokerCompleteProfileBinding mBinding;
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

        return mBinding.getRoot();
    }
    private void funGenderSpinner()
    {
        ArrayList<String > array=new ArrayList<>();
        array.add("ذكر");
        array.add("أنثي");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), R.layout.item_dropdwon,array);
        mBinding.genderSpinner.setAdapter(adapter);

    }
}