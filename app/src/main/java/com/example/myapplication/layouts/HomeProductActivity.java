package com.example.myapplication.layouts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapters.CardListAdapter;
import com.example.myapplication.layouts.interfaces.UpdatedLists;
import com.example.myapplication.model.CardItemModel;
import com.google.gson.Gson;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class HomeProductActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    ArrayList<CardItemModel> itemsList;
    private static final String TAG = "TAG";
    private static final String PREFERENCES_NAME = "my_prefs";
    private static final String CARD_ITEM_LIST = "items_list";
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_product);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        progressBar = findViewById(R.id.progressBar);

        JsonDataBackGround jdg = new JsonDataBackGround();
        jdg.execute();

    }

    @Override
    protected void onStop() {
        super.onStop();
     /*   sharedPreferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(itemsList);
        prefEditor.putString(CARD_ITEM_LIST, json);
        prefEditor.apply();*/

        try {
            FileOutputStream writeData = openFileOutput("veggiesMenu.json", MODE_PRIVATE);
            Gson getFileData = new Gson();
            String writeFile = getFileData.toJson(itemsList);
            writeData.write(writeFile.getBytes());
            writeData.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public class JsonDataBackGround extends AsyncTask<JSONArray, Integer, ArrayList<CardItemModel>>
            implements UpdatedLists {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<CardItemModel> doInBackground(JSONArray... jsonArrays) {
            ArrayList<CardItemModel> items = new ArrayList<>();
            sharedPreferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
            String prefFile = sharedPreferences.getString(CARD_ITEM_LIST, null);
            /*if (prefFile != null) {
                try {
                    JSONArray jArray = new JSONArray(prefFile);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject jsonObject = jArray.getJSONObject(i);
                        items.add(new CardItemModel(jsonObject.getString("product_image"),
                                jsonObject.optString("product_name"), jsonObject.getString("product_description"),
                                jsonObject.getInt("product_comment_review"), jsonObject.getInt("product_pin_review"),
                                jsonObject.getDouble("product_price"), jsonObject.getString("check_type_food"),
                                jsonObject.getString("check_spices"), jsonObject.getString("rating_bar"),
                                jsonObject.getInt("number_rating"), jsonObject.getInt("product_quantity")
                        ));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
            InputStream inputStream = null;
            try {
                inputStream = openFileInput("veggiesMenu.json");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (inputStream != null) {
                String ret = "";
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while (true) {
                    try {
                        if ((receiveString = bufferedReader.readLine()) == null)
                            break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    stringBuilder.append(receiveString);
                }

                ret = stringBuilder.toString();

                try {
                    JSONArray jsonArray = new JSONArray(ret);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        items.add(new CardItemModel(object.getString("product_image"),
                                object.optString("product_name"), object.getString("product_description"),
                                object.getInt("product_comment_review"), object.getInt("product_pin_review"),
                                object.getDouble("product_price"), object.getString("check_type_food"),
                                object.getString("check_spices"), object.getString("rating_bar"),
                                object.getInt("number_rating"), object.getInt("product_quantity")
                        ));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    JSONArray jsonArray = new JSONArray(ReadingFile.getJsonFromAssets
                            (HomeProductActivity.this,
                                    "veggiesMenu.json"));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        items.add(new CardItemModel(object.getString("product_image"),
                                object.optString("product_name"), object.getString("product_description"),
                                object.getInt("product_comment_review"), object.getInt("product_pin_review"),
                                object.getDouble("product_price"), object.getString("check_type_food"),
                                object.getString("check_spices"), object.getString("rating_bar"),
                                object.getInt("number_rating"), object.getInt("product_quantity")
                        ));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return items;
        }

        @Override
        protected void onPostExecute(ArrayList<CardItemModel> cardItemModels) {
            super.onPostExecute(cardItemModels);

            itemsList = cardItemModels;
            CardListAdapter cardListAdapter;

            progressBar.setVisibility(View.GONE);
           /*SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
            String json = sharedPreferences.getString(CARD_ITEM_LIST, null);
            if (json != null) {
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<CardItemModel>>() {}.getType();
                cardItemModels = gson.fromJson(json, type);
                cardListAdapter = new CardListAdapter(cardItemModels, HomeProductActivity.this);
                recyclerView.setAdapter(cardListAdapter);
                Toast.makeText(HomeProductActivity.this, "read data", Toast.LENGTH_SHORT).show();
            } else {
            cardListAdapter = new CardListAdapter(cardItemModels, HomeProductActivity.this);
            recyclerView.setAdapter(cardListAdapter);
            Toast.makeText(HomeProductActivity.this, "finished", Toast.LENGTH_LONG).show();
            }*/

           /*
            // save data using file
            File fileEvents = new File("data/data/com.example.myapplication/files/veggiesMenu.json");
            if (fileEvents.exists()) {
                try {
                    FileReader reader = new FileReader(fileEvents);
//                    BufferedReader br = new BufferedReader(new FileReader(fileEvents));
//                    String readText;
//                    String json = null;
//                    while ((readText = br.readLine()) != null) {
//                        json = readText;
//                    }
                    Gson gson = new Gson();
                    Type checkType = new TypeToken<ArrayList<CardItemModel>>() {
                    }.getType();
                    cardItemModels = gson.fromJson(reader, checkType);
                    cardListAdapter = new CardListAdapter(cardItemModels, HomeProductActivity.this);
                    recyclerView.setAdapter(cardListAdapter);
                    Toast.makeText(HomeProductActivity.this, "file load", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(HomeProductActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            } else {
                cardListAdapter = new CardListAdapter(cardItemModels, HomeProductActivity.this);
                recyclerView.setAdapter(cardListAdapter);
                Toast.makeText(HomeProductActivity.this, "json load", Toast.LENGTH_LONG).show();
            }*/

            cardListAdapter = new CardListAdapter(itemsList, HomeProductActivity.this
                    , this);
            recyclerView.setAdapter(cardListAdapter);
        }

        @Override
        public void getUpdateList(ArrayList<CardItemModel> cardItemModels) {
            itemsList = cardItemModels;
        }
    }
}

