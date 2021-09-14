package com.peng.plant.wattviewer2.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.peng.plant.wattviewer2.util.ScrollZoomLayoutManager;
import com.peng.plant.wattviewer2.controller.TiltScrollController;
import com.peng.plant.wattviewer2.R;
import com.peng.plant.wattviewer2.adapter.LocalfolderAdapter;
import com.peng.plant.wattviewer2.data.LocalFolderData;
import com.peng.plant.wattviewer2.listener.itemClickListener;

import java.util.ArrayList;


public class LocalGallery extends AppCompatActivity implements itemClickListener, TiltScrollController.ScrollListener {

    RecyclerView recyclerView;
    TextView empty;
    private RecyclerView.Adapter folderAdapter;
    LocalfolderAdapter folderadapter;
    TiltScrollController mScrollController;
    ScrollZoomLayoutManager scrollZoomLayoutManager;
    private SnapHelper snapHelper;
    private int sensor_statistic;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_gallery_folder);

        if(ContextCompat.checkSelfPermission(LocalGallery.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(LocalGallery.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

        empty = findViewById(R.id.empty);

//        mScrollController = new TiltScrollController(getApplicationContext(), this);

        recyclerView = findViewById(R.id.folderRecycler);
//        recyclerView.addItemDecoration(new MarginDecoration(this));
        scrollZoomLayoutManager = new ScrollZoomLayoutManager(this, Dp2px(10));
        recyclerView.setLayoutManager(scrollZoomLayoutManager);
        recyclerView.hasFixedSize();
        snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        ArrayList<LocalFolderData> folders = getPicturePaths();

        if (folders.isEmpty()){
            empty.setVisibility(View.VISIBLE);
        }else {
            folderadapter = new LocalfolderAdapter(folders, LocalGallery.this, this);
            folderAdapter = folderadapter;
            recyclerView.setAdapter(folderadapter);
        }

        changeStatusBarColor();

    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        mScrollController.requestAllSensors();
//        recyclerView.smoothScrollBy(sensor_statistic, 0);
//    }
//    @Override
//    public void onPause() {
//        super.onPause();
//        mScrollController.releaseAllSensors();
//    }

    private ArrayList<LocalFolderData> getPicturePaths() {
        ArrayList<LocalFolderData> picFolders = new ArrayList<>();
        ArrayList<String> picPaths = new ArrayList<>();
        Uri allImagesuri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.BUCKET_ID};
        Cursor cursor = this.getContentResolver().query(allImagesuri, projection, null, null, null);
        try {
            if (cursor != null){
                cursor.moveToFirst();
            }
            do {
                LocalFolderData folders = new LocalFolderData();
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
                String folder = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                String datapath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));

                String folderpaths = datapath.substring(0, datapath.lastIndexOf(folder+"/"));
                folderpaths = folderpaths+folder+"/";
                if (!picPaths.contains(folderpaths)) {
                    picPaths.add(folderpaths);

                    folders.setPath(folderpaths);
                    folders.setFolderName(folder);
                    folders.setFirstPic(datapath);//폴더 이미지 설정
                    folders.addpics();
                    picFolders.add(folders);
                }else{
                    for(int i = 0;i<picFolders.size();i++){
                        if(picFolders.get(i).getPath().equals(folderpaths)){
                            picFolders.get(i).setFirstPic(datapath);
                            picFolders.get(i).addpics();
                        }
                    }
                }
            }while(cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(int i = 0;i < picFolders.size();i++){
            Log.d("picture folders",picFolders.get(i).getFolderName()+" and path = "+picFolders.get(i).getPath()+" "+picFolders.get(i).getNumberOfPics());
        }

        //reverse order ArrayList
       /* ArrayList<imageFolder> reverseFolders = new ArrayList<>();

        for(int i = picFolders.size()-1;i > reverseFolders.size()-1;i--){
            reverseFolders.add(picFolders.get(i));
        }*/

        return picFolders;
    }




    @Override
    public void onfolderClicked(String pictureFolderPath, String folderName) {
        Intent move = new Intent(LocalGallery.this,LocalImageList.class);
        move.putExtra("folderPath",pictureFolderPath);
        move.putExtra("folderName",folderName);

        //move.putExtra("recyclerItemSize",getCardsOptimalWidth(4));
        startActivity(move);
    }

    @Override
    public void onPicClicked(String picturePath, String imageUri, int position) {

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void changeStatusBarColor()
    {
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.black));

    }

    public int Dp2px(float dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Override
    public void onTilt(int x, int y, float delta) {
        recyclerView.smoothScrollBy((int) (x * (scrollZoomLayoutManager.getEachItemWidth())/2),0);
        folderadapter.choice_position(scrollZoomLayoutManager.getCurrentPosition());

        sensor_statistic = (int) (x * (scrollZoomLayoutManager.getEachItemWidth())/2);
    }


}
