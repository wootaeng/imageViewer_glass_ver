package com.peng.plant.wattviewer2.data;

public class LocalFolderData {

    private  String path;
    private  String FolderName;
    private int numberOfPics = 0;
    private String firstPic;

    public LocalFolderData(String path, String folderName, int numberOfPics, String firstPic) {
        this.path = path;
        FolderName = folderName;
        this.numberOfPics = numberOfPics;
        this.firstPic = firstPic;
    }

    public LocalFolderData() {

    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFolderName() {
        return FolderName;
    }

    public void setFolderName(String folderName) {
        FolderName = folderName;
    }

    public int getNumberOfPics() {
        return numberOfPics;
    }

    public void setNumberOfPics(int numberOfPics) {
        this.numberOfPics = numberOfPics;
    }

    public String getFirstPic() {
        return firstPic;
    }

    public void setFirstPic(String firstPic) {
        this.firstPic = firstPic;
    }

    public void addpics() {
        this.numberOfPics++;
    }
}
