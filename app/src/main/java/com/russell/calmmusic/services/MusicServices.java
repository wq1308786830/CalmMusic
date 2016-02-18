package com.russell.calmmusic.services;

import android.content.Context;
import android.media.MediaPlayer;

import com.russell.calmmusic.bean.MusicInfo;

import java.util.List;

/**
 * Created by Russell on 16/1/29.
 */
public interface MusicServices {
    Context getContext();
    boolean setContext(Context context);
    void initMediaPlayer(int o);
    List<MusicInfo> getMusicList();
    void nextMusic();
    void stopMusic();
}
