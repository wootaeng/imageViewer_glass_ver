package com.peng.plant.wattviewer2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.peng.plant.wattviewer2.adapter.LocalfolderAdapter;
import com.peng.plant.wattviewer2.controller.TiltScrollController;
import com.peng.plant.wattviewer2.data.LocalFolderData;
import com.peng.plant.wattviewer2.listener.CenterScrollListener;
import com.peng.plant.wattviewer2.listener.itemClickListener;
import com.peng.plant.wattviewer2.util.ScrollZoomLayoutManager;
import com.peng.plant.wattviewer2.view.LocalImageList;
import com.peng.plant.wattviewer2.view.LocalfolderList;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //화면 계속 켜짐
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //delay 실행
        new Handler().postDelayed(new Runnable() {//3초 후 실행행            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LocalfolderList.class);
                startActivity(intent);
                finish();

            }
        }, 3000);

    }
   
}