package com.longhb.myapplication.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.longhb.myapplication.adapter.AdapterViewPagerImage;
import com.longhb.myapplication.adapter.BottomSheetAdapter;
import com.longhb.myapplication.databinding.ActivityImageDetailBinding;
import com.longhb.myapplication.model.ImageDetailCategory;
import com.longhb.myapplication.utils.Conts;
import com.longhb.myapplication.viewmodel.ImageDetailViewModel;

import java.util.List;

public class ImageDetailActivity extends AppCompatActivity {
    ActivityImageDetailBinding binding;


    ImageDetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImageDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //lay du lieu tu man truoc
        handDataBeforActivity();

        settingBottomSheet(viewModel.getNumCur());

        binding.mapContent.viewPager.setAdapter(new AdapterViewPagerImage(getSupportFragmentManager(), viewModel.getListImage()));
        binding.mapContent.viewPager.setCurrentItem(viewModel.getNumCur());
        binding.mapContent.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                settingBottomSheet(position);
                viewModel.setNumCur(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void settingBottomSheet(int position) {
        binding.locationsListContent.setImage(viewModel.getListImage().get(position));
        binding.locationsListContent.rcv.setAdapter(new BottomSheetAdapter(viewModel.getListImage().get(position).getArrTags()));
        binding.locationsListContent.rcv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
    }

    private void handDataBeforActivity() {
        viewModel = new ViewModelProvider(this).get(ImageDetailViewModel.class);
        viewModel.setListImage((List<ImageDetailCategory>) getIntent().getSerializableExtra(Conts.CODE_PUT_LIST_IMAGE));
        viewModel.setNumCur(getIntent().getIntExtra(Conts.CODE_PUT_CUSOR, 0));
    }

}