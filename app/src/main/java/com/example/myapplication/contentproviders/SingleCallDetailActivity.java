package com.example.myapplication.contentproviders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

public class SingleCallDetailActivity extends AppCompatActivity {

    private TextView tvcallerName;
    private TextView tvcallerNumber;
    private ImageView ivCallerImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlecall_detail);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        tvcallerName = findViewById(R.id.tv_call_name);
        tvcallerNumber = findViewById(R.id.tv_user_number);
        ivCallerImage = findViewById(R.id.call_image);

//        String callerName = getIntent().getStringExtra("name");
//        String callerNumber = getIntent().getStringExtra("number");
//        String callerImage = getIntent().getStringExtra("callerImage");
//
//        tvcallerName.setText(callerName);
//        tvcallerNumber.setText(callerNumber);
//        ivCallerImage.setImageURI(Uri.parse(callerImage));

    }
}