package com.russell.calmmusic.tools.androids;

import android.content.Context;
import android.media.AudioManager;

public class AudioFocusHelper implements AudioManager.OnAudioFocusChangeListener {
    AudioManager mAudioManager;
    Context mContext;

    // other fields here, you'll probably hold a reference to an interface
    // that you can use to communicate the focus changes to your Service

    public AudioFocusHelper(Context ctx) {
        this.mContext = ctx;
        mAudioManager = (AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE);
        // ...
    }

    public boolean requestFocus() {
        return AudioManager.AUDIOFOCUS_REQUEST_GRANTED ==
            mAudioManager.requestAudioFocus((AudioManager.OnAudioFocusChangeListener) mContext, AudioManager.STREAM_MUSIC,
            AudioManager.AUDIOFOCUS_GAIN);
    }

    public boolean abandonFocus() {
        return AudioManager.AUDIOFOCUS_REQUEST_GRANTED ==
            mAudioManager.abandonAudioFocus(this);
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        // let your service know about the focus change
    }
}