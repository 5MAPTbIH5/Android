package com.example.usersgallery.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.usersgallery.R;
import com.example.usersgallery.database.GalleryDB;
import com.example.usersgallery.gallery.ui.all_photos.RecyclerViewPhotosAdapter;
import com.example.usersgallery.logining.LoginActivity;
import com.example.usersgallery.models.Photo;
import com.example.usersgallery.models.User;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;


public class GalleryActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    public User us;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_all_photos, R.id.nav_users_photos)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        us = (User) getIntent().getExtras().getSerializable(User.class.getSimpleName());

        View header = navigationView.getHeaderView(0);
        ((TextView) header.findViewById(R.id.tvUser)).setText("Пользователь: " + us.getLogin());
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        setResult(LoginActivity.SIGN_OUT, new Intent().putExtra(User.class.getSimpleName(), us));
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RecyclerViewPhotosAdapter.SOME_USER_EDITABLE_PHOTOS:
                if (data != null) {
                    Photo somePhoto = (Photo) data.getExtras().getSerializable(Photo.class.getSimpleName());
                    GalleryDB db = new GalleryDB(this);
                    if (somePhoto.getId() == null) {
                        db.createPhoto(somePhoto);
                    } else {
                        if (somePhoto.getIdUser() == -1) {
                            db.deletePhoto(somePhoto);
                        } else {
                            db.editPhoto(somePhoto);
                        }
                    }
                    db.close();
                }
                break;
        }
    }
}