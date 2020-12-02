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

import com.longhb.myapplication.adapter.ListImageAdapter;
import com.longhb.myapplication.databinding.FragmentFavoriteBinding;
import com.longhb.myapplication.model.ImageDetail;
import com.longhb.myapplication.utils.HomeFragmentEvent;
import com.longhb.myapplication.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {
    private static FavoriteFragment INSTANCE;
    private HomeViewModel viewModel;
    private FragmentFavoriteBinding binding;

    private List<ImageDetail> listFavorite;
    private ListImageAdapter adapter;

    private HomeFragmentEvent callback;

    public FavoriteFragment(HomeFragmentEvent callback) {
        this.callback = callback;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);

        createData();

        settingRecyclerView();

        binding.imageButton.setOnClickListener(view -> callback.openNavigationView());

        return binding.getRoot();
    }

    private void settingRecyclerView() {
        binding.rcv.setAdapter(adapter);
        binding.rcv.setLayoutManager(new GridLayoutManager(getContext(), 3));
    }

    private void createData() {
        listFavorite = new ArrayList<>();
        adapter = new ListImageAdapter(listFavorite);
        viewModel.getListFavorite().observe(getActivity(), imageDetails -> {
            listFavorite.addAll(imageDetails);
            adapter.notifyDataSetChanged();
            if (listFavorite.size()!=0){
                binding.tvMess.setVisibility(View.GONE);
            }else {
                binding.tvMess.setVisibility(View.VISIBLE);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        listFavorite.clear();
        adapter.notifyDataSetChanged();
        viewModel.getAllFavorite();
    }
}
