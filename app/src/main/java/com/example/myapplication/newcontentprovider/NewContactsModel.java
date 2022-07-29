package com.example.myapplication.newcontentprovider;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class NewContactsModel implements Parcelable {
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

    protected NewContactsModel(Parcel in) {
        contactName = in.readString();
        contactNumber = in.readString();
        contactImage = in.readParcelable(Uri.class.getClassLoader());
        contactId = in.readString();
        contactEmail = in.readString();
    }

    public static final Parcelable.Creator<NewContactsModel> CREATOR = new Parcelable.Creator<NewContactsModel>() {
        @Override
        public NewContactsModel createFromParcel(Parcel in) {
            return new NewContactsModel(in);
        }

        @Override
        public NewContactsModel[] newArray(int size) {
            return new NewContactsModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(contactName);
        dest.writeString(contactNumber);
        dest.writeParcelable(contactImage, flags);
        dest.writeString(contactId);
        dest.writeString(contactEmail);
    }
}
