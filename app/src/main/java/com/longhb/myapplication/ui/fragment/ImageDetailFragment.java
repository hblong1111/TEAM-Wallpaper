package com.longhb.myapplication.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.longhb.myapplication.databinding.FragmentImageDetailBinding;

public class ImageDetailFragment extends Fragment {
    FragmentImageDetailBinding binding;
    String urlImage;

    public ImageDetailFragment(String urlImage) {
        this.urlImage = urlImage;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentImageDetailBinding.inflate(inflater, container, false);
        ImageView imageView = binding.photoView;
        PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(imageView);
        photoViewAttacher.setScaleType(ImageView.ScaleType.CENTER_CROP);
        binding.setUrl(urlImage);
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("longhbb", "onDestroy: ");
    }
}
