package com.example.myapplication.roomdb;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {StudentsModel.class}, version = 1)
public abstract class StudentDataBase extends RoomDatabase {
    public abstract StudentDao getStudentDao();

    /*public static StudentDataBase studentDB;

    public static StudentDataBase getInstance(Context context) {
        if (studentDB == null) {
            studentDB = buildDatabaseInstance(context);
        }
        return studentDB;
    }

    private static StudentDataBase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context, StudentDataBase.class, "student_database")
                .allowMainThreadQueries().build();
    }

    public void cleanUp() {
        studentDB = null;
    }*/

}
