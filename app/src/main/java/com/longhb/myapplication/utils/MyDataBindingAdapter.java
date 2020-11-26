package com.longhb.myapplication.utils;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.longhb.myapplication.R;

public class MyDataBindingAdapter  {
    @BindingAdapter("loadImage")
    public static void loadImage( ImageView imageView,String url){
        Glide.with(imageView).load(url).placeholder(R.drawable.ic_thumbnail).into(imageView);
    }
}
