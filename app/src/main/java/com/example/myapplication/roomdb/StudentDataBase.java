package com.example.myapplication.roomdb;

import android.content.Context;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.DeleteColumn;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {StudentsModel.class}, version = 2)
public abstract class StudentDataBase extends RoomDatabase {
    public abstract StudentDao getStudentDao();

    static Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE student_tbl ADD COLUMN gender TEXT DEFAULT 'female'");
        }
    };

    public static StudentDataBase studentDB;

    public static StudentDataBase getInstance(Context context) {
        if (studentDB == null) {
            studentDB = Room.databaseBuilder(context, StudentDataBase.class, "student_database")
                    .addMigrations(MIGRATION_1_2)
                    .allowMainThreadQueries()
                    .build();
        }
        return studentDB;
    }
}