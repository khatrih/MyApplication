package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

//class
//inheritance
public class BaseActivity extends RecyclerViewDemo implements Runnable {
    modelView model = new modelView();  //object
    TextView setName, firstNumber, secondNumber, Answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        setName = findViewById(R.id.textView);

        firstNumber = findViewById(R.id.firstNumber);
        secondNumber = findViewById(R.id.secondNumber);
        Answer = findViewById(R.id.Answer);

        Log.d("TAG", "oncreate");

        //encapsulation
        getData();
        setName.setText(model.getName());

        run();

    }

    @Override
    public void getData() {

    }

    //interface
    @Override
    public void run() {
        Toast.makeText(this, "Override method", Toast.LENGTH_SHORT).show();
    }
}