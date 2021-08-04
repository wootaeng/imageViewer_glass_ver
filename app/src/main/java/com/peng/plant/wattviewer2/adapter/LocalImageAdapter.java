package com.peng.plant.wattviewer2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.peng.plant.wattviewer2.R;
import com.peng.plant.wattviewer2.data.LocalimageData;
import com.peng.plant.wattviewer2.itemClickListener;

import java.util.ArrayList;

import static androidx.core.view.ViewCompat.setTransitionName;

public class LocalImageAdapter extends RecyclerView.Adapter<LocalImageAdapter.ViewHolder> {

    private ArrayList<LocalimageData> imageList;
    private Context context;
    private final itemClickListener picListerner;


    public LocalImageAdapter(ArrayList<LocalimageData> imageList, Context context, itemClickListener picListerner) {
        this.imageList = imageList;
        this.context = context;
        this.picListerner = picListerner;
    }

    @Override
    public LocalImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.local_image_item, parent, false);

        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.3);
        v.setLayoutParams(layoutParams);



        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LocalImageAdapter.ViewHolder holder, int position) {
        final LocalimageData image = imageList.get(position);

        Glide.with(holder.itemView).load(image.getPicturePath()).apply(new RequestOptions().centerCrop()).into(holder.picture);

        holder.picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picListerner.onPicClicked(image.getPicturePath(), image.getImageUri(), position);
            }
        });


//        Glide.with(context).load(image.getPicturePath()).apply(new RequestOptions().centerCrop()).into(holder.picture);
//
//        setTransitionName(holder.picture, String.valueOf(position) + "_image");
//
//        holder.picture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                picListerner.onPicClicked(holder, position, imageList);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView picture;
        Button select;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            picture = itemView.findViewById(R.id.fileimg);
            select = itemView.findViewById(R.id.select);
        }
    }


}
