package com.app.taysir.UserAccess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import com.app.taysir.R;
import com.app.taysir.databinding.ActivityAccessMainBinding;

import java.util.Locale;

public class AccessMainActivity extends AppCompatActivity {
     private ActivityAccessMainBinding mBinding;
    private AppBarConfiguration appBarConfiguration;
    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding=ActivityAccessMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        changeLocalLanguage("en");
        funNavController();

    }
    private void changeLocalLanguage(String lang)
    {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources resources = this.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
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