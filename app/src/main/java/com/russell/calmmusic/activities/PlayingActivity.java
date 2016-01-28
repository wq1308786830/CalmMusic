package com.russell.calmmusic.activities;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.TargetApi;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.russell.calmmusic.R;
import com.russell.calmmusic.services.MusicServices;

public class PlayingActivity extends AppCompatActivity implements View.OnTouchListener,MediaPlayer.OnCompletionListener {

    private ImageView playNeedle;
    private ImageView discPlay;
    private MusicServices musicServices;
    MediaPlayer mediaPlayer;
    ObjectAnimator anim;

//    public ImageView getDiscPlay() {
//        return discPlay;
//    }
//
//    public void setDiscPlay(ImageView discPlay) {
//        this.discPlay = discPlay;
//    }
    private int lastX = 0, lastY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);
        mediaPlayer = MusicServices.getmMediaPlayer();
        playNeedle = (ImageView) findViewById(R.id.play_needle);
        discPlay = (ImageView) findViewById(R.id.disc);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        controller();
    }

    public void controller() {
        int dur = mediaPlayer.getDuration();
        Keyframe kf0 = Keyframe.ofFloat(0f, 0f); /*时间在0%的时候动画对象的角度*/
        Keyframe kf1 = Keyframe.ofFloat(1f, 360f); /*时间在100%的时候(旋转一圈的时间)动画对象的角度*/
        PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofKeyframe("rotation", kf0, kf1); /*设置动画类型和关键值*/
        anim = ObjectAnimator.ofPropertyValuesHolder(discPlay, pvhRotation); /*将设置的属性绑定在需要动画的对象上*/
        anim.setDuration(8000); /*设置一圈的时间间隔*/
        anim.setInterpolator(new LinearInterpolator()); /*设置为匀速*/
        anim.setRepeatCount(dur / 8000);
        anim.setRepeatMode(ObjectAnimator.RESTART);
        anim.start();
        playNeedle.setOnTouchListener(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int ea = motionEvent.getAction();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        int right = view.getRight();
        int left = view.getLeft();
        int top = view.getTop();
        int bottom = view.getBottom();
//        int dis_x = right - (int) ((right - left) * 0.02);
//        int dis_y = screenHeight - bottom + (int) ((bottom - top) * 0.02);
        int dis_x = right - 6;
        int dis_y = bottom - 6;
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
                float x = dis_x - motionEvent.getRawX();
                float y = dis_y - motionEvent.getRawY();
                double alpha = Math.toDegrees(Math.atan(y / x));

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
                Animation hyperspace = AnimationUtils.loadAnimation(this, R.anim.hyperspace_jump);
                hyperspace.setFillAfter(true);
                playNeedle.startAnimation(hyperspace);
//                view.layout(l, t, r, b);
//                lastX = (int) motionEvent.getRawX();
//                lastY = (int) motionEvent.getRawY();
//                view.postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }
        return true;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        anim.setAutoCancel(true);
        anim.cancel();
    }
}
