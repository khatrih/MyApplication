package com.example.myapplication.roomdb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "student_tbl")
public class StudentsModel {
    @PrimaryKey(autoGenerate = true)
    public int sid;
    @ColumnInfo(name = "student_name")
    public String sName;
    @ColumnInfo(name = "student_email")
    public String sEmail;
    @ColumnInfo(name = "student_address")
    public String sAddress;
    @ColumnInfo(name = "student_mobile_no")
    public String SMobileNo;
    @ColumnInfo(name = "student_qualified")
    public String sQualification;

    @Override
    public String toString() {
        return "StudentsModel{" +
                "sid=" + sid +
                ", sName='" + sName + '\'' +
                ", sEmail='" + sEmail + '\'' +
                ", sAddress='" + sAddress + '\'' +
                ", SMobileNo='" + SMobileNo + '\'' +
                ", sQualification='" + sQualification + '\'' +
                '}';
    }

    public StudentsModel(String sName, String sEmail, String sAddress, String SMobileNo,
                         String sQualification) {

        this.sName = sName;
        this.sEmail = sEmail;
        this.sAddress = sAddress;
        this.SMobileNo = SMobileNo;
        this.sQualification = sQualification;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
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

    public String getSMobileNo() {
        return SMobileNo;
    }

    public void setSMobileNo(String SMobileNo) {
        this.SMobileNo = SMobileNo;
    }

    public String getsQualification() {
        return sQualification;
    }

    public void setsQualification(String sQualification) {
        this.sQualification = sQualification;
    }
}
