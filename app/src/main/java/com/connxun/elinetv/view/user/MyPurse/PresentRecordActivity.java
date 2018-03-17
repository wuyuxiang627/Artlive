package com.connxun.elinetv.view.user.MyPurse;

/**
 * Created by Administrator on 2018\3\9 0009.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.connxun.elinetv.R;
import com.connxun.elinetv.adapter.User.PresenteRecordAdapter;
import com.connxun.elinetv.base.ui.BaseActivity;
import com.connxun.elinetv.base.ui.GridRecyclerView;
import com.connxun.elinetv.entity.Entity;
import com.connxun.elinetv.entity.order.WithdrawalsList;
import com.connxun.elinetv.presenter.Order.OrderPresenter;
import com.connxun.elinetv.util.RecyclerViewAnimation;
import com.connxun.elinetv.view.user.ITestView;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 提现记录
 */
@ContentView(R.layout.activity_present_record)
public class PresentRecordActivity extends BaseActivity{
    OrderPresenter orderPresenter = new OrderPresenter(this);

    @ViewInject(R.id.glv_present_record)
    GridRecyclerView glvRecyclerView;

    @ViewInject(R.id.rl_finish)
    RelativeLayout rlFinish;

    @ViewInject(R.id.tv_title)
    TextView tvTitle;

    private List<WithdrawalsList> totalTalkList = new ArrayList<>(); //评论总集合
    private List<WithdrawalsList> requestTalkList = new ArrayList<>(); //请求回来的数据集合

    int page = 1; //页数
    boolean blType = false;
    private PresenteRecordAdapter presenteRecordAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("提现记录");
        setRecyclerView();
        orderPresenter.onCreate();
        orderPresenter.getWithdrawalsList(page+"","20");
        orderPresenter.attachView(getWithdrawalsView);

    }


    private void setRecyclerView() {
        LinearLayoutManager xLinearLayoutManager = new LinearLayoutManager(this);
        //xLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        glvRecyclerView.setLayoutManager(xLinearLayoutManager);
        glvRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设定下拉刷新样式
        glvRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);//设定上拉加载样式
        glvRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);     //设定下拉刷新显示图片（不必须）
        presenteRecordAdapter = new PresenteRecordAdapter(PresentRecordActivity.this,totalTalkList);
        glvRecyclerView.setAdapter(presenteRecordAdapter);

/**
 *设定下拉刷新和上拉加载监听
 */
        glvRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            //上拉加载监听

            @Override
            public void onLoadMore() {
                blType = true;
//                addData();  //上拉加载添加数据
                orderPresenter.onCreate();
                orderPresenter.getWithdrawalsList(page+"","20");
                orderPresenter.attachView(getWithdrawalsView);

            };
            //下拉刷新监听
            @Override
            public void onRefresh() {
                blType = false;
                page = 1;
                orderPresenter.onCreate();
                orderPresenter.getWithdrawalsList(page+"","20");
                orderPresenter.attachView(getWithdrawalsView);
            }
        });

    }


    @Override
    public void setOnClick() {
        super.setOnClick();
        rlFinish.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId())
        {
            case R.id.rl_finish:
                finish();
                break;
        }
    }

    private Entity<WithdrawalsList> withdrawLists;
    //评论列表
    public ITestView getWithdrawalsView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            withdrawLists = (Entity<WithdrawalsList>) object;

            if(withdrawLists.getData().getList().size() == 0 || withdrawLists.getData().getList() == null){
                presenteRecordAdapter.notifyDataSetChanged();
                return;
            }

            if(blType){
                //上啦加载
                glvRecyclerView.loadMoreComplete();    //加载数据完成（取消加载动画）
                requestTalkList = withdrawLists.getData().getList();
                totalTalkList.addAll(requestTalkList);
                presenteRecordAdapter.notifyDataSetChanged();
                page = page + 1;

            }else {
                //下拉刷新
                RecyclerViewAnimation.runLayoutAnimation(glvRecyclerView);
                totalTalkList.clear();
                requestTalkList = withdrawLists.getData().getList();
                totalTalkList.addAll(requestTalkList);
                presenteRecordAdapter.notifyDataSetChanged();
                glvRecyclerView.refreshComplete();
                page =2;

            }


        }

        @Override
        public void onError(Object object) {

        }
    };




}
