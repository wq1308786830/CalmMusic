package com.russell.calmmusic.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.russell.calmmusic.R;
import com.russell.calmmusic.bean.MusicInfo;
import com.russell.calmmusic.services.AnimationServices;
import com.russell.calmmusic.services.MusicServices;
import com.russell.calmmusic.services.imp.AnimationServicesImpl;
import com.russell.calmmusic.services.imp.MusicServicesImp;
import com.russell.calmmusic.tools.MusicLoader;

import java.util.List;
import java.util.Random;

public class PlayingActivity extends AppCompatActivity implements View.OnTouchListener {

    private ImageView playNeedle;
    private ImageView discPlay;
    MediaPlayer mediaPlayer;
    private float lastX = 0;
    private float lastY = 0;
    private List<MusicInfo> musicInfos;
    private MusicServices musicServices;
    private AnimationServices animationServices = new AnimationServicesImpl();
    private MusicLoader musicLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);
        init();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        animationServices.controlDisc(discPlay);
        playNeedle.setOnTouchListener(this);
    }

    public void init() {
        musicServices = new MusicServicesImp(this);
        musicLoader = new MusicLoader(this);
        mediaPlayer = musicServices.getMediaPlayer();
        musicInfos = musicLoader.getMusicList();
        playNeedle = (ImageView) findViewById(R.id.play_needle);
        discPlay = (ImageView) findViewById(R.id.disc);
        if (mediaPlayer.isPlaying()) {
            Animation hyperspace = AnimationUtils.loadAnimation(this, R.anim.play_needdle);
            hyperspace.setFillAfter(true);
            playNeedle.startAnimation(hyperspace);
        }
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
        switch (ea) {
            case MotionEvent.ACTION_DOWN:
                lastX = motionEvent.getRawX();//获取触摸事件触摸位置的原始X坐标
                lastY = motionEvent.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float inc_x = motionEvent.getRawX() - lastX;
                float inc_y = motionEvent.getRawY() - lastY;
                if (inc_y > 0 && !mediaPlayer.isPlaying()) {
                    Animation hyperspace = AnimationUtils.loadAnimation(this, R.anim.play_needdle);
                    hyperspace.setFillAfter(true);
                    playNeedle.startAnimation(hyperspace);
                    int position = new Random().nextInt(Math.abs(musicInfos.size()));
                    musicServices.initMediaPlayer(position);
                } else if (inc_y < 0 && mediaPlayer.isPlaying()) {
                    Animation hyperspace = AnimationUtils.loadAnimation(this, R.anim.stop_needle);
                    hyperspace.setFillAfter(true);
                    playNeedle.startAnimation(hyperspace);
                    mediaPlayer.stop();
                    mediaPlayer.release();
                } else {
                }

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
}
