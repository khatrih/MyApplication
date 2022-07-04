package com.example.myapplication.newcontentprovider;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.example.myapplication.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class NewContactsActivity extends AppCompatActivity {
    private RecyclerView contactListView;
    private ArrayList<NewContactsModel> arrayList;
    private NewContactsAdapter adapter;
    private static ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contacts);

        contactListView = findViewById(R.id.contacts_listview);
        contactListView.setLayoutManager(new LinearLayoutManager(this));

        new LoadContacts().execute();
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
    }
*/

    @SuppressLint("Range")
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

    }



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