package com.example.myapplication.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myapplication.fragments.GalleryFragment;
import com.example.myapplication.fragments.SearchFragment;
import com.example.myapplication.fragments.SettingFragment;


public class FragmentAdapter extends FragmentPagerAdapter {

    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return new SearchFragment();
            case 2:
                return new SettingFragment();
            default:
                return new GalleryFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;

        if (position == 0) {
            title = "Gallery";
        } else if (position == 1) {
            title = "Search";
        } else if (position == 2) {
            title = "Setting";
        }
        return title;
    }


}
