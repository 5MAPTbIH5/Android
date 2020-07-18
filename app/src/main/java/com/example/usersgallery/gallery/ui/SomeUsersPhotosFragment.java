package com.example.usersgallery.gallery.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.usersgallery.R;
import com.example.usersgallery.gallery.ui.all_photos.AllPhotosFragment;
import com.example.usersgallery.models.Photo;
import com.squareup.picasso.Picasso;

public class SomeUsersPhotosFragment extends Fragment {

    AppCompatActivity context;
    Photo currPhoto;

    public SomeUsersPhotosFragment() {
    }

    public SomeUsersPhotosFragment(AppCompatActivity context, Photo photo) {
        this.context = context;
        this.currPhoto = photo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        Picasso.get().load(currPhoto.getPath()).into(((ImageView) view.findViewById(R.id.imgPhotoInfo)));
        ((TextView) view.findViewById(R.id.tvTitleInfo)).setText(currPhoto.getTitle());
        ((TextView) view.findViewById(R.id.tvDescriptionInfo)).setText(currPhoto.getDescription());
        ((TextView) view.findViewById(R.id.tvPathInfo)).setText(currPhoto.getPath());
        final TextView author = view.findViewById(R.id.tvAuthorInfo);
        author.setText(String.valueOf(currPhoto.getIdUser()));
        author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.getSupportFragmentManager().beginTransaction().replace(R.id.rootInfo, new AllPhotosFragment(currPhoto.getIdUser())).commit();
                InfoPhotoActivity.goOnSecondPage();
            }
        });
        view.findViewById(R.id.btnBackInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.finish();
            }
        });

        return view;
    }
}
