package com.peng.plant.wattviewer2;

import com.peng.plant.wattviewer2.adapter.LocalImageAdapter;
import com.peng.plant.wattviewer2.data.LocalimageData;

import java.util.ArrayList;

public interface itemClickListener {

    void onfolderClicked(String pictureFolderPath,String folderName);

    void onPicClicked(String imageUri, String picturePath);


}
