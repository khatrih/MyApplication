package com.example.myapplication.contentproviders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity {

    private RecyclerView listView;
    private TextView noContacts;
    private static final String TAG = "TAG";
    private static final int REQUEST_CODE = 1;
    ArrayList<ContactsModel> contactsModels;
    private ContactsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        noContacts = findViewById(R.id.no_contacts);
        listView = findViewById(R.id.contacts_list);
        Button btnPermission = findViewById(R.id.btn_permission);

        contactsModels = new ArrayList<>();

        /*if (listView == null) {
            noContacts.setText(R.string.no_data);
        } else {
            noContacts.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            getContacts();
        }*/

        /*if (listView != null) {
            noContacts.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            getContacts();
        } else {
            noContacts.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }*/

        // getAdapter();
        getContacts();
        requestPermissions();

        btnPermission.setOnClickListener(v -> {
            if (checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions();
            }
        });
    }

    private void getAdapter() {
        adapter = new ContactsAdapter(ContactActivity.this, contactsModels);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(adapter);
    }

    private void requestPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.READ_CONTACTS)) {
            Log.d(TAG, "permission not applied");
            new AlertDialog.Builder(this)
                    .setTitle("permission require")
                    .setMessage("please apply permission to access contacts")
                    .setPositiveButton("ok", (dialog, which) -> {
                        ActivityCompat.requestPermissions(ContactActivity.this, new String[]{
                                Manifest.permission.READ_CONTACTS}, REQUEST_CODE);
                        getContacts();
                    })
                    .setNegativeButton("cancel", (dialog, which) -> Log.d(TAG, "cancel"))
                    .create()
                    .show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE);
        }
    }

    public void getContacts() {
        if (checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE);
        } else {

            /*Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, null, null, null);
            startManagingCursor(cursor);
            String[] data = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone._ID,
                    ContactsContract.CommonDataKinds.Phone.PHOTO_URI};
            int[] to = {android.R.id.text1, android.R.id.text2};

            noContacts.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2
                    , cursor, data, to);
            listView.setAdapter(adapter);*/

            /*String contactId = "";
            String contactName = "";
            Uri Image;

            Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);

            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    int contactPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                    if (contactPhoneNumber > 0) {
                        contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
                        contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        //int contactImage = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)));

                        Cursor phoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null, ContactsContract.CommonDataKinds.Phone._ID + " = ?",
                                new String[]{contactId}, null);

                        if (phoneCursor.moveToNext()) {
                            String contactNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            Image = Uri.withAppendedPath(ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(contactId)),
                                    ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
                            contactsModels.add(new ContactsModel(contactName, contactNumber, Image));
                        }

                        phoneCursor.close();
                    }
                }
            }
            cursor.close();
            adapter.notifyDataSetChanged();*/

            contactsModels = readContacts();
            adapter = new ContactsAdapter(ContactActivity.this, contactsModels);
            listView.setLayoutManager(new LinearLayoutManager(this));
            listView.setAdapter(adapter);
        }
    }

    /*public Uri getPhotoUri(long contactId) {

        try {
            Cursor cursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI, null, ContactsContract.Data.CONTACT_ID + "="
                    + contactId + " AND " + ContactsContract.Data.MIMETYPE + "='"
                    + ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE
                    + "'", null, null);
            if (cursor != null) {
                if (!cursor.moveToFirst()) {
                    return null; // no photo
                }
            } else {
                return null; // error in cursor process
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        Uri person = ContentUris.withAppendedId(
                ContactsContract.Contacts.CONTENT_URI, contactId);
        return Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
    }*/

/*    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getContacts();
                Log.d(TAG, "permission granted");
            } else {
                Log.d(TAG, "permission denied");
            }
        }
    }*/

    @SuppressLint("Range")
    private ArrayList<ContactsModel> readContacts() {
        ArrayList<ContactsModel> contactsList = new ArrayList<>();

        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        Cursor contactCursor = getContentResolver().query(uri, null, null, null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC ");
        if (contactCursor.moveToFirst()) {
            long contactId = contactCursor.getLong(contactCursor.getColumnIndex("_ID"));
            do {
                Cursor dataCursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI, null,
                        ContactsContract.Data.CONTACT_ID + "=" + contactId, null, null);

                String callerName = "";
                String callerNumber = "";
                String callerEmail = "";
                String imagePath = "" + R.drawable.ic_call_image;
                byte[] callerImage = null;

                if (dataCursor.moveToFirst()) {
                    callerName = dataCursor.getString(dataCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    do {
                        if (dataCursor.getString(dataCursor.getColumnIndex("mimetype"))
                                .equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {
                            switch (dataCursor.getInt(dataCursor.getColumnIndex("data2"))) {
                                /*case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                                    String Home_NUMBER = dataCursor.getString(dataCursor.getColumnIndex("data1"));
                                    callerNumber += Home_NUMBER;
                                    break;
                                case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                                    String Work_NUMBER = dataCursor.getString(dataCursor.getColumnIndex("data1"));
                                    callerNumber += Work_NUMBER;
                                    break;*/
                                case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                                    String Mobile_NUMBER = dataCursor.getString(dataCursor.getColumnIndex("data1"));
                                    callerNumber += Mobile_NUMBER;
                                    break;
                            }
                        }
                        if (dataCursor.getString(dataCursor.getColumnIndex("mimetype"))
                                .equals(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)) {
                            switch (dataCursor.getInt(dataCursor.getColumnIndex("data2"))) {
                                case ContactsContract.CommonDataKinds.Email.TYPE_HOME:
                                    String homeEmail = dataCursor.getString(dataCursor.getColumnIndex("data1"));
                                    callerEmail += homeEmail;
                                    break;
                            }
                        }
                        if (dataCursor.getString(dataCursor.getColumnIndex("mimetype"))
                                .equals(ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)) {
                            callerImage = dataCursor.getBlob(dataCursor.getColumnIndex("data15"));
                            if (callerImage != null) {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(callerImage, 0, callerImage.length);
                                File cacheDirectory = getBaseContext().getCacheDir();
                                File tmp = new File(cacheDirectory.getPath() + "/_androidCache" + contactId + ".png");
                                try {
                                    FileOutputStream fileOutputStream = new FileOutputStream(tmp);
                                    bitmap.compress(Bitmap.CompressFormat.PNG, 0, fileOutputStream);
                                    fileOutputStream.flush();
                                    fileOutputStream.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                imagePath = tmp.getPath();
                            }
                        }
                    } while (dataCursor.moveToNext());
                    contactsModels.add(new ContactsModel(Long.toString(contactId), callerName, callerNumber, imagePath, callerEmail));
                }
            } while (contactCursor.moveToNext());
        }
        return contactsList;
    }
}
