package com.example.myapplication.loginflow;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.myapplication.R;
import com.example.myapplication.adapters.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    TabLayout tabLayout;
    ViewPager viewPager;
/*    Fragment fragment;
    TextView appBarName;
    AppBarLayout AppBar;
    FragmentTransaction fragmentTransaction;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        w.setStatusBarColor(getResources().getColor(R.color.blue));

        tabLayout = findViewById(R.id.tabLayout);

        viewPager = findViewById(R.id.viewPager);

        frameLayout = findViewById(R.id.frameLayout);

        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));

        tabLayout.setupWithViewPager(viewPager);

        //viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectedIndicator(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

/*        AppBar = findViewById(R.id.AppBar);

        appBarName = findViewById(R.id.AppBarNameView);

        frameLayout = findViewById(R.id.frameLayout);

        tabLayout = findViewById(R.id.tabLayout);


        AppBar.setElevation(2);
        appBarName.setText("MyApp");

        tabLayout.setScrollPosition(0,0f,true);

        fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new GalleryFragment());
        fragmentTransaction.commit();


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new GalleryFragment();
                        break;
                    case 1:
                        fragment = new SearchFragment();
                        break;
                    case 2:
                        fragment = new SettingFragment();
                        break;
                }
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout, fragment);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/
    }

    public void selectedIndicator(int position) {
        viewPager.setCurrentItem(position);
    }

}