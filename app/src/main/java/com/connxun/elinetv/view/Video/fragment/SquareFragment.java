package com.connxun.elinetv.view.Video.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.connxun.elinetv.R;
import com.connxun.elinetv.adapter.VideoShortAdapter.VideoUniversalAdapter;
import com.connxun.elinetv.base.ui.BaseFragment;
import com.connxun.elinetv.base.ui.GridRecyclerView;
import com.connxun.elinetv.entity.Entity;
import com.connxun.elinetv.entity.EntityObject;
import com.connxun.elinetv.entity.GetVideoInfo;
import com.connxun.elinetv.entity.VideoEtivity;
import com.connxun.elinetv.observer.INotifyListener;
import com.connxun.elinetv.observer.NotifyListenerManager;
import com.connxun.elinetv.observer.NotifyObject;
import com.connxun.elinetv.presenter.video.VideoPresenter;
import com.connxun.elinetv.util.RecyclerViewAnimation;
import com.connxun.elinetv.view.VideoShort.VideoPlayActivity;
import com.connxun.elinetv.view.user.ITestView;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

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

public class SquareFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,INotifyListener {

    private VideoPresenter videoPresenter = new VideoPresenter(getActivity()); //视频列表
    private Entity<VideoEtivity> videoEntity;
    private List<VideoEtivity> videoList = new ArrayList<>();

    private EntityObject<GetVideoInfo> videoInfoEntity;

    String videoPath = "http://vod6952l0zv.vod.126.net/vod6952l0zv/278441da-3221-440c-a927-798ec571ad18.mp4";

    VideoUniversalAdapter videoUniversalAdapter;
    GridLayoutManager gridLayoutManager = null;

    @ViewInject(R.id.rlv_fragment_video)
    GridRecyclerView rlvVideoView;
//    @ViewInject(R.id.swl_video_square)
//    SwipeRefreshLayout swlSquare;
    private View view;

    private VideoEtivity positionVideoEntity;
    private int clickPosition;


    int page = 1; //页数
    boolean blType = false;


    private List<VideoEtivity> totalTalkList = new ArrayList<>(); //评论总集合
    private List<VideoEtivity> requestTalkList = new ArrayList<>(); //请求回来的数据集合

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
        registerListener();

        videoPresenter.onCreate();
        videoPresenter.getVideoLIst("1","20","");
        videoPresenter.attachView(VideoListView);
        return view;
    }

    private void initView() {
        gridLayoutManager = new GridLayoutManager(getActivity(),2);

        rlvVideoView.setLayoutManager(gridLayoutManager);
        rlvVideoView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设定下拉刷新样式
        rlvVideoView.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);//设定上拉加载样式
        rlvVideoView.setArrowImageView(R.drawable.iconfont_downgrey);

        videoUniversalAdapter = new VideoUniversalAdapter(getActivity(),totalTalkList);
        rlvVideoView.setAdapter(videoUniversalAdapter);

        /**
         *设定下拉刷新和上拉加载监听
         */
        rlvVideoView.setLoadingListener(new XRecyclerView.LoadingListener() {
            //上拉加载监听

            @Override
            public void onLoadMore() {
                blType = true;
                videoPresenter.onCreate();
                videoPresenter.getVideoLIst(page+"","20","");
                videoPresenter.attachView(VideoListView);

            };
            //下拉刷新监听
            @Override
            public void onRefresh() {
                blType = false;
                page = 1;
                videoPresenter.onCreate();
                videoPresenter.getVideoLIst(page+"","20","");
                videoPresenter.attachView(VideoListView);
            }
        });
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);

    }

    private List<VideoEtivity> videoListNew = new ArrayList<>();
    private ITestView VideoListView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            videoEntity = (Entity<VideoEtivity>) object;

            if (videoEntity.getData().getList().size() == 0 || videoEntity.getData().getList() == null) {
                videoUniversalAdapter.notifyDataSetChanged();
                return;
            }

            if (blType) {
                //上啦加载
                rlvVideoView.loadMoreComplete();    //加载数据完成（取消加载动画）
                requestTalkList = videoEntity.getData().getList();
                totalTalkList.addAll(requestTalkList);
                videoUniversalAdapter.notifyDataSetChanged();
                page = page + 1;

            } else {
                //下拉刷新
                RecyclerViewAnimation.runLayoutAnimation(rlvVideoView);
                totalTalkList.clear();
                requestTalkList = videoEntity.getData().getList();
                totalTalkList.addAll(requestTalkList);
                videoUniversalAdapter.notifyDataSetChanged();
                rlvVideoView.refreshComplete();
                page = 2;

            }


            videoUniversalAdapter.setOnItemClickListener(new VideoUniversalAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    videoPresenter.onCreate();
                    clickPosition = position;
                    videoPresenter.getVideoInfo(totalTalkList.get(position).getVideoNo()+"");
                    positionVideoEntity = totalTalkList.get(position);
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
            intent.putExtra("videoInfoEntity", (Serializable) videoInfoEntity);
            intent.putExtra("positionVideoEntity",positionVideoEntity);
            startActivityForResult(intent,100);

        }

        @Override
        public void onError(Object object) {

        }
    };


    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_from_bottom);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case 100:
//                VideoEtivity positionVideoEntity = (VideoEtivity) data.getSerializableExtra("positionVideoEntity");
//                videoList.get(clickPosition).setLoveNum(positionVideoEntity.getLoveNum());
                break;
        }


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegisterListener();
    }

    @Override
    public void onRefresh() {
        videoList.clear();
        videoPresenter.onCreate();
        videoPresenter.getVideoLIst("1","20","");
        videoPresenter.attachView(VideoListView);
    }

    @Override
    public void notifyUpdate(NotifyObject obj) {

        if( positionVideoEntity != null){
            return ;
        }
        try{
            switch (obj.what)
            {
                case 1: //增加
//                videoInfoEntity.getData().getUser().setState("1");
                    videoList.get(clickPosition).setLoveNum(positionVideoEntity.getLoveNum()+1);
                    break;
                case 2: //减少

                    videoList.get(clickPosition).setLoveNum(positionVideoEntity.getLoveNum()-1);
                    break;

            }
        }catch (Exception e){

        }

    }

    @Override
    public void registerListener() {
        NotifyListenerManager.getInstance().registerListener(this);
    }

    @Override
    public void unRegisterListener() {
        NotifyListenerManager.getInstance().unRegisterListener(this);
    }
}
