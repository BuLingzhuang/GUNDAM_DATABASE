package com.blz.gundam_database.entities;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "MOBILE_SUIT_ENTITY".
 */
public class MobileSuitEntity {

    private String objectId;
    private String workId;
    private String originalName;
    private String modelSeries;
    private String scale;
    private String itemNo;
    private String launchDate;
    private String price;
    private String images;
    private String headImage;

    public MobileSuitEntity() {
    }

    public MobileSuitEntity(String objectId) {
        this.objectId = objectId;
    }

    public MobileSuitEntity(String objectId, String workId, String originalName, String modelSeries, String scale, String itemNo, String launchDate, String price, String images, String headImage) {
        this.objectId = objectId;
        this.workId = workId;
        this.originalName = originalName;
        this.modelSeries = modelSeries;
        this.scale = scale;
        this.itemNo = itemNo;
        this.launchDate = launchDate;
        this.price = price;
        this.images = images;
        this.headImage = headImage;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getModelSeries() {
        return modelSeries;
    }

    public void setModelSeries(String modelSeries) {
        this.modelSeries = modelSeries;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(String launchDate) {
        this.launchDate = launchDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

}