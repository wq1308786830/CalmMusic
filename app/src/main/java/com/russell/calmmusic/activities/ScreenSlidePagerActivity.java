package com.russell.calmmusic.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.russell.calmmusic.R;
import com.russell.calmmusic.fragments.ScreenSlideActivityFragment;
import com.russell.calmmusic.services.MusicServices;
import com.russell.calmmusic.services.imp.MusicServicesImp;
import com.russell.calmmusic.tools.Util;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXMusicObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class ScreenSlidePagerActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final String APP_ID = "wx1b42c40fb2baf654";
    private IWXAPI api;
    private int[] showImg;

    private float lastX = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);
        initData();
        initViews();
        regToWx();
    }

    private void initData() {
        showImg = new int[]{R.mipmap.index_daily_ban1, R.mipmap.index_daily_ban2,
                R.mipmap.index_new_america, R.mipmap.index_new_china, R.mipmap.index_new_korea};
    }

    public void initViews() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        ImageView shareWx = (ImageView) findViewById(R.id.share);


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
        ImageView imageView = (ImageView) findViewById(R.id.discCtrl);
        ViewPager mPager = (ViewPager) findViewById(R.id.pager);
        View linearLayout = findViewById(R.id.allMusic);
        View reaLayout = findViewById(R.id.controller);

        mPager.setAdapter(mPagerAdapter);

        linearLayout.setOnClickListener(this);
        imageView.setOnTouchListener(this);
        shareWx.setOnClickListener(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }
    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.allMusic:
                intent.setClass(ScreenSlidePagerActivity.this, ListActivity.class);
                startActivity(intent);
                break;
            case R.id.share:
                WXMusicObject musicObject = new WXMusicObject();
                musicObject.musicUrl = "";
                WXMediaMessage mediaMessage = new WXMediaMessage();
                mediaMessage.mediaObject = musicObject;
                mediaMessage.title = "";
                mediaMessage.description = "";
                Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.placeholder_disk_play_fm);
                mediaMessage.thumbData = Util.bmpToByteArray(thumb, true);
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("music");
                req.message = mediaMessage;
//                req.scene = isTimelineCb.isChecked() ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
                api.sendReq(req);
                finish();
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int ea = event.getAction();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        MusicServices musicServices = new MusicServicesImp(ScreenSlidePagerActivity.this);
        switch (ea) {
            case MotionEvent.ACTION_DOWN://获取触摸事件触摸位置的原始X坐标
                lastX = event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                lastX -= event.getRawX();
                if (event.getEventTime() > event.getDownTime() && Math.abs(lastX) > 20.0) {
                    if (lastX < 0) {
                        musicServices.nextMusic();
                    } else {
                        musicServices.preMusic();
                    }
                } else if (event.getEventTime() >= event.getDownTime()
                        + 500 && !(Math.abs(lastX) > 20.0)) {
                    Intent intent = new Intent();
                    intent.setClass(ScreenSlidePagerActivity.this, PlayingActivity.class);
                    startActivity(intent);
                    return false;
                } else if (event.getEventTime() < event.getDownTime()
                        + 500 && !(Math.abs(lastX) > 20.0)) {
                    musicServices.stopOrPlay();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }
        return true;
    }

    private void regToWx(){
        api = WXAPIFactory.createWXAPI(this, APP_ID, true);
        api.registerApp(APP_ID);
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