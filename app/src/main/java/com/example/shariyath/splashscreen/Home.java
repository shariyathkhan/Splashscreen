package com.example.shariyath.splashscreen;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.shariyath.splashscreen.adapter.CityAdapter;
import com.example.shariyath.splashscreen.citiesdata.Bangalore;
import com.example.shariyath.splashscreen.citiesdata.Chennai;
import com.example.shariyath.splashscreen.citiesdata.Mumbai;
import com.example.shariyath.splashscreen.citiesdata.NewDelhi;

import java.util.ArrayList;
import java.util.HashMap;

public class Home extends AppCompatActivity {

    private static final String TAG = Home.class.getSimpleName();
    RecyclerView recycler_view_city;
    LinearLayoutManager mLayoutManager;
    TabLayout tabLayout;
    ViewPager viewPager;
    CityPagerAdapter cityPagerAdapter;
    TextView toolbar_title;

    ArrayList<String> arrayList_city = new ArrayList<String>();
    CityAdapter adapter_city_list;

    private final String[] PAGE_TITLES = new String[] {
            "Chennai",
            "Mumbai",
            "Bangalore",
            "New Delhi"
    };

    // The fragments that are used as the individual pages
    private final Fragment[] PAGES = new Fragment[] {
            new Chennai(),
            new Mumbai(),
            new Bangalore(),
            new NewDelhi()
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* recycler_view_city = (RecyclerView) findViewById(R.id.recycler_view_city);
        arrayList_city.add("Chennai");
        arrayList_city.add("Mumbai");
        arrayList_city.add("Bangalore");
        arrayList_city.add("New Delhi");
        mLayoutManager = new LinearLayoutManager(Home.this);
        recycler_view_city.setLayoutManager(new LinearLayoutManager(Home.this, LinearLayoutManager.VERTICAL, false));
        recycler_view_city.addItemDecoration(new DividerItemDecoration(Home.this, LinearLayoutManager.VERTICAL));
        recycler_view_city.setLayoutManager(mLayoutManager);

        adapter_city_list = new CityAdapter(Home.this,arrayList_city);
        recycler_view_city.setAdapter(adapter_city_list);*/

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.page_city);

        //Adding adapter to pager
        viewPager.setAdapter(new Home.CityPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        //tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        //Adding onTabSelectedListener to swipe views
        //tabLayout.setOnTabSelectedListener(this);

        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            public void onPageSelected(int position) {
                // Check if this is the page you want.
                Log.i("Testing_page_list","" + position);
            }
        });

        //#374876 #f55d14
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#374876"));
        // tabLayout.setSelectedTabIndicatorHeight((int) (1 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#5d5d5d"), Color.parseColor("#374876"));
    }

    public class CityPagerAdapter extends FragmentPagerAdapter {

        public CityPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return PAGES[position];
        }

        @Override
        public int getCount() {
            return PAGES.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return PAGE_TITLES[position];
        }

    }


}
