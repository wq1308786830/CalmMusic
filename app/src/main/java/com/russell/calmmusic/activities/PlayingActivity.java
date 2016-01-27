package com.russell.calmmusic.activities;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.russell.calmmusic.R;
import com.russell.calmmusic.services.MusicServices;

import java.util.List;

public class PlayingActivity extends AppCompatActivity implements View.OnTouchListener {

    ImageView playNeedle;
    ImageView disc;
    MusicServices musicServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);
        playNeedle = (ImageView) findViewById(R.id.play_needle);
        disc = (ImageView) findViewById(R.id.disc);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.disc_rotate);
//        hyperspaceJumpAnimation.setRepeatMode(Animation.RESTART);
        disc.startAnimation(hyperspaceJumpAnimation);
        controller();

    }

    public void controller() {

        ViewGroup.LayoutParams o = playNeedle.getLayoutParams();
        playNeedle.setOnTouchListener(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int lastX = 0, lastY = 0;
        int ea = motionEvent.getAction();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        switch (ea) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) motionEvent.getRawX();//获取触摸事件触摸位置的原始X坐标
                lastY = (int) motionEvent.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                //event.getRawX();获得移动的位置
                int dx = (int) motionEvent.getRawX() - lastX;
                int dy = (int) motionEvent.getRawY() - lastY;
                int l = view.getLeft() + dx;
                int b = view.getBottom() + dy;
                int r = view.getRight() + dx;
                int t = view.getTop() + dy;

//下面判断移动是否超出屏幕
                if (l < 0) {
                    l = 0;
                    r = l + view.getWidth();
                }
                if (t < 0) {
                    t = 0;
                    b = t + view.getHeight();
                }
                if (r > screenWidth) {
                    r = screenWidth;
                    l = r - view.getWidth();
                }
                if (b > screenHeight) {
                    b = screenHeight;
                    t = b - view.getHeight();
                }
                view.layout(l, t, r, b);
                lastX = (int) motionEvent.getRawX();
                lastY = (int) motionEvent.getRawY();
                view.postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

}
