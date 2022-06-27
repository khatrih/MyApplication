package com.example.myapplication.roomdb;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface StudentDao {

    /*@Query("SELECT * FROM student_tbl ORDER BY student_name ASC")
    ArrayList<StudentsModel> getData();*/

    @Insert
    void insertRecords(StudentsModel studentsModel);
}
