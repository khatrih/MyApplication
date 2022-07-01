package com.example.myapplication.contentproviders;

import android.net.Uri;

public class ContactsModel {
    private String callName;
    private String callNumber;
    private String callID;
    private int callImage;
    private Uri image;

    public ContactsModel(String contactName, String contactsNumber, Uri image) {
        this.callName = contactName;
        this.callNumber = contactsNumber;
//        this.callImage = callImage;
        this.image = image;
    }

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public String getCallName() {
        return callName;
    }

    public void setCallName(String callName) {
        this.callName = callName;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public String getCallID() {
        return callID;
    }

    public void setCallID(String callID) {
        this.callID = callID;
    }

    public int getCallImage() {
        return callImage;
    }

    public void setCallImage(int callImage) {
        this.callImage = callImage;
    }
}
