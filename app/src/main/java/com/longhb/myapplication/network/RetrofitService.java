package com.longhb.myapplication.network;

import com.longhb.myapplication.model.Category;
import com.longhb.myapplication.model.ImageDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {
    @GET("/battleroyale/api/api.php")
    Call<List<Category>> getAllCategory(@Query("action") String action);

    @GET("/battleroyale/api/api.php")
    Call<List<ImageDetail>> getListImageDetail(@Query("action") String action,
                                               @Query("id") String id,
                                               @Query("offset") String offset);

    @GET("/battleroyale/api/api.php")
    Call<List<ImageDetail>> getImageOther(@Query("action") String action,
                                          @Query("offset") String offset);

    @GET("/battleroyale/api/api.php")
    Call<List<ImageDetail>> searchImage(@Query("action") String action,
                                         @Query("search") String search,
                                          @Query("offset") String offset);
}
