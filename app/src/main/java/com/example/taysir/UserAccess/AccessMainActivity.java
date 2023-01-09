package com.example.taysir.UserAccess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.taysir.R;
import com.example.taysir.databinding.ActivityAccessMainBinding;

public class AccessMainActivity extends AppCompatActivity {
     private ActivityAccessMainBinding mBinding;
    private AppBarConfiguration appBarConfiguration;
    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding=ActivityAccessMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());


    }
    private void funNavController()
    {
        navController = Navigation.findNavController(this, R.id.access_nav_host_fragment);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.access_nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}