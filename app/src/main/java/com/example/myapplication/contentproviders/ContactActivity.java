package com.example.myapplication.contentproviders;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ContactActivity extends AppCompatActivity {

    private TextView contacts;
    private TextView noContacts;
    private ListView listView;
    private Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        contacts = findViewById(R.id.contacts);
        noContacts = findViewById(R.id.no_contacts);
        listView = findViewById(R.id.ListView);

        getContacts();
    }

    public void getContacts() {

        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        startManagingCursor(cursor);

        String[] data = {ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone._ID};
        int[] to = {android.R.id.text1, android.R.id.text2};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2,
                cursor, data, to);

        listView.setAdapter(adapter);
    }
}