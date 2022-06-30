package com.example.myapplication.roomdb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "student_tbl")
public class StudentsModel {
    @PrimaryKey(autoGenerate = true)
    public int sId;
    @ColumnInfo(name = "student_name")
    public String sName;
    @ColumnInfo(name = "student_email")
    public String sEmail;
    @ColumnInfo(name = "student_address")
    public String sAddress;
    @ColumnInfo(name = "student_mobile_no")
    public String sMobileNo;
    @ColumnInfo(name = "student_qualified")
    public String sQualification;
    @ColumnInfo(name = "gender")
    public String sGender;

    public StudentsModel(int sId, String sName, String sEmail, String sAddress, String sMobileNo,
                         String sQualification, String sGender) {
        this.sId = sId;
        this.sName = sName;
        this.sEmail = sEmail;
        this.sAddress = sAddress;
        this.sMobileNo = sMobileNo;
        this.sQualification = sQualification;
        this.sGender = sGender;
    }

    public int getsId() {
        return sId;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsEmail() {
        return sEmail;
    }

    public void setsEmail(String sEmail) {
        this.sEmail = sEmail;
    }

    public String getsAddress() {
        return sAddress;
    }

    public void setsAddress(String sAddress) {
        this.sAddress = sAddress;
    }

    public String getsMobileNo() {
        return sMobileNo;
    }

    public void setsMobileNo(String sMobileNo) {
        this.sMobileNo = sMobileNo;
    }

    public String getsQualification() {
        return sQualification;
    }

    public void setsQualification(String sQualification) {
        this.sQualification = sQualification;
    }

    public String getsGender() {
        return sGender;
    }

    public void setsGender(String sGender) {
        this.sGender = sGender;
    }
}
