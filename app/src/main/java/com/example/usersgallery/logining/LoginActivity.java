package com.example.usersgallery.logining;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.usersgallery.R;
import com.example.usersgallery.database.GalleryDB;
import com.example.usersgallery.gallery.GalleryActivity;
import com.example.usersgallery.models.Photo;
import com.example.usersgallery.models.User;

public class LoginActivity extends AppCompatActivity {
    final public static int SIGN_OUT = 0;
    final public static int REQUEST_WRITE_STORAGE = 1;

    EditText username;
    EditText password;
    GalleryDB db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logining);

        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        db = new GalleryDB(this);

//        db.createUser(new User(null,"1", "1"));
//        db.createUser(new User(null,"2", null));
//
//        db.createPhoto(new Photo(null, "photo 1", "borodach", "https://vgorode.ua/img/article/5434/57_main-v1583942641.jpg", 1));
//        db.createPhoto(new Photo(null, "photo 11", "painter", "https://im-01.forfun.com/fetch/w295-ch400-preview/b4/b457b2a365a39af2b922a2909cdc004a.jpeg", 1));
//        db.createPhoto(new Photo(null, "photo 2", "driver", "https://akzent.zp.ua/wp-content/uploads/2019/06/282430.jpg", 2));


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Некорректный логин", Toast.LENGTH_SHORT).show();
                } else {
                    loadingProgressBar.setVisibility(View.VISIBLE);
                    loginButton.setEnabled(false);
                    User us = db.signIn(username.getText().toString(), password.getText().toString());
                    if (us != null) {
                        if (us.getPassword().equals(password.getText().toString())) {
                            Toast.makeText(LoginActivity.this, "Привет, " + us.getLogin(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginActivity.this, GalleryActivity.class);
                            intent.putExtra(User.class.getSimpleName(), us);
                            startActivityForResult(intent, SIGN_OUT);
                        } else {
                            Toast.makeText(LoginActivity.this, "Неверный пароль", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (db.createUser(new User(null, username.getText().toString(), password.getText().toString()))) {
                            Toast.makeText(LoginActivity.this, "Был создан новый пользователь", Toast.LENGTH_SHORT).show();
                            loginButton.callOnClick();
                        } else {
                            Toast.makeText(LoginActivity.this, "Ошибка при создании нового пользователя", Toast.LENGTH_SHORT).show();
                        }
                    }

                    loadingProgressBar.setVisibility(View.INVISIBLE);
                    loginButton.setEnabled(true);
                }
            }
        });

        checkPermission();
    }

    public void checkPermission() {
        boolean hasPermission = (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_WRITE_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Получено разрешение на чтение и запись", Toast.LENGTH_SHORT).show();
                } else {
                    checkPermission();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SIGN_OUT:
                username.setText("");
                password.setText("");
                User us = (User) data.getExtras().getSerializable(User.class.getSimpleName());
                Toast.makeText(this, "Пользователь " + us.getLogin() + " вышел из галереи", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, "Выход", Toast.LENGTH_SHORT).show();
        finish();
    }
}