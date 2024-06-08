package com.example.kpabarenova;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "favName";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String TABLE_NAME2 = "SignLog";
    private static final String COLUMN_ID2 = "id";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String TABLE_NAME3 = "readName";
    private static final String COLUMN_ID3 = "id";
    private static final String COLUMN_NAME3 = "name_read";


    public DBHelper(Context context) {
        super(context, "favName.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT)";
        String createTable2 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME2 + " (" +
                COLUMN_ID2 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EMAIL + " TEXT, " + COLUMN_PASSWORD + " TEXT)";
        String createTable3 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME3 + " (" +
                COLUMN_ID3 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME3 + " TEXT)";
        db.execSQL(createTable);
        db.execSQL(createTable2);
        db.execSQL(createTable3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
        onCreate(db);
    }
    // Добавление нового избранного
    public boolean addLike(Like like) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, like.getName());
        long result = db.insert(TABLE_NAME, null, cv);
        db.close();
        return result != -1;
    }
    public boolean addRead(Read read) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME3, read.getName());
        long result = db.insert(TABLE_NAME3, null, cv);
        db.close();
        return result != -1;
    }
    public Boolean addlog(Profiles profiles){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_EMAIL, profiles.getEmail());
        cv.put(COLUMN_PASSWORD, profiles.getPassword());
        long result = db.insert(TABLE_NAME2, null, cv);
        db.close();
        return result != -1;
    }
    // Удаление Like
    public boolean deleteData(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COLUMN_NAME + " = ?", new String[]{name});
        db.close();
        return result > 0;
    }

    public boolean deleteRead(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME3, COLUMN_NAME3 + " = ?", new String[]{name});
        db.close();
        return result > 0;
    }

    // Получение всех контактов Like
    public List<Like> getAllData() {
        List<Like> likeList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " +
                TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                Like likeBook = new Like(
                        cursor.getInt(0), cursor.getString(1));
                likeList.add(likeBook);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return likeList;
    }

    public List<Read> getAllRead() {
        List<Read> readList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " +
                TABLE_NAME3, null);
        if (cursor.moveToFirst()) {
            do {
                Read readBook = new Read(
                        cursor.getInt(0), cursor.getString(1));
                readList.add(readBook);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return readList;
    }
//    Проверка почты
    public Cursor checkEmail(String email){
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM " + TABLE_NAME2 + " WHERE " + COLUMN_EMAIL + "=?", new String[]{email});
        return cursor;
    }
    public Cursor checkEmailPassword(String email, String password){
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM " + TABLE_NAME2 + " WHERE " + COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?", new String[]{email, password});
        return cursor;
    }

}
