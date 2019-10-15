package com.example.notemanagementsystem.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.notemanagementsystem.model.Priority;

import java.util.ArrayList;

public class DatabasePriority extends SQLiteOpenHelper {
    private static final String DBNAME = "nms1.db";
    private static final String PRIORITY_TBL = "priority";
    private static final String NAME_COL = "name";
    private static final String CREATED_DATE_COL = "createdDate";


    public DatabasePriority(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }

    private String createPriorityTable() {
        StringBuffer sb = new StringBuffer();

        sb.append("create table ").append(PRIORITY_TBL);
        sb.append("(").append(NAME_COL).append(" text(50) primary key, ");
        sb.append(CREATED_DATE_COL).append(" date DEFAULT (datetime('now','localtime')))");

        Log.d("DatabasePriority.createPriorityTable", sb.toString());

        return sb.toString();
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(createPriorityTable());
        } catch (Exception ex) {
            Log.d("DatabasePriority.onCreate", ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long insertPriority(Priority priority) {
        SQLiteDatabase db = this.getWritableDatabase();
        long ret = 0;

        try {
            ContentValues cv = new ContentValues();
            cv.put(NAME_COL, priority.getName());

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
                name = cursor.getString(cursor.getColumnIndex(NAME_COL));
                createDate = cursor.getString(cursor.getColumnIndex(CREATED_DATE_COL));
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
                name = cursor.getString(cursor.getColumnIndex(NAME_COL));
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
            cv.put(NAME_COL, priority.getName());

            String whereClause = NAME_COL + " = ?";
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

        String whereClause = NAME_COL + " = ?";
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
}