package com.connxun.elinetv.view.My;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import android.view.View;
import android.widget.RelativeLayout;


import com.connxun.elinetv.R;
import com.connxun.elinetv.adapter.MyAdapter.MyFancesAdapter;
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

/**
 * Created by connxun-16 on 2018/3/2.
 */

@ContentView(R.layout.activity_my_fances)
public class MyFancesActivity extends BaseActivity {


    UserinfoPresenter userinfoPresenter = new UserinfoPresenter(this);

    @ViewInject(R.id.rl_my_fances_finish)
    RelativeLayout rlFinish;

    @ViewInject(R.id.rlv_my_fans)
    XRecyclerView rlvTalkList;

    private Entity<UserFollowEntity> userFollowEntityEntity;
    private String videoNo;

//    Entity<UserFollowEntity> userFollowEntityEntity;
    MyFancesAdapter videoTalkAdapter;

    private List<UserFollowEntity> fanceslist = new ArrayList<>(); //总集合
    private List<UserFollowEntity> requestuserFancseList = new ArrayList<>(); //请求回来的数据集合

    int page = 1; //页数
    boolean blType = false;
    String length ="20";




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setOnClick();
        setRecyclerView();
        userinfoPresenter.onCreate();
        userinfoPresenter.getAttentionFollowerList(page+"","20");
        userinfoPresenter.attachView(getContentListView);

    }

    private void setRecyclerView() {
        LinearLayoutManager xLinearLayoutManager = new LinearLayoutManager(this);
        //xLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlvTalkList.setLayoutManager(xLinearLayoutManager);
        rlvTalkList.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设定下拉刷新样式
        rlvTalkList.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);//设定上拉加载样式
        rlvTalkList.setArrowImageView(R.drawable.iconfont_downgrey);     //设定下拉刷新显示图片（不必须）

        //设置adapter
        videoTalkAdapter = new MyFancesAdapter(MyFancesActivity.this,fanceslist);
        rlvTalkList.setAdapter(videoTalkAdapter);

/**
 *设定下拉刷新和上拉加载监听
 */
        rlvTalkList.setLoadingListener(new XRecyclerView.LoadingListener() {
            //上拉加载监听

            @Override
            public void onLoadMore() {
                blType = true;
//                addData();  //上拉加载添加数据
                userinfoPresenter.onCreate();
                userinfoPresenter.getAttentionFollowerList(page+"","20");
                userinfoPresenter.attachView(getContentListView);

            };
            //下拉刷新监听
            @Override
            public void onRefresh() {
                blType = false;
                page = 1;
                userinfoPresenter.onCreate();
                userinfoPresenter.getAttentionFollowerList(page+"","20");
                userinfoPresenter.attachView(getContentListView);
            }
        });



    }

    private void initView() {
        rlvTalkList = findViewById(R.id.rlv_my_fans);
        rlFinish = findViewById(R.id.rl_my_fances_title);

    }



    public void setOnClick() {
        rlFinish.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId())
        {
            case R.id.rl_my_fances_finish:
                finish();
                break;
        }
    }



    //评论列表
    public ITestView getContentListView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            userFollowEntityEntity = (Entity<UserFollowEntity>) object;

            if(userFollowEntityEntity.getData().getList().size() == 0 || userFollowEntityEntity.getData().getList() == null){
                videoTalkAdapter.notifyDataSetChanged();
                return;
            }

            if(blType){
                //上啦加载
                rlvTalkList.loadMoreComplete();    //加载数据完成（取消加载动画）
                requestuserFancseList = userFollowEntityEntity.getData().getList();
                fanceslist.addAll(requestuserFancseList);
                videoTalkAdapter.notifyDataSetChanged();
                page = page + 1;

            }else {
                //下拉刷新
                fanceslist.clear();
                requestuserFancseList = userFollowEntityEntity.getData().getList();
                fanceslist.addAll(requestuserFancseList);
                videoTalkAdapter.notifyDataSetChanged();
                rlvTalkList.refreshComplete();
                page =2;

            }


        }

        @Override
        public void onError(Object object) {

        }
    };





}
