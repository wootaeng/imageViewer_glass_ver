package com.peng.plant.wattviewer2.activity;

import android.content.Context;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.github.chrisbanes.photoview.OnMatrixChangedListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.peng.plant.wattviewer2.R;


public class LocalImage extends AppCompatActivity {



    ImageView img;
//    PhotoView img;
    Button btn1, btn2;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_image_view);
        img = findViewById(R.id.image);

        Glide.with(this)
                .load(getIntent().getStringExtra("picturePath"))
                .into(img);

//        img = findViewById(R.id.photoView);
//        Glide.with(this).load(getIntent().getStringExtra("picturePath")).diskCacheStrategy(DiskCacheStrategy.ALL).thumbnail(0.5f).into(img);

        





    }


}