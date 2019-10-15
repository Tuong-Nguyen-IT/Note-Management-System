package com.example.notemanagementsystem.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.notemanagementsystem.model.Category;

import java.util.ArrayList;

public class DatabaseCategory extends SQLiteOpenHelper {
    private static final String DBNAME = "nms.db";
    private static final String CATEGORY_TBL = "category";
    private static final String NAME_COL = "name";
    private static final String CREATED_DATE_COL = "createdDate";


    public DatabaseCategory(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }

    private String createCategoryTable() {
        StringBuffer sb = new StringBuffer();

        sb.append("create table ").append(CATEGORY_TBL);
        sb.append("(").append(NAME_COL).append(" text(50) primary key, ");
        sb.append(CREATED_DATE_COL).append(" date DEFAULT (datetime('now','localtime')))");

        Log.d("DatabaseCategory.createCategoryTable", sb.toString());

        return sb.toString();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(createCategoryTable());
        } catch (Exception ex) {
            Log.d("DatabaseCategory.onCreate", ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public long insertCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        long ret = 0;

        try {
            ContentValues cv = new ContentValues();
            cv.put(NAME_COL, category.getName());

            ret = db.insertOrThrow(CATEGORY_TBL, null, cv);
        } catch (Exception ex) {
            Log.d("DatabaseCategory.insertCategory", ex.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return ret;
    }
    public ArrayList<Category> getAllCategory() {
        ArrayList<Category> data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = db.query(CATEGORY_TBL, null, null, null, null, null, null);
            String name, createDate;

            while (cursor.moveToNext()) {
                name = cursor.getString(cursor.getColumnIndex(NAME_COL));
                createDate = cursor.getString(cursor.getColumnIndex(CREATED_DATE_COL));
                data.add(new Category(name, createDate));
            }
        } catch (Exception ex) {
            Log.d("DatabaseCategory.getAllCategory", ex.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
            if (cursor != null) {
                cursor.close();
            }
        }
        return data;
    }
    public ArrayList<String> getAllCategoryName() {
        ArrayList<String> data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = db.query(CATEGORY_TBL, null, null, null, null, null, null);
            String name;

            while (cursor.moveToNext()) {
                name = cursor.getString(cursor.getColumnIndex(NAME_COL));
                data.add(name);
            }

        } catch (Exception ex) {
            Log.d("DatabaseCategory.getAllCategoryName", ex.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
            if (cursor != null) {
                cursor.close();
            }
        }
        return data;
    }
    public int updateCategoryName(String key, Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        int ret = 0;
        try {
            cv.put(NAME_COL, category.getName());

            String whereClause = NAME_COL + " = ?";
            String whereArgs[] = {key};
            ret = db.update(CATEGORY_TBL, cv, whereClause, whereArgs);
        } catch (Exception ex) {
            Log.d("DatabaseCategory.updateCategoryName", ex.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return ret;
    }
    public int deleteCategory(String key) {
        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause = NAME_COL + " = ?";
        String whereArgs[] = {key};

        int ret = 0;
        try {
            ret = db.delete(CATEGORY_TBL, whereClause, whereArgs);
        } catch (Exception ex) {
            Log.d("DatabaseCategory.deleteCatetory", ex.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return ret;
    }
}