package com.connxun.elinetv.view.My;

import android.support.v4.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;

import com.connxun.elinetv.R;
import com.connxun.elinetv.adapter.MyAdapter.MyFollowAdapter;

import com.connxun.elinetv.base.ui.BaseActivity;
import com.connxun.elinetv.entity.Entity;
import com.connxun.elinetv.entity.user.UserFollowEntity;
import com.connxun.elinetv.presenter.user.UserinfoPresenter;

import com.connxun.elinetv.view.user.ITestView;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_my_follow)
public class MyFollowActivity extends BaseActivity {


    UserinfoPresenter userinfoPresenter = new UserinfoPresenter(this);
    private List<UserFollowEntity> followList = new ArrayList<>(); //评论总集合
    private List<UserFollowEntity> requestfollowList = new ArrayList<>(); //请求回来的数据集合



    int page = 1; //页数
    boolean blType = false;
    String length ="20";

    @ViewInject(R.id.rl_my_follow_title)
    RelativeLayout rl_my_follow_title;

    @ViewInject(R.id.swl_follow)
    SwipeRefreshLayout swl_follow;

    @ViewInject(R.id.rlv_my_follow)
    XRecyclerView rlv_my_follow;

    Entity<UserFollowEntity> talkEntity;

    MyFollowAdapter followAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //nizhidadaokdljfaldsajf
            initView();
        setRecyclerView();

        //用户关注列表
        userinfoPresenter.onCreate();
        userinfoPresenter.getAttentionFollowList(page+"",length);
        userinfoPresenter.attachView(AttentionFollowListView);

    }
    private void setRecyclerView() {
        LinearLayoutManager xLinearLayoutManager = new LinearLayoutManager(this);
        xLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlv_my_follow.setLayoutManager(xLinearLayoutManager);
        rlv_my_follow.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设定下拉刷新样式
        rlv_my_follow.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);//设定上拉加载样式
        rlv_my_follow.setArrowImageView(R.drawable.iconfont_downgrey);     //设定下拉刷新显示图片（不必须）
        followAdapter = new MyFollowAdapter(MyFollowActivity.this,followList);
        rlv_my_follow.setAdapter(followAdapter);

/**
 *设定下拉刷新和上拉加载监听
 */
        rlv_my_follow.setLoadingListener(new XRecyclerView.LoadingListener() {
            //上拉加载监听

            @Override
            public void onLoadMore() {
                blType = true;
//                addData();  //上拉加载添加数据
                userinfoPresenter.onCreate();
                userinfoPresenter.getAttentionFollowList(page+"",length);
                userinfoPresenter.attachView(AttentionFollowListView);

            };
            //下拉刷新监听
            @Override
            public void onRefresh() {
                blType = false;
                page = 1;
                userinfoPresenter.onCreate();
                userinfoPresenter.getAttentionFollowList(page+"",length);
                userinfoPresenter.attachView(AttentionFollowListView);
            }
        });



    }


    private void initView() {
        rl_my_follow_title = findViewById(R.id.rl_my_follow_title);
        swl_follow = findViewById(R.id.swl_follow);
        rlv_my_follow = findViewById(R.id.rlv_my_follow);

        rl_my_follow_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    //关注列表
    private ITestView AttentionFollowListView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            //显示adapter
            talkEntity = (Entity<UserFollowEntity>) object;
            if(talkEntity.getData().getList().size() == 0 || talkEntity.getData().getList() == null){
                followAdapter.notifyDataSetChanged();
                return;
            }

            if(blType){
                //上啦加载
                rlv_my_follow.loadMoreComplete();    //加载数据完成（取消加载动画）
                requestfollowList = talkEntity.getData().getList();
                followList.addAll(requestfollowList);
                followAdapter.notifyDataSetChanged();
                page = page + 1;

            }else {
                //下拉刷新
                followList.clear();
                requestfollowList = talkEntity.getData().getList();
                followList.addAll(requestfollowList);
                followAdapter.notifyDataSetChanged();
                rlv_my_follow.refreshComplete();
                page =2;

            }

        }

        @Override
        public void onError(Object object) {

        }
    };


}
