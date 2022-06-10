package com.example.myapplication.databasedemo;

public class CourseDataModel {

    private String sName;
    private String sEmail;
    private String SAddress;
    private String sPhoneNUmber;
    private String sDegreeType;

    public CourseDataModel(String sName, String sEmail, String SAddress, String sPhoneNUmber, String sDegreeType) {
        this.sName = sName;
        this.sEmail = sEmail;
        this.SAddress = SAddress;
        this.sPhoneNUmber = sPhoneNUmber;
        this.sDegreeType = sDegreeType;
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
}
