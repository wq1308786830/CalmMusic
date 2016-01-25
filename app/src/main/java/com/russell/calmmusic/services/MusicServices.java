package com.russell.calmmusic.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.russell.calmmusic.bean.MusicInfo;
import com.russell.calmmusic.tools.MusicLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Russell on 16/1/25.
 */
public class MusicServices extends Service implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener,
        AudioManager.OnAudioFocusChangeListener {
    private static final String ACTION_PLAY = "com.example.action.PLAY";
    private static List<MusicInfo> musicList = new ArrayList<MusicInfo>();
    MediaPlayer mMediaPlayer = null;

    public void playMusic(int position) {
        /**
         * 播放音乐
         */
        initMediaPlayer();
        Uri uri = Uri.parse(musicList.get(position).getUrl());
        Log.i("url=======", uri + "");
        try {
            mMediaPlayer.setDataSource(getApplicationContext(), uri);
            mMediaPlayer.prepareAsync(); // prepare async to not block main thread
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initMediaPlayer() {
//        mMediaPlayer = new MediaPlayer();
        MusicLoader  musicLoader = new MusicLoader(getContentResolver());
        musicList = musicLoader.getMusicList();
        mMediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnCompletionListener(this);  //this指向调用它的对象，此处是mMediaPlayer
        mMediaPlayer.setOnErrorListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equals(ACTION_PLAY)) {
            mMediaPlayer = new MediaPlayer(); // initialize it here
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.prepareAsync(); // prepare async to not block main thread
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * Called when MediaPlayer is ready
     */
    @Override
    public void onPrepared(MediaPlayer mMediaPlayer) {
        mMediaPlayer.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        int position = new Random().nextInt(Math.abs(musicList.size()));
        playMusic(position);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onError(MediaPlayer mMediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onAudioFocusChange(int i) {
        switch (i) {
            case AudioManager.AUDIOFOCUS_GAIN:
                // resume playback
                if (mMediaPlayer == null) initMediaPlayer();
                else if (!mMediaPlayer.isPlaying()) mMediaPlayer.start();
                mMediaPlayer.setVolume(1.0f, 1.0f);
                break;

            case AudioManager.AUDIOFOCUS_LOSS:
                // Lost focus for an unbounded amount of time: stop playback and release media player
                if (mMediaPlayer.isPlaying()) mMediaPlayer.stop();
                mMediaPlayer.release();
                mMediaPlayer = null;
                break;

            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                // Lost focus for a short time, but we have to stop
                // playback. We don't release the media player because playback
                // is likely to resume
                if (mMediaPlayer.isPlaying()) mMediaPlayer.pause();
                break;

            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                // Lost focus for a short time, but it's ok to keep playing
                // at an attenuated level
                if (mMediaPlayer.isPlaying()) mMediaPlayer.setVolume(0.1f, 0.1f);
                break;
        }
    }

}
