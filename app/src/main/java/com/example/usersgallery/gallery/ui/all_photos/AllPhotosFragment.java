package com.example.usersgallery.gallery.ui.all_photos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usersgallery.R;
import com.example.usersgallery.database.GalleryDB;
import com.example.usersgallery.models.Photo;

import java.util.List;

public class AllPhotosFragment extends Fragment {

    Integer userId;

    public AllPhotosFragment(){

    }

    public AllPhotosFragment(Integer userId){
        this.userId = userId;
    }

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        GalleryDB db;
        db = new GalleryDB(inflater.getContext());
        List<Photo> list;
        if(userId != null){
            list = db.getAllUsersPhotos(userId);
            userId = null;
        }
        else {
            list = db.getAllPhotos();
        }

        View root = inflater.inflate(R.layout.fragment_photos_recyclerview, container, false);
        RecyclerView rvAllPhotos = root.findViewById(R.id.rvPhotos);
        RecyclerViewPhotosAdapter adapter = new RecyclerViewPhotosAdapter(getContext(), rvAllPhotos, list, RecyclerViewPhotosAdapter.ALL_PHOTOS);
        rvAllPhotos.setAdapter(adapter);
        //rvAllPhotos.addItemDecoration(new DividerItemDecoration(rvAllPhotos.getContext(), DividerItemDecoration.VERTICAL));

        return root;
    }
}