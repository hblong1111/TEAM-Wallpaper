package com.longhb.myapplication.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.longhb.myapplication.ui.fragment.CategoryFragment;
import com.longhb.myapplication.ui.fragment.FeaturedFragment;
import com.longhb.myapplication.ui.fragment.PopularFragment;
import com.longhb.myapplication.ui.fragment.RandomFragment;
import com.longhb.myapplication.ui.fragment.RecentFragment;

public class ViewPagerHomeAdapter extends FragmentStatePagerAdapter {
    private String[] strings = new String[]{"category", "recent", "featured", "popular", "random"};

    public ViewPagerHomeAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return CategoryFragment.newInstance();
            case 1:
                return   RecentFragment.newInstance();
            case 2:
                return   FeaturedFragment.newInstance();
            case 3:
                return   PopularFragment.newInstance();
            case 4:
                return   RandomFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return strings.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return strings[position];
    }
}
