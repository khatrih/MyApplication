package com.example.myapplication.databasedemo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "test.db";
    private static final int DB_VERSION = 2;
    private static final String TABLE_NAME = "student";
    private static final String ID_COL = "id";
    private static final String NAME_COL = "student_name";
    private static final String EMAIL_COL = "student_email";
    private static final String ADDRESS_COL = "student_address";
    private static final String PHONE_NO_COL = "student_phone";
    private static final String QUALIFICATION_COL = "student_qualification";
    String filed = "MCA";

    //creating database
    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creating table
        String query = "CREATE TABLE " + TABLE_NAME + "("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT, "
                + EMAIL_COL + " TEXT, "
                + ADDRESS_COL + " TEXT, "
                + PHONE_NO_COL + " TEXT, "
                + QUALIFICATION_COL + " TEXT)";

        db.execSQL(query);
    }

    //inserting data
    public void addNewCourse(String studentName, String studentEmail, String studentAddress,
                             String studentPhone, String studentQualification) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NAME_COL, studentName);
        values.put(EMAIL_COL, studentEmail);
        values.put(ADDRESS_COL, studentAddress);
        values.put(PHONE_NO_COL, studentPhone);
        values.put(QUALIFICATION_COL, studentQualification);

        sqLiteDatabase.insert(TABLE_NAME, null, values);
        sqLiteDatabase.close();
    }

    //filter
    public ArrayList<CourseDataModel> fetchAllData() {
        SQLiteDatabase sdb = this.getReadableDatabase();
        Cursor cursor = sdb.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<CourseDataModel> dataModels = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    dataModels.add(new CourseDataModel(cursor.getString(1),
                            cursor.getString(2), cursor.getString(3),
                            cursor.getString(4), cursor.getString(5)));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return dataModels;
    }
    /*public ArrayList<CourseDataModel> getMCAData(){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME,null);
    }*/
    //public ArrayList<CourseDataModel> getMBAData(){}

    //readable data
    public ArrayList<CourseDataModel> readCourse() {
        SQLiteDatabase sdb = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = sdb.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<CourseDataModel> dataModels = new ArrayList<>();

        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    dataModels.add(new CourseDataModel(cursor.getString(1),
                            cursor.getString(2), cursor.getString(3),
                            cursor.getString(4), cursor.getString(5)));
                } while (cursor.moveToNext());
            }
        }
        return dataModels;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
