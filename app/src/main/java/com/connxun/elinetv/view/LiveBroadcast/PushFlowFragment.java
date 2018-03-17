package com.connxun.elinetv.view.LiveBroadcast;

/**
 * Created by Administrator on 2018\3\10 0010.
 */

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.connxun.elinetv.R;
import com.connxun.elinetv.base.ui.BaseFragment;
import com.connxun.elinetv.util.AnimationUtil;
import com.connxun.elinetv.util.ToastUtils;
import com.connxun.elinetv.view.MediaPreview.fragment.FreeLiveFragment;
import com.netease.vcloud.video.render.NeteaseView;

/**
 * 推流-直播-挑战
 */
public class PushFlowFragment extends BaseFragment implements CapturePreviewContract.CapturePreviewUi{
    View view;

    /**
     * 滤镜模式的SurfaceView
     */
    private NeteaseView filterSurfaceView;

    /**
     * 普通模式的SurfaceView
     */
    private NeteaseView normalSurfaceView;

    /**
     * 控制器
     */
    CapturePreviewController controller;

    //各大板块
    FreeLiveFragment freeLiveFragment;
    ChallengeFragment challengeFragment;

    FrameLayout layoutChallenge;
    FrameLayout layoutLive;





    //底部控制
    RelativeLayout rlLive;
    TextView tvLive;
    View VLive;

    RelativeLayout rlChallenge;
    TextView tvChallenge;
    View VChallenge;






    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_push_flow,null);

        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(view);
        loadFragment(); //加载fragment

        controller = getCaptureController();

        controller.handleIntent(getActivity().getIntent());

    }


    /**
     * 加载布局
     */
    private void loadFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        freeLiveFragment = new FreeLiveFragment();
        transaction.replace(R.id.layout_fragment_live, freeLiveFragment);
        challengeFragment = new ChallengeFragment();
        transaction.replace(R.id.layout_fragment_challenge, challengeFragment);
        transaction.commit();
    }



    private void initView(View view) {
        filterSurfaceView = view.findViewById(R.id.live_videoview);
        normalSurfaceView = view.findViewById(R.id.live_videovie_heng);

        //容器
        layoutChallenge = view.findViewById(R.id.layout_fragment_challenge);
        layoutLive = view.findViewById(R.id.layout_fragment_live);



        //底部控制
        rlLive = view.findViewById(R.id.rl_push_flow_live);
        tvLive = view.findViewById(R.id.tv_push_flow_live);
        VLive = view.findViewById(R.id.view_push_flow_live);

        rlChallenge = view.findViewById(R.id.rl_push_flow_challenge);
        tvChallenge = view.findViewById(R.id.tv_push_flow_challenge);
        VChallenge = view.findViewById(R.id.view_push_flow_challenge);
        setOnClick();
    }


    public void setOnClick() {
        super.setOnClick();
        rlChallenge.setOnClickListener(this);
        rlLive.setOnClickListener(this);


    }

    private int type= 0;

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId())
        {
            case R.id.rl_push_flow_live:
                tvLive.setTextColor(Color.parseColor("#ff7602"));
                tvChallenge.setTextColor(Color.parseColor("#FFFFFF"));
                VChallenge.setVisibility(View.INVISIBLE);
                VLive.setVisibility(View.VISIBLE);
                layoutChallenge.setVisibility(View.GONE);
                layoutLive.setVisibility(View.VISIBLE);
                if(type != 1){
                    layoutChallenge.startAnimation(AnimationUtil.startAnimation(0.0f,1.0f,0.0f,0.0f));
                    layoutLive.startAnimation(AnimationUtil.startAnimation(-1.0f,0.0f,0.0f,0.0f));
                    type = 1;
                }

                break;
            case R.id.rl_push_flow_challenge:
                tvChallenge.setTextColor(Color.parseColor("#ff7602"));
                tvLive.setTextColor(Color.parseColor("#FFFFFF"));
                VLive.setVisibility(View.INVISIBLE);
                VChallenge.setVisibility(View.VISIBLE);
                layoutChallenge.setVisibility(View.VISIBLE);
                layoutLive.setVisibility(View.GONE);

                if(type != 2){
                    layoutChallenge.startAnimation(AnimationUtil.startAnimation(1.0f,0.0f,0.0f,0.0f));
                    layoutLive.startAnimation(AnimationUtil.startAnimation(0.0f,-1.0f,0.0f,0.0f));
                    type = 2;
                }



                break;
        }





    }

    //右进左
    public TranslateAnimation startAnimation(int fromXValue,int toXValue,int fromYValue,int toyValue){
        TranslateAnimation mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(500);
        return mShowAction;
    }

    //左进右
    public TranslateAnimation startAnimation(){
        TranslateAnimation mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(500);
        return mShowAction;
    }


    public TranslateAnimation hindAnimation(int fromXValue){
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f);
        mHiddenAction.setDuration(500);
        return  mHiddenAction;
    }


    public TranslateAnimation hindAnimation(){
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f);
        mHiddenAction.setDuration(500);
        return  mHiddenAction;
    }




    /**
     *  获取Ui对应的controller
     * @return
     */
    private CapturePreviewController getCaptureController() {
        return new CapturePreviewController(getActivity(), this);
    }


    /**
     * 设置直播开始按钮, 是否可点击
     * @param clickable
     */
    @Override
    public void setStartBtnClickable(boolean clickable) {

    }

    @Override
    public void checkInitVisible(PublishParam mPublishParam) {

    }

    @Override
    public void setSurfaceView(boolean hasFilter) {

    }

    @Override
    public void setPreviewSize(boolean hasFilter, int previewWidth, int previewHeight) {

    }

    @Override
    public View getDisplaySurfaceView(boolean hasFilter) {
        if(hasFilter){
            return filterSurfaceView;
        }else{
            return normalSurfaceView;
        }
    }

    /**
     * 正常开始直播
     */
    @Override
    public void onStartLivingFinished() {

    }

    /**
     * 停止直播完成时回调
     */
    @Override
    public void onStopLivingFinished() {

    }

    /**
     * 设置audio按钮状态
     * @param isPlay 是否正在开启
     */
    @Override
    public void setAudioBtnState(boolean isPlay) {

    }

    /**
     * 设置Video按钮状态
     * @param isPlay 是否正在开启
     */
    @Override
    public void setVideoBtnState(boolean isPlay) {

    }

    @Override
    public void setFilterSeekBarVisible(boolean visible) {

    }

    @Override
    public void showAudioAnimate(boolean b) {

    }

    @Override
    public void onDisconnect() {

    }

    @Override
    public void normalFinish() {

    }

    @Override
    public void onStartInit() {

    }

    @Override
    public void onCameraPermissionError() {
        ToastUtils.showLong("无法打开相机\", \"可能没有相关的权限,请开启权限后重试");
    }

    @Override
    public void onAudioPermissionError() {
        ToastUtils.showLong("无法开启录音\", \"可能没有相关的权限,请开启权限后重试");
    }
}
