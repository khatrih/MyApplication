package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowDetailsActivity extends AppCompatActivity {

    AppCompatButton backBtn;
    TextView name, email, password, confirmPassword, Gender, countryName;
    ImageView loadImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        name = findViewById(R.id.tvOfName);
        email = findViewById(R.id.tvOfEmail);
        password = findViewById(R.id.tvOfPW);
        confirmPassword = findViewById(R.id.tvOfCPW);
        backBtn = findViewById(R.id.backBtn);
        loadImage = findViewById(R.id.cameraImages);
        Gender = findViewById(R.id.rb);
        countryName = findViewById(R.id.spinnerText);

        /*if (getIntent().hasExtra("byteArray")){
          Bitmap _bitmap = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("byteArray"),0,getIntent().getByteArrayExtra("byteArray").length);
          imageView.setImageBitmap(_bitmap);
          Toast.makeText(this, "image update", Toast.LENGTH_SHORT).show();
          }*/

//        Bitmap bmp = extras.getParcelable("imagebitmap");
//        imageView.setImageBitmap(bmp);

        /*tvName.setText(getIntent().getExtras().getString("name"));
        tvEmail.setText(getIntent().getExtras().getString("email"));
        tvPW.setText(getIntent().getExtras().getString("passcode"));
        tvCPW.setText(getIntent().getExtras().getString("confirmPassword"));*/
        Gender.setText(getIntent().getStringExtra("gender"));
        countryName.setText(getIntent().getStringExtra("i"));

        backBtn.setOnClickListener(v -> onBackPressed());
    }
}