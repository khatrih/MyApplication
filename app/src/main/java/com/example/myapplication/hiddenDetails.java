package com.example.myapplication;

public class hiddenDetails extends BaseActivity{
    private static final String countryName= "India";

    @Override
    public void getData() {
        super.getData();

    }

    void getName(){
        System.out.println(countryName);
        getData();
    }
}
