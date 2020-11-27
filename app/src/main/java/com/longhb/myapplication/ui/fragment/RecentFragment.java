package com.longhb.myapplication.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.longhb.myapplication.adapter.ListImageAdapter;
import com.longhb.myapplication.databinding.FragmentFeaturedBinding;
import com.longhb.myapplication.model.ImageDetailCategory;
import com.longhb.myapplication.utils.Conts;
import com.longhb.myapplication.utils.EndlessRecyclerViewScrollListener;
import com.longhb.myapplication.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class RecentFragment extends Fragment {
    private HomeViewModel model;
    private FragmentFeaturedBinding binding;
    private List<ImageDetailCategory> imageDetailList;
    private ListImageAdapter adapter;
    private GridLayoutManager layoutManager;

    private static RecentFragment INSTANCE;

    private RecentFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFeaturedBinding.inflate(inflater, container, false);

        createData();

        configView();

        return binding.getRoot();
    }

    private void configView() {
        settingRecyclerView();

        settingSwipeReferesh();
    }

    private void settingSwipeReferesh() {
        binding.swiperefresh.setOnRefreshListener(() -> {
            imageDetailList.clear();
            model.setListRecent(imageDetailList);
            model.getImageDetailOther(Conts.ACTION_GET_RECENT, imageDetailList.size() + "");
            binding.swiperefresh.setRefreshing(false);
        });
    }

    private void settingRecyclerView() {
        layoutManager = new GridLayoutManager(getContext(), 3);
        binding.rcv.setAdapter(adapter);
        binding.rcv.setLayoutManager(layoutManager);
        binding.rcv.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                binding.swiperefresh.setRefreshing(true);
                model.getImageDetailOther(Conts.ACTION_GET_RECENT, imageDetailList.size() + "");
                binding.swiperefresh.setRefreshing(false);
            }
        });
    }

    private void createData() {
        model = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        imageDetailList = new ArrayList<>();
        adapter = new ListImageAdapter(imageDetailList);
        model.getListRecent().observe(getActivity(), imageDetailCategories -> {
            imageDetailList.addAll(imageDetailCategories);
            adapter.notifyDataSetChanged();
        });

        model.getImageDetailOther(Conts.ACTION_GET_RECENT, imageDetailList.size() + "");
    }

    public static RecentFragment getINSTANCE() {
        if (INSTANCE==null){
            INSTANCE=new RecentFragment();
        }
        return INSTANCE;
    }
}
