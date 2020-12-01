package com.longhb.myapplication.repository;

import android.app.Application;

import androidx.room.Room;

import com.longhb.myapplication.db.AppDatabase;
import com.longhb.myapplication.model.ImageDetail;
import com.longhb.myapplication.model.RetrofitModul;
import com.longhb.myapplication.network.RetrofitService;
import com.longhb.myapplication.utils.Conts;

import java.util.List;

import retrofit2.Call;

public class ImageDetailRepository {
    private RetrofitService retrofitService;
    AppDatabase db;

    public ImageDetailRepository(Application application) {
        this.retrofitService = RetrofitModul.getINSTANCE();
        db = Room.databaseBuilder(application,
                AppDatabase.class, Conts.DATABASE_NAME).allowMainThreadQueries().build();
    }

    public Call<List<ImageDetail>> getAllCategory(String idCategory, String offset) {
        return retrofitService.getListImageDetail(Conts.ACTION_GET_CATEGORY_DETAIL, idCategory, offset);
    }

    public Call<List<ImageDetail>> getImageDetailOther(String action, String offset) {
        return retrofitService.getImageOther(action, offset);
    }

    public Call<List<ImageDetail>> searchImage(String action,String search, String offset) {
        return retrofitService.searchImage(action,search, offset);
    }

    public long checkImage(int id) {
        return db.userDao().check(id);
    }

    public void insert(ImageDetail image) {
        db.userDao().insert(image);
    }

    public void delete(ImageDetail image) {
        db.userDao().delete(image);
    }

    public List<ImageDetail> getAll() {
        return db.userDao().getAll();
    }
}
