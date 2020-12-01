package com.longhb.myapplication.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.longhb.myapplication.R;
import com.longhb.myapplication.adapter.ViewPagerHomeAdapter;
import com.longhb.myapplication.databinding.FragmentHomeBinding;
import com.longhb.myapplication.utils.HomeFragmentEvent;
import com.longhb.myapplication.viewmodel.HomeViewModel;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;

    private HomeFragmentEvent callback;

    public HomeFragment(HomeFragmentEvent callback) {
        this.callback = callback;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        binding.viewPager.setAdapter(new ViewPagerHomeAdapter(getActivity().getSupportFragmentManager()));
        binding.viewPager.setCurrentItem(3);
        binding.viewPager.setOffscreenPageLimit(5);
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        Log.d("longhbb", "onCreateView: ");

        binding.imageButton.setOnClickListener(this);
        binding.imageButton2.setOnClickListener(this);
        viewModel.getAllCategory();
        return binding.getRoot();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageButton:
                callback.openNavigationView();
                break;
            case R.id.imageButton2:
                callback.searchImage();
                break;
        }
    }



}
