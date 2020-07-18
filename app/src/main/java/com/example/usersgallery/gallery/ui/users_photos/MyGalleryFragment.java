package com.example.usersgallery.gallery.ui.users_photos;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usersgallery.R;
import com.example.usersgallery.database.GalleryDB;
import com.example.usersgallery.gallery.ui.MyEditablePhotoActivity;
import com.example.usersgallery.gallery.ui.all_photos.RecyclerViewPhotosAdapter;
import com.example.usersgallery.models.Photo;
import com.example.usersgallery.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MyGalleryFragment extends Fragment {

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        GalleryDB db;
        db = new GalleryDB(inflater.getContext());
        User us = (User) ((AppCompatActivity) inflater.getContext()).getIntent().getExtras().getSerializable(User.class.getSimpleName());
        List<Photo> list = db.getAllUsersPhotos(us.getId());

        View root = inflater.inflate(R.layout.fragment_photos_recyclerview_edit, container, false);
        RecyclerView rvAllPhotos = root.findViewById(R.id.rvPhotos);
        RecyclerViewPhotosAdapter adapter = new RecyclerViewPhotosAdapter(getContext(), rvAllPhotos, list, RecyclerViewPhotosAdapter.SOME_USER_EDITABLE_PHOTOS);
        rvAllPhotos.setAdapter(adapter);

        FloatingActionButton fab = root.findViewById(R.id.fabCreatePhoto);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User us = (User) ((AppCompatActivity) inflater.getContext()).getIntent().getExtras().getSerializable(User.class.getSimpleName());
                Intent intent = new Intent(inflater.getContext(), MyEditablePhotoActivity.class);
                intent.putExtra(Photo.class.getSimpleName(), new Photo(null, null, null, null, us.getId()));
                ((AppCompatActivity) inflater.getContext()).startActivityForResult(intent, RecyclerViewPhotosAdapter.SOME_USER_EDITABLE_PHOTOS);
            }
        });

        return root;
    }
}