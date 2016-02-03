package com.russell.calmmusic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;
import android.view.ViewGroup.LayoutParams;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {


    Button buttonNext;
    ImageSwitcher imageSwitcher;
    Animation slide_in_left, slide_out_right, slide_in_right, slide_out_left;
    private float lastX = 0;

    int imageResources[] = {
            R.mipmap.tree,
            R.mipmap.tree_bluesky,
            R.mipmap.treeslight,
            R.mipmap.treesshadow,
    };

    int curIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        init();
        switchImage();
    }

    private void init() {
        imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        buttonNext = (Button) findViewById(R.id.buttonNext);

        slide_in_left = AnimationUtils.loadAnimation(this,
                R.anim.slide_in_left);
        slide_out_right = AnimationUtils.loadAnimation(this,
                R.anim.slide_out_right);
        slide_in_right = AnimationUtils.loadAnimation(this,
                R.anim.slide_in_right);
        slide_out_left = AnimationUtils.loadAnimation(this,
                R.anim.slide_out_left);
        imageSwitcher.setOnTouchListener(this);
    }

    private void switchImage() {
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {

            @Override
            public View makeView() {

                ImageView imageView = new ImageView(MainActivity.this);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

                LayoutParams params = new ImageSwitcher.LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

                imageView.setLayoutParams(params);
                return imageView;

            }
        });
        curIndex = 0;
        imageSwitcher.setImageResource(imageResources[curIndex]);

        buttonNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (curIndex == imageResources.length - 1) {
                    curIndex = 0;
                    imageSwitcher.setImageResource(imageResources[curIndex]);
                } else {
                    imageSwitcher.setImageResource(imageResources[++curIndex]);
                }
            }
        });
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int ea = motionEvent.getAction();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        switch (ea) {
            case MotionEvent.ACTION_DOWN://获取触摸事件触摸位置的原始X坐标
                lastX = motionEvent.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                float off = motionEvent.getRawX() - lastX;
                if (off > 0) {
                    imageSwitcher.setInAnimation(slide_in_left);
                    imageSwitcher.setOutAnimation(slide_out_right);
                    if (curIndex == imageResources.length - 1) {
                        curIndex = 0;
                        imageSwitcher.setImageResource(imageResources[curIndex]);
                    } else {
                        imageSwitcher.setImageResource(imageResources[++curIndex]);
                    }
                } else if (off < 0) {
                    imageSwitcher.setInAnimation(slide_in_right);
                    imageSwitcher.setOutAnimation(slide_out_left);
                    if (curIndex == 0) {
                        curIndex = imageResources.length - 1;
                        imageSwitcher.setImageResource(imageResources[curIndex]);
                    } else {
                        imageSwitcher.setImageResource(imageResources[--curIndex]);
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }
        return true;
    }
}
