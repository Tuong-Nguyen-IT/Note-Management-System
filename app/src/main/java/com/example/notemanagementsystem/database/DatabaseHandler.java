package com.example.notemanagementsystem.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.notemanagementsystem.model.Category;
import com.example.notemanagementsystem.model.Priority;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    Context context;
    private static final String DATABASE_NAME = "note_ms_db";
    private static final String AUTOINCREMENT = "autoincrement";
    private static final String PRIMARY_KEY = "primary key";
    private static final String ID = "id";
    private static final String PRIORITY_TBL = "priority";
    private static final String PRIORITY_NAME = "priority_name";
    private static final String PRIORITY_CREATED_DATE = "priority_createdDate";
    private static final String CATEGORY_TBL = "category";
    private static final String CATEGORY_NAME = "category_name";
    private static final String CATEGORY_CREATED_DATE = "category_createdDate";
    private static final String TABLE_NAME = "student";

    public DatabaseHandler(@Nullable Context context) {
        super(context, "Login.db", null,1);
        //super(context, DATABASE_NAME, null, 1);
    //this.context =   context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(createCategoryTable());
        } catch (Exception ex) {
            Log.d("DatabaseCategory.onCreate", ex.getMessage());
        }
        try {
            db.execSQL(createPriorityTable());
        } catch (Exception ex) {
            Log.d("DatabasePriority.onCreate", ex.getMessage());
        }


    }

    private String createCategoryTable() {
        StringBuffer sb = new StringBuffer();

        sb.append("create table ").append(CATEGORY_TBL);
        sb.append("(").append(CATEGORY_NAME).append(" text(50) primary key, ");
        sb.append(CATEGORY_CREATED_DATE).append(" date DEFAULT (datetime('now','localtime')))");

        Log.d("DatabaseCategory.createCategoryTable", sb.toString());

        return sb.toString();
    }
    private String createPriorityTable() {
        StringBuffer sb = new StringBuffer();

        sb.append("create table ").append(PRIORITY_TBL);
        sb.append("(").append(PRIORITY_NAME).append(" text(50) primary key, ");
        sb.append(PRIORITY_CREATED_DATE).append(" date DEFAULT (datetime('now','localtime')))");

        Log.d("DatabasePriority.createPriorityTable", sb.toString());

        return sb.toString();
    }
    public void addCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CATEGORY_NAME, category.getName());
        db.insert(CATEGORY_TBL, null, values);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // dont't care
        db.execSQL("drop table if exists accout");
    }
    //inserting in database
    public boolean insert(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email",email);
        contentValues.put("password",password);
        long ins = db.insert("accout", null,contentValues);
        if (ins == -1) return false;
        else return true;
    }
    //checking if email exists;
    public Boolean chkemail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from accout where email=?",new String[]{email});
        if(cursor.getCount()>0) return false;
        else return true;
    }
    public long insertPriority(Priority priority) {
        SQLiteDatabase db = this.getWritableDatabase();
        long ret = 0;

        try {
            ContentValues cv = new ContentValues();
            cv.put(PRIORITY_NAME, priority.getName());

            ret = db.insertOrThrow(PRIORITY_TBL, null, cv);
        } catch (Exception ex) {
            Log.d("DatabasePriority.insertPriority", ex.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return ret;
    }
    public ArrayList<Priority> getAllPriority() {
        ArrayList<Priority> data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = db.query(PRIORITY_TBL, null, null, null, null, null, null);
            String name, createDate;

            while (cursor.moveToNext()) {
                name = cursor.getString(cursor.getColumnIndex(PRIORITY_NAME));
                createDate = cursor.getString(cursor.getColumnIndex(PRIORITY_CREATED_DATE));
                data.add(new Priority(name, createDate));
            }
        } catch (Exception ex) {
            Log.d("DatabasePriority.getAllPriority", ex.getMessage());
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
    public ArrayList<String> getAllPriorityName() {
        ArrayList<String> data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = db.query(PRIORITY_TBL, null, null, null, null, null, null);
            String name;

            while (cursor.moveToNext()) {
                name = cursor.getString(cursor.getColumnIndex(PRIORITY_NAME));
                data.add(name);
            }

        } catch (Exception ex) {
            Log.d("DatabasePriority.getAllPriorityName", ex.getMessage());
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
    public int updatePriorityName(String key, Priority priority) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        int ret = 0;
        try {
            cv.put(PRIORITY_NAME, priority.getName());

            String whereClause = PRIORITY_NAME + " = ?";
            String whereArgs[] = {key};
            ret = db.update(PRIORITY_TBL, cv, whereClause, whereArgs);
        } catch (Exception ex) {
            Log.d("DatabasePriority.updatePriorityName", ex.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return ret;
    }


    public int deletePriority(String key) {
        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause = PRIORITY_NAME + " = ?";
        String whereArgs[] = {key};

        int ret = 0;
        try {
            ret = db.delete(PRIORITY_TBL, whereClause, whereArgs);
        } catch (Exception ex) {
            Log.d("DatabasePriority.deletePriority", ex.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return ret;
    }

    public long insertCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        long ret = 0;

        try {
            ContentValues cv = new ContentValues();
            cv.put(CATEGORY_NAME, category.getName());

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
                name = cursor.getString(cursor.getColumnIndex(CATEGORY_NAME));
                createDate = cursor.getString(cursor.getColumnIndex(CATEGORY_CREATED_DATE));
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
                name = cursor.getString(cursor.getColumnIndex(CATEGORY_NAME));
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
            cv.put(CATEGORY_NAME, category.getName());

            String whereClause = CATEGORY_NAME + " = ?";
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

        String whereClause = CATEGORY_NAME + " = ?";
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
