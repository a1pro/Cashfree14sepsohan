package com.app.cashfree.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.app.cashfree.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Retrofit;

public class HomeActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private BottomNavigationView bottom_view;
    private NavController navController;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.add_bene:
                    navController.navigate(R.id.scannerFragment);
                    return true;
                case R.id.setting:
                    navController.navigate(R.id.settingFragment);
                    return true;
                case R.id.home:
                    navController.navigate(R.id.homeFragment);
                    return true;
            }
            return false;
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        navController = Navigation.findNavController(HomeActivity.this, R.id.nav_host_fragment);
        init();
    }

    private void init() {
        bottom_view = findViewById(R.id.bottom_view);
        bottom_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bottom_view.setSelectedItemId(R.id.bottom_view);
        navController.navigate(R.id.homeFragment);
        navController.getCurrentDestination().getId();
        navController.addOnDestinationChangedListener(onDestinationChangedListener);
    }


    NavController.OnDestinationChangedListener onDestinationChangedListener = new NavController.OnDestinationChangedListener() {
        @Override
        public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
            if (R.id.homeFragment == destination.getId()) {
                bottom_view.getMenu().getItem(0).setChecked(true);
            }
            if (R.id.scannerFragment == destination.getId()) {
                bottom_view.getMenu().getItem(1).setChecked(true);
            }
            if (R.id.settingFragment == destination.getId()) {
                bottom_view.getMenu().getItem(2).setChecked(true);
            }
        }
    };
}