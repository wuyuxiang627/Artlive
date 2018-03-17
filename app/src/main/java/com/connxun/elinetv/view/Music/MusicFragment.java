package com.connxun.elinetv.view.Music;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;







import android.view.View;
import android.view.ViewGroup;

import com.connxun.elinetv.R;
import com.connxun.elinetv.adapter.VideoShortAdapter.SoundTrackAdapter;
import com.connxun.elinetv.base.ui.BaseFragment;
import com.connxun.elinetv.entity.Song;
import com.connxun.elinetv.service.PlayMusicService;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018\3\16 0016.
 */

public class MusicFragment extends BaseFragment {
    View view;

    private int positions = -1; //点击 的音乐
    private PlayMusicService.MusicBinder musicBinder; //音乐播放的binnder
    private ServiceConnection conn;
    ArrayList<Song> songs; //音乐文件集合
    SoundTrackAdapter soundTrackAdapter; //音乐集合
    RecyclerView rlvMusic;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_music,null);

        initView();

        //绑定播放音乐的service
        bindMusicService();

        //设置音乐
        setSongsData();

        return view;
    }



    /**
     * 绑定service
     */
    private void bindMusicService() {
        Intent intent = new Intent(getActivity(), PlayMusicService.class);
        conn = new ServiceConnection() {
            //异常断开时 执行
            public void onServiceDisconnected(ComponentName name) {
            }
            //当与service绑定成功后 执行
            public void onServiceConnected(ComponentName name, IBinder service) {
                musicBinder = (PlayMusicService.MusicBinder) service;

            }
        };
        getActivity().bindService(intent, conn, Service.BIND_AUTO_CREATE);
    }


    /**
     * 设置音乐
     */
    private void setSongsData() {
        soundTrackAdapter = new SoundTrackAdapter(getActivity(),musicBinder,songs);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),1);
        rlvMusic.setLayoutManager(gridLayoutManager);
        rlvMusic.setAdapter(soundTrackAdapter);
    }

    private void initView() {
        rlvMusic = view.findViewById(R.id.rlv_music_list);
    }

}
