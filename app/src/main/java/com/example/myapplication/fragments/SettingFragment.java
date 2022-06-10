package com.example.myapplication.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.myapplication.R;
import com.example.myapplication.loginflow.HomeActivity;

public class SettingFragment extends Fragment implements View.OnClickListener {

    private Button nextToSetting;
    private Button preTOGallery;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        nextToSetting = view.findViewById(R.id.nextToGallery);
        preTOGallery = view.findViewById(R.id.previousToSearch);
        nextToSetting.setOnClickListener(this);
        preTOGallery.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.nextToGallery) {
            ((HomeActivity) getActivity()).selectedIndicator(0);
            FragmentManager fm = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, new GalleryFragment());
            fragmentTransaction.commit();
        } else if (v.getId() == R.id.previousToSearch) {
            ((HomeActivity) getActivity()).selectedIndicator(1);
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, new SearchFragment());
            fragmentTransaction.commit();
        }
    }
}