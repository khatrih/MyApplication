package com.example.myapplication.contentproviders;

public class ContactsModel {
    private String contactName;
    private int ContactsNumber;
    private int contactsID;

    public ContactsModel(String contactName, int contactsNumber, int contactsID) {
        this.contactName = contactName;
        ContactsNumber = contactsNumber;
        this.contactsID = contactsID;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public int getContactsNumber() {
        return ContactsNumber;
    }

    public void setContactsNumber(int contactsNumber) {
        ContactsNumber = contactsNumber;
    }

    public int getContactsID() {
        return contactsID;
    }

    public void setContactsID(int contactsID) {
        this.contactsID = contactsID;
    }
}
