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
import com.example.notemanagementsystem.model.User;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    Context context;
    private static final String TAG = "DB_ERROR";
    private static final String DATABASE_NAME = "note_ms_db";
    private static final String AUTOINCREMENT = "autoincrement";
    private static final String PRIMARY_KEY = "primary key";
    private static final String ID = "id";
    //priority
    private static final String PRIORITY_TBL = "priority";
    private static final String PRIORITY_ID = "priority_ID";
    private static final String PRIORITY_NAME = "priority_name";
    private static final String PRIORITY_CREATED_DATE = "priority_createdDate";
    //category
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


    //SignUp
    private static final String TABLE_USER = "tbl_user";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String EMAIL = "user_email";
    private static final String PASSWORD = "user_password";
    private static final String ACTIVE = "user_active";
    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 3);
        //this.context =   context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(create_TBL_Category());
        } catch (Exception ex) {
            Log.d("DB.onCreate", ex.getMessage());
        }
        try {
            db.execSQL(create_TBL_Priority());
        } catch (Exception ex) {
            Log.d("DB.onCreate", ex.getMessage());
        }
        try {
            db.execSQL(create_tbl_status());

        } catch (Exception ex) {
            Log.d("DB.onCreate", ex.getMessage());
        }

        db.execSQL(CreateNoteTable());

        /////
        createTableUser(db);

    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // dont't care
    }
    //insertUser in database
    /*public boolean insertUser(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email",email);
        contentValues.put("password",password);
        long ins = db.insert("accout", null,contentValues);
        if (ins == -1) return false;
        else return true;
    }*/
    //checking if email exists;
    /*public Boolean chkemail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from accout where email=?",new String[]{email});
        if(cursor.getCount()>0) return false;
        else return true;
    }*/
    public String create_TBL_Category() {
        StringBuffer sb = new StringBuffer();
        //category
        sb.append("create table ").append(CATEGORY_TBL);
        sb.append(" (").append(CATEGORY_ID).append(" integer ").append(PRIMARY_KEY);
        sb.append(" ").append(AUTOINCREMENT).append(", ");
        sb.append(CATEGORY_NAME).append(" text(50), ");
        sb.append(CATEGORY_CREATED_DATE).append(" date DEFAULT (datetime('now','localtime'))) ");
        Log.d("DB.create_TBL_Category", sb.toString());
        return sb.toString();
    }
    public String create_TBL_Priority(){
        StringBuffer sb = new StringBuffer();
        //priority
        sb.append("create table ").append(PRIORITY_TBL);
        sb.append(" (").append(PRIORITY_ID).append(" integer ").append(PRIMARY_KEY);
        sb.append(" ").append(AUTOINCREMENT).append(", ");
        sb.append(PRIORITY_NAME).append(" text(50), ");
        sb.append(PRIORITY_CREATED_DATE).append(" date DEFAULT (datetime('now','localtime'))) ");
        Log.d("DB.create_TBL_Priority", sb.toString());
        return sb.toString();
    }


    //note table
    public String CreateNoteTable(){
        StringBuffer sb = new StringBuffer();
        sb.append("create table ").append(NOTE_TBL);
        //ID
        sb.append(" (").append(NOTE_ID).append(" integer ").append(PRIMARY_KEY);
        sb.append(" ").append(AUTOINCREMENT).append(", ");
        sb.append(NOTE_NAME).append(" text(50), ");
        sb.append(NOTE_CATEGORY_NAME).append(" text(50), ");
        sb.append(NOTE_PRIORITY_NAME).append(" text(50), ");
        sb.append(NOTE_STATUS_NAME).append(" text(50), ");
        sb.append(NOTE_PLAN_DATE).append(" text(50), ");
        sb.append(NOTE_CREATED_DATE).append(" date DEFAULT (datetime('now','localtime')));");


        Log.d("DB.createCategoryTable", sb.toString());
        return sb.toString();

    }
    public long insertPriority(Priority priority) {
        SQLiteDatabase db = this.getWritableDatabase();
        long ret = 0;
        try {
            ContentValues cv = new ContentValues();
            cv.put(PRIORITY_NAME, priority.getName());

            ret = db.insertOrThrow(PRIORITY_TBL, null, cv);
        } catch (Exception ex) {
            Log.d("DB.insertPriority", ex.getMessage());
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
            Log.d("DB.getAllPriority", ex.getMessage());
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
            Log.d("DB.getAllPriorityName", ex.getMessage());
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
            Log.d("DB.updatePriorityName", ex.getMessage());
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
            Log.d("DB.deletePriority", ex.getMessage());
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
            Log.d("DB.insertCategory", ex.getMessage());
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
            Log.d("DB.getAllCategory", ex.getMessage());
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
            Log.d("DB.getAllCategoryName", ex.getMessage());
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
            Log.d("DB.updateCategoryName", ex.getMessage());
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
            Log.d("DB.deleteCatetory", ex.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return ret;
    }

//status's menthod----------------------------------------------------------------------------------------------------------------------------------------
    public String create_tbl_status(){
        StringBuffer sb = new StringBuffer();

        sb.append("create table ").append(TABLE_STATUS);
        sb.append(" (").append(ID).append(" integer ").append(PRIMARY_KEY);
        sb.append(" ").append(AUTOINCREMENT).append(", ");
        sb.append(STATUS_NAME).append(" text(50), ");
        sb.append(STATUS_DATE).append(" date DEFAULT (datetime('now','localtime')))");

        Log.d("DB.createStatusTable", sb.toString());
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
            Log.d("Database.insertStatus", ex.getMessage());
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

    public ArrayList<String> getAllStatusName() {
        ArrayList<String> data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_STATUS, null, null, null, null, null, null);
            String name;

            while (cursor.moveToNext()) {
                name = cursor.getString(cursor.getColumnIndex(STATUS_NAME));
                data.add(name);
            }

        } catch (Exception ex) {
            Log.d("DB.getAllCategoryName", ex.getMessage());
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
            Log.d("DB.updateStatusName", ex.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return ret;
    }

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
//end status menthod---------------------------------------------------------------------------------------
//note's menthod=========================================================================================
    public long insertNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = 0;

        try {
            ContentValues cv = new ContentValues();
            cv.put(NOTE_NAME, note.getName());
            cv.put(NOTE_CATEGORY_NAME, note.getCategory_name());
            cv.put(NOTE_PRIORITY_NAME, note.getPriority_name());
            cv.put(NOTE_STATUS_NAME, note.getStatus_name());
            cv.put(NOTE_PLAN_DATE, note.getPlan_date());

            result = db.insertOrThrow(NOTE_TBL, null, cv);
        } catch (Exception ex) {
            Log.d("DB.insertNote", ex.getMessage());
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
            String name,category_name,priority_name,status_name,plan_date, createDate;

            while (cursor.moveToNext()) {
                name = cursor.getString(cursor.getColumnIndex(NOTE_NAME));
                createDate = cursor.getString(cursor.getColumnIndex(NOTE_CREATED_DATE));
                category_name = cursor.getString(cursor.getColumnIndex(NOTE_CATEGORY_NAME));
                priority_name = cursor.getString(cursor.getColumnIndex(NOTE_PRIORITY_NAME));
                status_name = cursor.getString(cursor.getColumnIndex(NOTE_STATUS_NAME));
                plan_date = cursor.getString(cursor.getColumnIndex(NOTE_PLAN_DATE));
                data.add(new Note(name,category_name,priority_name,status_name,plan_date, createDate));
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
            cv.put(NOTE_CATEGORY_NAME, note.getCategory_name());
            cv.put(NOTE_PRIORITY_NAME, note.getPriority_name());
            cv.put(NOTE_STATUS_NAME, note.getStatus_name());
            cv.put(NOTE_PLAN_DATE, note.getPlan_date());
            String whereClause =  NOTE_NAME+ " = ?";
            String whereArgs[] = {key};
            ret = db.update(NOTE_TBL, cv, whereClause, whereArgs);
        } catch (Exception ex) {
            Log.d("DB.updateNoteName", ex.getMessage());
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




//end note menthod===========================================================================================================================
    public float countStatus(String nameStatus){
        int dem=0;
        //String test="";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            StringBuffer sb = new StringBuffer();

            //sb.append("select count(*)").append(" from ").append(NOTE_TBL).append(" where ").append(NOTE_STATUS_NAME).append(" = ").append(nameStatus);
            sb.append("select count(*)").append(" from ").append(NOTE_TBL).append(" where ").append(NOTE_STATUS_NAME).append(" = '"+nameStatus+"'");
            cursor = db.rawQuery(sb.toString(),null);
            while (cursor.moveToNext()) {
                dem = cursor.getInt(0);
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
        return dem;
    }
    public float sumStatus(){
        int dem=0;
        //String test="";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            StringBuffer sb = new StringBuffer();

            //sb.append("select count(*)").append(" from ").append(NOTE_TBL).append(" where ").append(NOTE_STATUS_NAME).append(" = ").append(nameStatus);
            sb.append("select count(*)").append(" from ").append(TABLE_STATUS);
            cursor = db.rawQuery(sb.toString(),null);
            while (cursor.moveToNext()) {
                dem = cursor.getInt(0);
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
        return dem;
    }
    //User ============================================================================================================
    public void createTableUser(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_USER + " ( " +
                ID + " INTEGER " + PRIMARY_KEY + " " + AUTOINCREMENT + ", " +
                FIRST_NAME + " TEXT, " +
                LAST_NAME + " TEXT,  " +
                EMAIL + " TEXT, " +
                PASSWORD + " TEXT, " +
                ACTIVE + " integer default 0, " +
                "UNIQUE(" + EMAIL + "));";
        try {
            db.execSQL(sql);
        } catch (Exception e) {
            db.close();
            Log.e(TAG, "createTableUser: " + e.getMessage());
        }
    }

    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        if (user != null) {
            cv.put(EMAIL, user.getEmail());
            cv.put(PASSWORD, user.getPwd());
            try {
                return db.insert(TABLE_USER, null, cv);
            } catch (Exception e) {
                Log.e(TAG, "addUser: " + e.getMessage());
            }
        }
        db.close();
        return -1;
    }

    public User getUserFromEmail(String userEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        String colunms[] = {ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD};
        String selections = EMAIL + "= ?";
        String selectionArgs[] = {userEmail};
        Cursor cursor;
        cursor = db.query(TABLE_USER, colunms, selections, selectionArgs, null, null, null);
        cursor.moveToNext();
        if (cursor.moveToNext()) {
            int ID = cursor.getInt(0);
            String firstName = cursor.getString(1);
            String lastName = cursor.getString(2);
            String email = cursor.getString(3);
            String password = cursor.getString(4);
            User user = new User(ID, firstName, lastName, email, password);
            return user;
        }
        db.close();
        return null;
    }

    public User getActiveUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        String colunms[] = {ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD};
        String selections = ACTIVE + " = ?";
        String selectionArgs[] = {String.valueOf(1)};
        Cursor cursor;
        cursor = db.query(TABLE_USER, colunms, selections, selectionArgs, null, null, null);
        if (cursor.moveToNext()) {
            int ID = cursor.getInt(0);
            String firstName = cursor.getString(1);
            String lastName = cursor.getString(2);
            String email = cursor.getString(3);
            String password = cursor.getString(4);
            User user = new User(ID, firstName, lastName, email, password);
            return user;
        }
        db.close();
        return null;
    }

    public User getUser(String userEmail, String userPassword) {
        SQLiteDatabase db = this.getReadableDatabase();
        String colunms[] = {ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD};
        String selections = EMAIL + "= ? and " + PASSWORD + " = ?";
        String selectionArgs[] = {userEmail, userPassword};
        Cursor cursor;
        cursor = db.query(TABLE_USER, colunms, selections, selectionArgs, null, null, null);

        if (cursor.moveToNext()) {
            int ID = cursor.getInt(0);
            String firstName = cursor.getString(1);
            String lastName = cursor.getString(2);
            String email = cursor.getString(3);
            String password = cursor.getString(4);
            User user = new User(ID, firstName, lastName, email, password);
            return user;
        }
        db.close();
        return null;
    }


    public int setSateActive(User user) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        values.put(ACTIVE, 1);
        String whereClause = EMAIL + "= ?";
        String whereArgs[] = {user.getEmail()};
        try {
            return db.update(TABLE_USER, values, whereClause, whereArgs);
        } catch (Exception e) {
            Log.e(TAG, "setSateActive: " + e.getMessage());
            db.close();
            return 0;
        }
    }

    public int resetActive() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ACTIVE, 0);
        try {
            return db.update(TABLE_USER, values, null, null);
        } catch (Exception e) {
            Log.e(TAG, "resetActive: " + e.getMessage());
            db.close();
            return 0;
        }
    }

    public int updateProfile(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FIRST_NAME, user.getFirstName());
        values.put(LAST_NAME, user.getLastName());
        values.put(EMAIL, user.getEmail());
        String whereClause = "ID = ?";
        String whereArgs[] = {user.getID() + ""};
        try {
            return db.update(TABLE_USER, values, whereClause, whereArgs);
        } catch (Exception e) {
            return 0;
        }

    }

    public int changePassword(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PASSWORD, user.getPwd());
        String whereClause = "ID = ?";
        String whereArgs[] = {user.getID() + ""};
        try {
            return db.update(TABLE_USER, contentValues, whereClause, whereArgs);
        } catch (Exception e) {
            Log.e(TAG, "changePassword: " + e.getMessage());
            db.close();
            return 0;
        }
    }


//
//    public int addUser(User user){
//
//    }


}
