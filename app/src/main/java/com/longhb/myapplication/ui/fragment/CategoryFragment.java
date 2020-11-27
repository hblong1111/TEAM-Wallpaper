package com.longhb.myapplication.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.longhb.myapplication.adapter.CategoryAdapter;
import com.longhb.myapplication.databinding.FragmentCategoryBinding;
import com.longhb.myapplication.model.Category;
import com.longhb.myapplication.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {
    HomeViewModel model;

    private FragmentCategoryBinding binding;

    private CategoryAdapter adapter;

    private List<Category> categoryList;
    private static CategoryFragment INSTANCE;

    private CategoryFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentCategoryBinding.inflate(inflater);


        model = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);


        categoryList = new ArrayList<>();
        adapter = new CategoryAdapter(categoryList);

        binding.rcv.setAdapter(adapter);

        binding.rcv.setLayoutManager(new LinearLayoutManager(getContext()));

        model.getListCategoryLiveData().observe(this, categories -> {
            categoryList.addAll(categories);
            adapter.notifyDataSetChanged();
        });

        settingSwipeRefresh();

        return binding.getRoot();
    }

    private void settingSwipeRefresh() {
        binding.swiperefresh.setOnRefreshListener(() -> {
            categoryList.clear();
            model.setListCategoryLiveData(categoryList);
            model.getAllCategory();
            binding.swiperefresh.setRefreshing(false);
        });
    }

    public static CategoryFragment getINSTANCE() {
        if (INSTANCE == null) INSTANCE = new CategoryFragment();
        return INSTANCE;
    }
}
