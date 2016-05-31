package com.blz.gundam_database.entities;

/**
 * Created by BuLingzhuang
 * on 2016/5/31
 * E-mail bulingzhuang@foxmail.com
 */
public class MSDetailImageEntity {
    private String imageUri;
    private int height;
    private int width;

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public MSDetailImageEntity(String imageUri, int height, int width) {

        this.imageUri = imageUri;
        this.height = height;
        this.width = width;
    }
}
