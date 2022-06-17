package com.example.myapplication.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.layouts.interfaces.UpdatedLists;
import com.example.myapplication.model.CardItemModel;
import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.viewHolder> {

    ArrayList<CardItemModel> cardItemModels;
    Context context;
    UpdatedLists getCardItemList;

    public CardListAdapter(ArrayList<CardItemModel> list, Context context, UpdatedLists getCardItemList) {
        this.cardItemModels = list;
        this.context = context;
        this.getCardItemList = getCardItemList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_view, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        CardItemModel model = cardItemModels.get(position);

        int resId = context.getResources().getIdentifier(model.getProduct_image(),
                "drawable", context.getPackageName());

        Drawable getImage = ContextCompat.getDrawable(context, resId);
        holder.productImage.setImageDrawable(getImage);
        holder.productName.setText(model.getProduct_name());
        holder.productDescription.setText(model.getProduct_description());
        holder.productPrice.setText(String.format("₹ %s", model.getProduct_price()));
        holder.productPin.setText(String.valueOf(model.getProduct_pin_review()));
        holder.productQuantity.setText(String.valueOf(model.getProduct_quantity()));
        holder.productComment.setText(String.valueOf(model.getProduct_comment_review()));
        holder.foodRating.setRating(Float.parseFloat(model.getRating_bar()));
        holder.ratingNumber.setText(String.valueOf(model.getNumber_rating()));

        //set when notifyItemChanged
        if (model.isCheck_pin()) {
            holder.checkPinned.setImageResource(R.drawable.dark_pin);
        } else {
            holder.checkPinned.setImageResource(R.drawable.pin);
        }

        if (model.getCheck_spices().equalsIgnoreCase("spices")) {
            holder.checkSpices.setImageResource(R.drawable.chilli);
        } else {
            holder.checkSpices.setImageDrawable(null);
        }

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


        holder.addProductQuality.setOnClickListener(v -> {
            cardItemModels.get(position).setProduct_quantity(model.getProduct_quantity() + 1);
            notifyItemChanged(position);
            getCardItemList.getUpdateList(cardItemModels);

        });

        holder.removeProductQuality.setOnClickListener(v -> {
            if (model.getProduct_quantity() > 1) {
                notifyItemChanged(position);
                getCardItemList.getUpdateList(cardItemModels.get(position).
                        setProduct_quantity(model.getProduct_quantity() - 1));
//                saveDataIntoPreferences();
            }
        });

        holder.checkPinned.setOnClickListener(v -> {
            notifyItemChanged(position);
            getCardItemList.getUpdateList(cardItemModels.get(position).
                    setCheck_pin(!cardItemModels.get(position).isCheck_pin()));
        });
    }


   /*  public void saveDataIntoFile() {
        try {
            FileOutputStream writeData = context.openFileOutput("veggiesMenu.json", Context.MODE_PRIVATE);
            Gson getFileData = new Gson();
            String writeFile = getFileData.toJson(cardItemModels);
            writeData.write(writeFile.getBytes());
            writeData.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

     /*public void saveDataIntoPreferences() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cardItemModels); //serialize
        prefEditor.putString("items_list", json);
        prefEditor.apply();
    }*/

    @Override
    public int getItemCount() {
        return cardItemModels.size();
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

