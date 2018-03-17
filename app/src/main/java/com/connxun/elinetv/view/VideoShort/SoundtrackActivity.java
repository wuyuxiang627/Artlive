package com.connxun.elinetv.view.VideoShort;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.connxun.elinetv.R;
import com.connxun.elinetv.adapter.VideoShortAdapter.SoundTrackAdapter;
import com.connxun.elinetv.base.ui.BaseActivity;
import com.connxun.elinetv.base.util.LogUtil;
import com.connxun.elinetv.entity.Song;
import com.connxun.elinetv.observer.NotifyObject;
import com.connxun.elinetv.service.PlayMusicService;
import com.connxun.elinetv.service.PlayMusicService.MusicBinder;
import com.connxun.elinetv.util.AudioUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * Created by connxun-16 on 2018/1/25.
 * 配乐
 */
@ContentView(R.layout.activity_soundtrack)
public class SoundtrackActivity extends BaseActivity{

    String TAG = SoundtrackActivity.class.getCanonicalName();
    @ViewInject(R.id.rl_video_short_soundtrack_finish)
    RelativeLayout rlFinish; //销毁
    @ViewInject(R.id.rlv_video_short_soundtrack)
    RecyclerView rlvSoundTrack; //音乐

    ArrayList<Song> songs; //音乐文件集合
    SoundTrackAdapter soundTrackAdapter; //音乐集合

    private int positions = -1; //点击 的音乐
    private MusicBinder musicBinder; //音乐播放的binnder
    private ServiceConnection conn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        songs = AudioUtils.getAllSongs(this);
        LogUtil.Log(TAG,LogUtil.LOG_E,songs.toString());


        //绑定播放音乐的service
        bindMusicService();

        //设置音乐
        setSongsData();

        //监听
        setListener();
    }


    /**
     * 绑定service
     */
    private void bindMusicService() {
        Intent intent = new Intent(this, PlayMusicService.class);
        conn = new ServiceConnection() {
            //异常断开时 执行
            public void onServiceDisconnected(ComponentName name) {
            }
            //当与service绑定成功后 执行
            public void onServiceConnected(ComponentName name, IBinder service) {
                musicBinder = (MusicBinder) service;

            }
        };
        this.bindService(intent, conn, Service.BIND_AUTO_CREATE);
    }


    /**
     * 设置音乐
     */
    private void setSongsData() {
        soundTrackAdapter = new SoundTrackAdapter(this,musicBinder,songs);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
        rlvSoundTrack.setLayoutManager(gridLayoutManager);
        rlvSoundTrack.setAdapter(soundTrackAdapter);
    }

    /**
     * 监听
     */
    private void setListener() {
        rlFinish.setOnClickListener(this);
        soundTrackAdapter.setOnItemClickListener(new SoundTrackAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(positions != -1 && positions == position){
                  boolean blTYpe= musicBinder.playOrPause();
                  songs.get(position).setBlType(blTYpe);
                }else {
                    musicBinder.playMusic(songs.get(position).getFileUrl());
                }
                positions = position;
                soundTrackAdapter.setSelectedPosition(position);
                soundTrackAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId())
        {
            case R.id.rl_video_short_soundtrack_finish:
                this.finish();
                break;
        }
    }


    @Override
    public void notifyUpdate(NotifyObject obj) {
        switch (obj.what)
        {
            case 1:
                musicBinder.playOrPause();
                break;
            case 2:
                String musicUrl = obj.str;
                break;

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(musicBinder != null){
            musicBinder.playOrPause();
        }
    }
}
