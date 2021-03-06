package com.longhb.myapplication.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.longhb.myapplication.R;
import com.longhb.myapplication.adapter.ListImageAdapter;
import com.longhb.myapplication.databinding.ActivityCategoryDetailBinding;
import com.longhb.myapplication.model.ImageDetail;
import com.longhb.myapplication.model.MyViewModelFactory;
import com.longhb.myapplication.utils.Conts;
import com.longhb.myapplication.utils.EndlessRecyclerViewScrollListener;
import com.longhb.myapplication.viewmodel.ListImageViewModel;

import java.util.ArrayList;
import java.util.List;


public class CategoryDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private ListImageViewModel viewModel;
    private ActivityCategoryDetailBinding binding;

    private List<ImageDetail> imageDetailList;
    private ListImageAdapter adapter;
    private GridLayoutManager layoutManager;

    private String idCategory;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this,new MyViewModelFactory(getApplication())).get(ListImageViewModel.class);


        createData();

        settingRecyclerView();

        setOnClick();

        settingSwipeRefresh();
    }

    private void setOnClick() {
        binding.imageButton.setOnClickListener(this);
    }

    private void settingRecyclerView() {
        layoutManager = new GridLayoutManager(this, 3);
        binding.rcv.setAdapter(adapter);
        binding.rcv.setLayoutManager(layoutManager);

        binding.rcv.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                binding.swiperefresh.setRefreshing(true);
                viewModel.getList(idCategory, imageDetailList.size() + "");
                binding.swiperefresh.setRefreshing(false);
            }
        });
    }

    private void createData() {
        idCategory = getIntent().getStringExtra(Conts.CODE_PUT_ID_CATEGORY);
        title = getIntent().getStringExtra(Conts.CODE_PUT_TITLE_CATEGORY);

        binding.setTitle(title);

        imageDetailList = new ArrayList<>();
        adapter = new ListImageAdapter(imageDetailList);

        viewModel.getMutableLiveDataListDetail().observe(this, imageDetailCategories -> {
            imageDetailList.addAll(imageDetailCategories);
            adapter.notifyDataSetChanged();
        });

        viewModel.getList(idCategory, imageDetailList.size() + "");
    }

    private void settingSwipeRefresh() {
        binding.swiperefresh.setOnRefreshListener(() -> {
            imageDetailList.clear();
            viewModel.setMutableLiveDataListDetail(imageDetailList);
            viewModel.getList(idCategory, imageDetailList.size()+"");
            binding.swiperefresh.setRefreshing(false);
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageButton:
                onBackPressed();
                break;
        }
    }
}