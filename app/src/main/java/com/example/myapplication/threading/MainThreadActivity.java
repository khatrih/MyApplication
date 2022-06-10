package com.example.myapplication.threading;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainThreadActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private Button startProgress;
    Thread thread ;
    //private TextView setName;
    //private EditText etTargetName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_thread);
        //setName = findViewById(R.id.txt_name);
        //etTargetName = findViewById(R.id.edit_txt);
        startProgress = findViewById(R.id.btn_update);
        progressBar = findViewById(R.id.progress_bar);
        Log.d("TAG", "main thread " + Thread.currentThread().getName());

        /*WorkerThread wt = new WorkerThread();
        wt.start();*/

        startProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 1; i <= 10; i++) {
                            final int progressValue = i;
                            Log.d("TAG", "different thread " + thread.getName());
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            progressBar.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(progressValue);
                                }
                            });
                        }
                        Log.d("TAG", "run: "+ thread.getState());
                    }
                };
                thread = new Thread(runnable);
                thread.start();
            }
        });

        /*Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.d("TAG","different thread "+Thread.currentThread().getName());
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainThreadActivity.this, "runOnUiThread"+Thread.currentThread().getName(), Toast.LENGTH_SHORT).show();
                        etTargetName.setText("runOnUiThread "+Thread.currentThread().getName());
                    }
                });


        Handler handler = new Handler(getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("TAG", "post: "+Thread.currentThread().getName());
                        etTargetName.setText("handler post method "+Thread.currentThread().getName());
                    }
                });

         handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("TAG", "postDelayed: "+Thread.currentThread().getName());
                        etTargetName.setText("handler postDelayed method "+Thread.currentThread().getName());
                    }
                },15000);
            }
        };*/

    }
}




    /*while (!(wt.isDone)) {
        }
        Handler mainHandler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.d("TAG", "handleMessage: "+Thread.currentThread().getName());
                switch (msg.what) {
                    case 1:
                        int progress = msg.arg1;
                        progressBar.setProgress(progress);

                    case 2:
                        String fin = "";
                        int progressing = msg.arg1;
                        progressBar.setProgress(progressing);
                        int time_taken = msg.arg2;
                        String[] arr = (String[]) msg.obj;
                        for (int i=0; i< arr.length;i++){
                            fin = arr[i];
                        }
                        txt.setText(fin);

                }
            }
        };
        wt.StartDownloading(mainHandler);*/