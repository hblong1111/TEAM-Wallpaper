package com.longhb.myapplication.ui.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupMenu;
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
    private static final int CODE_HOME_SCREEN = 111;
    private static final int CODE_LOCK_SCREEN = 222;
    ActivityImageDetailBinding binding;


    ImageDetailViewModel viewModel;
    private String urlImageDownload;
    private String nameImageDowload;
    private String pathRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImageDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //lay du lieu tu man truoc
        handDataBeforActivity();

        settingBottomSheet(viewModel.getNumCur());

        settingViewPage();

        setOnClickView();

        binding.mapContent.btnBack.setOnClickListener(view -> onBackPressed());

    }

    private void setOnClickView() {
        binding.locationsListContent.btnDownload.setOnClickListener(this);
        binding.locationsListContent.btnFavorite.setOnClickListener(this);
        binding.locationsListContent.btnSetWallpaper.setOnClickListener(this);
        binding.locationsListContent.btnShare.setOnClickListener(this);
        binding.locationsListContent.view.setOnClickListener(this);
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
            binding.locationsListContent.btnFavorite.setImageResource(R.drawable.ic_baseline_favorite);
        } else {
            binding.locationsListContent.btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_border);
        }
    }

    private void settingBottomSheet(int position) {
        setIconFavorite();
        binding.locationsListContent.setImage(viewModel.getListImage().get(position));
        binding.locationsListContent.rcv.setAdapter(new BottomSheetAdapter(viewModel.getListImage().get(position).getArrTags()));
        binding.locationsListContent.rcv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

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
                urlImageDownload = imageDetail.getUrlImage();
                nameImageDowload = imageDetail.getImageUpload();
                break;
            case R.id.btnShare:
                shareItem(imageDetail.getUrlImage());
                break;
            case R.id.btnSetWallpaper:
                showPopup(imageDetail.getUrlImage());
                break;
            case R.id.btnFavorite:
                favouriteImage(imageDetail);
                break;
            case R.id.view:
                break;
        }
    }

    private void showPopup(String urlImage) {
        PopupMenu popup = new PopupMenu(this, binding.locationsListContent.btnSetWallpaper);
        //Inflating the Popup using xml file
        popup.getMenuInflater()
                .inflate(R.menu.popup_menu, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(item -> {
            int code = 0;
            switch (item.getItemId()) {
                case R.id.item_menu_home_screen:
                    code = CODE_HOME_SCREEN;
                    break;
                case R.id.item_menu_lock_screen:
                    code = CODE_LOCK_SCREEN;
                    break;
                case R.id.item_menu_both:
                    code = CODE_HOME_SCREEN + CODE_LOCK_SCREEN;
                    break;
            }

            new TaskSetWallpaper(code).execute(urlImage);
            return true;
        });

        popup.show(); //showing popup menu
    }



    class TaskSetWallpaper extends AsyncTask<String, Void, InputStream> implements com.longhb.myapplication.ui.activity.TaskSetWallpaper {
        private ProgressDialog dialog;

        private int isLock;

        public TaskSetWallpaper(int isLock) {
            this.isLock = isLock;
            this.dialog = new ProgressDialog(ImageDetailActivity.this);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Set Wallpaper, please wait...");
            dialog.show();
        }


        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(InputStream result) {
            // do UI work here
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream ins = null;
            try {
                WallpaperManager wpm = WallpaperManager.getInstance(ImageDetailActivity.this);
                ins = new URL(strings[0]).openStream();
                if (isLock == CODE_HOME_SCREEN) {
                    wpm.setStream(ins, null, true, WallpaperManager.FLAG_SYSTEM);
                } else if (isLock == CODE_LOCK_SCREEN) {
                    wpm.setStream(ins, null, true, WallpaperManager.FLAG_LOCK);
                } else {
                    wpm.setStream(ins);
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ins;
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
        } else {
            viewModel.insertImage(imageDetail);
        }
        setIconFavorite();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //TODO
                    downloadImage(urlImageDownload, nameImageDowload);
                } else {
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    class TaskDownloadImage extends AsyncTask<String, Void, Integer> {
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
        protected void onPostExecute(Integer result) {
            // do UI work here
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            if (result != 0) {
                Toast.makeText(ImageDetailActivity.this, "Download success!", Toast.LENGTH_SHORT).show();
                refreshGallery(ImageDetailActivity.this,pathRefresh);
            } else {
                Toast.makeText(ImageDetailActivity.this, "Download error!", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected Integer doInBackground(String... strings) {
            int downloadedSize = 0;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoOutput(true);
                urlConnection.connect();
                File folder = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), getString(R.string.app_name));
                if(!folder.exists()){
                    folder.mkdirs();
                }
                File file = new File(folder.getPath(), strings[1]);
                FileOutputStream fileOutput = new FileOutputStream(file.getAbsoluteFile());
                InputStream inputStream = urlConnection.getInputStream();
                byte[] buffer = new byte[1024];
                int bufferLength = 0;
                while ((bufferLength = inputStream.read(buffer)) > 0) {
                    fileOutput.write(buffer, 0, bufferLength);
                    downloadedSize += bufferLength;
                }
                fileOutput.close();
                pathRefresh=file.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return downloadedSize;
        }
    }
    public static void refreshGallery(Context context, String path) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(Uri.fromFile(new File(path)));
        context.sendBroadcast(mediaScanIntent);
    }
}
