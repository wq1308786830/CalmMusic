/**
 *
 */

package com.russell.calmmusic.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore.Audio.Media;
import android.util.Log;

import com.russell.calmmusic.bean.MusicInfo;

public class MusicLoader implements MediaPlayer.OnCompletionListener{

    private static final String TAG = "com.example.nature.MusicLoader";
    private static boolean completion = false;
    private static List<MusicInfo> musicList = new ArrayList<MusicInfo>();
    //Uri，指向external的database  
    private Uri contentUri = Media.EXTERNAL_CONTENT_URI;
    //projection：选择的列; where：过滤条件; sortOrder：排序。
    private String[] projection = {
            Media._ID,
            Media.DISPLAY_NAME,
            Media.DATA,
            Media.ALBUM,
            Media.ARTIST,
            Media.DURATION,
            Media.SIZE
    };
    private String where = "mime_type in ('audio/mpeg','audio/x-ms-wma') and is_music > 0 ";
    private String sortOrder = Media.DATA;
    static List<MusicInfo> MUSIC_INFOS;

    public MusicLoader(ContentResolver contentResolver) {                                                                                                             //利用ContentResolver的query函数来查询数据，然后将得到的结果放到MusicInfo对象中，最后放到数组中
        Cursor cursor = contentResolver.query(contentUri, projection, where, null, sortOrder);
        System.out.println(cursor);
        if (cursor == null) {
            Log.v(TAG, "Line(37) Music Loader cursor == null.");
        } else if (!cursor.moveToFirst()) {
            Log.v(TAG, "Line(39) Music Loader cursor.moveToFirst() returns false.");
        } else {
            int displayNameCol = cursor.getColumnIndex(Media.DISPLAY_NAME);
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
        }
    }

    public List<MusicInfo> getMusicList() {
        return musicList;
    }

    public Uri getMusicUriById(long id) {
        Uri uri = ContentUris.withAppendedId(contentUri, id);
        return uri;
    }
    public void musicPlayer(String url, List<?> list){
        /**
         * 播放音乐
         */
        Log.i("url=======", url);
        MUSIC_INFOS = (List<MusicInfo>) list;
        final MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setLooping(false); /*循环*/
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setLooping(completion); /*循环*/
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare(); // might take long! (for buffering, etc)
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(this);  //this指向调用它的对象，此处是mediaPlayer
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        int position = new Random().nextInt(Math.abs(MUSIC_INFOS.size()));
        musicPlayer((MUSIC_INFOS.get(position)).getUrl(), MUSIC_INFOS);
    }
}