package com.example.myapplication.threading;

import static android.os.Looper.getMainLooper;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class WorkerThread extends Thread {

    Handler handler = null;
    boolean isDone = false;
    private static final int PROGRESS = 1;
    private int Download_Done;

    @Override
    public void run() {
        super.run();
        Log.d("TAG", "new thread " + Thread.currentThread().getName());
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 15000);

        /*Looper.prepare();
        handler = new Handler(getMainLooper());
        isDone = true;
        Looper.loop();*/
    }

    /*public void StartDownloading(Handler mainHandler) {
        if (Thread.currentThread() != this) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    StartDownloading(mainHandler);
                }
            });
            return;
        }

        for (int i = 1; i <= 100; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message msg = Message.obtain(handler);
            if (i != 100) {
                msg.arg1 = i;
                msg.what = PROGRESS;
                mainHandler.sendMessage(msg);
            } else {
                msg.arg1 = 100;
                msg.what = Download_Done;
                msg.arg2 = 50;
                String[] arr = {"Download done", "from my movies.com", "in download folder"};
                msg.obj = arr;
                mainHandler.sendMessage(msg);
            }
        }
    }*/

}
