package com.example.myapplication.contentproviders;

import android.net.Uri;

public class ContactsModel {
    private String callName;
    private String callNumber;
    private String callID;
    private String callImage;
    private Uri image;
    private String callEmail;

    public ContactsModel(String id, String contactName, String contactsNumber, String contactImage, String contactEmail) {
        this.callID = id;
        this.callName = contactName;
        this.callNumber = contactsNumber;
        this.callImage = contactImage;
        this.callEmail = contactEmail;
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

    public String getCallImage() {
        return callImage;
    }

    public void setCallImage(String callImage) {
        this.callImage = callImage;
    }
}
