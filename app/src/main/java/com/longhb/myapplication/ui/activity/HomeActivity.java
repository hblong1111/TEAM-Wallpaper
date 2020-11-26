package com.longhb.myapplication.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.longhb.myapplication.R;
import com.longhb.myapplication.adapter.ViewPagerHomeAdapter;
import com.longhb.myapplication.databinding.ActivityHomeBinding;
import com.longhb.myapplication.model.Category;
import com.longhb.myapplication.viewmodel.HomeViewModel;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private HomeViewModel viewModel;

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding.viewPager.setAdapter(new ViewPagerHomeAdapter(getSupportFragmentManager()));
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        viewModel.getAllCategory();


    }
}