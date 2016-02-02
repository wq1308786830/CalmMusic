package com.russell.calmmusic.services.imp;

import android.view.MotionEvent;
import android.view.View;

import com.russell.calmmusic.services.TouchServices;

import java.util.Map;

/**
 * Created by qi on 2016/2/1.
 */
public class TouchServicesImpl implements TouchServices, View.OnTouchListener {
    @Override
    public Map<Integer, Float> direction$Length() {
        return null;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
