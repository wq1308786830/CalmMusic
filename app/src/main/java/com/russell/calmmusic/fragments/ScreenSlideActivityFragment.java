package com.russell.calmmusic.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.russell.calmmusic.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ScreenSlideActivityFragment extends Fragment {

    public ScreenSlideActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide, container, false);

        return rootView;
    }
}
