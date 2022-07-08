package com.example.myapplication.contentproviders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.newcontentprovider.BottomFragment;
import com.squareup.picasso.Picasso;

public class SingleCallDetailActivity extends AppCompatActivity {

    private TextView tvCallerName;
    private TextView tvCallerNumber;
    private TextView tvCallerEmail;
    private ImageView ivCallerImage;
    private ImageView ivEmailIcon;
    private TextView tvEmailLabel;
    private ImageView ivCall;
    private ImageView ivDirectCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlecall_detail);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        initializingAllView();

        String callerName = getIntent().getStringExtra("callerName");
        String callerNumber = getIntent().getStringExtra("callerNumber");
        String callerEmail = getIntent().getStringExtra("callerEmail");
        String callerImage = getIntent().getStringExtra("callerImage");
        Uri uriImage = Uri.parse(callerImage);

        if (callerEmail.matches("")) {
            ivEmailIcon.setVisibility(View.GONE);
            tvCallerEmail.setVisibility(View.GONE);
            tvEmailLabel.setVisibility(View.GONE);
        } else {
            ivEmailIcon.setVisibility(View.VISIBLE);
            tvCallerEmail.setVisibility(View.VISIBLE);
            tvEmailLabel.setVisibility(View.VISIBLE);
            tvCallerEmail.setText(callerEmail);
        }
        tvCallerName.setText(callerName);
        tvCallerNumber.setText(callerNumber);
        Picasso.get().load(uriImage).placeholder(R.drawable.ic_call_image).into(ivCallerImage);

        tvCallerNumber.setOnClickListener(v -> {
            Intent callingIntent = new Intent(Intent.ACTION_DIAL);
            callingIntent.setData(Uri.fromParts("tel", callerNumber, null));
            startActivity(callingIntent);
        });
        ivCall.setOnClickListener(v -> {
            Intent callingIntent = new Intent(Intent.ACTION_DIAL);
            callingIntent.setData(Uri.fromParts("tel", callerNumber, null));
            startActivity(callingIntent);
        });
        ivDirectCall.setOnClickListener(v -> {
            Intent callingIntent = new Intent(Intent.ACTION_DIAL);
            callingIntent.setData(Uri.fromParts("tel", callerNumber, null));
            startActivity(callingIntent);
        });

        ivEmailIcon.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("plain/text");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{callerEmail});
            startActivity(Intent.createChooser(emailIntent, ""));
        });
        tvCallerEmail.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("plain/text");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{callerEmail});
            startActivity(emailIntent);
        });


        //send data to BottomSheet
        Bundle sendToBottomSheet = new Bundle();
        sendToBottomSheet.putString("phoneNumber", callerNumber);
        sendToBottomSheet.putString("email", callerEmail);
        BottomFragment bottomSheet = new BottomFragment();
        bottomSheet.setArguments(sendToBottomSheet);
        bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());

    }

    private void initializingAllView() {
        tvCallerName = findViewById(R.id.tv_call_name);
        ivCallerImage = findViewById(R.id.iv_call_image);
        tvCallerNumber = findViewById(R.id.tv_user_number);
        tvCallerEmail = findViewById(R.id.tv_user_email);
        ivEmailIcon = findViewById(R.id.card_view_email);
        tvEmailLabel = findViewById(R.id.tv_email_home);
        ivCall = findViewById(R.id.card_view_call);
        ivDirectCall = findViewById(R.id.iv_call);
    }
}