package com.example.myapplication.contentproviders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

public class SingleCallDetailActivity extends AppCompatActivity {

    private TextView tvcallerName;
    private TextView tvcallerNumber;
    private TextView tvcallerEmail;
    private ImageView ivCallerImage;
    private ImageView ivEmail;
    private TextView tvEmailLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlecall_detail);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        tvcallerName = findViewById(R.id.tv_call_name);
        tvcallerNumber = findViewById(R.id.tv_user_number);
        ivCallerImage = findViewById(R.id.iv_call_image);
        tvcallerEmail = findViewById(R.id.tv_user_email);

        ivEmail = findViewById(R.id.card_view_email);
        tvEmailLabel = findViewById(R.id.tv_email_home);

        String callerName = getIntent().getStringExtra("name");
        String callerNumber = getIntent().getStringExtra("number");
        String callerEmail = getIntent().getStringExtra("callerEmail");
        String callerImage = getIntent().getStringExtra("callerImage");

        if (callerEmail.matches("")) {
            ivEmail.setVisibility(View.GONE);
            tvcallerEmail.setVisibility(View.GONE);
            tvEmailLabel.setVisibility(View.GONE);
        } else {
            ivEmail.setVisibility(View.VISIBLE);
            tvcallerEmail.setVisibility(View.VISIBLE);
            tvEmailLabel.setVisibility(View.VISIBLE);
            tvcallerEmail.setText(callerEmail);
        }

        tvcallerName.setText(callerName);
        tvcallerNumber.setText(callerNumber);
        Bitmap image = BitmapFactory.decodeFile(callerImage);
        ivCallerImage.setImageBitmap(image);

    }
}