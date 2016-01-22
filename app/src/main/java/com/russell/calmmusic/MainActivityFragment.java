package com.russell.calmmusic;

import android.content.ContentResolver;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.russell.calmmusic.bean.MusicInfo;
import com.russell.calmmusic.tools.MusicLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private HomeAdapter mAdapter;
    private RecyclerView recycler;
    private ContentResolver contentResolver;
    private MusicLoader musicLoader;
    private List<MusicInfo> list;
    private Random random = new Random();
    private int position = 0;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initData();
        initView(view);
        return view;
    }

    private void initView(View view) {
        recycler = (RecyclerView) view.findViewById(R.id.recycler_view);
//        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        recycler.setAdapter(mAdapter = new HomeAdapter());
    }

    protected void initData() {
        contentResolver = getActivity().getContentResolver();
        musicLoader = new MusicLoader(contentResolver);
        list = musicLoader.getMusicList();
//        for (int i = 0; i < list.size(); i++) {
//            MusicInfo musicInfo = (MusicInfo) list.get(i);
//            String url = musicInfo.getUrl();
//            String name = musicInfo.getTitle();
//            String artist = musicInfo.getArtist();
//            String album = musicInfo.getAlbum();
//            int duration = musicInfo.getDuration();
////            Log.i("url", url);
//        }
        position = random.nextInt(Math.abs(list.size()));
        musicLoader.musicPlayer((list.get(position)).getUrl(), list);
    }


    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> implements View.OnClickListener {

        @Override
        public HomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(HomeAdapter.MyViewHolder holder, int position) {
            holder.songName.setText(list.get(position).getTitle());
            holder.songSinger.setText(list.get(position).getArtist());
            holder.itemView.setOnClickListener(this);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public void onClick(View view) {
            Log.i("position======", String.valueOf(position));
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView songName;
            public TextView songSinger;


            public MyViewHolder(View itemView) {
                super(itemView);
                songName = (TextView) itemView.findViewById(R.id.songName);
                songSinger = (TextView) itemView.findViewById(R.id.songSinger);
            }
        }
    }
}