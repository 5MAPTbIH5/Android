package com.example.usersgallery.gallery.ui.all_photos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usersgallery.R;
import com.example.usersgallery.gallery.ui.InfoPhotoActivity;
import com.example.usersgallery.gallery.ui.MyEditablePhotoActivity;
import com.example.usersgallery.models.MyViewHolderPhoto;
import com.example.usersgallery.models.Photo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewPhotosAdapter extends RecyclerView.Adapter<MyViewHolderPhoto> {

    final public static int ALL_PHOTOS = 0;
    final public static int SOME_USER_EDITABLE_PHOTOS = 1;
//    final public static int SOME_USER_EDITABLE_PHOTOS_ADD = 2;

    private LayoutInflater inflater;
    private RecyclerView rv;
    private List<Photo> photos;
    private int type;

    public RecyclerViewPhotosAdapter(Context context, RecyclerView rv, List<Photo> photos, int type) {
        this.inflater = LayoutInflater.from(context);
        this.rv = rv;
        if(photos == null){
            this.photos = new ArrayList<>();
        }
        else {
            this.photos = photos;
        }
        this.type = type;
    }

    @NonNull
    @Override
    public MyViewHolderPhoto onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.view_for_recyclerview_photos, parent, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = rv.getChildLayoutPosition(view);
                Intent intent;
                switch (type) {
                    case RecyclerViewPhotosAdapter.ALL_PHOTOS:
                        intent = new Intent(inflater.getContext(), InfoPhotoActivity.class);
                        break;
                    case RecyclerViewPhotosAdapter.SOME_USER_EDITABLE_PHOTOS:
                        intent = new Intent(inflater.getContext(), MyEditablePhotoActivity.class);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + type);
                }
                intent.putExtra(Photo.class.getSimpleName(), photos.get(position));
                ((AppCompatActivity) inflater.getContext()).startActivityForResult(intent, type);
//                ((AppCompatActivity) inflater.getContext()).startActivity(intent);
            }
        });
        return new MyViewHolderPhoto(view);

}

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderPhoto holder, int position) {
        Photo currPhoto = photos.get(position);
        Picasso.get().load(currPhoto.getPath()).into(holder.photo);
        holder.title.setText(currPhoto.getTitle());
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }
}
