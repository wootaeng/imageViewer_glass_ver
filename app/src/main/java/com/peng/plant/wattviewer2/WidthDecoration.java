package com.peng.plant.wattviewer2;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WidthDecoration extends RecyclerView.ItemDecoration {
    private int divWidth;

    public WidthDecoration(int divWidth) {
        this.divWidth = divWidth;
    }

    @Override
    public void getItemOffsets(
            @NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.right = divWidth;
    }
}
