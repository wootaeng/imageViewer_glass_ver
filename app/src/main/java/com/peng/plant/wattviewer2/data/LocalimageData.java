package com.peng.plant.wattviewer2.data;

public class LocalimageData {

    private String picturName;
    private String picturePath;
    private  String pictureSize;
    private  String imageUri;
    private Boolean selected = false;

    public LocalimageData(String picturName, String picturePath, String pictureSize, String imageUri) {
        this.picturName = picturName;
        this.picturePath = picturePath;
        this.pictureSize = pictureSize;
        this.imageUri = imageUri;

    }

    public LocalimageData() {

    }

    public String getPicturName() {
        return picturName;
    }

    public void setPicturName(String picturName) {
        this.picturName = picturName;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getPictureSize() {
        return pictureSize;
    }

    public void setPictureSize(String pictureSize) {
        this.pictureSize = pictureSize;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
