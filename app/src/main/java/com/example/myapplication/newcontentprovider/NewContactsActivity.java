package com.example.myapplication.newcontentprovider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;

import com.example.myapplication.R;
import com.example.myapplication.to_do_list.ToDoListLoginActivity;

import java.io.Serializable;
import java.util.ArrayList;

public class NewContactsActivity extends AppCompatActivity {
    private RecyclerView contactListView;
    private ArrayList<NewContactsModel> newContactsModels = new ArrayList<>();
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contacts);

        contactListView = findViewById(R.id.rvContacts);
        contactListView.setLayoutManager(new LinearLayoutManager(this));
        dialog = new ProgressDialog(NewContactsActivity.this);
        dialog.setMessage("please wait...");
        checkPermissions();
    }

    private void checkPermissions() {
        //CHECK SELF PERMISSION
        if (ContextCompat.checkSelfPermission(NewContactsActivity.this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(NewContactsActivity.this, new String[]{
                    Manifest.permission.READ_CONTACTS}, 100);
        } else {
            getContactList();
        }
    }

    @SuppressLint("Range")
    private void getContactList() {
        newContactsModels = new ArrayList<>();
        //ContactsContract defines an extensible database of contact-related information
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        Cursor contactCursor = getContentResolver().query(uri, null, null, null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC ");
        dialog.show();
        if (contactCursor.getCount() > 0) {
            while (contactCursor.moveToNext()) {
                String contactId = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts._ID));
                String contactName = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                Uri dataUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                Cursor dataCursor = getContentResolver().query(dataUri, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{contactId}, null);

                if (dataCursor.moveToNext()) {
                    String contactEmail = "";
                    String contactNumber = dataCursor.getString(dataCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    Uri myImage = getPhotoUri(Long.parseLong(contactId));
                    Cursor emailsCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{contactId}, null);

                    if (emailsCursor.moveToFirst()) {
                        String Email = emailsCursor.getString(emailsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        contactEmail += Email;
                    }
                    dialog.dismiss();
                    newContactsModels.add(new NewContactsModel(contactName, contactNumber, myImage, contactId, contactEmail));
                    NewContactsAdapter adapter = new NewContactsAdapter(NewContactsActivity.this, newContactsModels);
                    contactListView.setAdapter(adapter);
                    emailsCursor.close();
                    dataCursor.close();
                }
            }
            contactCursor.close();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getContactList();
        } else {
            checkPermissions();
        }
    }

    public Uri getPhotoUri(long contactId) {
        try {
            Cursor cursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI, null,
                    ContactsContract.Data.CONTACT_ID + "=" + contactId + " AND " + ContactsContract.Data.MIMETYPE + "='"
                            + ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE + "'", null, null);
            if (cursor != null) {
                if (!cursor.moveToFirst()) {
                    return null; // no photo
                }
            } else {
                return null; // error in cursor process
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        Uri person = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
        return Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
    }
}