package com.example.myapplication.databasedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.ByteArrayOutputStream;
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
    private static final String IMAGE_COL = "student_images";
    private byte[] studentImageBitmap;
    private byte[] updatedStudentBitmap;

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
                + QUALIFICATION_COL + " TEXT, "
                + IMAGE_COL + " BLOB "
                + ")";
        db.execSQL(query);
    }

    //inserting data
    public void addNewCourse(String studentName, String studentEmail, String studentAddress,
                             String studentPhone, String studentQualification, Bitmap studentImage)
            throws SQLException {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        studentImage.compress(Bitmap.CompressFormat.JPEG, 10, stream);
        studentImageBitmap = stream.toByteArray();

        values.put(NAME_COL, studentName);
        values.put(EMAIL_COL, studentEmail);
        values.put(ADDRESS_COL, studentAddress);
        values.put(PHONE_NO_COL, studentPhone);
        values.put(QUALIFICATION_COL, studentQualification);
        values.put(IMAGE_COL, studentImageBitmap);

        sqLiteDatabase.insert(TABLE_NAME, null, values);
        Log.d("TAG", "addNewCourse: " + values);
        sqLiteDatabase.close();
    }

    //readable data
    public ArrayList<CourseDataModel> readCourse() {
        SQLiteDatabase sdb = this.getReadableDatabase();
        Cursor cursor = sdb.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<CourseDataModel> dataModels = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    dataModels.add(new CourseDataModel(cursor.getString(1),
                            cursor.getString(2), cursor.getString(3),
                            cursor.getString(4), cursor.getString(5),
                            cursor.getBlob(6)));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return dataModels;
    }

    //filter fetch all data
    public ArrayList<CourseDataModel> fetchAllData() {
        SQLiteDatabase sdb = this.getReadableDatabase();
        Cursor cursor = sdb.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<CourseDataModel> dataModels = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    dataModels.add(new CourseDataModel(cursor.getString(1),
                            cursor.getString(2), cursor.getString(3),
                            cursor.getString(4), cursor.getString(5),
                            cursor.getBlob(6)));
                } while (cursor.moveToNext());
            }

            cursor.close();
        }
        return dataModels;
    }

    //fetching MCA
    public ArrayList<CourseDataModel> fetchData(String degreeType) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query;
        if (degreeType.equalsIgnoreCase("All")){
            query = "SELECT * FROM " + TABLE_NAME;
        }else {
            query= "SELECT * FROM student WHERE student_qualification='" + degreeType + "'";
        }
        Cursor c = sqLiteDatabase.rawQuery(query, null);
        ArrayList<CourseDataModel> mcaData = new ArrayList<>();
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    mcaData.add(new CourseDataModel(c.getString(1),
                            c.getString(2), c.getString(3),
                            c.getString(4), c.getString(5),
                            c.getBlob(6)));
                } while (c.moveToNext());
            }

            c.close();
        }
        return mcaData;
    }
/*
    //fetching MBA
    public ArrayList<CourseDataModel> fetchMBAData() {
        String degreeType = "MBA";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM student WHERE student_qualification='" + degreeType + "'";
        Cursor c = sqLiteDatabase.rawQuery(query, null);
        ArrayList<CourseDataModel> mcaData = new ArrayList<>();
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    mcaData.add(new CourseDataModel(c.getString(1),
                            c.getString(2), c.getString(3),
                            c.getString(4), c.getString(5),
                            c.getBlob(6)));
                } while (c.moveToNext());
            }
            c.close();
        }
        return mcaData;
    }*/

    public void updateStudentDetails(String sNAME, String studentName, String studentEmail, String studentAddress,
                             String studentPhone, String studentQualification, Bitmap studentImage){
        SQLiteDatabase liteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        studentImage.compress(Bitmap.CompressFormat.JPEG, 10, stream);
        updatedStudentBitmap = stream.toByteArray();

        values.put(NAME_COL, studentName);
        values.put(EMAIL_COL, studentEmail);
        values.put(ADDRESS_COL, studentAddress);
        values.put(PHONE_NO_COL, studentPhone);
        values.put(QUALIFICATION_COL, studentQualification);
        values.put(IMAGE_COL, updatedStudentBitmap);

        liteDatabase.update(TABLE_NAME, values, "id=?", new String[]{sNAME});
        liteDatabase.close();
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
