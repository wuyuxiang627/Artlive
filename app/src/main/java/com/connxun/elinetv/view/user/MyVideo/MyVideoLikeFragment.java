package com.connxun.elinetv.view.user.MyVideo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.connxun.elinetv.R;
import com.connxun.elinetv.adapter.VideoShortAdapter.VideoUniversalAdapter;
import com.connxun.elinetv.app.BaseApplication;
import com.connxun.elinetv.base.ui.BaseFragment;
import com.connxun.elinetv.base.ui.GridRecyclerView;
import com.connxun.elinetv.entity.Entity;
import com.connxun.elinetv.entity.EntityObject;
import com.connxun.elinetv.entity.GetVideoInfo;
import com.connxun.elinetv.entity.VideoEtivity;
import com.connxun.elinetv.presenter.video.VideoPresenter;
import com.connxun.elinetv.util.RecyclerViewAnimation;
import com.connxun.elinetv.view.VideoShort.VideoPlayActivity;
import com.connxun.elinetv.view.user.ITestView;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\3\7 0007.
 */

public class MyVideoLikeFragment extends BaseFragment {
    VideoPresenter videoPresenter = new VideoPresenter(getActivity());


    View view;

    GridRecyclerView xRlvView;

    Entity<VideoEtivity> VideoEtivity;

    private List<VideoEtivity> videoEtivityList = new ArrayList<>(); //视频总集合
    private List<VideoEtivity> requestVideoList = new ArrayList<>(); //请求回来的数据集合

    int page = 1; //页数
    boolean blType = false;

    VideoUniversalAdapter videoUniversalAdapter;
    private int clickPosition;
    private VideoEtivity positionVideoEntity;
    private EntityObject<GetVideoInfo> videoInfoEntity;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_video_like,null);

        initView(view);

        setRecyclerView();

        videoPresenter.onCreate();
        videoPresenter.getVideoUserLoveVideo(page+"","20",BaseApplication.getUserNo());
        videoPresenter.attachView(getLoveListView);

        return view;
    }

    @Override
    public void setOnClick() {
        super.setOnClick();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
    }

    private void initView(View view) {
        xRlvView = view.findViewById(R.id.rlv_video_liek_view);

    }

    private void setRecyclerView() {
        GridLayoutManager xLinearLayoutManager = new GridLayoutManager(getActivity(),2);
        //xLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRlvView.setLayoutManager(xLinearLayoutManager);
        xRlvView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设定下拉刷新样式
        xRlvView.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);//设定上拉加载样式
        xRlvView.setArrowImageView(R.drawable.iconfont_downgrey);     //设定下拉刷新显示图片（不必须）
        videoUniversalAdapter = new VideoUniversalAdapter(getActivity(),videoEtivityList);
        xRlvView.setAdapter(videoUniversalAdapter);

/**
 *设定下拉刷新和上拉加载监听
 */
        xRlvView.setLoadingListener(new XRecyclerView.LoadingListener() {
            //上拉加载监听

            @Override
            public void onLoadMore() {
                blType = true;
//                addData();  //上拉加载添加数据
                videoPresenter.onCreate();
                videoPresenter.getVideoUserLoveVideo(page+"","20",BaseApplication.getUserNo());
                videoPresenter.attachView(getLoveListView);

            };
            //下拉刷新监听
            @Override
            public void onRefresh() {
                blType = false;
                page = 1;
                RecyclerViewAnimation.runLayoutAnimation(xRlvView);
                videoPresenter.onCreate();
                videoPresenter.getVideoUserLoveVideo(page+"","20",BaseApplication.getUserNo());
                videoPresenter.attachView(getLoveListView);
            }
        });

        videoUniversalAdapter.setOnItemClickListener(new VideoUniversalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                videoPresenter.onCreate();
                clickPosition = position;
                videoPresenter.getVideoInfo(videoEtivityList.get(position).getVideoNo()+"");
                positionVideoEntity = videoEtivityList.get(position);
                videoPresenter.attachView(VideoInfoView);
            }
        });

    }


    /**
     * 获取视频详情
     */
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


    public ITestView getLoveListView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            VideoEtivity = (Entity<VideoEtivity>) object;

            if(VideoEtivity.getData().getTotalRow() == 0){
                xRlvView.loadMoreComplete();
                xRlvView.refreshComplete();
                return;
            }

            if(VideoEtivity.getData().getList().size() == 0 || VideoEtivity.getData().getList() == null){
                videoUniversalAdapter.notifyDataSetChanged();
                return;
            }
            Log.e("framgnet","MyVideoLikeFragment 可见"+VideoEtivity.getData().getList().size());

            if(blType){
                //上啦加载
                xRlvView.loadMoreComplete();    //加载数据完成（取消加载动画）
                requestVideoList = VideoEtivity.getData().getList();
                videoEtivityList.addAll(requestVideoList);
                videoUniversalAdapter.notifyDataSetChanged();
                page = page + 1;

            }else {
                //下拉刷新
                videoEtivityList.clear();
                requestVideoList = VideoEtivity.getData().getList();
                videoEtivityList.addAll(requestVideoList);
                videoUniversalAdapter.notifyDataSetChanged();
                xRlvView.refreshComplete();
                page =2;

            }
        }

        @Override
        public void onError(Object object) {

        }
    };

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(getUserVisibleHint()) {
            Log.e("framgnet","MyVideoLikeFragment 可见");
//            isVisible = true;
        } else {
            Log.e("framgnet","MyVideoLikeFragment 不可见");
//            isVisible = false;
        }
    }





}
