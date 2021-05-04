package com.ru.crypto;

import android.content.ContentValues;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ru.crypto.api.INetworkService;
import com.ru.crypto.di.components.GlobalDataRepoComponent;
import com.ru.crypto.models.GlobalCryptoData;
import com.ru.crypto.repositories.CryptoCurrencyRepository;
import com.ru.crypto.repositories.GlobalDataRepository;
import com.ru.crypto.ui.currency_profile.CurrencyProfileFragment;
import com.ru.crypto.ui.fragments.TimeRangeFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Date;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.bottom_navigation);

        NavHostFragment navHostFragment =(NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    public BottomNavigationView getNav() {
        return  navigationView;
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
}