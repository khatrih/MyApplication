package com.example.myapplication.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.loginflow.HomeActivity;

public class SearchFragment extends Fragment implements View.OnClickListener {

    private Button nextToSetting;
    private Button previous;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        nextToSetting = view.findViewById(R.id.nextToSetting);
        previous = view.findViewById(R.id.previousToGallery);
        nextToSetting.setOnClickListener(this);
        previous.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.nextToSetting) {
            ((HomeActivity) getActivity()).selectedIndicator(2);
            Fragment fragment = new SettingFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout, fragment);
            transaction.commit();
        } else if (v.getId() == R.id.previousToGallery) {
            ((HomeActivity) getActivity()).selectedIndicator(0);
            Fragment fragment = new GalleryFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout, fragment);
            transaction.commit();
        }

    }
}