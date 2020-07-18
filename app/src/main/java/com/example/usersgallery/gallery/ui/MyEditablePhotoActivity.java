package com.example.usersgallery.gallery.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.usersgallery.R;
import com.example.usersgallery.gallery.ui.all_photos.RecyclerViewPhotosAdapter;
import com.example.usersgallery.models.Photo;
import com.squareup.picasso.Picasso;


public class MyEditablePhotoActivity extends AppCompatActivity {
    public final static int CHANGE_FOTO = 1;

    ImageView photo;
    TextView title;
    TextView description;
    TextView path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_editable_photo);

        final Photo currPhoto = (Photo) getIntent().getExtras().getSerializable(Photo.class.getSimpleName());
        photo = findViewById(R.id.imgPhotoEdit);
        title = findViewById(R.id.etTitleEdit);
        title.setText(currPhoto.getTitle());
        description = findViewById(R.id.etDescriptionEdit);
        description.setText(currPhoto.getDescription());
        path = findViewById(R.id.etPathEdit);
        path.setText(currPhoto.getPath());
        checkPhoto();

        findViewById(R.id.btnCheckPhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPhoto();
            }
        });
        findViewById(R.id.btnChangeFile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
//                startActivityForResult(Intent.createChooser(intent, "Select Picture"), CHANGE_FOTO);

                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CHANGE_FOTO);

            }
        });
        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currPhoto.setTitle(title.getText().toString());
                currPhoto.setDescription(description.getText().toString());
                currPhoto.setPath(path.getText().toString());
                setResult(RecyclerViewPhotosAdapter.SOME_USER_EDITABLE_PHOTOS, new Intent().putExtra(Photo.class.getSimpleName(), currPhoto));
                onBackPressed();
            }
        });
        findViewById(R.id.btnBackEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Button del = findViewById(R.id.btnDelete);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currPhoto.setIdUser(-1);
                setResult(RecyclerViewPhotosAdapter.SOME_USER_EDITABLE_PHOTOS, new Intent().putExtra(Photo.class.getSimpleName(), currPhoto));
                onBackPressed();
            }
        });
        if(currPhoto.getId() == null){
            del.setVisibility(View.GONE);
        }
    }

    private void checkPhoto() {
        if(!path.getText().toString().isEmpty()){
            Picasso.get().load(path.getText().toString()).into(photo);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CHANGE_FOTO:
                if(resultCode == RESULT_OK){
                    Uri res = data.getData();
                    path.setText(res.toString());
                    checkPhoto();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}