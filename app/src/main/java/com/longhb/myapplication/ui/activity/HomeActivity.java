package com.longhb.myapplication.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.longhb.myapplication.R;
import com.longhb.myapplication.adapter.ViewPagerHomeAdapter;
import com.longhb.myapplication.databinding.ActivityHomeBinding;
import com.longhb.myapplication.model.Category;
import com.longhb.myapplication.model.MyViewModelFactory;
import com.longhb.myapplication.ui.fragment.FavoriteFragment;
import com.longhb.myapplication.ui.fragment.HomeFragment;
import com.longhb.myapplication.utils.HomeFragmentEvent;
import com.longhb.myapplication.viewmodel.HomeViewModel;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements  HomeFragmentEvent, NavigationView.OnNavigationItemSelectedListener {

    private HomeViewModel viewModel;

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this, new MyViewModelFactory(getApplication())).get(HomeViewModel.class);

        getSupportFragmentManager().beginTransaction().replace(R.id.frContainer, new HomeFragment(this)).commit();

        binding.nav.setNavigationItemSelectedListener(this);

    }

    @Override
    public void openNavigationView() {
        binding.drawLayout.openDrawer(binding.nav);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_nav_explore:
                getSupportFragmentManager().beginTransaction().replace(R.id.frContainer, new HomeFragment(this)).commit();
                break;
            case R.id.item_nav_favorite:
                getSupportFragmentManager().beginTransaction().replace(R.id.frContainer, new FavoriteFragment(this)).commit();
                break;
        }
        binding.drawLayout.closeDrawer(binding.nav);
        return true;
    }
}