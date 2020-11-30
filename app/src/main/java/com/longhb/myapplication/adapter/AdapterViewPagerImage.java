package com.longhb.myapplication.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.longhb.myapplication.model.ImageDetail;
import com.longhb.myapplication.ui.fragment.ImageDetailFragment;

import java.util.List;

public class AdapterViewPagerImage extends FragmentPagerAdapter {
    List<ImageDetail> list;
    public AdapterViewPagerImage(@NonNull FragmentManager fm, List<ImageDetail> list) {
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
