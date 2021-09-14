package com.peng.plant.wattviewer2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.peng.plant.wattviewer2.R;
import com.peng.plant.wattviewer2.data.LocalFolderData;
import com.peng.plant.wattviewer2.listener.itemClickListener;

import java.util.ArrayList;


public class LocalfolderAdapter extends RecyclerView.Adapter<LocalfolderAdapter.ViewHolder> {

    private ArrayList<LocalFolderData> folders;
    private Context folderContx;
    private itemClickListener listenToClick;
    private int position_check;

    public LocalfolderAdapter(ArrayList<LocalFolderData> folders, Context folderContx, itemClickListener listen){
        this.folders = folders;
        this.folderContx = folderContx;
        this.listenToClick = listen;
    }

    @Override
    public LocalfolderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.local_folder_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LocalfolderAdapter.ViewHolder holder, int position) {
        final LocalFolderData folder = folders.get(position);

        Glide.with(folderContx)
                .load(folder.getFirstPic())
                .apply(new RequestOptions().centerCrop())
                .into(holder.folderPic);

        //setting the number of images
        String text = ""+folder.getFolderName();
        String folderSizeString=""+folder.getNumberOfPics()+" Media";
        holder.folderSize.setText(folderSizeString);
        holder.folderName.setText(text);

        holder.folderPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenToClick.onfolderClicked(folder.getPath(),folder.getFolderName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return folders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView folderPic;
        TextView folderName;
        //set textview for foldersize
        TextView folderSize;

        CardView folderCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            folderPic = itemView.findViewById(R.id.folderPic);
            folderName = itemView.findViewById(R.id.folderName);
            folderSize=itemView.findViewById(R.id.folderSize);
            folderCard = itemView.findViewById(R.id.folderCard);
        }
    }

    public void choice_position(int position) {
        position_check = position;
    }
}
