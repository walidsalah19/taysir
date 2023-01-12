package com.example.taysir.Broker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.taysir.R;
import com.example.taysir.databinding.ActivityBrokerMainBinding;
import com.example.taysir.databinding.ActivityCustomerMainBinding;

public class BrokerMainActivity extends AppCompatActivity {
    private ActivityBrokerMainBinding mBinding;
    private AppBarConfiguration appBarConfiguration;
    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding= ActivityBrokerMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        funNavController();

    }
    private void funNavController()
    {
        navController = Navigation.findNavController(this, R.id.broker_nav_host_fragment);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.broker_nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}