package com.example.taysir.UserAccess;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taysir.R;
import com.example.taysir.databinding.FragmentStartBinding;

public class StartFragment extends Fragment {
    private FragmentStartBinding mBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentStartBinding.inflate(inflater,container,false);
        funStart();
        return mBinding.getRoot();
    }
    private void funStart()
    {
        mBinding.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(StartFragment.this)
                        .navigate(R.id.selectAccessType);
            }
        });
    }
}