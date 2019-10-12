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

public class Dbcate extends SQLiteOpenHelper {
    private static final String DBNAME = "nms.db";

    // Category table
    private static final String CATEGORY_TBL = "category";

    // Column name
    private static final String NAME_COL = "name";
    private static final String CREATED_DATE_COL = "createdDate";


    public Dbcate(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }

    private String createCategoryTable() {
        StringBuffer sb = new StringBuffer();

        sb.append("create table ").append(CATEGORY_TBL);
        sb.append("(").append(NAME_COL).append(" text(50) primary key, ");
        sb.append(CREATED_DATE_COL).append(" date DEFAULT (datetime('now','localtime')))");

        Log.d("Dbcate.createCategoryTable", sb.toString());

        return sb.toString();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            // Create category table
            sqLiteDatabase.execSQL(createCategoryTable());
        } catch (Exception ex) {
            Log.d("Dbcate.onCreate", ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // Handling category table

    /**
     * Insert into category table
     *
     * @param category
     * @return Number of records
     */
    public long insertCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        long ret = 0;

        try {
            ContentValues cv = new ContentValues();
            cv.put(NAME_COL, category.getName());

            ret = db.insertOrThrow(CATEGORY_TBL, null, cv);
        } catch (Exception ex) {
            Log.d("Dbcate.insertCategory", ex.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return ret;
    }

    /**
     * Get all catetory from database
     *
     * @return List of category
     */
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
            Log.d("Dbcate.getAllCategory", ex.getMessage());
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

    /**
     * Get all catetory from database
     *
     * @return List of category name
     */
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
            Log.d("Dbcate.getAllCategoryName", ex.getMessage());
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

    /**
     * Update category name
     *
     * @param key
     * @param category
     * @return Number of records
     */
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
            Log.d("Dbcate.updateCategoryName", ex.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return ret;
    }

    /**
     * Delete category
     *
     * @param key
     * @return Nunber of records
     */
    public int deleteCategory(String key) {
        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause = NAME_COL + " = ?";
        String whereArgs[] = {key};

        int ret = 0;
        try {
            ret = db.delete(CATEGORY_TBL, whereClause, whereArgs);
        } catch (Exception ex) {
            Log.d("Dbcate.deleteCatetory", ex.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return ret;
    }
}
