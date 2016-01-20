package com.russell.calmmusic;

import android.content.ContentResolver;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
public class MainActivityFragment extends Fragment implements MediaPlayer.OnCompletionListener {

    private HomeAdapter mAdapter;
    private List<String> mDatas = new ArrayList<>();
    private RecyclerView recycler;
    private ContentResolver contentResolver;
    private MusicLoader musicLoader;
    private List<?> list;
    private Random random = new Random();
    private int position = 0;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initData();
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        recycler = (RecyclerView) view.findViewById(R.id.recycler_view);
//        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        recycler.setAdapter(mAdapter = new HomeAdapter());
    }

    protected void initData() {
        contentResolver = getActivity().getContentResolver();
        musicLoader = new MusicLoader(contentResolver);
        list = musicLoader.getMusicList();
        for (int i = 0; i < list.size(); i++) {
            MusicInfo musicInfo = (MusicInfo) list.get(i);
            String a = musicInfo.getUrl();
//            Log.i("url", a);
        }
        position = random.nextInt(Math.abs(list.size()));
        musicLoader.musicPlayer(((MusicInfo) list.get(position)).getUrl(), list);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        position = random.nextInt(Math.abs(list.size()));
        musicLoader.musicPlayer(((MusicInfo) list.get(position)).getUrl(), list);
    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

        @Override
        public HomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(HomeAdapter.MyViewHolder holder, int position) {
            holder.tv.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv;

            public MyViewHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.recycler_list_item);
            }
        }
    }
}