package com.longhb.myapplication.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
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

import doubled.rate.RateDialog;


public class HomeActivity extends AppCompatActivity implements HomeFragmentEvent, NavigationView.OnNavigationItemSelectedListener {

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
    public void searchImage() {
        startActivity(new Intent(HomeActivity.this,SearchActivity.class));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_nav_explore:
                getSupportFragmentManager().beginTransaction().replace(R.id.frContainer, new HomeFragment(this)).commit();
                break;
            case R.id.item_nav_favorite:
                getSupportFragmentManager().beginTransaction().replace(R.id.frContainer, new FavoriteFragment(this)).commit();
                break;
            case R.id.item_nav_share:
                shareText(HomeActivity.this, getString(R.string.app_name)+"\nhttps://play.google.com/store/apps/details?id="+getPackageName(), "");
                break;
            case R.id.item_nav_policy:
                privacyPolicy(this, getString(R.string.policy));
                break;
            case R.id.item_nav_rate:
                rateApp(false);
                break;
        }
        binding.drawLayout.closeDrawer(binding.nav);
        return false;
    }

    private void rateApp(boolean b) {
        RateDialog dialog=new RateDialog(this);
        if (!dialog.isRate()){
            dialog.show(b);
        }else if (b){
            finish();
        }
    }

    public static void privacyPolicy(Context context, String link) {
        Uri uri = Uri.parse(link);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    public static void shareText(Context context, String text, String subject) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        context.startActivity(Intent.createChooser(shareIntent, "Share..."));
    }

    @Override
    public void onBackPressed() {
        rateApp(true);
    }
}