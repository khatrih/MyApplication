package com.example.myapplication.layouts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapters.CardListAdapter;
import com.example.myapplication.model.CardItemModel;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeProductActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_product);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        progressBar = findViewById(R.id.progressBar);
        //Log.d("TAG","readFileJson"+ReadingFile.getJsonFromAssets(this,"veggiesMenu.txt"));

        JsonDataBackGround jdg = new JsonDataBackGround();
        jdg.execute();


    }

    public class JsonDataBackGround extends AsyncTask<JSONArray, Integer, ArrayList<CardItemModel>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<CardItemModel> doInBackground(JSONArray... jsonArrays) {
            ArrayList<CardItemModel> items = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(ReadingFile.getJsonFromAssets(HomeProductActivity.this));
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
            return items;
        }

        @Override
        protected void onPostExecute(ArrayList<CardItemModel> cardItemModels) {
            super.onPostExecute(cardItemModels);
            progressBar.setVisibility(View.GONE);
            CardListAdapter cardListAdapter = new CardListAdapter(cardItemModels, HomeProductActivity.this);
            recyclerView.setAdapter(cardListAdapter);
            Toast.makeText(HomeProductActivity.this, "finished", Toast.LENGTH_LONG).show();
        }
    }
}

