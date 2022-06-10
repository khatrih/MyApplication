package com.example.myapplication.multiplethreading;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

public class MultipleThreadsActivity extends AppCompatActivity {

    private EditText editText;
    private Button clickButton;
    private TextView textView;
    private TextView setThreadText;
    Thread thread;
    Thread thread1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_threads);

        editText = findViewById(R.id.edit_text);
        clickButton = findViewById(R.id.btn_click);
        textView = findViewById(R.id.txt_1);
        setThreadText = findViewById(R.id.txt_2);

        Log.i("TAG", "onCreate: " + Thread.currentThread().getName());
        Log.i("TAG", "onCreate: " + Thread.currentThread().getState());
        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runThread();
                runThread1();
            }
        });
    }

    private void runThread1() {
        thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(10000);
                            Toast.makeText(MultipleThreadsActivity.this, "since the 10 sec. late ", Toast.LENGTH_SHORT).show();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        textView.setText("android_developer.com");
                        Toast.makeText(MultipleThreadsActivity.this, "getPriority" + thread1.getPriority(), Toast.LENGTH_SHORT).show();
                        Log.i("TAG", "getName: " + thread1.getName());
                        Log.i("TAG", "getState: " + thread1.getState());
                        Log.i("TAG", "getPriority: " + thread1.getPriority());
                    }
                });
            }
        });
        thread1.start();
        thread1.setPriority(Thread.MIN_PRIORITY);
    }

    private void runThread() {
        String str = editText.getText().toString();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("TAG", "name " + thread.getName());
                        Log.i("TAG", "state " + thread.getState());
                        Log.i("TAG", "priority " + thread.getPriority());
                        setThreadText.setText(thread.getName());
                    }
                });
            }
        });
        thread.start();
        //thread.setName(th);
        thread.setPriority(Thread.MAX_PRIORITY);
    }
}