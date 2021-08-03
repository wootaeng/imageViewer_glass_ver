package com.peng.plant.wattviewer2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.peng.plant.wattviewer2.R;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context c;
    private List<Integer> list;

    public SliderAdapter(Context c, List<Integer> list) {
        this.c = c;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(c).inflate(R.layout.listitem_slider,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((viewHolder)holder).bindData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class viewHolder extends RecyclerView.ViewHolder{
        ImageView ivImage;
        public viewHolder(@NonNull View v) {
            super(v);
            ivImage = v.findViewById(R.id.ivSlider);
        }

        void bindData(Integer images){
            Glide.with(c).load(images).into(ivImage);
        }
    }
}
