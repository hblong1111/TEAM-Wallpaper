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
//        binding.viewPager.setOffscreenPageLimit(5);
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        Log.d("longhbb", "onCreateView: ");

        binding.imageButton.setOnClickListener(this);
        return binding.getRoot();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageButton:
                callback.openNavigationView();
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("longhbb", "onResume: ");

        viewModel.getAllCategory();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("longhbb", "onCreate: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("longhbb", "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("longhbb", "onStop: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("longhbb", "onStart: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("longhbb", "onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("longhbb", "onDestroy: ");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d("longhbb", "onAttach: ");
    }

    @Override
    public void onAttachFragment(@NonNull Fragment childFragment) {
        super.onAttachFragment(childFragment);
        Log.d("longhbb", "onAttachFragment: ");
    }
}
