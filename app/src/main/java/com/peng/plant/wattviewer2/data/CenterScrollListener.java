package com.peng.plant.wattviewer2.data;

import androidx.recyclerview.widget.RecyclerView;

public class CenterScrollListener extends RecyclerView.OnScrollListener{
    private boolean mAutoSet = false;

    @Override
    public void onScrollStateChanged(final RecyclerView recyclerView, final int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if(!(layoutManager instanceof CircleLayoutManager) && !(layoutManager instanceof ScrollZoomLayoutManager)){
            mAutoSet = true;
            return;
        }

        if(!mAutoSet){
            if(newState == RecyclerView.SCROLL_STATE_IDLE){
                final int dx;
                if(layoutManager instanceof CircleLayoutManager){
                    dx = ((CircleLayoutManager) layoutManager).getOffsetCenterView();
                }else{
                    dx = ((ScrollZoomLayoutManager)layoutManager).getOffsetCenterView();

                }
                recyclerView.smoothScrollBy(dx,0);
//                Log.d("aaaaaaaaa", "onScrollStateChanged:|||| "+ recyclerView.getChildAdapterPosition(recyclerView));
            }
            mAutoSet = true;
        }
        if(newState == RecyclerView.SCROLL_STATE_DRAGGING || newState == RecyclerView.SCROLL_STATE_SETTLING){
            mAutoSet = false;
        }
    }
}
