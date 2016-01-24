package com.russell.calmmusic;

import android.media.MediaPlayer;

/**
 * Created by qi on 2016/1/22.
 */
public class MyApplication extends android.app.Application {
    private static MediaPlayer mediaPlayer = new MediaPlayer();

    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
