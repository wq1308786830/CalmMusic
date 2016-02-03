/**
 * 所有关于音乐的服务
 */

package com.russell.calmmusic.services.imp;

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
import android.view.SurfaceHolder;
import android.widget.MediaController;

import com.russell.calmmusic.bean.MusicInfo;
import com.russell.calmmusic.services.MusicServices;
import com.russell.calmmusic.tools.datas.MusicLoader;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Created by Russell on 16/1/25.
 */
public class MusicServicesImp extends Service implements MusicServices, MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener, AudioManager.OnAudioFocusChangeListener,
        MediaPlayer.OnVideoSizeChangedListener, SurfaceHolder.Callback, MediaController.MediaPlayerControl {
    private int lastPosition;
    private List<MusicInfo> musicList;
    private Context context;
    public static MediaPlayer mediaPlayer;
    private MediaController playMusic, stopMusic, nextMusic, lastMusic;
    MusicLoader musicLoader;

    public void setMusicList(List<MusicInfo> musicList) {
        this.musicList = musicList;
    }

    public MusicLoader getMusicLoader() {
        return musicLoader;
    }

    public void setMusicLoader(MusicLoader musicLoader) {
        this.musicLoader = musicLoader;
    }

    public MusicServicesImp(Context context) {
        this.context = context;
    }

    @Override
    public List<MusicInfo> getMusicList() {
        return musicList;
    }

    @Override
    public void nextMusic() {
        lastPosition++;
        if (lastPosition <= musicList.size()) {
            initMediaPlayer(lastPosition);
        } else {
            initMediaPlayer(0);
        }
    }

    @Override
    public void stopMusic() {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    @Override
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    @Override
    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public boolean setContext(Context context) {
        this.context = context;
        return context != null;
    }

    public void init() {
        musicLoader = new MusicLoader(context);
        musicList = musicLoader.getMusicList();
    }

    @Override
    public void initMediaPlayer(int position) {
        init();
        this.lastPosition = position;
        if (mediaPlayer == null) {
            /*mediaPlayer对象可能被release释放掉，或者点击进来就是空对象*/
            mediaPlayer = new MediaPlayer();
        }
        mediaPlayer.reset();
        mediaPlayer.setWakeMode(context, PowerManager.PARTIAL_WAKE_LOCK);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN);
        Uri uri = Uri.parse(musicList.get(position).getUrl());
        Log.i("url=======", uri + "");
        try {
            mediaPlayer.setDataSource(context, uri);
            mediaPlayer.prepareAsync(); // prepare async to not block main thread
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);  //this指向调用它的对象，此处是mediaPlayer
        mediaPlayer.setOnErrorListener(this);
    }

    /**
     * Called when MediaPlayer is ready
     */
    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
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
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onAudioFocusChange(int i) {
        switch (i) {
            case AudioManager.AUDIOFOCUS_GAIN:
                // resume playback
                if (mediaPlayer == null) {
                    int position = new Random().nextInt(musicList.size());
                    initMediaPlayer(position);
                } else if (!mediaPlayer.isPlaying())
                    mediaPlayer.start();
                mediaPlayer.setVolume(1.0f, 1.0f);
                break;

            case AudioManager.AUDIOFOCUS_LOSS:
                // Lost focus for an unbounded amount of time: stop playback and release media player
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                break;

            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                // Lost focus for a short time, but we have to stop
                // playback. We don't release the media player because playback
                // is likely to resume
                if (mediaPlayer.isPlaying())
                    mediaPlayer.pause();
                break;

            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                // Lost focus for a short time, but it's ok to keep playing
                // at an attenuated level
                if (mediaPlayer.isPlaying())
                    mediaPlayer.setVolume(0.1f, 0.1f);
                break;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void start() {

    }

    @Override
    public void pause() {

    }

    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        return 0;
    }

    @Override
    public void seekTo(int i) {

    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return false;
    }

    @Override
    public boolean canSeekBackward() {
        return false;
    }

    @Override
    public boolean canSeekForward() {
        return false;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i1) {

    }
}
