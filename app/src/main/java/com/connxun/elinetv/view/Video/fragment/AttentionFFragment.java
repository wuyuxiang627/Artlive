package com.connxun.elinetv.view.Video.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.connxun.elinetv.R;
import com.connxun.elinetv.adapter.VideoShortAdapter.VideoUniversalWaterFallAdapter;
import com.connxun.elinetv.base.ui.BaseFragment;
import com.connxun.elinetv.entity.Entity;
import com.connxun.elinetv.entity.EntityObject;
import com.connxun.elinetv.entity.GetVideoInfo;
import com.connxun.elinetv.entity.VideoEtivity;
import com.connxun.elinetv.presenter.video.VideoPresenter;
import com.connxun.elinetv.view.VideoShort.VideoPlayActivity;
import com.connxun.elinetv.view.user.ITestView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by connxun-16 on 2018/1/10.
 */

/**
 * 广场
 */

public class AttentionFFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    private VideoPresenter videoPresenter = new VideoPresenter(getActivity()); //视频列表
    private Entity<VideoEtivity> videoEntity;
    private List<VideoEtivity> videoList = new ArrayList<>();

    private EntityObject<GetVideoInfo> videoInfoEntity;

    String videoPath = "http://vod6952l0zv.vod.126.net/vod6952l0zv/278441da-3221-440c-a927-798ec571ad18.mp4";

    VideoUniversalWaterFallAdapter videoUniversalAdapter;
    GridLayoutManager gridLayoutManager = null;

    @ViewInject(R.id.rlv_fragment_video)
    RecyclerView rlvVideoView;
//    @ViewInject(R.id.swl_video_square)
//    SwipeRefreshLayout swlSquare;
    private View view;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_video_square, null);
        x.view().inject(this, view);

        initView();

        videoPresenter.onCreate();
        videoPresenter.getVideoLIst("1","20","");
        videoPresenter.attachView(VideoListView);
        return view;
    }

    private void initView() {
        rlvVideoView.setLayoutManager(new
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        videoUniversalAdapter = new VideoUniversalWaterFallAdapter(getActivity());
        rlvVideoView.setAdapter(videoUniversalAdapter);



        //为SwipeRefreshLayout设置监听事件
//        swlSquare.setOnRefreshListener(this);
//        //为SwipeRefreshLayout设置刷新时的颜色变化，最多可以设置4种
//        swlSquare.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);

    }

    private List<VideoEtivity> videoListNew = new ArrayList<>();
    private VideoEtivity positionVideoEntity;
    private ITestView VideoListView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            //结束后停止刷新
//            swlSquare.setRefreshing(false);
            videoUniversalAdapter.notifyDataSetChanged();
            videoEntity = (Entity<VideoEtivity>) object;
            videoListNew = videoEntity.getData().getList();
            videoList.addAll(videoListNew);
//            videoUniversalAdapter.notifyDataSetChanged();

            videoUniversalAdapter.getList().addAll(videoList);
            videoUniversalAdapter.getRandomHeight(videoList);
            videoUniversalAdapter.notifyDataSetChanged();



            videoUniversalAdapter.setOnItemClickListener(new VideoUniversalWaterFallAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    videoPresenter.onCreate();
                    videoPresenter.getVideoInfo(videoList.get(position).getVideoNo()+"");
                    positionVideoEntity = videoList.get(position);
                    videoPresenter.attachView(VideoInfoView);
                }
            });
        }

        @Override
        public void onError(Object object) {
//            swlSquare.setRefreshing(false);

        }
    };

    private ITestView VideoInfoView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            videoInfoEntity = (EntityObject<GetVideoInfo>) object;
            String voideoPath = videoInfoEntity.getData().getInfo().getOrigUrl();
            Intent intent = new Intent(getContext(), VideoPlayActivity.class);
            intent.putExtra("videoPath",voideoPath);
            intent.putExtra("positionVideoEntity",positionVideoEntity);
            intent.putExtra("videoInfoEntity", (Serializable) videoInfoEntity);
//            intent.putExtra("videoInfoEntity", (Serializable) videoInfoEntity);
            startActivity(intent);

        }

        @Override
        public void onError(Object object) {

        }
    };


    @Override
    public void onRefresh() {
        videoList.clear();
        videoPresenter.onCreate();
        videoPresenter.getVideoLIst("1","20","");
        videoPresenter.attachView(VideoListView);
    }
}
