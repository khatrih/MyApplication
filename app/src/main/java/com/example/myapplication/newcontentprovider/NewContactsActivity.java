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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.contentproviders.ContactActivity;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.Permission;
import java.util.ArrayList;

public class NewContactsActivity extends AppCompatActivity {
    private RecyclerView contactListView;
    private ArrayList<NewContactsModel> arrayList;
    private NewContactsAdapter adapter;
    private static ProgressDialog pd;
    String photoPath;
    byte[] photoByte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contacts);

        contactListView = findViewById(R.id.contacts_listview);
        contactListView.setLayoutManager(new LinearLayoutManager(this));

        checkPermissions();
    }

    private void checkPermissions() {
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
        arrayList = new ArrayList<>();
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        Cursor contactCursor = getContentResolver().query(uri, null, null, null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC ");

        if (contactCursor.getCount() > 0) {

            while (contactCursor.moveToNext()) {
                String contactId = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts._ID));
                String contactName = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                Uri uriPhone = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

                Cursor phoneCursor = getContentResolver().query(uriPhone, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{contactId}
                        , null);

                photoPath = "" + R.drawable.ic_call_image;
                photoByte = null;
                String contactEmailAddresses = "";

                if (phoneCursor.moveToNext()) {
                    String contactNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds
                            .Phone.NUMBER));

                    Uri myImage = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, contactId);
                    InputStream photo_stream = ContactsContract.Contacts.openContactPhotoInputStream(getContentResolver(), myImage);
                    BufferedInputStream buf = new BufferedInputStream(photo_stream);
                    Bitmap bit = BitmapFactory.decodeStream(buf);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bit.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    photoByte = stream.toByteArray();

                    Bitmap bitmap = BitmapFactory.decodeByteArray(photoByte, 0, photoByte.length);
                    File cacheDire = getApplicationContext().getCacheDir();
                    File tmp = new File(cacheDire.getPath() + "/_androidjunk" + contactId + ".png");
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(tmp);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 10, fileOutputStream);
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    photoPath = tmp.getPath();

                    Cursor emailCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                            null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId,
                            null, null);
                    while (emailCursor.moveToNext()) {
                        String email = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        contactEmailAddresses += email;
                    }

                    arrayList.add(new NewContactsModel(contactId, contactName, contactNumber, photoPath, contactEmailAddresses));
                    emailCursor.close();
                    phoneCursor.close();
                }
            }
            contactCursor.close();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter = new NewContactsAdapter(NewContactsActivity.this, arrayList);
        contactListView.setLayoutManager(new LinearLayoutManager(this));
        contactListView.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0]
                == PackageManager.PERMISSION_GRANTED) {
            getContactList();
        } else {
            checkPermissions();
        }
    }

    /*@SuppressLint("Range")
    private ArrayList<NewContactsModel> readContacts() {
        ArrayList<NewContactsModel> contactList = new ArrayList<NewContactsModel>();

        Uri uri = ContactsContract.Contacts.CONTENT_URI; // Contact URI
        Cursor contactsCursor = getContentResolver().query(uri, null, null,
                null, ContactsContract.Contacts.DISPLAY_NAME + " ASC "); // Return
        if (contactsCursor.moveToFirst()) {
            do {
                long contctId = contactsCursor.getLong(contactsCursor.getColumnIndex("_ID"));
                Uri dataUri = ContactsContract.Data.CONTENT_URI;
                Cursor dataCursor = getContentResolver().query(dataUri, null,
                        ContactsContract.Data.CONTACT_ID + " = " + contctId,
                        null, null);
                String displayName = "";
                String nickName = "";
                String homePhone = "";
                String mobilePhone = "";
                String workPhone = "";
                String photoPath = "" + R.drawable.ic_call_image; // Photo path
                byte[] photoByte = null;
                String homeEmail = "";
                String workEmail = "";
                String companyName = "";
                String title = "";
                String contactNumbers = "";
                String contactEmailAddresses = "";
                String contactOtherDetails = "";

                // Now start the cusrsor
                if (dataCursor.moveToFirst()) {
                    displayName = dataCursor
                            .getString(dataCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    do {
                        if (dataCursor.getString(dataCursor.getColumnIndex("mimetype"))
                                .equals(ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE)) {
                            nickName = dataCursor.getString(dataCursor.getColumnIndex("data1"));
                            contactOtherDetails += "NickName : " + nickName + "n";
                        }

                        if (dataCursor.getString(dataCursor.getColumnIndex("mimetype"))
                                .equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {
                            switch (dataCursor.getInt(dataCursor.getColumnIndex("data2"))) {
                                case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                                    homePhone = dataCursor.getString(dataCursor.getColumnIndex("data1"));
                                    contactNumbers += "Home Phone : " + homePhone + "n";
                                    break;
                                case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                                    workPhone = dataCursor.getString(dataCursor.getColumnIndex("data1"));
                                    contactNumbers = contactNumbers + ("Work Phone : " + workPhone + "n");
                                    break;
                                case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                                    mobilePhone = dataCursor.getString(dataCursor.getColumnIndex("data1"));
                                    contactNumbers += "Mobile Phone : " + mobilePhone + "n";
                                    break;
                            }
                        }
                        if (dataCursor.getString(dataCursor.getColumnIndex("mimetype"))
                                .equals(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)) {
                            switch (dataCursor.getInt(dataCursor.getColumnIndex("data2"))) {
                                case ContactsContract.CommonDataKinds.Email.TYPE_HOME:
                                    homeEmail = dataCursor.getString(dataCursor.getColumnIndex("data1"));
                                    contactEmailAddresses += "Home Email : " + homeEmail + "n";
                                    break;
                                case ContactsContract.CommonDataKinds.Email.TYPE_WORK:
                                    workEmail = dataCursor.getString(dataCursor.getColumnIndex("data1"));
                                    contactEmailAddresses += "Work Email : " + workEmail + "n";
                                    break;
                            }
                        }

                        if (dataCursor.getString(dataCursor.getColumnIndex("mimetype"))
                                .equals(ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)) {
                            companyName = dataCursor.getString(dataCursor.getColumnIndex("data1"));
                            contactOtherDetails += "Coompany Name : " + companyName + "n";
                            title = dataCursor.getString(dataCursor.getColumnIndex("data4"));
                            contactOtherDetails += "Title : " + title + "n";
                        }

                        if (dataCursor.getString(dataCursor.getColumnIndex("mimetype"))
                                .equals(ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)) {
                            photoByte = dataCursor.getBlob(dataCursor.getColumnIndex("data15"));

                            if (photoByte != null) {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(photoByte, 0, photoByte.length);
                                File cacheDirectory = getBaseContext().getCacheDir();
                                File tmp = new File(cacheDirectory.getPath() + "/_androhub" + contctId + ".png");
                                try {
                                    FileOutputStream fileOutputStream = new FileOutputStream(tmp);
                                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                                    fileOutputStream.flush();
                                    fileOutputStream.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                photoPath = tmp.getPath();
                            }
                        }

                    } while (dataCursor.moveToNext());
                    contactList.add(new NewContactsModel(Long.toString(contctId), displayName, contactNumbers,
                            contactEmailAddresses, photoPath, contactOtherDetails));
                }
            } while (contactsCursor.moveToNext());
        }
        return contactList;
    }*/

    /*@SuppressLint("Range")
    private ArrayList<NewContactsModel> readContacts() {
        ArrayList<NewContactsModel> contactsList = new ArrayList<>();

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
                    contactsList.add(new NewContactsModel(Long.toString(contactId), callerName, callerNumber, imagePath, callerEmail));
                }
                dataCursor.close();
            } while (contactCursor.moveToNext());
        }
        contactCursor.close();
        return contactsList;
    }

    private class LoadContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            arrayList = readContacts();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (arrayList != null && arrayList.size() > 0) {
                if (adapter == null) {
                    adapter = new NewContactsAdapter(NewContactsActivity.this, arrayList);
                    contactListView.setAdapter(adapter);
                }
            } else {
                Toast.makeText(NewContactsActivity.this, "No Contacts Available",
                        Toast.LENGTH_LONG).show();
            }
            if (pd.isShowing())
                pd.dismiss();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = ProgressDialog.show(NewContactsActivity.this, "Loading Contacts", "Please Wait...");
        }

    }*/

    /*private class loadContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = ProgressDialog.show(NewContactsActivity.this, "loading contacts", "please wait...");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            arrayList = readContacts();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            pd.dismiss();
            if (arrayList != null && arrayList.size() > 0) {
                getSupportActionBar().setSubtitle(arrayList.size() + "Contacts");
                adapter = null;
                if (adapter == null) {
                    adapter = new NewContactsAdapter(NewContactsActivity.this, arrayList);
                    contactListView.setAdapter(adapter);
                } else {
                    Toast.makeText(NewContactsActivity.this, "There are no Contacts", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private ArrayList<NewContactsModel> readContacts() {
        ArrayList<NewContactsModel> contactsLists = new ArrayList<>();

        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        Cursor contactCursor = getContentResolver().query(uri, null, null, null,
                ContactsContract.Contacts.DISPLAY_NAME + "ASC");

        if (contactCursor.moveToFirst()) {
            do {
                long contactId = contactCursor.getLong(contactCursor.getColumnIndex("_ID"));
                Uri dataUri = ContactsContract.Data.CONTENT_URI;

                Cursor dataCursor = getContentResolver().query(dataUri, null,
                        ContactsContract.Data.CONTACT_ID + " = " + contactId, null, null);

                String displayName = "";
                String mobilePhone = "";
                String photoPath = "" + R.drawable.ic_call_image;
                byte[] photoByte = null;

                if (dataCursor.moveToFirst()) {
                    displayName = dataCursor.getString(dataCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    mobilePhone = dataCursor.getString(dataCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    do {
                        if (dataCursor.getString(dataCursor.getColumnIndex("mimetype"))
                                .equals(ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)) {
                            photoByte = dataCursor.getBlob(dataCursor.getColumnIndex("data15"));

                            if (photoByte != null) {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(photoByte, 0, photoByte.length);
                                File cacheDirectory = getBaseContext().getCacheDir();
                                File temp = new File(cacheDirectory.getPath() + "/_thumbnails" + contactId + ".png");

                                try {
                                    FileOutputStream fileOutputStream = new FileOutputStream(temp);
                                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                                    fileOutputStream.flush();
                                    fileOutputStream.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                photoPath = temp.getPath();
                            }
                            contactsLists.add(new NewContactsModel(displayName, mobilePhone, Long.toString(contactId), photoPath));
                        }
                    } while (dataCursor.moveToNext());
                }
            } while (contactCursor.moveToNext());
        }
        return contactsLists;
    }*/

}