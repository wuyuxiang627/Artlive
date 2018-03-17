package com.connxun.elinetv.view.user.Account;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.connxun.elinetv.R;
import com.connxun.elinetv.adapter.MyAdapter.MyIllegalQueryAdapter;
import com.connxun.elinetv.base.ui.BaseActivity;
import com.connxun.elinetv.entity.Entity;
import com.connxun.elinetv.entity.user.UserIllegalQueryEntity;
import com.connxun.elinetv.presenter.user.UserinfoPresenter;
import com.connxun.elinetv.view.Setting.ComplaintActivity;
import com.connxun.elinetv.view.user.ITestView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;


import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_illegal_query)
public class IllegalQueryActivity extends BaseActivity {
    UserinfoPresenter userinfoPresenter = new UserinfoPresenter(this);
    private List<UserIllegalQueryEntity> illegalQueryList = new ArrayList<>(); //评论总集合
    private List<UserIllegalQueryEntity> requestillegalQueryList = new ArrayList<>(); //请求回来的数据集合
    Entity<UserIllegalQueryEntity> talkEntity;

    @ViewInject(R.id.rl_illegal_query)
    RelativeLayout rl_illegal_query;

    @ViewInject(R.id.rlv_illegalquery)
    XRecyclerView rlvTalkList;

    @ViewInject(R.id.btn_illegalquery_complaint)
    Button btn_illegalquery_complaint;


    int page = 1; //页数
    boolean blType = false;
    String length ="20";


    MyIllegalQueryAdapter myIllegalQueryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        setOnClick();
        setRecyclerView();
        userinfoPresenter.onCreate();
        userinfoPresenter.getAttentionFollowerList(page+"","20");
        userinfoPresenter.attachView(AttentionFollowListView);

    }
    private void setRecyclerView() {
        LinearLayoutManager xLinearLayoutManager = new LinearLayoutManager(this);
        //xLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlvTalkList.setLayoutManager(xLinearLayoutManager);
       rlvTalkList.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设定下拉刷新样式
        rlvTalkList.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);//设定上拉加载样式
        rlvTalkList.setArrowImageView(R.drawable.iconfont_downgrey);     //设定下拉刷新显示图片（不必须）

        //设置adapter
        myIllegalQueryAdapter = new MyIllegalQueryAdapter(IllegalQueryActivity.this, illegalQueryList);
        rlvTalkList.setAdapter(myIllegalQueryAdapter);

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
                userinfoPresenter.getAttentionFollowerList(page + "", "20");
                userinfoPresenter.attachView(AttentionFollowListView);

            }

            ;

            //下拉刷新监听
            @Override
            public void onRefresh() {
                blType = false;
                page = 1;
                userinfoPresenter.onCreate();
                userinfoPresenter.getAttentionFollowerList(page + "", "20");
                userinfoPresenter.attachView(AttentionFollowListView);
            }
        });
    }

    private void initView() {
        rl_illegal_query.setOnClickListener(this);
        btn_illegalquery_complaint.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        Intent intent = null;
        switch (view.getId()){
            case R.id.rl_illegal_query:
                finish();
                break;
            case R.id.  btn_illegalquery_complaint: //我要申诉
                startActivity(new Intent(this, ComplaintActivity.class));
                break;


        }

    }
    //关注列表
    private ITestView AttentionFollowListView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            //显示adapter
            talkEntity = (Entity<UserIllegalQueryEntity>) object;
          if(talkEntity.getData().getList().size() == 0 || talkEntity.getData().getList() == null){
              myIllegalQueryAdapter.notifyDataSetChanged();
                return;
            }

        if(blType){
                //上啦加载
            rlvTalkList.loadMoreComplete();    //加载数据完成（取消加载动画）
                requestillegalQueryList = talkEntity.getData().getList();
                illegalQueryList.addAll(requestillegalQueryList);
            myIllegalQueryAdapter.notifyDataSetChanged();
                page = page + 1;

            }else {
                //下拉刷新
                illegalQueryList.clear();
                requestillegalQueryList = talkEntity.getData().getList();
                illegalQueryList.addAll(requestillegalQueryList);
            myIllegalQueryAdapter.notifyDataSetChanged();
                rlvTalkList.refreshComplete();
                page =2;

            }

        }

        @Override
        public void onError(Object object) {

        }
    };
}
