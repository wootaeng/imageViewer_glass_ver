package com.peng.plant.wattviewer2.activity;

import android.annotation.SuppressLint;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.android.material.imageview.ShapeableImageView;
import com.peng.plant.wattviewer2.R;

import java.io.File;
import java.util.Objects;

public class LocalImage extends AppCompatActivity {

    SubsamplingScaleImageView img;

//    ImageView img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_image_view);
//        img = findViewById(R.id.image);
//
//        Glide.with(this)
//                .load(getIntent().getStringExtra("picturePath"))
//                .into(img);

        img = (SubsamplingScaleImageView) findViewById(R.id.image);



    }


}