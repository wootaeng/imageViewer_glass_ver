package com.peng.plant.wattviewer2;

import com.peng.plant.wattviewer2.adapter.LocalImageAdapter;
import com.peng.plant.wattviewer2.data.LocalimageData;

import java.util.ArrayList;

public interface itemClickListener {
    void onPicClicked(String pictureFolderPath,String folderName);

    void onPicClicked(LocalImageAdapter.ViewHolder holder, int position, ArrayList<LocalimageData> pictureList);

}
