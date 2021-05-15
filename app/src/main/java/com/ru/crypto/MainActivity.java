package com.ru.crypto;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ru.crypto.services.RestartBroadcastReceiver;
import com.ru.crypto.services.ServiceAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationView = findViewById(R.id.bottom_navigation);

        NavHostFragment navHostFragment =(NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(mNavigationView, navController);

    }

    @Override
    protected void onStart() {
        super.onStart();
        initializeToolbar();

    }

    public void initializeToolbar () {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.getMenu();
        MenuItem menuItem = toolbar.getMenu().findItem(R.id.action_search);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            RestartBroadcastReceiver.scheduleJob(this);
        } else {
            ServiceAdmin bck = new ServiceAdmin();
            bck.launchService(this);
        }
    }

    public BottomNavigationView getNav() {
        return mNavigationView;
    }


}