package com.example.notemanagementsystem.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.notemanagementsystem.model.Category;
import com.example.notemanagementsystem.model.Note;
import com.example.notemanagementsystem.model.Priority;
import com.example.notemanagementsystem.model.Status;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    Context context;
    private static final String DATABASE_NAME = "note_ms_db";
    private static final String AUTOINCREMENT = "autoincrement";
    private static final String PRIMARY_KEY = "primary key";
    private static final String ID = "id";

    private static final String PRIORITY_TBL = "priority";
    private static final String PRIORITY_ID = "priority_ID";
    private static final String PRIORITY_NAME = "priority_name";
    private static final String PRIORITY_CREATED_DATE = "priority_createdDate";
    private static final String CATEGORY_TBL = "category";
    private static final String CATEGORY_ID = "cateID";
    private static final String CATEGORY_NAME = "category_name";
    private static final String CATEGORY_CREATED_DATE = "category_createdDate";
    private static final String TABLE_NAME = "student";

    //status
    private static final String TABLE_STATUS = "tbl_status";
    private static final String STATUS_NAME = "status_name";
    private static final String STATUS_DATE = "status_date";

    //note
    private static final String NOTE_TBL = "note_tbl";
    private static final String NOTE_ID = "note_ID";
    private static final String NOTE_NAME = "note_name";
    private static final String NOTE_STATUS_NAME = "note_status_name";
    private static final String NOTE_PRIORITY_NAME= "note_priority_name";
    private static final String NOTE_CATEGORY_NAME= "note_category_name";
    private static final String NOTE_CREATED_DATE = "note_createdDate";
    private static final String NOTE_PLAN_DATE = "note_planDate";

    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 3);
        //this.context =   context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(createTable());
        } catch (Exception ex) {
            Log.d("DatabaseHandler.onCreate", ex.getMessage());
        }

        try {
            //db.execSQL(CreateNoteTable());
            db.execSQL(create_tbl_status());

        } catch (Exception ex) {
            Log.d("DatabaseHandler.onCreate", ex.getMessage());
        }
        db.execSQL(CreateNoteTable());

       // db.execSQL("Create table note(note_name text(50) primary key ,note_createdDate date DEFAULT (datime('now','localtime')))");
        db.execSQL("Create table accout(email text primary key ,password text)");
    }

    private String createTable() {
        StringBuffer sb = new StringBuffer();
        //category
        sb.append("create table ").append(CATEGORY_TBL);
        sb.append("(").append(CATEGORY_ID).append(" integer ").append(PRIMARY_KEY);
        sb.append(AUTOINCREMENT).append(", ");
        sb.append(CATEGORY_NAME).append(" text(50), ");
        sb.append(CATEGORY_CREATED_DATE).append(" date DEFAULT (datetime('now','localtime')))");
        //priority
        sb.append("create table ").append(PRIORITY_TBL);
        sb.append("(").append(PRIORITY_ID).append(" integer ").append(PRIMARY_KEY);
        sb.append(AUTOINCREMENT).append(", ");
        sb.append(PRIORITY_NAME).append(" text(50), ");
        sb.append(PRIORITY_CREATED_DATE).append(" date DEFAULT (datetime('now','localtime')))");

        Log.d("DatabaseHandler.createTable", sb.toString());
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
    }
    //insertSignUp in database
    public boolean insertSignUp(String email, String password){
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
            Log.d("DatabaseHandler.insertPriority", ex.getMessage());
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
            Log.d("DatabaseHandler.getAllPriority", ex.getMessage());
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
            Log.d("DatabaseHandler.getAllPriorityName", ex.getMessage());
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
            Log.d("DatabaseHandler.updatePriorityName", ex.getMessage());
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
            Log.d("DatabaseHandler.deletePriority", ex.getMessage());
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
            Log.d("DatabaseHandler.insertCategory", ex.getMessage());
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
            Log.d("DatabaseHandler.getAllCategory", ex.getMessage());
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
            Log.d("DatabaseHandler.getAllCategoryName", ex.getMessage());
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
            Log.d("DatabaseHandler.updateCategoryName", ex.getMessage());
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
            Log.d("DatabaseHandler.deleteCatetory", ex.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return ret;
    }

    //status's menthod
    public String create_tbl_status(){
        /*String sql = "CREATE TABLE " + TABLE_STATUS + " (" +
                ID + " integer " + PRIMARY_KEY + ' ' + AUTOINCREMENT + ", " +
                STATUS_NAME + " text" + STATUS_DATE + "text" + STATUS_DATE + "date DEFAULT (datetime('now','localtime')))"+
                ")";
        db.execSQL(sql);*/
        StringBuffer sb = new StringBuffer();

        sb.append("create table ").append(TABLE_STATUS);
        sb.append("(").append(STATUS_NAME).append(" text(50) primary key, ");
        sb.append(STATUS_DATE).append(" date DEFAULT (datetime('now','localtime')))");

        Log.d("Database.createCategoryTable", sb.toString());
        return sb.toString();
    }
    public long addStatus(Status status){
        SQLiteDatabase db = this.getWritableDatabase();
        long ret = 0;

        try {
            ContentValues cv = new ContentValues();
            cv.put(STATUS_NAME, status.getName());

            ret = db.insertOrThrow(TABLE_STATUS, null, cv);
        } catch (Exception ex) {
            Log.d("Database.insertCategory", ex.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return ret;
    }
    public ArrayList<Status> getAllStatus() {
        ArrayList<Status> data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_STATUS, null, null, null, null, null, null);
            String name, createDate;

            while (cursor.moveToNext()) {
                name = cursor.getString(cursor.getColumnIndex(STATUS_NAME));
                createDate = cursor.getString(cursor.getColumnIndex(STATUS_DATE));
                data.add(new Status(name, createDate));
            }
        } catch (Exception ex) {
            Log.d("Database.getAllCategory", ex.getMessage());
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
    public int updateStatusName(String key, Status status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        int ret = 0;
        try {
            cv.put(STATUS_NAME, status.getName());

            String whereClause = STATUS_NAME + " = ?";
            String whereArgs[] = {key};
            ret = db.update(TABLE_STATUS, cv, whereClause, whereArgs);
        } catch (Exception ex) {
            Log.d("Database.updateStatusName", ex.getMessage());
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
    public int deleteStatus(String key) {
        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause = STATUS_NAME + " = ?";
        String whereArgs[] = {key};

        int ret = 0;
        try {
            ret = db.delete(TABLE_STATUS, whereClause, whereArgs);
        } catch (Exception ex) {
            Log.d("Database.deleteCatetory", ex.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return ret;
    }
    //end status menthod


    //note method

     //sb.append("create table ").append(NOTE_TBL);
       // sb.append("(").append(NOTE_ID).append(" integer ").append(PRIMARY_KEY);
        //sb.append(AUTOINCREMENT).append(", ");
       // sb.append(NOTE_NAME).append(" text(50), ");
    //sb.append(NOTE_CATEGORY_NAME).append(" text(50), ");
    //sb.append(NOTE_PRIORITY_NAME).append(" text(50), ");
    //sb.append(NOTE_STATUS_NAME).append(" text(50), ");
    //sb.append(NOTE_PLAN_DATE).append(" text(50), ");
        //sb.append(NOTE_CREATED_DATE).append(" date DEFAULT (datetime('now','localtime')))");

    public String CreateNoteTable(){
        StringBuffer sb = new StringBuffer();
        sb.append("create table ").append(NOTE_TBL);
        //ID
        sb.append("(").append(NOTE_NAME).append(" text(50) primary key, ");

        sb.append(NOTE_CREATED_DATE).append(" date DEFAULT (datetime('now','localtime')));");


        Log.d("Database.createCategoryTable", sb.toString());
        return sb.toString();

    }



    public long insertNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = 0;

        try {
            ContentValues cv = new ContentValues();
            cv.put(NOTE_NAME, note.getName());

            result = db.insertOrThrow(NOTE_TBL, null, cv);
        } catch (Exception ex) {
            Log.d("DatabaseHandler.insertNote", ex.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return result;
    }

    public ArrayList<Note> getAllNote() {
        ArrayList<Note> data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = db.query(NOTE_TBL, null, null, null, null, null, null);
            String name, createDate;

            while (cursor.moveToNext()) {
                name = cursor.getString(cursor.getColumnIndex(NOTE_NAME));
                createDate = cursor.getString(cursor.getColumnIndex(NOTE_CREATED_DATE));
                data.add(new Note(name, createDate));
            }
        } catch (Exception ex) {
            Log.d("DatabaseNote.getAllNote", ex.getMessage());
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

    @SuppressLint("LongLogTag")
    public ArrayList<String> getAllNoteName() {
        ArrayList<String> data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = db.query(NOTE_TBL, null, null, null, null, null, null);
            String name;

            while (cursor.moveToNext()) {
                name = cursor.getString(cursor.getColumnIndex(NOTE_NAME));
                data.add(name);
            }

        } catch (Exception ex) {
            Log.d("DatabaseNote.getAllNoteName", ex.getMessage());
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

    //@SuppressLint("LongLogTag")
    public int updateNoteName(String key, Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        int ret = 0;
        try {
            cv.put(NOTE_NAME, note.getName());

            String whereClause =  NOTE_NAME+ " = ?";
            String whereArgs[] = {key};
            ret = db.update(NOTE_TBL, cv, whereClause, whereArgs);
        } catch (Exception ex) {
            Log.d("DatabaseNote.updateNoteName", ex.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return ret;
    }

    public int deleteNote(String key) {
        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause = NOTE_NAME + " = ?";
        String whereArgs[] = {key};

        int ret = 0;
        try {
            ret = db.delete(NOTE_TBL, whereClause, whereArgs);
        } catch (Exception ex) {
            Log.d("Database.deleteNote", ex.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return ret;
    }




    //end note method

}
