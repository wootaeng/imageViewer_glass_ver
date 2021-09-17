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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.peng.plant.wattviewer2.MainActivity;
import com.peng.plant.wattviewer2.R;
import com.peng.plant.wattviewer2.adapter.LocalfolderAdapter;
import com.peng.plant.wattviewer2.controller.TiltScrollController;
import com.peng.plant.wattviewer2.data.LocalFolderData;
import com.peng.plant.wattviewer2.listener.CenterScrollListener;
import com.peng.plant.wattviewer2.listener.itemClickListener;
import com.peng.plant.wattviewer2.util.ScrollZoomLayoutManager;

import java.util.ArrayList;

public class LocalfolderList extends AppCompatActivity implements itemClickListener, TiltScrollController.ScrollListener{

    private RecyclerView recyclerView;
    private TextView empty;
    private ImageView select_box;
    private Button select;
    private RecyclerView.Adapter folderAdapter;
    private LocalfolderAdapter folderadapter;
    private TiltScrollController mScrollController;
    private ScrollZoomLayoutManager scrollZoomLayoutManager;
    private SnapHelper snapHelper;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_gallery_folder);

        if(ContextCompat.checkSelfPermission(LocalfolderList.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(LocalfolderList.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

        empty = findViewById(R.id.empty);
        select_box = findViewById(R.id.select_box);
        select = findViewById(R.id.select);

        recyclerView = findViewById(R.id.folderRecycler);
        snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        mScrollController = new TiltScrollController(getApplicationContext(), this);
        scrollZoomLayoutManager = new ScrollZoomLayoutManager(this, Dp2px(5));

        recyclerView.addOnScrollListener(new CenterScrollListener());
        recyclerView.setLayoutManager(scrollZoomLayoutManager);
//        recyclerView.hasFixedSize();

        ArrayList<LocalFolderData> folders = getPicturePaths();

        if (folders.isEmpty()){
            empty.setVisibility(View.VISIBLE);
        }else {
            folderadapter = new LocalfolderAdapter(folders, LocalfolderList.this, this);
            folderAdapter = folderadapter;
            recyclerView.setAdapter(folderadapter);
            select_box.setVisibility(View.VISIBLE);
            select.setVisibility(View.VISIBLE);
            select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent move = new Intent(LocalfolderList.this, LocalImageList.class);
                    move.putExtra("folderPath",folders.get(scrollZoomLayoutManager.getCurrentPosition()).getPath());
                    move.putExtra("folderName",folders.get(scrollZoomLayoutManager.getCurrentPosition()).getFolderName());

                    startActivity(move);
                }
            });
        }

        changeStatusBarColor();

    }



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


        return picFolders;
    }




    @Override
    public void onfolderClicked(String pictureFolderPath, String folderName) {
        Intent move = new Intent(LocalfolderList.this,LocalImageList.class);
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
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

    }

    public int Dp2px(float dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Override
    public void onTilt(int x, int y, float delta) {
        if (Math.abs(delta) > 3) {
            recyclerView.smoothScrollBy(x *(scrollZoomLayoutManager.getEachItemWidth()), 0);
//            smoothScrollBy(x * (layoutManager.getEachItemWidth()), 0);
        } else
            recyclerView.smoothScrollBy(x *(scrollZoomLayoutManager.getEachItemWidth() / 10) , 0);
//            smoothScrollBy(x * (layoutManager.getEachItemWidth() / 6), 0)

    }


}
