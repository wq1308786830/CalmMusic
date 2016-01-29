/**
 * MainActivity中的Fragment用于显示音乐列表
 */

package com.russell.calmmusic.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.russell.calmmusic.R;
import com.russell.calmmusic.activities.PlayingActivity;
import com.russell.calmmusic.bean.MusicInfo;
import com.russell.calmmusic.services.MusicServices;
import com.russell.calmmusic.services.imp.MusicServicesImp;
import com.russell.calmmusic.tools.DividerItemDecoration;
import com.russell.calmmusic.tools.MusicLoader;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private HomeAdapter mAdapter;
    private RecyclerView recycler;
    private MusicLoader musicLoader;
    private MusicServices musicServicesImp;
    private List<MusicInfo> list;


    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        musicLoader = new MusicLoader(getActivity());
        musicServicesImp = new MusicServicesImp(getActivity());
        initData();
        initView(view);
        return view;
    }

    private void initView(View view) {
        recycler = (RecyclerView) view.findViewById(R.id.recycler_view);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recycler.setLayoutManager(new GridLayoutManager(getActivity(), 1));   /*改变布局类型*/
        recycler.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recycler.setAdapter(mAdapter = new HomeAdapter(list));
    }

    protected void initData() {
        list = musicLoader.getMusicList();
    }


    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> implements View.OnClickListener {

        private List<MusicInfo> datas;

        public HomeAdapter(List<MusicInfo> datas) {
            this.datas = datas;
        }

        @Override
        public HomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
            view.setOnClickListener(this);
            return holder;
        }

        @Override
        public void onBindViewHolder(HomeAdapter.MyViewHolder holder, int position) {
            String ss = list.get(position).getArtist() + " - " + list.get(position).getAlbum();
            String pos = String.valueOf(position+1);
            holder.songName.setText(list.get(position).getTitle());
            holder.songSinger.setText(ss);
            holder.songPosition.setText(pos);

            //将数据保存在itemView的Tag中，以便点击时进行获取
            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(this);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public void onClick(View view) {
            int o = (int) view.getTag();
            musicServicesImp.initMediaPlayer(o);
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            intent.setClass(getActivity(), PlayingActivity.class);
            bundle.putInt("tag", o);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView songName;
            public TextView songSinger;
            public TextView songPosition;

            public MyViewHolder(View itemView) {
                super(itemView);
                songName = (TextView) itemView.findViewById(R.id.songName);
                songSinger = (TextView) itemView.findViewById(R.id.songSinger);
                songPosition = (TextView) itemView.findViewById(R.id.itemPosition);
            }
        }
    }
}