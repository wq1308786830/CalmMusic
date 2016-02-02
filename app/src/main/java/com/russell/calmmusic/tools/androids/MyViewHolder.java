package com.russell.calmmusic.tools.androids;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.russell.calmmusic.R;

/**
 * Created by Russell on 16/1/22.
 */


class MyViewHolder extends RecyclerView.ViewHolder {
    TextView songName;
    TextView songSinger;

    public MyViewHolder(View itemView) {
        super(itemView);
        songName = (TextView) itemView.findViewById(R.id.songName);
//        songSinger = (TextView) itemView.findViewById(R.id.songSinger);
    }
}

