package com.russell.calmmusic.services.imp;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.russell.calmmusic.services.AnimationServices;
import com.russell.calmmusic.services.MusicServices;

/**
 * Created by Russell on 16/1/29.
 */
public class AnimationServicesImpl implements AnimationServices {
    MusicServices musicServices;
    ObjectAnimator anim;

    @Override
    public void controlDisc(ImageView discPlay) { // TODO: 2016/1/30  controlDisc(ImageView discPlay,int flag)播放则有动画，不播放则没有
        musicServices  = new MusicServicesImp(discPlay.getContext());
        int dur = musicServices.getMediaPlayer().getDuration();
        Keyframe kf0 = Keyframe.ofFloat(0f, 0f); /*时间在0%的时候动画对象的角度*/
        Keyframe kf1 = Keyframe.ofFloat(1f, 360f); /*时间在100%的时候(旋转一圈的时间)动画对象的角度*/
        PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofKeyframe("rotation", kf0, kf1); /*设置动画类型和关键值*/
        anim = ObjectAnimator.ofPropertyValuesHolder(discPlay, pvhRotation); /*将设置的属性绑定在需要动画的对象上*/
        anim.setDuration(8000); /*设置一圈的时间间隔*/
        anim.setInterpolator(new LinearInterpolator()); /*设置为匀速*/
//        anim.setRepeatCount(dur / 8000);
        anim.setRepeatCount(Animation.INFINITE); /*设置为循环*/
        anim.setRepeatMode(ObjectAnimator.RESTART);
        anim.start();
    }

    @Override
    public boolean controlNeedle() {
        return false;
    }
}
