package com.longhb.myapplication.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.longhb.myapplication.R;
import com.longhb.myapplication.adapter.AdapterViewPagerImage;
import com.longhb.myapplication.adapter.BottomSheetAdapter;
import com.longhb.myapplication.databinding.ActivityImageDetailBinding;
import com.longhb.myapplication.model.ImageDetail;
import com.longhb.myapplication.model.MyViewModelFactory;
import com.longhb.myapplication.utils.Conts;
import com.longhb.myapplication.viewmodel.ImageDetailViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ImageDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;
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

        settingViewPage();

        binding.mapContent.btnBack.setOnClickListener(view -> onBackPressed());

    }

    private void settingViewPage() {
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

                setIconFavorite();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setIconFavorite() {
        if (viewModel.checkImage(viewModel.getImageDetailCur().getImageId())) {
            binding.locationsListContent.btnFavorite.setImageResource(R.drawable.ic_star_white);
        } else {
            binding.locationsListContent.btnFavorite.setImageResource(R.drawable.ic_star_outline);
        }
    }

    private void settingBottomSheet(int position) {
        setIconFavorite();
        binding.locationsListContent.setImage(viewModel.getListImage().get(position));
        binding.locationsListContent.rcv.setAdapter(new BottomSheetAdapter(viewModel.getListImage().get(position).getArrTags()));
        binding.locationsListContent.rcv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        binding.locationsListContent.btnDownload.setOnClickListener(this);
        binding.locationsListContent.btnFavorite.setOnClickListener(this);
        binding.locationsListContent.btnSetWallpaper.setOnClickListener(this);
        binding.locationsListContent.btnShare.setOnClickListener(this);
    }

    private void handDataBeforActivity() {
        viewModel = new ViewModelProvider(this, new MyViewModelFactory(getApplication())).get(ImageDetailViewModel.class);
        viewModel.setListImage((List<ImageDetail>) getIntent().getSerializableExtra(Conts.CODE_PUT_LIST_IMAGE));
        viewModel.setNumCur(getIntent().getIntExtra(Conts.CODE_PUT_CUSOR, 0));
    }

    @Override
    public void onClick(View view) {
        ImageDetail imageDetail = viewModel.getImageDetailCur();
        switch (view.getId()) {
            case R.id.btnDownload:
                downloadImage(imageDetail.getUrlImage(), imageDetail.getImageUpload());
                break;
            case R.id.btnShare:
                shareItem(imageDetail.getUrlImage());
                break;
            case R.id.btnSetWallpaper:
                break;
            case R.id.btnFavorite:
                favouriteImage(imageDetail);
                break;
        }
    }


    private void downloadImage(String urlImage, String name) {
        //ask for permisson
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat
                    .requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }

        //Download
        new TaskDownloadImage(this).execute(new String[]{urlImage, name});

    }


    private void shareItem(String urlImage) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, "Sharing Image");
        i.putExtra(Intent.EXTRA_TEXT, urlImage);
        startActivity(Intent.createChooser(i, "Share Image"));
    }


    private void favouriteImage(ImageDetail imageDetail) {
        if (viewModel.checkImage(imageDetail.getImageId())) {
            viewModel.deleteImage(imageDetail);
            Toast.makeText(this, "Delete image!", Toast.LENGTH_SHORT).show();
        } else {
            viewModel.insertImage(imageDetail);
            Toast.makeText(this, "Insert image!", Toast.LENGTH_SHORT).show();
        }
        setIconFavorite();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT)
                            .show();
                    //TODO
                } else {
                    // Permission Denied
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    class TaskDownloadImage extends AsyncTask<String, Void, Void> {
        private ProgressDialog dialog;

        public TaskDownloadImage(Context context) {
            this.dialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Downloading, please wait...");
            dialog.show();
        }


        @Override
        protected void onPostExecute(Void result) {
            // do UI work here
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
        @Override
        protected Void doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoOutput(true);
                urlConnection.connect();
                File SDCardRoot = Environment.getExternalStorageDirectory().getAbsoluteFile();
                File file = new File(SDCardRoot, strings[1]);
                if (file.createNewFile()) {
                    file.createNewFile();
                }
                FileOutputStream fileOutput = new FileOutputStream(file.getAbsoluteFile());
                InputStream inputStream = urlConnection.getInputStream();
                int totalSize = urlConnection.getContentLength();
                int downloadedSize = 0;
                byte[] buffer = new byte[1024];
                int bufferLength = 0;
                while ((bufferLength = inputStream.read(buffer)) > 0) {
                    fileOutput.write(buffer, 0, bufferLength);
                    downloadedSize += bufferLength;
                    Log.i("Progress:", "downloadedSize:" + downloadedSize + "totalSize:" + totalSize);
                }
                fileOutput.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
