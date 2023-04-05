/*
 * File Name: DatabaseHelper.java
 * Description: Database handle for the app.  It manages the creation and upgrade of the database,
 *  as well as the CRUD operations (create, read, update, delete) for tasks and user authentication (registration and login).
 *
 * Project Revision:
 *      Mariana Mozzer, 2023.03.30: Created
 */



package com.example.taskmaster_v2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "TaskDatabase.db";

    private static final String TABLE_NAME = "tasks";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DUE_DATE = "due_date";
    private static final String COLUMN_PRIORITY = "priority";
    private static final String COLUMN_NOTES = "notes";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_DUE_DATE + " TEXT,"
                + COLUMN_PRIORITY + " INTEGER,"
                + COLUMN_NOTES + " TEXT)";

        String CREATE_USERS_TABLE = "CREATE TABLE users ("
                + "username TEXT PRIMARY KEY,"
                + "password TEXT)";

        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_USERS_TABLE);

        // Seed data in the users table
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", "user1");
        contentValues.put("password", "password1");
        db.insert("users", null, contentValues);

        contentValues = new ContentValues();
        contentValues.put("username", "user2");
        contentValues.put("password", "password2");
        db.insert("users", null, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addTask(TaskModel task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, task.getName());
        values.put(COLUMN_DESCRIPTION, task.getDescription());
        values.put(COLUMN_DUE_DATE, task.getDueDate());
        values.put(COLUMN_PRIORITY, task.getPriority());
        values.put(COLUMN_NOTES, task.getNotes());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<TaskModel> getAllTasks() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<TaskModel> courseModalArrayList = new ArrayList<>();

        if (cursorCourses.moveToFirst()) {

            do {
                courseModalArrayList.add(new TaskModel(cursorCourses.getString(1),
                        cursorCourses.getString(2),
                        cursorCourses.getString(3),
                        cursorCourses.getInt(4),
                        cursorCourses.getString(5)));

            } while (cursorCourses.moveToNext());

        }
        cursorCourses.close();
        return courseModalArrayList;
    }

    public void deleteTask(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, " name=?", new String[] { name });
        db.close();
    }

    public void updateTask(String originalName, String name, String description, String dueDate, int priority, String notes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_DUE_DATE, dueDate);
        values.put(COLUMN_PRIORITY, priority);
        values.put(COLUMN_NOTES, notes);
        db.update(TABLE_NAME, values, "name=?", new String[] { originalName });
        db.close();
    }

    public TaskModel getTask(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] { COLUMN_ID,
                        COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_DUE_DATE, COLUMN_PRIORITY, COLUMN_NOTES }, COLUMN_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        TaskModel task = null;

        if (cursor != null && cursor.moveToFirst()) {
            task = new TaskModel(cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getString(5));
        }

        cursor.close();

        return task;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username=? AND password=?", new String[]{username, password});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        db.close();
        return exists;
    }

    public boolean registerUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        long result = db.insert("users", null, values);
        db.close();
        return result != -1;
    }

}
