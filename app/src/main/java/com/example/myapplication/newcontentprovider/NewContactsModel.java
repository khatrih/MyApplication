package com.example.myapplication.newcontentprovider;

import android.net.Uri;

import java.util.ArrayList;

public class NewContactsModel {
    /*private String newContactsName;
    private String newContactsNumber;
    private String newContactsId;
    private String newContactsImage;

    public NewContactsModel(String newContactsName, String newContactsNumber, String newContactsId, String newContactsImage) {
        this.newContactsName = newContactsName;
        this.newContactsNumber = newContactsNumber;
        this.newContactsId = newContactsId;
        this.newContactsImage = newContactsImage;
    }

    public String getNewContactsName() {
        return newContactsName;
    }

    public void setNewContactsName(String newContactsName) {
        this.newContactsName = newContactsName;
    }

    public String getNewContactsNumber() {
        return newContactsNumber;
    }

    public void setNewContactsNumber(String newContactsNumber) {
        this.newContactsNumber = newContactsNumber;
    }

    public String getNewContactsId() {
        return newContactsId;
    }

    public void setNewContactsId(String newContactsId) {
        this.newContactsId = newContactsId;
    }

    public String getNewContactsImage() {
        return newContactsImage;
    }

    public void setNewContactsImage(String newContactsImage) {
        this.newContactsImage = newContactsImage;
    }*/

    /*private String contactId, contactName, contactNumber, contactEmail,
            contactPhoto, contactOtherDetails;

    public NewContactsModel(String contactId, String contactName, String contactNumber, String contactEmail,
                            String contactPhoto, String contactOtherDetails) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.contactNumber = contactNumber;
        this.contactPhoto = contactPhoto;
        this.contactOtherDetails = contactOtherDetails;
    }

    public String getContactID() {
        return contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getContactPhoto() {
        return contactPhoto;
    }

    public String getContactOtherDetails() {
        return contactOtherDetails;
    }*/

    private String callName;
    private String callNumber;
    private String callID;
    private String callImage;
    private Uri image;
    private String callEmail;

    public NewContactsModel(String id, String contactName, String contactsNumber, String contactImage, String contactEmail) {
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
