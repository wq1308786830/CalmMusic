package com.russell.calmmusic.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.russell.calmmusic.R;
import com.russell.calmmusic.fragments.ScreenSlideActivityFragment;

public class ScreenSlidePagerActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 5;
    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;
    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;
    private View linearLayout, reaLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);
        initViews();
    }

    public void initViews() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ScreenSlideActivityFragment fragment = new ScreenSlideActivityFragment();

        setSupportActionBar(toolbar);
        fragmentTransaction.add(R.id.pager, fragment);
        fragmentTransaction.commit();

        linearLayout = findViewById(R.id.allMusic);
        reaLayout = findViewById(R.id.controller);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(fragmentManager);
        mPager.setAdapter(mPagerAdapter);

        linearLayout.setOnClickListener(this);
        reaLayout.setOnClickListener(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.allMusic:
                Intent intent = new Intent();
                intent.setClass(ScreenSlidePagerActivity.this, ListActivity.class);
                startActivity(intent);
                break;
            case R.id.controller:
                Intent intent1 = new Intent();
                intent1.setClass(ScreenSlidePagerActivity.this, PlayingActivity.class);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new ScreenSlideActivityFragment();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}