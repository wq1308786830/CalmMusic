package com.russell.calmmusic.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.russell.calmmusic.R;
import com.russell.calmmusic.activities.NetEaseWeb;

/**
 * A placeholder fragment containing a simple view.
 */
public class ScreenSlideActivityFragment extends Fragment implements View.OnClickListener {

    public ScreenSlideActivityFragment() {
    }

    public static ScreenSlideActivityFragment newInstance(int showImg) {
        ScreenSlideActivityFragment f = new ScreenSlideActivityFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("showImg", showImg);
        f.setArguments(args);

        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide, container, false);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.switch_img);

        if (getArguments().getInt("showImg") != 0)
            imageView.setImageResource(getArguments().getInt("showImg"));
        imageView.setOnClickListener(this);
        return rootView;
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (getArguments().getInt("showImg")){
            case R.mipmap.index_new_korea:
                intent.setClass(getActivity(), NetEaseWeb.class);
                startActivity(intent);
                System.out.println("aaa==========");
                break;
            case R.mipmap.index_daily_ban1:
                System.out.println("aaa==========");
                break;
            case R.mipmap.index_daily_ban2:
                System.out.println("aaa==========");
                break;
            case R.mipmap.index_new_america:
                System.out.println("aaa==========");
                break;
            case R.mipmap.index_new_china:
                System.out.println("aaa==========");
                break;
            default:
                break;
        }
    }
}
