/**
 * 所有关于音乐的服务
 */

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
import java.util.List;
import java.util.Random;

/**
 * Created by Russell on 16/1/25.
 */
public class MusicServices extends Service implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener,
        AudioManager.OnAudioFocusChangeListener {
    private static final String ACTION_PLAY = "com.russell.calmmusic.MainActivity";
    //    private static final String ACTION_PLAY = "com.russell.calmmusic.FragmentMainActivity";
    private static List<MusicInfo> musicList;
    private final Context context;
    static MediaPlayer mMediaPlayer;
    MusicLoader musicLoader;

    public static MediaPlayer getmMediaPlayer() {
        return mMediaPlayer;
    }

    public MusicServices(Context context) {
        this.context = context;
        this.musicLoader = new MusicLoader(context);
        musicList = musicLoader.getMusicList();
    }

    public void initMediaPlayer(int position) {
        if (mMediaPlayer == null) {
            /**
             * mMediaPlayer对象可能被release释放掉，或者点击进来就是空对象
             */
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setWakeMode(context, PowerManager.PARTIAL_WAKE_LOCK);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            int result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC,
                    AudioManager.AUDIOFOCUS_GAIN);
        }
        mMediaPlayer.reset();
        Uri uri = Uri.parse(musicList.get(position).getUrl());
        Log.i("url=======", uri + "");
        try {
            mMediaPlayer.setDataSource(context, uri);
            mMediaPlayer.prepareAsync(); // prepare async to not block main thread
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnCompletionListener(this);  //this指向调用它的对象，此处是mMediaPlayer
        mMediaPlayer.setOnErrorListener(this);
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
        initMediaPlayer(position);
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
                if (mMediaPlayer == null) {
                    int position = new Random().nextInt(musicList.size());
                    initMediaPlayer(position);
                } else if (!mMediaPlayer.isPlaying()) mMediaPlayer.start();
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
