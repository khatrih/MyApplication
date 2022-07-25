package com.example.myapplication.databasedemo;

import java.io.Serializable;

public class CourseDataModel implements Serializable {
    private int sId;
    private String sName;
    private String sEmail;
    private String SAddress;
    private String sPhoneNUmber;
    private String sDegreeType;
    private byte[] sImage;

    public CourseDataModel(String sName, String sEmail, String SAddress, String sPhoneNUmber,
                           String sDegreeType, byte[] sImage) {
        this.sName = sName;
        this.sEmail = sEmail;
        this.SAddress = SAddress;
        this.sPhoneNUmber = sPhoneNUmber;
        this.sDegreeType = sDegreeType;
        this.sImage = sImage;
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

    public String getSAddress() {
        return SAddress;
    }

    public void setSAddress(String SAddress) {
        this.SAddress = SAddress;
    }

    public String getsPhoneNUmber() {
        return sPhoneNUmber;
    }

    public void setsPhoneNUmber(String sPhoneNUmber) {
        this.sPhoneNUmber = sPhoneNUmber;
    }

    public String getsDegreeType() {
        return sDegreeType;
    }

    public void setsDegreeType(String sDegreeType) {
        this.sDegreeType = sDegreeType;
    }

    public byte[] getsImage() {
        return sImage;
    }

    public void setsImage(byte[] sImage) {
        this.sImage = sImage;
    }

    public int getsId() {
        return sId;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }
}
