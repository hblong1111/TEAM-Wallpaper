package com.longhb.myapplication.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.longhb.myapplication.R;
import com.longhb.myapplication.adapter.ListImageAdapter;
import com.longhb.myapplication.databinding.ActivitySearchBinding;
import com.longhb.myapplication.model.ImageDetail;
import com.longhb.myapplication.model.MyViewModelFactory;
import com.longhb.myapplication.utils.EndlessRecyclerViewScrollListener;
import com.longhb.myapplication.viewmodel.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivitySearchBinding binding;
    private SearchViewModel viewModel;

    private List<ImageDetail> list;
    private ListImageAdapter adapter;
    private GridLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configData();

        settingRCV();

        binding.btnBack.setOnClickListener(this);
        binding.edtSearch.setOnEditorActionListener((textView, i, keyEvent) -> {
            hideKeyboart();
            list.clear();
            adapter.notifyDataSetChanged();
            viewModel.searchImage(binding.edtSearch.getText().toString(), "" + list.size());
            return true;
        });
    }

    private void hideKeyboart() {

        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void settingRCV() {
        manager = new GridLayoutManager(this, 3);
        binding.rcv.setAdapter(adapter);
        binding.rcv.setLayoutManager(manager);
        binding.rcv.addOnScrollListener(new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                viewModel.searchImage(binding.edtSearch.getText().toString(), "" + list.size());
            }
        });
    }

    private void configData() {
        viewModel = new ViewModelProvider(this, new MyViewModelFactory(getApplication())).get(SearchViewModel.class);
        list = new ArrayList<>();
        adapter = new ListImageAdapter(list);
        viewModel.getMutableLiveDataListImage().observe(this, imageDetails -> {
            list.addAll(imageDetails);
            adapter.notifyDataSetChanged();
            if (list.size() == 0) {
                Toast.makeText(this, "No matching results were found.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;
        }
    }
}