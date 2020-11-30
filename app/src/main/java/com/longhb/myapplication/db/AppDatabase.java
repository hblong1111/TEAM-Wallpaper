package com.longhb.myapplication.db;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.longhb.myapplication.model.ImageDetail;

@Database(entities = {ImageDetail.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ImageDao userDao();
}
