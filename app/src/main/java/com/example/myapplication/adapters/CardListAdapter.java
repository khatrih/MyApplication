package com.example.myapplication.adapters;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.CardItemModel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.viewHolder> {

    ArrayList<CardItemModel> list;
    Context context;
    private static final String QUANTITY = "product_quantity";
    private boolean check_pin = true;

    public CardListAdapter(ArrayList<CardItemModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_view, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        CardItemModel model = list.get(position);
//        SharedPreferences preferences = context.getSharedPreferences("savedDataItems", MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();

        int resId = context.getResources().getIdentifier(model.getProduct_image(),
                "drawable", context.getPackageName());
        @SuppressLint("UseCompatLoadingForDrawables")
        Drawable getImage = context.getResources().getDrawable(resId);
        holder.productImage.setImageDrawable(getImage);
        holder.productName.setText(model.getProduct_name());
        holder.productDescription.setText(model.getProduct_description());
        holder.productPrice.setText(String.format("₹ %s", model.getProduct_price()));
        holder.productPin.setText(String.valueOf(model.getProduct_pin_review()));
        holder.productComment.setText(String.valueOf(model.getProduct_comment_review()));
        holder.productQuantity.setText(String.valueOf(model.getProduct_quantity()));
        holder.foodRating.setRating(Float.parseFloat(model.getRating_bar()));
        holder.ratingNumber.setText(String.valueOf(model.getNumber_rating()));

        switch (model.getCheck_type_food()) {
            case "veg":
                holder.checkTypeFood.setImageResource(R.drawable.veg);
                break;
            case "non-veg":
                holder.checkTypeFood.setImageResource(R.drawable.non_veg);
                break;
            case "veg-non-veg":
                holder.checkTypeFood.setImageResource(R.drawable.veg_non_veg);
                break;
        }

        if (model.getCheck_spices().equalsIgnoreCase("spices")) {
            holder.checkSpices.setImageResource(R.drawable.chilli);
        } else {
            holder.checkSpices.setImageDrawable(null);
        }

        holder.addProductQuality.setOnClickListener(v -> {
            int counter = Integer.parseInt(holder.productQuantity.getText().toString());
            counter++;
            holder.productQuantity.setText(String.valueOf(counter));
            //write into the file
            //OutputStream − This is used to write data to a destination
            try {
                FileOutputStream fOut = context.openFileOutput("veggiesMenu.json" + position, MODE_PRIVATE);
                String getData = holder.productQuantity.getText().toString();
                fOut.write(getData.getBytes());
                fOut.close();
                Toast.makeText(context, "update quantity", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            /*String pQuantityAdd = holder.productQuantity.getText().toString();
            for (int i = 0; i < list.size(); i++) {
                editor.putString(QUANTITY + position, pQuantityAdd);
                editor.apply();
            }*/
        });

        holder.removeProductQuality.setOnClickListener(v -> {
            int counter = Integer.parseInt(holder.productQuantity.getText().toString());
            if (counter > 1) {
                counter--;
                holder.productQuantity.setText(String.valueOf(counter));
                //write into the file
                //OutputStream − This is used to write data to a destination
                try {
                    FileOutputStream fOut = context.openFileOutput("veggiesMenu.json" + position, MODE_PRIVATE);
                    String getData = holder.productQuantity.getText().toString();
                    fOut.write(getData.getBytes());
                    fOut.close();
                    Toast.makeText(context, "update quantity", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                /*String pQuantityRemove = holder.productQuantity.getText().toString();
                for (int i = 0; i < list.size(); i++) {
                    editor.putString(QUANTITY + position, pQuantityRemove);
                    editor.apply();
                }*/
            }
        });

        /*holder.checkPinned.setOnClickListener(v -> {
            if (check_pin) {

                holder.checkPinned.setImageResource(R.drawable.dark_pin);
                Toast.makeText(context, "pinned ", Toast.LENGTH_LONG).show();
                check_pin = false;

                for (int i = 0; i < list.size(); i++) {
                    editor.putBoolean("click" + position, true);
                    editor.apply();
                }
            } else {

                check_pin = true;
                holder.checkPinned.setImageResource(R.drawable.pin);
                Toast.makeText(context, "Unpinned ", Toast.LENGTH_LONG).show();

                for (int i = 0; i < list.size(); i++) {
                    editor.putBoolean("click" + position, false);
                    editor.apply();
                }
            }
        });

        SharedPreferences p = context.getSharedPreferences("savedDataItems", MODE_PRIVATE);
        for (int i = 0; i < list.size(); i++) {
            String getValue = p.getString(QUANTITY + position, holder.productQuantity.getText().toString());
            holder.productQuantity.setText(getValue);

            boolean clickedValue = p.getBoolean("click" + position, false);
            if (clickedValue) {
                holder.checkPinned.setImageResource(R.drawable.dark_pin);
            } else {
                holder.checkPinned.setImageResource(R.drawable.pin);
            }
        }*/

        /*  read file and retrieve data
            InputStream − This is used to read data from a source*/
        try {

            /*FileInputStream fis = context.openFileInput("veggiesMenu.txt"+position);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            holder.productQuantity.setText(sb);*/

            FileInputStream fin = context.openFileInput("veggiesMenu.json" + position);
            int c;
            String temp = "";
            while ((c = fin.read()) != -1) {
                temp = temp + (char) c;
            }
            holder.productQuantity.setText(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        private final ImageView productImage;
        private final TextView productName;
        private final TextView productDescription;
        private final TextView productPrice;
        private final TextView productComment;
        private final TextView productPin;
        private final ImageView checkTypeFood;
        private final ImageView checkSpices;
        private final RatingBar foodRating;
        private final TextView ratingNumber;
        private final TextView productQuantity;
        private final ImageView addProductQuality;
        private final ImageView removeProductQuality;
        private final ImageView checkPinned;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productDescription = itemView.findViewById(R.id.product_description);
            productPrice = itemView.findViewById(R.id.product_price);
            productComment = itemView.findViewById(R.id.comment_message);
            productPin = itemView.findViewById(R.id.pin_message);
            checkSpices = itemView.findViewById(R.id.spices_food);
            checkTypeFood = itemView.findViewById(R.id.check_veg_non_veg);
            foodRating = itemView.findViewById(R.id.ratingBar);
            ratingNumber = itemView.findViewById(R.id.rating_number);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            addProductQuality = itemView.findViewById(R.id.add_product_quality);
            removeProductQuality = itemView.findViewById(R.id.remove_product_quality);
            checkPinned = itemView.findViewById(R.id.product_pin);
        }
    }
}

