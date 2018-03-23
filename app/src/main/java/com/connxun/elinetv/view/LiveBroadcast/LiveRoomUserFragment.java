package com.connxun.elinetv.view.LiveBroadcast;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.connxun.elinetv.R;
import com.connxun.elinetv.adapter.Live.Challenge.ChallengradeAdapter;
import com.connxun.elinetv.adapter.Live.CheetahStaffAdapter;
import com.connxun.elinetv.app.BaseApplication;
import com.connxun.elinetv.base.ui.BaseFragment;
import com.connxun.elinetv.base.ui.CustomRoundView;
import com.connxun.elinetv.base.ui.MagicTextView;
import com.connxun.elinetv.entity.IMGift;
import com.connxun.elinetv.entity.Live;
import com.connxun.elinetv.entity.LiveModel;
import com.connxun.elinetv.util.LiveGiftUtil;
import com.connxun.elinetv.util.ToastUtils;
import com.connxun.elinetv.view.MediaPreview.LIveRoomActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMember;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018\3\21 0021.
 */

@SuppressLint("ValidFragment")
public class LiveRoomUserFragment extends BaseFragment {

    View view;

    //挑战顶部
    @BindView(R.id.iv_challenge_top_user_photo)
    SimpleDraweeView ivChallengeTopUserPhoto;
    @BindView(R.id.tv_challenge_top_user_name)
    TextView tvChallengeTopUserName;
    @BindView(R.id.tv_challenge_top_watch_number)
    TextView tvChallengeTopWatchNumber;
    @BindView(R.id.tv_challenge_top_live_number)
    TextView tvChallengeTopLiveNumber;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.ib_challenge_top_watch_comment)
    ImageButton ibChallengeTopWatchComment;
    @BindView(R.id.rl_challenge_top_user)
    RelativeLayout rlChallengeTopUser;
    @BindView(R.id.rl_challenge_top)
    RelativeLayout rlChallengeTop;
    @BindView(R.id.iv_challenge_top_finish)
    ImageButton ivChallengeTopFinish;
    @BindView(R.id.rlv_challenge_top_staff)
    RecyclerView rlvChallengeTopStaff;
    @BindView(R.id.rb_challenge_contribution)
    RadioButton rbChallengeContribution;
    //直播顶部
    @BindView(R.id.iv_media_preview_user_photo)
    SimpleDraweeView ivMediaPreviewUserPhoto;
    @BindView(R.id.tv_media_preview_user_name)
    TextView tvMediaPreviewUserName;
    @BindView(R.id.tv_media_preview_watch_number)
    TextView tvMediaPreviewWatchNumber;
    @BindView(R.id.rl_media_preview_user)
    RelativeLayout rlMediaPreviewUser;
    @BindView(R.id.tv_media_preview_number)
    TextView tvMediaPreviewNumber;
    @BindView(R.id.tv_media_preview_number_number)
    TextView tvMediaPreviewNumberNumber;
    @BindView(R.id.rlv_cheetah_staff)
    RecyclerView rlvCheetahStaff;
    @BindView(R.id.ib_live_media_preview_follow)
    ImageButton ibLiveMediaPreviewFollow;

    //底部
    @BindView(R.id.ib_challenge_bottom_gengduo)
    ImageButton ibChallengeBottomGengduo;
    @BindView(R.id.ib_challenge_bottom_xiaoxi)
    ImageButton ibChallengeBottomXiaoxi;
    @BindView(R.id.ib_challenge_bottom_fenxiang)
    ImageButton ibChallengeBottomFenxiang;
    @BindView(R.id.ib_challenge_bottom_fanzhuan)
    ImageButton ibChallengeBottomFanzhuan;


    //挑战中间
    @BindView(R.id.btn_challenge_meddle_start)
    Button btnChallengeMeddleStart;
    @BindView(R.id.iv_layout_challenge_meddle_ranking_img)
    ImageView ivLayoutChallengeMeddleRankingImg;
    @BindView(R.id.rlv_challenge_meddle_ranking_list_left)
    RecyclerView rlvChallengeMeddleRankingListLeft;
    @BindView(R.id.rlv_challenge_meddle_ranking_list_reight)
    RecyclerView rlvChallengeMeddleRankingListReight;
    @BindView(R.id.tv_layout_challenge_meddle_grading_result_title)
    TextView tvLayoutChallengeMeddleGradingResultTitle;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.rb_challenge_resue_results_title)
    RadioButton rbChallengeResueResultsTitle;
    @BindView(R.id.iv_challenge_resue_results_number_one)
    ImageView ivChallengeResueResultsNumberOne;
    @BindView(R.id.iv_challenge_resue_results_number_two)
    ImageView ivChallengeResueResultsNumberTwo;
    @BindView(R.id.iv_challenge_resue_results_number_three)
    ImageView ivChallengeResueResultsNumberThree;
    @BindView(R.id.ll_challenge_resue_results_number)
    LinearLayout llChallengeResueResultsNumber;
    @BindView(R.id.tv_challenge_jiuyuan_img)
    TextView tvChallengeJiuyuanImg;
    @BindView(R.id.tv_challenge_jiuyuan_text)
    TextView tvChallengeJiuyuanText;
    @BindView(R.id.rl_live_room_user)
    RelativeLayout rlLiveRoomUser;
    @BindView(R.id.rl_challenge_top_layout)
    RelativeLayout rlChallengeTopLayout;
    @BindView(R.id.rl_live_user_info)
    RelativeLayout rlLiveUserInfo;


    Unbinder unbinder;
    PushFlowFragment pushFlowFragment;

    LiveModel PullLiveModel;
    Live PushLiveModel;


    //挑战中间时间
    @BindView(R.id.tv_challenge_top_challenge_timer_start)
    TextView tvChallengeTopChallengeTimerStart;
    @BindView(R.id.iv_challenge_top_challenge_timer)
    ImageView ivChallengeTopChallengeTimer;
    @BindView(R.id.btn_challenge_top_start)
    Button btnChallengeTopStart;
    @BindView(R.id.ll_challenge_top_time)
    LinearLayout llChallengeTopTime;


    //评分时间
    @BindView(R.id.rl_challenge_grade_time)
    RelativeLayout rlChallengeGradeTime;
    @BindView(R.id.tv_challenge_top_challenge_timer)
    TextView tvChallengeTopChallengeTimer;
    @BindView(R.id.llgiftcontent)
    LinearLayout llgiftcontent;
    @BindView(R.id.textView6)
    TextView textView6;
    @BindView(R.id.rcy_challenge_grade_list)
    RecyclerView rcyChallengeGradeList;

    private LIveRoomActivity liveActivity;

    private int minute = 0;//这是分钟
    private int second = 10;//这是分钟后面的秒数。这里是以30分钟为例的，所以，minute是30，second是0
    private int startSecond = 4;
    private Timer timer;
    private TimerTask timerTask;

    private GridLayoutManager gridLayoutManager;
    private static CheetahStaffAdapter cheetahStaffAdapter; //人员集合
    static List<ChatRoomMember> result = new ArrayList<>();

    /**
     * 动画相关
     */
    private LiveGiftUtil liveGiftUtil = new LiveGiftUtil();

    //这是接收回来处理的消息
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    //挑战时间
                    if (minute == 0) {
                        if (second == 0) {
                            //挑战结束
                            tvChallengeTopChallengeTimer.setText("00:00");
                            //打开评分
                            rlChallengeGradeTime.setVisibility(View.VISIBLE);
                            llChallengeTopTime.setVisibility(View.GONE);

                            setGradeListTime();

                            if (timer != null) {
                                timer.cancel();
                                timer = null;
                            }
                            if (timerTask != null) {
                                timerTask = null;
                            }
                        } else {
                            second--;
                            if (second >= 10) {
                                tvChallengeTopChallengeTimer.setText("0" + minute + ":" + second);
                            } else {
                                tvChallengeTopChallengeTimer.setText("0" + minute + ":0" + second);
                            }
                        }
                    } else {
                        if (second == 0) {
                            second = 59;
                            minute--;
                            if (minute >= 10) {
                                tvChallengeTopChallengeTimer.setText(minute + ":" + second);
                            } else {
                                tvChallengeTopChallengeTimer.setText("0" + minute + ":" + second);
                            }
                        } else {
                            second--;
                            if (second >= 10) {
                                if (minute >= 10) {
                                    tvChallengeTopChallengeTimer.setText(minute + ":" + second);
                                } else {
                                    tvChallengeTopChallengeTimer.setText("0" + minute + ":" + second);
                                }
                            } else {
                                if (minute >= 10) {
                                    tvChallengeTopChallengeTimer.setText(minute + ":0" + second);
                                } else {
                                    tvChallengeTopChallengeTimer.setText("0" + minute + ":0" + second);
                                }
                            }
                        }
                    }


                    break;
                case 1:
                    //开始挑战
                    startSecond--;
                    btnChallengeTopStart.setText(startSecond + "");
                    if (startSecond == 0) {
                        if (timer != null) {
                            timer.cancel();
                            timer = null;
                        }
                        if (timerTask != null) {
                            timerTask = null;
                        }
                        llChallengeTopTime.setVisibility(View.VISIBLE);
                        tvChallengeTopChallengeTimerStart.setVisibility(View.VISIBLE);
                        ivChallengeTopChallengeTimer.setVisibility(View.VISIBLE);
                        tvChallengeTopChallengeTimer.setVisibility(View.VISIBLE);
                        btnChallengeMeddleStart.setVisibility(View.GONE);
                        btnChallengeTopStart.setVisibility(View.GONE);
                        //开始挑战倒计时
                        challengeStart();
                    }
                    break;
            }


        }

    };


    @SuppressLint("ValidFragment")
    public LiveRoomUserFragment(PushFlowFragment pushFlowFragment) {
        this.pushFlowFragment = pushFlowFragment;

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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_live_room_user, null);
        unbinder = ButterKnife.bind(this, view);
        PullLiveModel = (LiveModel) getActivity().getIntent().getSerializableExtra("mLiveModel"); //观看直播中的数据实体类
        PushLiveModel = (Live) getActivity().getIntent().getSerializableExtra("liveMOdel"); //自己直播中的数据实体类
//        setPushUserDate();
        return view;
    }

    //主播方资料
    public void setPushUserDate() {
        liveGiftUtil.clearTiming(llgiftcontent,getActivity());
        //直播
        if (PushLiveModel != null) {
            tvChallengeTopUserName.setText(BaseApplication.getUserSp().getString("nickName", "0"));
            tvMediaPreviewUserName.setText(BaseApplication.getUserSp().getString("nickName", "0"));
            String avatar = BaseApplication.getUserSp().getString("avatar", null);
            if (avatar != null) {
                //创建将要下载的图片的URI
                Uri imageUri = Uri.parse(avatar);
                //开始下载
                ivMediaPreviewUserPhoto.setImageURI(imageUri);
                ivChallengeTopUserPhoto.setImageURI(imageUri);
            }
            tvMediaPreviewNumberNumber.setText(PushLiveModel.getLiveId());
            tvChallengeTopLiveNumber.setText("直播间号: " + PushLiveModel.getLiveId());
            ibLiveMediaPreviewFollow.setVisibility(View.INVISIBLE);
            //显示布局
            ToastUtils.showLong("LiveOrChallenge: " + BaseApplication.blLiveTypeLiveOrChallenge);
            if (BaseApplication.blLiveTypeLiveOrChallenge) {
                rlLiveUserInfo.setVisibility(View.VISIBLE);
                rlChallengeTopLayout.setVisibility(View.GONE);
                setLiaotianshiView(220, 220);
                liveActivity.setLiaotianshiFragmentWH(220, 220);
            } else {
                rlLiveUserInfo.setVisibility(View.GONE);
                rlChallengeTopLayout.setVisibility(View.VISIBLE);
                setLiaotianshiView(160, 160);
                liveActivity.setLiaotianshiFragmentWH(160, 160);

            }
            //设置在线人数
            setChatRoom();

        }
        //挑战

    }

    //开始挑战-倒计时
    public void challengeStart() {
        //挑战时间
        tvChallengeTopChallengeTimer.setText(minute + ":" + second);

        timerTask = new TimerTask() {

            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 0;
                handler.sendMessage(msg);
            }
        };

        timer = new Timer();
        timer.schedule(timerTask, 0, 1000);
    }


    @Override
    public void onDestroy() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask = null;
        }
        minute = -1;
        second = -1;
        super.onDestroy();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ib_challenge_bottom_gengduo,
            R.id.ib_challenge_bottom_xiaoxi,
            R.id.ib_challenge_bottom_fenxiang,
            R.id.ib_challenge_bottom_fanzhuan,
            R.id.btn_challenge_meddle_start})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_challenge_bottom_gengduo:
                break;
            case R.id.ib_challenge_bottom_xiaoxi:
                pushFlowFragment.showInputPanel();
                break;
            case R.id.ib_challenge_bottom_fenxiang:
                break;
            case R.id.ib_challenge_bottom_fanzhuan:
                break;
            case R.id.btn_challenge_meddle_start:
                llChallengeTopTime.setVisibility(View.VISIBLE);
                tvChallengeTopChallengeTimerStart.setVisibility(View.GONE);
                ivChallengeTopChallengeTimer.setVisibility(View.GONE);
                tvChallengeTopChallengeTimer.setVisibility(View.GONE);
                final int time = 3;
                //开始挑战
                timerTask = new TimerTask() {

                    @Override
                    public void run() {

//
                        Message msg = new Message();
                        msg.what = 1;
                        handler.sendMessage(msg);
                    }
                };

                timer = new Timer();
                timer.schedule(timerTask, 0, 1000);

                break;
        }
    }


    public void setLiaotianshiView(float width, float height) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rlLiveRoomUser.getLayoutParams();
//        params.width = dip2px(getActivity(), width);
        params.height = dip2px(getActivity(), height);
        rlLiveRoomUser.setLayoutParams(params);
    }


    /**
     * dp转为px
     *
     * @param context  上下文
     * @param dipValue dp值
     * @return
     */
    private int dip2px(Context context, float dipValue) {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dipValue, r.getDisplayMetrics());
    }

    //设置评分集合
    private void setGradeListTime() {
        List<Integer> integers = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            integers.add(i);
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 5);
        rcyChallengeGradeList.setLayoutManager(gridLayoutManager);
        ChallengradeAdapter challengradeAdapter = new ChallengradeAdapter(R.layout.item_challenge_grade_time, integers);
        rcyChallengeGradeList.setAdapter(challengradeAdapter);
    }


    private void setChatRoom() {
        gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        rlvCheetahStaff.setLayoutManager(gridLayoutManager);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        rlvChallengeTopStaff.setLayoutManager(gridLayoutManager);
        cheetahStaffAdapter = new CheetahStaffAdapter(getActivity(), result);
        rlvCheetahStaff.setAdapter(cheetahStaffAdapter);
        rlvChallengeTopStaff.setAdapter(cheetahStaffAdapter);
    }


    public void setUSerPhotoNumber(List<ChatRoomMember> results) {
        result.clear();
        result.addAll(results);
        if (cheetahStaffAdapter != null) {
            cheetahStaffAdapter.notifyDataSetChanged();
        }
        //tv_challenge_top_watch_number
        if (tvChallengeTopWatchNumber != null) {
            tvChallengeTopWatchNumber.setText(results.size() + "人在线");
        }
        if (tvMediaPreviewWatchNumber != null) {
            tvMediaPreviewWatchNumber.setText(results.size() + "人在线");
        }


    }

    public void setIMGift(IMGift imGift){
        liveGiftUtil.showGift(imGift,llgiftcontent,getActivity());
    }
}
