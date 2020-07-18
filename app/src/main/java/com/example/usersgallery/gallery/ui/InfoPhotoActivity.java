package com.example.usersgallery.gallery.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.usersgallery.R;
import com.example.usersgallery.models.Photo;

public class InfoPhotoActivity extends AppCompatActivity {

    Photo currPhoto;
    static boolean onStartPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_photo);

        currPhoto = (Photo) getIntent().getExtras().getSerializable(Photo.class.getSimpleName());

        startPage();

    }

    private void startPage() {
        getSupportFragmentManager().beginTransaction().replace(R.id.rootInfo, new SomeUsersPhotosFragment(this, currPhoto)).commit();
        onStartPage = true;
    }

    @Override
    public void onBackPressed() {
        if(!onStartPage){
            startPage();
        }
        else {
            super.onBackPressed();
        }
    }

    public static void goOnSecondPage(){
        onStartPage = false;
    }
}