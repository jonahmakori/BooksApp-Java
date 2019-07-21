package com.hayssamsoussi.whattoread.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hayssamsoussi.whattoread.database.model.FavoriteBook;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Books.db";
    public static final String TABLE_NAME = "books_table";
    public static final String COL_1 = "id";
    public static final String COL_2 = "name";
    public static final String COL_3 = "author";
    public static final String COL_4 = "thumb";
    public static final String COL_5 = "bookid";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,AUTHOR TEXT,THUMB TEXT, BOOKID TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name,String author,String thumb, String publisher) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,author);
        contentValues.put(COL_4,thumb);
        contentValues.put(COL_5,publisher);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean updateData(String id, String name,String author,String thumb, String bookid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,author);
        contentValues.put(COL_4,thumb);
        contentValues.put(COL_4,bookid);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }

    public ArrayList<FavoriteBook> getAllData() {
        ArrayList<FavoriteBook> favoriteList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);

        while(res.moveToNext()) {
            String id = res.getString(0);   //0 is the number of id column in your database table
            String name = res.getString(1);
            String author = res.getString(2);
            String thumb = res.getString(3);
            String bookid = res.getString(4);

            FavoriteBook newDog = new FavoriteBook(id, name, author, thumb, bookid);
            favoriteList.add(newDog);
        }
        return favoriteList;
    }

    // Get User Details
    public ArrayList<HashMap<String, String>> getAllBooks(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> bookList = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> book = new HashMap<>();
            book.put(COL_1,cursor.getString(cursor.getColumnIndex(COL_1)));
            book.put(COL_2,cursor.getString(cursor.getColumnIndex(COL_2)));
            book.put(COL_3,cursor.getString(cursor.getColumnIndex(COL_3)));
            book.put(COL_4,cursor.getString(cursor.getColumnIndex(COL_4)));
            book.put(COL_5,cursor.getString(cursor.getColumnIndex(COL_5)));
            bookList.add(book);
        }
        return  bookList;
    }
    // Get User Details based on userid
}
