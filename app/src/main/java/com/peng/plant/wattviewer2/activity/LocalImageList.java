package com.peng.plant.wattviewer2.activity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.peng.plant.wattviewer2.MarginDecoration;
import com.peng.plant.wattviewer2.R;
import com.peng.plant.wattviewer2.adapter.LocalImageAdapter;
import com.peng.plant.wattviewer2.data.LocalimageData;
import com.peng.plant.wattviewer2.data.ScrollZoomLayoutManager;
import com.peng.plant.wattviewer2.data.TiltScrollController;
import com.peng.plant.wattviewer2.itemClickListener;

import java.util.ArrayList;

public class LocalImageList extends AppCompatActivity implements itemClickListener , TiltScrollController.ScrollListener{

    RecyclerView imageRecycler;
    ArrayList<LocalimageData> allimages;
    ProgressBar load;
    String folderPath;
    TextView folderName;
    TiltScrollController mTiltScrollController;
    ScrollZoomLayoutManager scrollZoomLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_image_list);

        folderName = findViewById(R.id.folderName);
        folderName.setText(getIntent().getStringExtra("folderName"));

        folderPath = getIntent().getStringExtra("folderPath");
        allimages = new ArrayList<>();
        imageRecycler = findViewById(R.id.recycler);
        mTiltScrollController = new TiltScrollController(getApplicationContext(),this);
//        scrollZoomLayoutManager = new ScrollZoomLayoutManager(this, Dp2px(5));
        imageRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        imageRecycler.addItemDecoration(new MarginDecoration(this));
        imageRecycler.hasFixedSize();
        load = findViewById(R.id.loader);

        if (allimages.isEmpty()){
            load.setVisibility(View.VISIBLE);
            allimages = getAllImagesByFolder(folderPath);
            imageRecycler.setAdapter(new LocalImageAdapter(allimages, LocalImageList.this, this));
            load.setVisibility(View.GONE);
            onTilt(80,0,1);
        } else {

        }

    }

    private ArrayList<LocalimageData> getAllImagesByFolder(String path) {

        ArrayList<LocalimageData> images = new ArrayList<>();
        Uri allVideosuri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE};
        Cursor cursor = LocalImageList.this.getContentResolver().query(allVideosuri, projection, MediaStore.Images.Media.DATA + " like ? ", new String[]{"%" + path + "%"}, null);
        try {
            cursor.moveToFirst();
            do {
                LocalimageData pic = new LocalimageData();

                pic.setPicturName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)));

                pic.setPicturePath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)));

                pic.setPictureSize(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)));

                images.add(pic);
            } while (cursor.moveToNext());
            cursor.close();
            ArrayList<LocalimageData> reSelection = new ArrayList<>();
            for (int i = images.size() - 1; i > -1; i--) {
                reSelection.add(images.get(i));
            }
            images = reSelection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return images;
    }

    public int Dp2px(float dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


    @Override
    public void onPicClicked(String pictureFolderPath, String folderName) {

    }

    @Override
    public void onPicClicked(LocalImageAdapter.ViewHolder holder, int position, ArrayList<LocalimageData> pictureList) {

    }


    @Override
    public void onTilt(int x, int y, float delta) {
        //        recyclerView.smoothScrollBy(x, y);
        if (Math.abs(delta) > 0.6) {
            imageRecycler.smoothScrollBy(x , 0);
//            smoothScrollBy(x * (layoutManager.getEachItemWidth()), 0);
        } else
            imageRecycler.smoothScrollBy(x , 0);
//            smoothScrollBy(x * (layoutManager.getEachItemWidth() / 6), 0)

    }
}
