package com.example.myapplication.layouts;

import android.content.Context;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ReadingFile {

    static String getJsonFromAssets(Context context){
        String jsonString = null;
        try {
            InputStream inputStream = context.getAssets().open("veggiesMenu.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonString = new String(buffer, StandardCharsets.UTF_8);
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonString;
    }



}
