package com.example.usersgallery.models;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usersgallery.R;

public class MyViewHolderPhoto extends RecyclerView.ViewHolder{

    public ImageView photo;
    public TextView title;

    public MyViewHolderPhoto(@NonNull View itemView) {
        super(itemView);
        photo = itemView.findViewById(R.id.imgPhoto);
        title = itemView.findViewById(R.id.tvPhotoTitle);
    }
}