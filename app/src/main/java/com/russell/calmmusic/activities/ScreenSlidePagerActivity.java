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
import android.widget.ImageView;

import com.russell.calmmusic.R;
import com.russell.calmmusic.fragments.ScreenSlideActivityFragment;

public class ScreenSlidePagerActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private int[] showImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);
        initData();
        initViews();
    }

    private void initData() {
        showImg = new int[]{R.mipmap.beautiful, R.mipmap.horse,
                R.mipmap.tree_bluesky, R.mipmap.treeslight, R.mipmap.split_plank};
    }

    public void initViews() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        setSupportActionBar(toolbar);
        fragmentTransaction.add(R.id.pager, ScreenSlideActivityFragment.newInstance(showImg[0]));
        fragmentTransaction.commit();

        /*
        The pager adapter, which provides the pages to the view pager widget.
        */
        PagerAdapter mPagerAdapter = new ScreenSlidePagerAdapter(fragmentManager);
        /*
          The pager widget, which handles animation and allows swiping horizontally to access previous
          and next wizard steps.
        */
        ImageView imageView = (ImageView) findViewById(R.id.switch_img);
        ViewPager mPager = (ViewPager) findViewById(R.id.pager);
        View linearLayout = findViewById(R.id.allMusic);
        View reaLayout = findViewById(R.id.controller);

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
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.allMusic:
                intent.setClass(ScreenSlidePagerActivity.this, ListActivity.class);
                startActivity(intent);
                break;
            case R.id.controller:
                intent.setClass(ScreenSlidePagerActivity.this, PlayingActivity.class);
                startActivity(intent);
                break;
            case R.mipmap.beautiful:
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
            return ScreenSlideActivityFragment.newInstance(showImg[position]);
        }

        @Override
        public int getCount() {
            return showImg.length;
        }
    }
}