package com.longhb.myapplication.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.longhb.myapplication.model.ImageDetail;

import java.util.List;

@Dao
public interface ImageDao {

    @Query("SELECT * FROM imagedetail")
    List<ImageDetail> getAll();

    @Insert
    void insert(ImageDetail imageDetail);

    @Delete
    void delete(ImageDetail imageDetail);

    @Query("SELECT * FROM ImageDetail WHERE image_id =:id")
    long check(int id);
}
