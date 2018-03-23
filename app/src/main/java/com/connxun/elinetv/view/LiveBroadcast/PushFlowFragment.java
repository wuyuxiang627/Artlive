package com.connxun.elinetv.view.LiveBroadcast;

/**
 * Created by Administrator on 2018\3\10 0010.
 */

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.connxun.elinetv.R;
import com.connxun.elinetv.app.BaseApplication;
import com.connxun.elinetv.base.ui.BaseFragment;
import com.connxun.elinetv.util.AnimationUtil;
import com.connxun.elinetv.util.ToastUtils;
import com.connxun.elinetv.view.LiveBroadcast.Challenge.ChallengeFragment;
import com.connxun.elinetv.view.LiveBroadcast.Iinterface.LiveOperatingState;
import com.connxun.elinetv.view.MediaPreview.LIveRoomActivity;
import com.connxun.elinetv.view.MediaPreview.fragment.FreeLiveFragment;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMember;
import com.netease.vcloud.video.render.NeteaseView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 推流-直播-挑战
 */
public class PushFlowFragment extends BaseFragment implements CapturePreviewContract.CapturePreviewUi, LiveOperatingState {
    View view;
    @BindView(R.id.live_videoview)
    NeteaseView liveVideoview;
    @BindView(R.id.live_videovie_heng)
    NeteaseView liveVideovieHeng;

    @BindView(R.id.iv_live_address)
    ImageView ivLiveAddress;
    @BindView(R.id.tv_live_address)
    TextView tvLiveAddress;
    @BindView(R.id.iv_live_finish)
    ImageView ivLiveFinish;
    @BindView(R.id.iv_live_camera_conversion)
    ImageView ivLiveCameraConversion;
    @BindView(R.id.iv_live_window)
    ImageView ivLiveWindow;
    @BindView(R.id.rl_push_flow_top)
    RelativeLayout rlPushFlowTop;
    @BindView(R.id.layout_fragment_challenge)
    FrameLayout layoutFragmentChallenge;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.tv_push_flow_live)
    TextView tvPushFlowLive;
    @BindView(R.id.view_push_flow_live)
    View viewPushFlowLive;
    @BindView(R.id.rl_push_flow_live)
    RelativeLayout rlPushFlowLive;
    @BindView(R.id.tv_push_flow_challenge)
    TextView tvPushFlowChallenge;
    @BindView(R.id.view_push_flow_challenge)
    View viewPushFlowChallenge;
    @BindView(R.id.rl_push_flow_challenge)
    RelativeLayout rlPushFlowChallenge;
    @BindView(R.id.rl_push_flow_type_bottom)
    RelativeLayout rlPushFlowTypeBottom;

    @BindView(R.id.layout_fragment_challenge_start)
    FrameLayout layoutFragmentChallengeStart;
    @BindView(R.id.layout_fragment_live)
    FrameLayout layoutFragmentLive;

    @BindView(R.id.layout_fragment_live_challenge_top_bottom)
    FrameLayout layoutFragmentLiveChallengeTopBottom;

    Unbinder unbinder;
    /**
     * 控制器
     */
    CapturePreviewController controller;

    //各大板块
    FreeLiveFragment freeLiveFragment;
    ChallengeFragment challengeFragment;
    LiveRoomUserFragment liveRoomUserFragment;

    private LIveRoomActivity liveActivity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_push_flow, null);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        liveActivity = (LIveRoomActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        liveActivity = null;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        initView(view);
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
        freeLiveFragment = new FreeLiveFragment(this);
        transaction.replace(R.id.layout_fragment_live, freeLiveFragment);
        challengeFragment = new ChallengeFragment(this);
        transaction.replace(R.id.layout_fragment_challenge, challengeFragment);
        liveRoomUserFragment = new LiveRoomUserFragment(this);
        transaction.replace(R.id.layout_fragment_live_challenge_top_bottom, liveRoomUserFragment);
        transaction.commit();
    }

    private int type = 0;

    /**
     * 获取Ui对应的controller
     *
     * @return
     */
    private CapturePreviewController getCaptureController() {
        return new CapturePreviewController(getActivity(), this);
    }


    /**
     * 设置直播开始按钮, 是否可点击
     *
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
        if (hasFilter) {
            return liveVideoview;
        } else {
            return liveVideovieHeng;
        }
    }

    /**
     * 正常开始直播
     */
    @Override
    public void onStartLivingFinished() {


        liveRoomUserFragment.setPushUserDate();
        rlPushFlowTypeBottom.setVisibility(View.GONE);
        rlPushFlowTop.setVisibility(View.GONE);
        layoutFragmentLive.setVisibility(View.GONE);
        layoutFragmentChallenge.setVisibility(View.GONE);
        layoutFragmentLiveChallengeTopBottom.setVisibility(View.VISIBLE);
        if(BaseApplication.blLiveTypeLiveOrChallenge){
            layoutFragmentLive.startAnimation(AnimationUtil.startAnimation(0.0f, -1.0f, 0.0f, 0.0f));
        }else {
            layoutFragmentChallenge.startAnimation(AnimationUtil.startAnimation(0.0f, -1.0f, 0.0f, 0.0f));
        }
        layoutFragmentLiveChallengeTopBottom.startAnimation(AnimationUtil.startAnimation(1.0f, 0.0f, 0.0f, 0.0f));



        if (liveActivity != null) {
            liveActivity.onStartLivingFinished();
        }
        DialogMaker.dismissProgressDialog();
    }

    /**
     * 停止直播完成时回调
     */
    @Override
    public void onStopLivingFinished() {

    }

    /**
     * 设置audio按钮状态
     *
     * @param isPlay 是否正在开启
     */
    @Override
    public void setAudioBtnState(boolean isPlay) {

    }

    /**
     * 设置Video按钮状态
     *
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.rl_push_flow_live, R.id.rl_push_flow_challenge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_push_flow_live: //直播
                if(BaseApplication.blLIveStart){
                    ToastUtils.showLong("直播开启中请稍后....");
                    return;
                }
                tvPushFlowLive.setTextColor(Color.parseColor("#ff7602"));
                tvPushFlowChallenge.setTextColor(Color.parseColor("#FFFFFF"));

                viewPushFlowChallenge.setVisibility(View.INVISIBLE);
                viewPushFlowLive.setVisibility(View.VISIBLE);

                layoutFragmentChallenge.setVisibility(View.GONE);
                layoutFragmentLive.setVisibility(View.VISIBLE);

                if (type != 1) {
                    BaseApplication.blLiveTypeLiveOrChallenge = true;
                    layoutFragmentChallenge.startAnimation(AnimationUtil.startAnimation(0.0f, 1.0f, 0.0f, 0.0f));
                    layoutFragmentLive.startAnimation(AnimationUtil.startAnimation(-1.0f, 0.0f, 0.0f, 0.0f));
                    type = 1;
                }
                break;
            case R.id.rl_push_flow_challenge: //挑战
                if(BaseApplication.blLIveStart){
                    ToastUtils.showLong("直播开启中请稍后....");
                    return;
                }
                tvPushFlowChallenge.setTextColor(Color.parseColor("#ff7602"));
                tvPushFlowLive.setTextColor(Color.parseColor("#FFFFFF"));

                viewPushFlowLive.setVisibility(View.INVISIBLE);
                viewPushFlowChallenge.setVisibility(View.VISIBLE);

                layoutFragmentChallenge.setVisibility(View.VISIBLE);
                layoutFragmentLive.setVisibility(View.GONE);

                if (type != 2) {
                    BaseApplication.blLiveTypeLiveOrChallenge = false;
                    challengeFragment.setvisibility();
                    layoutFragmentChallenge.startAnimation(AnimationUtil.startAnimation(1.0f, 0.0f, 0.0f, 0.0f));
                    layoutFragmentLive.startAnimation(AnimationUtil.startAnimation(0.0f, -1.0f, 0.0f, 0.0f));
                    type = 2;
                }

                break;
        }
    }


    /**
     * =====直播参数状态操控======
     */

    /**
     * 直播开始或停止
     */
    @Override
    public void contrellerLiveStartStop() {
        controller.liveStartStop();
    }

    @Override
    public void showInputPanel() {
        liveActivity.showInputPanel();

    }

    public void setUSerPhotoNumber(List<ChatRoomMember> results){
        liveRoomUserFragment.setUSerPhotoNumber(results);
    }


}
