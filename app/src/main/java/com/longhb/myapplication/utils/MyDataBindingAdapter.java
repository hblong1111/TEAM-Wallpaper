package com.longhb.myapplication.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.longhb.myapplication.R;

import java.io.IOException;
import java.net.URL;

public class MyDataBindingAdapter {
    @BindingAdapter("loadImage")
    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView).load(url).placeholder(R.drawable.ic_thumbnail).into(imageView);
    }

    @BindingAdapter("getSize")
    public static void getSize(TextView imageView, String url) {
        new TaskGetDetail(imageView).execute(url);
    }

    static class TaskGetDetail extends AsyncTask<String, String, String> {

        private TextView textView;

        public TaskGetDetail(TextView textView) {
            this.textView = textView;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                return bmp.getWidth() + "x" + bmp.getHeight();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textView.setText(s);
        }
    }
}
