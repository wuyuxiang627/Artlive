package com.connxun.elinetv.view.MediaPreview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.connxun.elinetv.R;
import com.connxun.elinetv.base.ui.BaseFragment;
import com.connxun.elinetv.entity.IMGift;
import com.connxun.elinetv.entity.LiveModel;
import com.connxun.elinetv.entity.live.challenge.GradingResultsEntity;
import com.connxun.elinetv.entity.live.challenge.RescueResultsEntity;
import com.connxun.elinetv.view.LiveBroadcast.LiveRoomUserFragment;
import com.connxun.elinetv.view.MediaPreview.liveplayer.LivePlayerController;
import com.connxun.elinetv.view.MediaPreview.liveplayer.NEVideoView;
import com.connxun.elinetv.view.MediaPreview.liveplayer.PlayerContract;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMember;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018\3\23 0023.
 */

/**
 * 观看管理
 */
public class WatchFragment extends BaseFragment implements PlayerContract.PlayerUi {
    View view;
    Unbinder unbinder;
    @BindView(R.id.video_view_watch)
    NEVideoView videoViewWatch;
    @BindView(R.id.layout_fragment_watch_top_bottom)
    FrameLayout layoutFragmentWatchTopBottom;
    private LIveRoomActivity liveActivity;
    public static String roomId = null;
    /**
     * 直播控制器
     */
    LivePlayerController mediaPlayController;


    private String mVideoPath = "rtmp://vc7a65f99.live.126.net/live/86e0e127e1dc4cd59543f33db5632396"; //文件路径
    private LiveModel mLiveModel; //直播参数
    private LiveRoomUserFragment liveRoomUserFragment;

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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_watch, null);
        unbinder = ButterKnife.bind(this, view);

        //相关参数
        mVideoPath = getActivity().getIntent().getStringExtra("mVideoPath");
        mLiveModel = (LiveModel) getActivity().getIntent().getSerializableExtra("mLiveModel");
        roomId = getActivity().getIntent().getStringExtra("roomId");

        loadFragment();

        //开启观看
        mediaPlayController = new LivePlayerController(getActivity(),
                this, videoViewWatch,
                mVideoPath, true, !true);
        mediaPlayController.initVideo();

        return view;
    }


    private void loadFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        liveRoomUserFragment = new LiveRoomUserFragment();
        transaction.replace(R.id.layout_fragment_watch_top_bottom,liveRoomUserFragment );
        transaction.commit();
        //直播结束
//        liveRoomUserFragment.setPushUserDate();
    }


    @Override
    public void onResume() {
        // 恢复播放
        if (mediaPlayController != null) {
            mediaPlayController.onActivityResume();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        //暂停播放
        if (mediaPlayController != null) {
            mediaPlayController.onActivityPause();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        // 释放资源
        if (mediaPlayController != null) {
            mediaPlayController.onActivityDestroy();
        }
        super.onDestroy();
    }


    @Override
    public void onBufferingUpdate() {
        Logger.e("这是啥");

    }

    @Override
    public void onSeekComplete() {
        Logger.e("这又是是啥");
    }

    /**
     * 显示视频推流结束
     */
    @Override
    public boolean onCompletion() {
        //由于设计了客户端重连机制,故认为源站发送的直播结束信号不可靠.转由云信SDK聊天室的直播状态来判断直播是否结束
        //此处收到直播完成时,进行重启直播处理
//        showLoading(true);
        mediaPlayController.restart();
        return true;
    }

    @Override
    public boolean onError(final String errorInfo) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    liveActivity.onChatRoomFinished(errorInfo);
                }
            });
        }
        return true;
    }

    @Override
    public void setFileName(String name) {

    }

    @Override
    public void showLoading(boolean show) {


    }

    @Override
    public void showAudioAnimate(boolean b) {

    }

    //设置在线人数
    public void setUSerPhotoNumber(List<ChatRoomMember> results){
        liveRoomUserFragment.setUSerPhotoNumber(results);
    }
    //收到礼物
    public void showGift(IMGift imGift){

        liveRoomUserFragment.setIMGift(imGift);
    }

    //显示评分
    public void showGradingView(){

        liveRoomUserFragment.showGradingView(String.valueOf(mLiveModel.getChallengeNo()) );
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //评分结果
    public void showFradingResult(GradingResultsEntity gradingResult) {
        liveRoomUserFragment.showFradingResult(gradingResult);
    }

    //救援结果
    public void showRescueResults(RescueResultsEntity rescueResultsEntity) {
        liveRoomUserFragment.showRescueResults(rescueResultsEntity);
    }
}
