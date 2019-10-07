package com.example.notemanagementsystem.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.notemanagementsystem.model.Category;

public class DatabaseHandler extends SQLiteOpenHelper {
    Context context;
    private static final String DATABASE_NAME = "note_ms_db";
    private static final String AUTOINCREMENT = "autoincrement";
    private static final String PRIMARY_KEY = "primary key";
    private static final String ID = "id";

    //init table's name
    private static final String TABLE_NAME = "student";


    //init category column's name
    private static final String TABLE_CATEGORY = "tbl_category";
    private static final String CATEGORY_NAME = "category_name";


    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // initialize table
        create_tbl_category(db);


    }

    // category's methods
    private void create_tbl_category(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" +
                ID + " integer " + PRIMARY_KEY + ' ' + AUTOINCREMENT + ", " +
                CATEGORY_NAME + " text" +
                ")";
        db.execSQL(sql);
    }

    public void addCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CATEGORY_NAME, category.getName());
        db.insert(TABLE_CATEGORY, null, values);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // dont't care
    }


    // priority's methods


}
