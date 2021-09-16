package com.peng.plant.wattviewer2.view;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.peng.plant.wattviewer2.R;
import com.peng.plant.wattviewer2.adapter.LocalImageAdapter;
import com.peng.plant.wattviewer2.listener.CenterScrollListener;
import com.peng.plant.wattviewer2.data.LocalimageData;
import com.peng.plant.wattviewer2.util.MarginDecoration;
import com.peng.plant.wattviewer2.util.ScrollZoomLayoutManager;
import com.peng.plant.wattviewer2.controller.TiltScrollController;
import com.peng.plant.wattviewer2.listener.itemClickListener;

import java.util.ArrayList;

public class LocalImageList extends AppCompatActivity implements itemClickListener , TiltScrollController.ScrollListener{
    private final String TAG = this.getClass().getSimpleName();


    private RecyclerView imageRecycler;
    private ArrayList<LocalimageData> allimages;
    private ProgressBar load;
    private String folderPath;
    private TextView folderName;
    private TiltScrollController mTiltScrollController;
    private ScrollZoomLayoutManager scrollZoomLayoutManager;
    private SnapHelper snapHelper;
    private ImageView select_box;
    private Button select;
    private LocalImageAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_image_list);

        folderName = findViewById(R.id.folderName);
        folderName.setText(getIntent().getStringExtra("folderName"));

        folderPath = getIntent().getStringExtra("folderPath");
        allimages = new ArrayList<>();


        select_box = findViewById(R.id.select_box);
        select = findViewById(R.id.select);
//        select.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                scrollZoomLayoutManager.getCurrentPosition();
//
//            }
//        });

        imageRecycler = findViewById(R.id.recycler);

        snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(imageRecycler);

        mTiltScrollController = new TiltScrollController(getApplicationContext(),this);
        scrollZoomLayoutManager = new ScrollZoomLayoutManager(this, Dp2px(5));

        imageRecycler.addOnScrollListener(new CenterScrollListener());
        imageRecycler.setLayoutManager(scrollZoomLayoutManager);
        load = findViewById(R.id.loader);


        if (allimages.isEmpty()){
            load.setVisibility(View.VISIBLE);
            allimages = getAllImagesByFolder(folderPath);
            imageRecycler.setAdapter(new LocalImageAdapter(allimages, LocalImageList.this, this));
            load.setVisibility(View.GONE);
            select_box.setVisibility(View.VISIBLE);
            select.setVisibility(View.VISIBLE);
            select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent move = new Intent(LocalImageList.this, LocalImage.class);
                    move.putExtra("picturePath", allimages.get(scrollZoomLayoutManager.getCurrentPosition()).getPicturePath());
                    move.putExtra("imageUri", allimages.get(scrollZoomLayoutManager.getCurrentPosition()).getImageUri());
                    startActivity(move);

                }
            });

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
    public void onTilt(int x, int y, float delta) {
        //        recyclerView.smoothScrollBy(x, y);
        if (Math.abs(delta) > 3) {
            imageRecycler.smoothScrollBy(x *(scrollZoomLayoutManager.getEachItemWidth()), 0);
//            smoothScrollBy(x * (layoutManager.getEachItemWidth()), 0);
        } else
            imageRecycler.smoothScrollBy(x *(scrollZoomLayoutManager.getEachItemWidth() / 10) , 0);
//            smoothScrollBy(x * (layoutManager.getEachItemWidth() / 6), 0)

    }


    @Override
    public void onfolderClicked(String pictureFolderPath, String folderName) {

    }

    @Override
    public void onPicClicked(String picturePath, String imageUri , int position) {

        imageRecycler.smoothScrollToPosition(position);
        Intent move = new Intent(LocalImageList.this, LocalImage.class);
        move.putExtra("picturePath", picturePath);
        move.putExtra("imageuri", imageUri);

        startActivity(move);


    }




}

