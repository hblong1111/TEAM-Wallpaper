package com.longhb.myapplication.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.longhb.myapplication.utils.Conts;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;

@Entity
public class    ImageDetail implements Serializable {

    @SerializedName("no")
    @Expose
    private Integer no;
    @PrimaryKey
    @ColumnInfo(name = "image_id")
    @SerializedName("image_id")
    @Expose
    private int imageId;
    @SerializedName("image_upload")
    @Expose
    private String imageUpload;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("view_count")
    @Expose
    private String viewCount;
    @SerializedName("download_count")
    @Expose
    private String downloadCount;
    @SerializedName("featured")
    @Expose
    private String featured;
    @SerializedName("tags")
    @Expose
    private String tags;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("category_name")
    @Expose
    private String categoryName;


    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getImageUpload() {
        return imageUpload;
    }

    public void setImageUpload(String imageUpload) {
        this.imageUpload = imageUpload;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(String downloadCount) {
        this.downloadCount = downloadCount;
    }

    public String getFeatured() {
        return featured;
    }

    public void setFeatured(String featured) {
        this.featured = featured;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getViews() {
        return Math.floor(Float.parseFloat(viewCount)/100 ) / 10 + "K";
    }

    public String getDownload() {
        return Math.floor(Float.parseFloat(downloadCount)/ 100) / 10 + "K";
    }


    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


    public String getUrlImage() {
        return Conts.URL_IMAGE + imageUpload;
    }

    public String[] getArrTags(){
        return tags.split(",");
    }
}

