package com.example.myapplication.newcontentprovider;

import android.net.Uri;

import java.util.ArrayList;

public class NewContactsModel {
    String contactName;
    String contactNumber;
    Uri contactImage;
    String contactId;
    String contactEmail;

    public NewContactsModel(String contactName, String contactNumber, Uri contactImage, String contactId,
                            String contactEmail) {
        this.contactName = contactName;
        this.contactNumber = contactNumber;
        this.contactImage = contactImage;
        this.contactId = contactId;
        this.contactEmail = contactEmail;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Uri getContactImage() {
        return contactImage;
    }

    public void setContactImage(Uri contactImage) {
        this.contactImage = contactImage;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}
