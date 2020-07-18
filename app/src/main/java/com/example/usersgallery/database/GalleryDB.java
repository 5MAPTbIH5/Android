package com.example.usersgallery.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.usersgallery.models.Photo;
import com.example.usersgallery.models.User;

import java.util.ArrayList;
import java.util.List;

public class GalleryDB extends SQLiteOpenHelper {
    static String dbName = "gallery_db";
    static int dbVersion = 1;


    public static final String TABLE_USERS = "users";

    public static final String KEY_USER_ID = "id";
    public static final String KEY_LOGIN = "login";
    public static final String KEY_PASSWORD = "password";


    public static final String TABLE_PHOTOS = "photos";

    public static final String KEY_PHOTO_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_PATH = "path";
    public static final String KEY_ID_USER = "id_user";


    public GalleryDB(@Nullable Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_USERS + "(" +
                KEY_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_LOGIN + " TEXT," +
                KEY_PASSWORD + " TEXT" + ")");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_PHOTOS + "(" +
                KEY_PHOTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_TITLE + " TEXT," +
                KEY_DESCRIPTION + " TEXT," +
                KEY_PATH + " TEXT," +
                KEY_ID_USER + " INTEGER" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PHOTOS);

        onCreate(sqLiteDatabase);
    }

    public User signIn(String login, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor curs = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + KEY_LOGIN + " = ?", new String[]{login});
        if (curs.moveToFirst()) {
            User fromDb = new User();
            fromDb.setId(curs.getInt(0));
            fromDb.setLogin(curs.getString(1));
            fromDb.setPassword(curs.getString(2));
            if (!curs.moveToNext()) {
                return fromDb;
            }
        }
        return null;
    }

    public boolean createUser(User us) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues vals = new ContentValues();
        vals.put(KEY_LOGIN, us.getLogin());
        vals.put(KEY_PASSWORD, us.getPassword());
        long res = db.insert(TABLE_USERS, null, vals);
        db.close();
        return res > 0;
    }

    public boolean createPhoto(Photo somePhoto) {
        if(!somePhoto.getTitle().isEmpty() && !somePhoto.getDescription().isEmpty() && !somePhoto.getPath().isEmpty()){
            SQLiteDatabase db = getWritableDatabase();
            ContentValues vals = new ContentValues();
            vals.put(KEY_TITLE, somePhoto.getTitle());
            vals.put(KEY_DESCRIPTION, somePhoto.getDescription());
            vals.put(KEY_PATH, somePhoto.getPath());
            vals.put(KEY_ID_USER, somePhoto.getIdUser());
            long res = db.insert(TABLE_PHOTOS, null, vals);
            db.close();
            return res > 0;
        }
        else {
            return false;
        }
    }

    public List<Photo> getAllPhotos() {
        List<Photo> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PHOTOS, null);
        if (cursor.moveToFirst()) {
            do {
                Photo buff = new Photo();
                buff.setId(cursor.getInt(cursor.getColumnIndex(KEY_PHOTO_ID)));
                buff.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
                buff.setDescription(cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)));
                buff.setPath(cursor.getString(cursor.getColumnIndex(KEY_PATH)));
                buff.setIdUser(cursor.getInt(cursor.getColumnIndex(KEY_ID_USER)));
                list.add(buff);
            } while (cursor.moveToNext());
            return list;
        }
        return null;
    }

    public List<Photo> getAllUsersPhotos(int userId) {
        List<Photo> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PHOTOS + " WHERE " + KEY_ID_USER + " = ?", new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst()) {
            do {
                Photo buff = new Photo();
                buff.setId(cursor.getInt(cursor.getColumnIndex(KEY_PHOTO_ID)));
                buff.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
                buff.setDescription(cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)));
                buff.setPath(cursor.getString(cursor.getColumnIndex(KEY_PATH)));
                buff.setIdUser(cursor.getInt(cursor.getColumnIndex(KEY_ID_USER)));
                list.add(buff);
            } while (cursor.moveToNext());
            return list;
        }
        return null;
    }

    public boolean editPhoto(Photo photo){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(KEY_TITLE, photo.getTitle());
        content.put(KEY_DESCRIPTION, photo.getDescription());
        content.put(KEY_PATH, photo.getPath());
        int res = db.update(TABLE_PHOTOS, content, "id = ?", new String[]{String.valueOf(photo.getId())});
        db.close();
        return res > 0;
    }

    public boolean deletePhoto(Photo photo) {
        SQLiteDatabase db = getWritableDatabase();
        int res = db.delete(TABLE_PHOTOS, "id = ?", new String[]{photo.getId().toString()});
        return res > 0;
    }
}