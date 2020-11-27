package com.longhb.myapplication.adapter;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;

import com.longhb.myapplication.model.ImageDetailCategory;
import com.longhb.myapplication.ui.fragment.ImageDetailFragment;
import com.longhb.myapplication.viewmodel.ImageDetailViewModel;

import java.util.List;

public class AdapterViewPagerImage extends FragmentPagerAdapter {
    List<ImageDetailCategory> list;
    public AdapterViewPagerImage(@NonNull FragmentManager fm, List<ImageDetailCategory> list) {
        super(fm);
        this.list = list;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return new ImageDetailFragment(list.get(position).getUrlImage());
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
