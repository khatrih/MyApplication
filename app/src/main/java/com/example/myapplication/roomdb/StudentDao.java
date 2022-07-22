package com.example.myapplication.roomdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StudentDao {

    @Query("SELECT * FROM student_tbl ORDER BY student_name ASC")
    List<StudentsModel> getAllStudentData();

    @Insert
    void insertRecords(StudentsModel studentsModel);

    @Query("DELETE FROM student_tbl WHERE sId = :id")
    void deletedStudentData(int id);

    @Query("UPDATE student_tbl SET student_name = :name, student_name = :name, student_email = :email, student_address = :address, student_mobile_no = :mobileno, student_qualified = :qualification, gender = :gender WHERE sID = :id")
    void updateStudentData(int id, String name, String email, String address, String mobileno, String qualification, String gender);

}
