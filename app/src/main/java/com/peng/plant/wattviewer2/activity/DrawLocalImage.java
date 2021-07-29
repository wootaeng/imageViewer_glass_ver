package com.peng.plant.wattviewer2.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

import com.peng.plant.wattviewer2.R;

public class DrawLocalImage extends View {

    Bitmap localimage;

    public DrawLocalImage(Context context) {
        super(context);
        localimage = BitmapFactory.decodeResource(getResources(), R.id.image);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(localimage,200,200,null);
    }
}
