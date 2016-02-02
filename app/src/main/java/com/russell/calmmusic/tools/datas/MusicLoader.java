/**
 * 获取音乐数据
 */

package com.russell.calmmusic.tools.datas;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Audio.Media;
import android.util.Log;

import com.russell.calmmusic.bean.MusicInfo;

import java.util.ArrayList;
import java.util.List;

public class MusicLoader {

    private Cursor cursor;
    private static final String TAG = "com.example.nature.MusicLoader";
    private List<MusicInfo> musicList = new ArrayList<>();
    //Uri，指向external的database  
    private Uri contentUri = Media.EXTERNAL_CONTENT_URI;
    //projection：选择的列; where：过滤条件; sortOrder：排序。
    private String[] projection = {
            Media._ID,
            Media.TITLE,
            Media.DATA,
            Media.ALBUM,
            Media.ARTIST,
            Media.DURATION,
            Media.SIZE
    };
    private String where = "mime_type in ('audio/mpeg','audio/x-ms-wma') and is_music > 0 ";
    private String sortOrder = Media.DATA;

    public MusicLoader(Context context) {
        //利用ContentResolver的query函数来查询数据，然后将得到的结果放到MusicInfo对象中，最后放到数组中
        Cursor cursor = context.getContentResolver().query(contentUri, projection, where, null, sortOrder);
        System.out.println(cursor);
        if (cursor == null) {
            Log.i(TAG, "MusicLoader cursor == null.");
        } else if (!cursor.moveToFirst()) {
            Log.i(TAG, "MusicLoader cursor.moveToFirst() returns false.");
        } else {
            this.cursor = cursor;
        }
    }

    private List<MusicInfo> setMusicList(Cursor cursor){
        int displayNameCol = cursor.getColumnIndex(Media.TITLE);
        int albumCol = cursor.getColumnIndex(Media.ALBUM);
        int idCol = cursor.getColumnIndex(Media._ID);
        int durationCol = cursor.getColumnIndex(Media.DURATION);
        int sizeCol = cursor.getColumnIndex(Media.SIZE);
        int artistCol = cursor.getColumnIndex(Media.ARTIST);
        int urlCol = cursor.getColumnIndex(Media.DATA);
        do {
            String title = cursor.getString(displayNameCol);
            String album = cursor.getString(albumCol);
            long id = cursor.getLong(idCol);
            int duration = cursor.getInt(durationCol);
            long size = cursor.getLong(sizeCol);
            String artist = cursor.getString(artistCol);
            String url = cursor.getString(urlCol);

            MusicInfo musicInfo = new MusicInfo(id, title);
            musicInfo.setAlbum(album);
            musicInfo.setDuration(duration);
            musicInfo.setSize(size);
            musicInfo.setArtist(artist);
            musicInfo.setUrl(url);
            musicList.add(musicInfo);

        } while (cursor.moveToNext());
        return musicList;
    }

    public void setMusicList(List<MusicInfo> musicList){
        this.musicList = setMusicList(cursor);
    }
    public List<MusicInfo> getMusicList() {
        return setMusicList(cursor);
    }

    public Uri getMusicUriById(long id) {
        return ContentUris.withAppendedId(contentUri, id);
    }

}