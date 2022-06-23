package com.example.myapplication.contentproviders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.myapplication.R;

import android.Manifest;
import android.app.AlertDialog;
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

public class ContactActivity extends AppCompatActivity {

    private ListView listView;
    private static final String TAG = "TAG";
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        TextView noContacts = findViewById(R.id.no_contacts);
        listView = findViewById(R.id.contacts_list);
        Button btnPermission = findViewById(R.id.btn_permission);

       /* if (listView!=null) {
            listView.setEmptyView(noContacts);
        }else{
            noContacts.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            getContacts();
        }*/
        if (listView != null) {
            noContacts.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            getContacts();
        } else {
            noContacts.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
        btnPermission.setOnClickListener(v -> requestPermissions());
    }

    private void requestPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.READ_CONTACTS)) {
            Log.d(TAG, "permission not applied");
            new AlertDialog.Builder(this)
                    .setTitle("permission require")
                    .setMessage("please apply permission to access contacts")
                    .setPositiveButton("ok", (dialog, which) ->
                            ActivityCompat.requestPermissions(ContactActivity.this, new String[]{
                                    Manifest.permission.READ_CONTACTS}, REQUEST_CODE))
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
    }

    @Override
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
    }
}
