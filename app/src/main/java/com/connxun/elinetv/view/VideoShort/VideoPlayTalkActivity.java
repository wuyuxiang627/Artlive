package com.connxun.elinetv.view.VideoShort;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.connxun.elinetv.R;
import com.connxun.elinetv.adapter.VideoShortAdapter.VideoTalkAdapter;
import com.connxun.elinetv.base.ui.BaseActivity;
import com.connxun.elinetv.entity.Entity;
import com.connxun.elinetv.entity.Video.Talk;
import com.connxun.elinetv.entity.VideoEtivity;
import com.connxun.elinetv.presenter.video.VideoPresenter;
import com.connxun.elinetv.util.ToastUtils;
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

@ContentView(R.layout.activity_play_video_talk)
public class VideoPlayTalkActivity extends BaseActivity {


    VideoPresenter videoPresenter = new VideoPresenter(this);

    @ViewInject(R.id.real)
    RelativeLayout rlFinish;

    @ViewInject(R.id.playtalk_commentnum)
    TextView tvCommentNum;

    @ViewInject(R.id.recycler_view_test_rv)
    XRecyclerView rlvTalkList;

    @ViewInject(R.id.playvideo_editcomment)
    EditText etCoomment;

    @ViewInject(R.id.playvideo_send)
    Button btnSendTalk;

    private VideoEtivity positionVideoEntity;
    private String videoNo;

    Entity<Talk> talkEntity;
    VideoTalkAdapter videoTalkAdapter;

    private List<Talk> totalTalkList = new ArrayList<>(); //评论总集合
    private List<Talk> requestTalkList = new ArrayList<>(); //请求回来的数据集合

    int page = 1; //页数
    boolean blType = false;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setOnClick();
        setRecyclerView();
        videoNo  = getIntent().getStringExtra("videoNO");
        positionVideoEntity = (VideoEtivity) getIntent().getSerializableExtra("videomodel");
        videoPresenter.onCreate();
        videoPresenter.getContentList(page,"0",videoNo);
        videoPresenter.attachView(getContentListView);

    }

    private void setRecyclerView() {
        LinearLayoutManager xLinearLayoutManager = new LinearLayoutManager(this);
        //xLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlvTalkList.setLayoutManager(xLinearLayoutManager);
        rlvTalkList.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设定下拉刷新样式
        rlvTalkList.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);//设定上拉加载样式
        rlvTalkList.setArrowImageView(R.drawable.iconfont_downgrey);     //设定下拉刷新显示图片（不必须）
        videoTalkAdapter = new VideoTalkAdapter(VideoPlayTalkActivity.this,totalTalkList);
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
                videoPresenter.onCreate();
                videoPresenter.getContentList(page,"0",videoNo);
                videoPresenter.attachView(getContentListView);

            };
            //下拉刷新监听
            @Override
            public void onRefresh() {
                blType = false;
                page = 1;
                videoPresenter.onCreate();
                videoPresenter.getContentList(page,"0",videoNo);
                videoPresenter.attachView(getContentListView);
            }
        });



    }

    private void initView() {
        tvCommentNum = findViewById(R.id.playtalk_commentnum);
        rlvTalkList = findViewById(R.id.recycler_view_test_rv);
        etCoomment = findViewById(R.id.playvideo_editcomment);
        btnSendTalk = findViewById(R.id.playvideo_send);
        rlFinish = findViewById(R.id.real);

    }



    public void setOnClick() {
        btnSendTalk.setOnClickListener(this);
        rlFinish.setOnClickListener(this);

        //评论
        etCoomment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(etCoomment.getText().length() >= 1){
                    btnSendTalk.setBackgroundColor(android.graphics.Color.parseColor("#ff7602"));
                    btnSendTalk.setTextColor(android.graphics.Color.parseColor("#ffffff"));
                }else {
                    btnSendTalk.setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));
                    btnSendTalk.setTextColor(android.graphics.Color.parseColor("#999999"));
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId())
        {
            case R.id.real:
                finish();
                break;
            case R.id.playvideo_send:

                if(etCoomment.getText().length()>= 1){
                    videoPresenter.onCreate();
                    videoPresenter.getContentAdd("0",etCoomment.getText().toString(),positionVideoEntity.getUserNo(),videoNo);
                    videoPresenter.attachView(getContentAddView);
                }

                break;
        }
    }

    public ITestView getContentAddView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            ToastUtils.showLong("评论成功");
            etCoomment.setText("");
//            videoPresenter.onCreate();
//            videoPresenter.getContentList("0",videoNo);
//            videoPresenter.attachView(getContentListView);
        }

        @Override
        public void onError(Object object) {

        }
    };


    //评论列表
    public ITestView getContentListView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            talkEntity = (Entity<Talk>) object;
            tvCommentNum.setText("( "+talkEntity.getData().getTotalRow()+" ) ");

            if(talkEntity.getData().getList().size() == 0 || talkEntity.getData().getList() == null){
                videoTalkAdapter.notifyDataSetChanged();
                return;
            }

            if(blType){
                //上啦加载
                rlvTalkList.loadMoreComplete();    //加载数据完成（取消加载动画）
                requestTalkList = talkEntity.getData().getList();
                totalTalkList.addAll(requestTalkList);
                videoTalkAdapter.notifyDataSetChanged();
                page = page + 1;

            }else {
                //下拉刷新
                runLayoutAnimation(rlvTalkList);
                totalTalkList.clear();
                requestTalkList = talkEntity.getData().getList();
                totalTalkList.addAll(requestTalkList);
                videoTalkAdapter.notifyDataSetChanged();
                rlvTalkList.refreshComplete();
                page =2;

            }


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


//        final Context context = recyclerView.getContext();
//
//        final LayoutAnimationController controller =
//                AnimationUtils.loadLayoutAnimation(context, item.getResourceId());
//
//        recyclerView.setLayoutAnimation(controller);
//        recyclerView.getAdapter().notifyDataSetChanged();
//        recyclerView.scheduleLayoutAnimation();




    }







}
