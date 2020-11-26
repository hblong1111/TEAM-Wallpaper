package com.longhb.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.longhb.myapplication.utils.Conts;

public class Category {
    @SerializedName("category_id")
    @Expose
    public String categoryId;
    @SerializedName("category_name")
    @Expose
    public String categoryName;
    @SerializedName("category_image")
    @Expose
    public String categoryImage;
    @SerializedName("total_wallpaper")
    @Expose
    public String totalWallpaper;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public String getMyTotalWallpaper() {
        return totalWallpaper + " Wallpapers";
    }

    public String getUrlImage() {
        return Conts.URL_IMAGE_CATEGORY + categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getTotalWallpaper() {
        return totalWallpaper;
    }

    public void setTotalWallpaper(String totalWallpaper) {
        this.totalWallpaper = totalWallpaper;
    }


}
