package com.example.myapplication.model;

import java.util.ArrayList;

public class CardItemModel {
    private String product_image;
    private String product_name;
    private String product_description;
    private String check_type_food;
    private String check_spices;
    private String rating_bar;
    private boolean check_pin;
    private double product_price;
    private int product_comment_review;
    private int product_pin_review;
    private int number_rating;
    private int product_quantity;

    public CardItemModel(String product_image, String product_name, String product_description, int product_comment_review,
                         int product_pin_review, double product_price, String check_type_food, String check_spices,
                         String rating_bar, int number_rating, int product_quantity) {
        this.product_image = product_image;
        this.product_name = product_name;
        this.product_description = product_description;
        this.product_comment_review = product_comment_review;
        this.product_pin_review = product_pin_review;
        this.product_price = product_price;
        this.check_type_food = check_type_food;
        this.check_spices = check_spices;
        this.rating_bar = rating_bar;
        this.number_rating = number_rating;
        this.product_quantity = product_quantity;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getCheck_type_food() {
        return check_type_food;
    }

    public void setCheck_type_food(String check_type_food) {
        this.check_type_food = check_type_food;
    }

    public String getCheck_spices() {
        return check_spices;
    }

    public void setCheck_spices(String check_spices) {
        this.check_spices = check_spices;
    }

    public String getRating_bar() {
        return rating_bar;
    }

    public void setRating_bar(String rating_bar) {
        this.rating_bar = rating_bar;
    }

    public boolean isCheck_pin() {
        return check_pin;
    }

      public ArrayList<CardItemModel> setCheck_pin(boolean check_pin) {
          this.check_pin = check_pin;
          return null;
      }
 /*   public void setCheck_pin(boolean check_pin) {
        this.check_pin = check_pin;
    }*/

    public double getProduct_price() {
        return product_price;
    }

    public void setProduct_price(double product_price) {
        this.product_price = product_price;
    }

    public int getProduct_comment_review() {
        return product_comment_review;
    }

    public void setProduct_comment_review(int product_comment_review) {
        this.product_comment_review = product_comment_review;
    }

    public int getProduct_pin_review() {
        return product_pin_review;
    }

    public void setProduct_pin_review(int product_pin_review) {
        this.product_pin_review = product_pin_review;
    }

    public int getNumber_rating() {
        return number_rating;
    }

    public void setNumber_rating(int number_rating) {
        this.number_rating = number_rating;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public ArrayList<CardItemModel> setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
        return null;
    }
/*
    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }*/
}
