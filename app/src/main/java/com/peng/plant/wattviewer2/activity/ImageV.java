package com.peng.plant.wattviewer2.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

import com.peng.plant.wattviewer2.R;

public class ImageV extends View {
    public ImageV(Context context) {
        super(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Bitmap edge_img = BitmapFactory.decodeResource(getResources(), R.drawable.edge);

        canvas.drawBitmap(edge_img, 0, 0, null);

    }


}
