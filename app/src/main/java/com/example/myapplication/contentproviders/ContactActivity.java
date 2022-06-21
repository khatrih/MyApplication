package com.example.myapplication.contentproviders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.myapplication.R;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ContactActivity extends AppCompatActivity {

    private TextView contacts;
    private TextView noContacts;
    private Button btnPermission;
    private ListView listView;
    private static final String TAG = "TAG";
    private static final int REQUEST_CODE = 211;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        contacts = findViewById(R.id.contacts);
        noContacts = findViewById(R.id.no_contacts);
        listView = findViewById(R.id.contacts_list);
        btnPermission = findViewById(R.id.btn_permission);

        if (listView != null) {
            noContacts.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            getContacts();
        } else {
            listView.setVisibility(View.GONE);
            noContacts.setVisibility(View.VISIBLE);
        }
        btnPermission.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(ContactActivity.this,
                    Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                getContacts();
                Log.d(TAG, "permission already applied");
            } else {
                requestPermissions();
            }
        });
    }

    private void requestPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.READ_CONTACTS)) {
            Log.d(TAG, "permission not applied");

            new AlertDialog.Builder(this)
                    .setTitle("permission require")
                    .setMessage("please apply permission to access contacts")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(ContactActivity.this, new String[]{
                                    Manifest.permission.READ_CONTACTS}, REQUEST_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            /*ActivityCompat.requestPermissions(ContactActivity.this,new String[]{
                                    Manifest.permission.READ_CONTACTS},REQUEST_CODE);*/
                            Log.d(TAG, "cancel");
                        }
                    }).create().show();

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE);
        }
    }

    public void getContacts() {
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        startManagingCursor(cursor);

        String[] data = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone._ID};
        int[] to = {android.R.id.text1, android.R.id.text2};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2
                , cursor, data, to);
        listView.setAdapter(adapter);
    }

   /* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getContacts();
            } else {
                Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }*/
}

