package com.example.kpabarenova;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "infoBooks.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "infoBooks";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "nameBook";
    private static final String COLUMN_INFO = "information";
    private static final String COLUMN_PHOTO = "photo";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor getSpecificInfo(String condition){
        SQLiteDatabase db = this.getReadableDatabase();
        String nameBook = condition;
        // Запрос к таблице infoBooks
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_INFO + " FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + "=?", new String[]{nameBook});
        return cursor;
    }

    public Cursor getSpecificPhoto(String condition){
        SQLiteDatabase db = this.getReadableDatabase();
        String nameBook = condition;
        // Запрос к таблице infoBooks
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_PHOTO + " FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + "=?", new String[]{nameBook});
        return cursor;
    }

    public List<Books> getAllData() {
        List<Books> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " +
                TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                Books contact = new Books(
                        cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contactList;
    }

}
