package com.longhb.myapplication.network;

import com.longhb.myapplication.model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {
    @GET("/battleroyale/api/api.php")
    Call<List<Category>> getAllCategory(@Query("action") String action);
}
