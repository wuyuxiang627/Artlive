package com.connxun.elinetv.view.LiveBroadcast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.connxun.elinetv.R;
import com.connxun.elinetv.adapter.Live.Challenge.ChallengeGiftNumAdapter;
import com.connxun.elinetv.adapter.Live.Challenge.ChallengeRankAdapter;
import com.connxun.elinetv.adapter.Live.Challenge.ChallengradeAdapter;
import com.connxun.elinetv.adapter.Live.CheetahStaffAdapter;
import com.connxun.elinetv.app.BaseApplication;
import com.connxun.elinetv.base.ui.BaseFragment;
import com.connxun.elinetv.entity.ChallengeLove;
import com.connxun.elinetv.entity.Entity;
import com.connxun.elinetv.entity.EntityObject;
import com.connxun.elinetv.entity.Gift;
import com.connxun.elinetv.entity.IMGift;
import com.connxun.elinetv.entity.JsonEntity;
import com.connxun.elinetv.entity.Live;
import com.connxun.elinetv.entity.LiveModel;
import com.connxun.elinetv.entity.live.ChallengeStratEntity;
import com.connxun.elinetv.entity.live.challenge.ChallengeModelEntity;
import com.connxun.elinetv.entity.live.challenge.GradingResultsEntity;
import com.connxun.elinetv.entity.live.challenge.RankEntity;
import com.connxun.elinetv.entity.live.challenge.RescueEntity;
import com.connxun.elinetv.entity.live.challenge.RescueResultsEntity;
import com.connxun.elinetv.entity.live.challenge.challengeLikeEntity;
import com.connxun.elinetv.entity.order.UserVC;
import com.connxun.elinetv.presenter.Gift.GiftPresenter;
import com.connxun.elinetv.presenter.Live.ChallengeTypePresenter;
import com.connxun.elinetv.presenter.Live.LivePresenter;
import com.connxun.elinetv.util.AnimationUtil;
import com.connxun.elinetv.util.LiveGiftUtil;
import com.connxun.elinetv.util.ToastUtils;
import com.connxun.elinetv.view.MediaPreview.LIveRoomActivity;
import com.connxun.elinetv.view.MediaPreview.WatchFragment;
import com.connxun.elinetv.view.user.ITestView;
import com.connxun.elinetv.view.user.MyPurse.MyPurseActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMember;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
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
    @BindView(R.id.ib_challenge_bottom_xiaoxi)
    ImageButton ibChallengeBottomXiaoxi;
    @BindView(R.id.ib_challenge_bottom_fenxiang)
    ImageButton ibChallengeBottomFenxiang;
    @BindView(R.id.ib_challenge_bottom_fanzhuan)
    ImageButton ibChallengeBottomFanzhuan;


    //挑战中间
    @BindView(R.id.iv_layout_challenge_meddle_ranking_img)
    ImageView ivLayoutChallengeMeddleRankingImg;
    @BindView(R.id.rlv_challenge_meddle_ranking_list_left)
    RecyclerView rlvChallengeMeddleRankingListLeft;
    @BindView(R.id.rlv_challenge_meddle_ranking_list_reight)
    RecyclerView rlvChallengeMeddleRankingListReight;
    @BindView(R.id.tv_layout_challenge_meddle_grading_result_title)
    TextView tvLayoutChallengeMeddleGradingResultTitle;
    @BindView(R.id.iv_challenge_grading_result_suc_err)
    ImageView ivChallengeFradingResultSucErr;
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
    WatchFragment watchFragment;

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
    @BindView(R.id.ll_challenge_meddle)
    LinearLayout llChallengeMeddle;
    @BindView(R.id.tv_challenge_meddle_challenge_name)
    TextView tvChallengeMeddleChallengeName;
    @BindView(R.id.iv_challenge_top_user_photo)
    SimpleDraweeView ivChallengeTopUserPhoto;
    @BindView(R.id.iv_media_preview_user_photo)
    SimpleDraweeView ivMediaPreviewUserPhoto;
    @BindView(R.id.btn_challenge_meddle_start)
    Button btnChallengeMeddleStart;
    @BindView(R.id.rl_grading_results)
    RelativeLayout rlGradingResults;
    @BindView(R.id.ib_challenge_bottom_gengduo)
    ImageButton ibChallengeBottomGengduo;
    @BindView(R.id.tv_challenge_grading_results_average)
    TextView tvChallengeGradingResultsAverage;
    @BindView(R.id.tv_challenge_grading_results_live)
    TextView tvChallengeGradingResultsLive;
    @BindView(R.id.tv_challenge_grading_results_gife)
    TextView tvChallengeGradingResultsGife;
    @BindView(R.id.iv_challenge_grading_result_img)
    ImageView ivChallengeGradingResultImg;
    @BindView(R.id.btn_challenge_grading_result)
    Button btnChallengeGradingResult;
    @BindView(R.id.rl_challenge_recue_results)
    RelativeLayout rlChallengeRecueResults;
    @BindView(R.id.btn_challenge_rescue_reults_ok)
    Button btnChallengeRescueReultsOk;
    @BindView(R.id.rl_challenge_ranking_list)
    RelativeLayout rlChallengeRankingList;
    @BindView(R.id.tv_challenge_rank_contribution_num)
    TextView tvChallengeRankContributionNum;
    @BindView(R.id.rl_challenge_rescue_send)
    RelativeLayout rlChallengeRescueSend;
    @BindView(R.id.tv_challenge_rescue_send_time)
    TextView tvChallengeRescueSendTime;
    @BindView(R.id.btn_challenge_recue_send)
    Button btnChallengeRecueSend;
    @BindView(R.id.tv_challenge_top_like)
    TextView tvChallengeTopLike;
    @BindView(R.id.tv_challenge_top_gift)
    TextView tvChallengeTopGift;
    @BindView(R.id.ib_challenge_bottom_gift)
    ImageButton ibChallengeBottomGift;
    @BindView(R.id.rv_challenge_gift_list)
    RecyclerView rvChallengeGiftList;
    @BindView(R.id.rl_challenge_gift)
    LinearLayout rlChallengeGift;
    @BindView(R.id.iv_media_preview_finish)
    ImageButton ivMediaPreviewFinish;

    private LIveRoomActivity liveActivity;

    private int minute = 20;//这是分钟
    private int second = 0;//这是分钟后面的秒数。这里是以30分钟为例的，所以，minute是30，second是0
    private int startSecond = 4;
    private Timer timer;
    private TimerTask timerTask;


    private GridLayoutManager gridLayoutManager;
    private static CheetahStaffAdapter cheetahStaffAdapter; //人员集合
    static List<ChatRoomMember> result = new ArrayList<>();
    private ChallengeTypePresenter challengeTypePresenter = new ChallengeTypePresenter(getActivity());
    private GiftPresenter giftPresenter = new GiftPresenter(getActivity());

    private List<RankEntity.Data.Rank> rankList = new ArrayList<>(); //富豪榜集合
    private List<RankEntity.Data.Rank> leftRankList = new ArrayList<>(); //左边
    private List<RankEntity.Data.Rank> reightRankList = new ArrayList<>(); //右边

    private Entity<Gift> giftEntity;
    private EntityObject<ChallengeStratEntity> challengeStratEntityEntity;

    private long Warmtime = 20000; //暖场时间
    private long showTime; //挑战时间

    //送救援票
    TimeCounts time; //倒计时;

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

                            tvChallengeTopChallengeTimer.setText("00:00");

//                            switch (msg.arg1)
//                            {
//                                case 1://暖场
//                                    tvChallengeTopChallengeTimerStart.setText("挑战中..");
//                                    btnChallengeMeddleStart.setVisibility(View.GONE);
//                                    btnChallengeTopStart.setVisibility(View.VISIBLE);
//                                    if (timer != null) {
//                                        timer.cancel();
//                                        timer = null;
//                                    }
//                                    if (timerTask != null) {
//                                        timerTask = null;
//                                    }
//                                    challengeTypePresenter.onCreate();
//                                    challengeTypePresenter.getChallengeStartChallenge(resourceNo);
//                                    challengeTypePresenter.attachView(startChallengeView);
//                                    break;
//                                case 2: //挑战

                            //挑战结束

                            //打开评分
//                            rlChallengeGradeTime.setVisibility(View.VISIBLE);
//                            llChallengeTopTime.setVisibility(View.GONE);
//                            setGradeListTime();
                            ivChallengeTopChallengeTimer.setVisibility(View.GONE);
                            tvChallengeTopChallengeTimer.setVisibility(View.GONE);
                            tvChallengeTopChallengeTimerStart.setText("等待评分结果中...");
                            tvChallengeTopChallengeTimerStart.setTextColor(Color.parseColor("#DA3748"));
//                                    if (timer != null) {
//                                        timer.cancel();
//                                        timer = null;
//                                    }
//                                    if (timerTask != null) {
//                                        timerTask = null;
//                                    }

//                                    break;
//                            }

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
                        change((int) showTime);
                        challengeStart();
                    }
                    break;
            }
        }

    };
    private boolean isAudience;
    private String resourceNo;
    private LivePresenter livePresenter = new LivePresenter(getActivity());


    @SuppressLint("ValidFragment")
    public LiveRoomUserFragment(PushFlowFragment pushFlowFragment) {
        this.pushFlowFragment = pushFlowFragment;
    }

    public LiveRoomUserFragment() {
//        this.watchFragment = watchFragment;
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
        isAudience = getActivity().getIntent().getBooleanExtra("is_audience", true);
        PullLiveModel = (LiveModel) getActivity().getIntent().getSerializableExtra("mLiveModel"); //观看直播中的数据实体类
        PushLiveModel = (Live) getActivity().getIntent().getSerializableExtra("liveMOdel"); //自己直播中的数据实体类
        setPushUserDate();
        return view;
    }

    //主播方资料
    public void setPushUserDate() {
        liveGiftUtil.clearTiming(llgiftcontent, getActivity());
        //直播

        if (isAudience) {
            //观众

            //挑战还是直播
            if (!BaseApplication.blLiveTypeLiveOrChallenge) {
                llChallengeMeddle.setVisibility(View.VISIBLE);
                rlChallengeRankingList.setVisibility(View.VISIBLE);
                challengeTypePresenter.onCreate();
                challengeTypePresenter.getChallengeVIew(PullLiveModel.getChallengeNo() + "");
                challengeTypePresenter.attachView(challengeView);

            } else {

            }
            setUserDateWatch();


        } else {
            //挑战还是直播
            if (!BaseApplication.blLiveTypeLiveOrChallenge) {
                llChallengeMeddle.setVisibility(View.VISIBLE);
                rlChallengeRankingList.setVisibility(View.VISIBLE);
            } else {

            }
            //主播
            setUserDateCapture();
        }

        //设置聊天室高度
        if (BaseApplication.blLiveTypeLiveOrChallenge) {
            //ib_challenge_bottom_gift
            ibChallengeBottomGift.setVisibility(View.GONE);
            rlLiveUserInfo.setVisibility(View.VISIBLE);
            rlChallengeTopLayout.setVisibility(View.GONE);
            setLiaotianshiView(220, 220);
            liveActivity.setLiaotianshiFragmentWH(220, 220);
        } else {
            ibChallengeBottomGift.setVisibility(View.VISIBLE);
            rlLiveUserInfo.setVisibility(View.GONE);
            rlChallengeTopLayout.setVisibility(View.VISIBLE);
            setLiaotianshiView(160, 160);
            liveActivity.setLiaotianshiFragmentWH(160, 160);
        }

//        设置在线人数
        setChatRoom();
    }


    //观众
    public void setUserDateWatch() {
        if (PullLiveModel != null) {
            tvChallengeTopUserName.setText(PullLiveModel.getNickName());
            tvMediaPreviewUserName.setText(PullLiveModel.getNickName());
            tvMediaPreviewNumberNumber.setText(PullLiveModel.getLiveId());
            tvChallengeTopLiveNumber.setText("直播间号: " + PullLiveModel.getLiveId());

            String userAcatar = PullLiveModel.getAvatar();
            if (userAcatar != null) {
                Uri uri = Uri.parse(userAcatar);
                ivMediaPreviewUserPhoto.setImageURI(uri);
                ivChallengeTopUserPhoto.setImageURI(uri);
            } else {
                ivChallengeTopUserPhoto.setBackgroundResource(R.drawable.iocn_login_logo);
                ivMediaPreviewUserPhoto.setBackgroundResource(R.drawable.iocn_login_logo);
            }

            //不开放的按钮
            //底部
            ibChallengeBottomFanzhuan.setVisibility(View.GONE);

            //中间-挑战
            btnChallengeMeddleStart.setVisibility(View.GONE);
        }
    }

    //主播
    public void setUserDateCapture() {
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
        }
    }


    //开始挑战-倒计时
    public void challengeStart() {
        //挑战时间
        tvChallengeTopChallengeTimer.setText(minute + ":" + second);
        btnChallengeTopStart.setVisibility(View.GONE);

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

    @OnClick({R.id.iv_media_preview_user_photo,
            R.id.iv_challenge_top_finish,
            R.id.iv_media_preview_finish,
            R.id.iv_challenge_top_user_photo,
            R.id.ib_challenge_bottom_gengduo,
            R.id.ib_challenge_bottom_xiaoxi,
            R.id.ib_challenge_bottom_fenxiang,
            R.id.ib_challenge_bottom_fanzhuan,
            R.id.btn_challenge_meddle_start, R.id.tv_challenge_top_like})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_media_preview_user_photo: //头像
            case R.id.iv_challenge_top_user_photo:
                if (liveActivity.isAudience) {
                    if (PushLiveModel != null) {
                        liveActivity.setView(PushLiveModel.getUserNo());
                    }
                    if (PullLiveModel != null) {
                        liveActivity.setView(PullLiveModel.getUserNo());
                    }
                } else {
                    ToastUtils.showLong("自己的资料去个人中心查看哟!!");
                }
                break;
            case R.id.iv_challenge_top_finish: //点击退出
                livePresenter.onCreate();
                if (PushLiveModel != null) {
                    try {
                        livePresenter.getLiveEndLive(PushLiveModel.getLiveNo() + "");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    livePresenter.attachView(finishLive);
                }
                break;
            case R.id.iv_media_preview_finish:
                livePresenter.onCreate();
                if (PushLiveModel != null) {
                    try {
                        livePresenter.getLiveEndLive(PushLiveModel.getLiveNo() + "");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    livePresenter.attachView(finishLive);
                }
                break;
            case R.id.ib_challenge_bottom_gengduo:
                break;
            case R.id.ib_challenge_bottom_xiaoxi:
                liveActivity.showInputPanel();
                break;
            case R.id.ib_challenge_bottom_fenxiang:
                break;
            case R.id.ib_challenge_bottom_fanzhuan:
                break;
            case R.id.btn_challenge_meddle_start:
                //开始挑战
                challengeTypePresenter.onCreate();
                challengeTypePresenter.getChallengeStartChallenge(resourceNo);
                challengeTypePresenter.attachView(startChallengeView);


                break;
            case R.id.tv_challenge_top_like:
                //点赞
                if (liveActivity.isAudience) {
                    challengeTypePresenter.onCreate();
                    challengeTypePresenter.getChallengeLove(PullLiveModel.getChallengeNo() + "");
                    challengeTypePresenter.attachView(challengeLoveView);
                } else {
                    ToastUtils.showLong("自己不能给自己点赞哟");
                }

                break;
        }
    }

    //点赞

    public ITestView challengeLoveView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            challengeLikeEntity challengeLikeEntity = (com.connxun.elinetv.entity.live.challenge.challengeLikeEntity) object;
            Drawable nav_up_xin = getResources().getDrawable(R.drawable.icon_challenge_like_suc);
            nav_up_xin.setBounds(0, 0, nav_up_xin.getMinimumWidth(), nav_up_xin.getMinimumHeight());
            tvChallengeTopLike.setCompoundDrawables(null, nav_up_xin, null, null);
            tvChallengeTopLike.setText(challengeLikeEntity.getData().getLoveNum() + "");
            tvChallengeTopLike.setEnabled(false);
//            tvChallengeTopLike.setBackgroundResource(R.drawable.icon_challenge_like_suc);
        }

        @Override
        public void onError(Object object) {

        }
    };


    //开始挑战
    public ITestView startChallengeView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            //开始挑战
            startChallenge();

        }

        @Override
        public void onError(Object object) {

        }
    };

    //开始挑战
    public void startChallenge() {
        llChallengeTopTime.setVisibility(View.VISIBLE);
        tvChallengeTopChallengeTimerStart.setVisibility(View.GONE);
        ivChallengeTopChallengeTimer.setVisibility(View.GONE);
        tvChallengeTopChallengeTimer.setVisibility(View.GONE);
        btnChallengeTopStart.setVisibility(View.VISIBLE);
//        final int time = 3;
        //开始挑战
        timerTask = new TimerTask() {

            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        };

        timer = new Timer();
        timer.schedule(timerTask, 0, 1000);
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
        //点击评分
        challengradeAdapter.setOnItemClickListener((adapter, view, position) -> {
            challengeTypePresenter.onCreate();
            challengeTypePresenter.getChallengeScore(String.valueOf(integers.get(position)), resourceNo);
            challengeTypePresenter.attachView(challengeScoreVIew);
        });
    }

    //点击评分-发送评分结果
    public ITestView challengeScoreVIew = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            rlChallengeGradeTime.setVisibility(View.GONE);
        }

        @Override
        public void onError(Object object) {
            String toastStr = (String) object;
            ToastUtils.showLong(toastStr);

        }
    };


    //设置在线人数的adapter
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

    //设置在线人数
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

    //设置礼物动画
    public void setIMGift(IMGift imGift) {
        if (llgiftcontent != null) {
            liveGiftUtil.showGift(imGift, llgiftcontent, getActivity());
        }
//
    }

    //开始评分
    public void showGradingView(String challengeNo) {
        this.resourceNo = challengeNo;
        rlChallengeRankingList.setVisibility(View.GONE);
        rlChallengeGradeTime.setVisibility(View.GONE); //评分结果
        rlChallengeGradeTime.setVisibility(View.VISIBLE);
        boolean blshow = true;
        if (blshow) {
            rlChallengeGradeTime.startAnimation(AnimationUtil.startAnimation(1.0f, 0.0f, 0.0f, 0.0f));
            rlChallengeRankingList.startAnimation(AnimationUtil.startAnimation(0.0f, -1.0f, 0.0f, 0.0f));
            blshow = false;
        }
        setGradeListTime();
    }


    //主播设置挑战名字
    public void setCaptureChallenge(ChallengeModelEntity challengeModelEntity, long showTime, String resourceNo) {

        this.showTime = showTime * 1000;
        this.resourceNo = resourceNo;
        if (tvChallengeMeddleChallengeName != null) {
            tvChallengeMeddleChallengeName.setText(challengeModelEntity.getName());
        }
//        llChallengeTopTime.setVisibility(View.VISIBLE);
//        tvChallengeTopChallengeTimerStart.setVisibility(View.VISIBLE);
//        ivChallengeTopChallengeTimer.setVisibility(View.VISIBLE);
//        tvChallengeTopChallengeTimer.setVisibility(View.VISIBLE);
//        tvChallengeTopChallengeTimerStart.setText("暖场中");
//        btnChallengeMeddleStart.setVisibility(View.GONE);
//        btnChallengeTopStart.setVisibility(View.GONE);
        //开始挑战倒计时
//        change((int) Warmtime); //设置暖场时间
//        challengeStart();


//        //挑战时间
//        tvChallengeTopChallengeTimer.setText(minute + ":" + second);
//
//        timerTask = new TimerTask() {
//
//            @Override
//            public void run() {
//                Message msg = new Message();
//                msg.what = 0;
//                msg.arg1 = 1;
//                handler.sendMessage(msg);
//            }
//        };
//
//        timer = new Timer();
//        timer.schedule(timerTask, 0, 1000);

    }

    //显示评分结果
    public void showFradingResult(GradingResultsEntity gradingResult) {
        ToastUtils.showLong("roomuser");
//        rlChallengeRecueResults.setVisibility(View.GONE);
//        rl_challenge_ranking_list
        rlChallengeGradeTime.setVisibility(View.GONE); //评分结果
        rlChallengeRankingList.setVisibility(View.GONE); //富豪榜
        llChallengeMeddle.setVisibility(View.VISIBLE);
        rlGradingResults.setVisibility(View.VISIBLE);
        boolean blshow = true;
        if (blshow) {
            rlGradingResults.startAnimation(AnimationUtil.startAnimation(1.0f, 0.0f, 0.0f, 0.0f));
            rlChallengeRankingList.startAnimation(AnimationUtil.startAnimation(0.0f, -1.0f, 0.0f, 0.0f));
            blshow = false;
        }

        tvChallengeGradingResultsAverage.setText(gradingResult.getData().getAverage() + "");
        tvChallengeGradingResultsLive.setText(gradingResult.getData().getLoveNumScore() + "");
        tvChallengeGradingResultsGife.setText(gradingResult.getData().getGiftNumScore() + "");
        if (gradingResult.getData().getResult().equals("A")) {
            ivChallengeFradingResultSucErr.setBackgroundResource(R.drawable.yjy_weitongguo);
            ivChallengeGradingResultImg.setBackgroundResource(R.drawable.pf_a);

        } else if (gradingResult.getData().getResult().equals("B")) {
            ivChallengeFradingResultSucErr.setBackgroundResource(R.drawable.yjy_weitongguo);
            ivChallengeGradingResultImg.setBackgroundResource(R.drawable.pf_b);

        } else if (gradingResult.getData().getResult().equals("C")) {
            ivChallengeFradingResultSucErr.setBackgroundResource(R.drawable.yjy_weitongguo);
            ivChallengeGradingResultImg.setBackgroundResource(R.drawable.pf_c);

        } else if (gradingResult.getData().getResult().equals("D")) {
            ivChallengeFradingResultSucErr.setBackgroundResource(R.drawable.yjy_weitongguo);
            ivChallengeGradingResultImg.setBackgroundResource(R.drawable.pf_d);

        } else if (gradingResult.getData().getResult().equals("E")) {
            ivChallengeFradingResultSucErr.setBackgroundResource(R.drawable.yjy_weitongguo);
            ivChallengeGradingResultImg.setBackgroundResource(R.drawable.pf_e);

        } else if (gradingResult.getData().getResult().equals("F")) {
            ivChallengeFradingResultSucErr.setBackgroundResource(R.drawable.yjy_weitongguo);
            ivChallengeGradingResultImg.setBackgroundResource(R.drawable.pf_f);
        }

        if (gradingResult.getData().getResult().equals("A") || gradingResult.getData().getResult().equals("B")) {
            //通过了
            btnChallengeGradingResult.setText("结束挑战");
            btnChallengeGradingResult.setBackgroundResource(R.drawable.icon_challenge_meddle_success_btn);
        } else {
            //未通过
            if (liveActivity.isAudience) {
                //观众
                //主播
                btnChallengeGradingResult.setText("救援 TA");
                btnChallengeGradingResult.setBackgroundResource(R.drawable.icon_challenge_meddle_grade_wait);
                //点击救援
                btnChallengeGradingResult.setOnClickListener(v -> {
//                    ToastUtils.showLong("救毛线啊,,表演成这鸟样儿...好意思送吗?");
                    rlChallengeRescueSend.setVisibility(View.VISIBLE);
                    rlGradingResults.setVisibility(View.GONE);
                    rlChallengeRescueSend.startAnimation(AnimationUtil.startAnimation(1.0f, 0.0f, 0.0f, 0.0f));
                    rlGradingResults.startAnimation(AnimationUtil.startAnimation(0.0f, -1.0f, 0.0f, 0.0f));
                    time = new TimeCounts(120000, 1000);
                    time.start();
                });
            } else {
                //主播
                btnChallengeGradingResult.setText("等待\n救援");
                btnChallengeGradingResult.setBackgroundResource(R.drawable.icon_challenge_meddle_grade_wait);
            }
        }

    }

    /*
       * 将秒数转为时分秒
       * */
    public String change(int second) {

        second /= 1000;
        int hour = second / 3600;
        int mint = (second % 3600) / 60;
        int sed = second % 60;
        minute = mint;
        this.second = sed;

        int h = 0;
        int d = 0;
        int s = 0;
        int temp = second % 3600;
        if (second > 3600) {
            h = second / 3600;
            if (temp != 0) {
                if (temp > 60) {
                    d = temp / 60;
                    if (temp % 60 != 0) {
                        s = temp % 60;
                    }
                } else {
                    s = temp;
                }
            }
        } else {
            d = second / 60;
            if (second % 60 != 0) {
                s = second % 60;
            }
        }
//        minute = d;
//        this.second = second;

        return h + ":" + d + ":" + s + "";
    }

    //显示救援过程
    public void showRecue(RescueEntity rescueEntity) {
        rlChallengeRankingList.setVisibility(View.GONE);
        llChallengeMeddle.setVisibility(View.VISIBLE);
        rlChallengeGradeTime.setVisibility(View.GONE);
        rlGradingResults.setVisibility(View.GONE);
        rlChallengeRecueResults.setVisibility(View.VISIBLE);
        rbChallengeResueResultsTitle.setVisibility(View.INVISIBLE);
        rlChallengeRescueSend.setVisibility(View.GONE);
        btnChallengeRescueReultsOk.setText("正在救援中...");
        boolean blshow = true;
        if (blshow) {
            rlChallengeRecueResults.startAnimation(AnimationUtil.startAnimation(1.0f, 0.0f, 0.0f, 0.0f));
            rlGradingResults.startAnimation(AnimationUtil.startAnimation(0.0f, -1.0f, 0.0f, 0.0f));
            blshow = false;
        }
        int recueNum = rescueEntity.getData().getRescueNum();
        if (recueNum < 10) {
            ivChallengeResueResultsNumberOne.setVisibility(View.GONE);
            ivChallengeResueResultsNumberTwo.setVisibility(View.GONE);
            ivChallengeResueResultsNumberThree.setVisibility(View.VISIBLE);
            setResultsNumer(ivChallengeResueResultsNumberThree, recueNum);

        } else if (recueNum >= 10 && recueNum < 100) {
            ivChallengeResueResultsNumberOne.setVisibility(View.GONE);
            ivChallengeResueResultsNumberTwo.setVisibility(View.VISIBLE);
            ivChallengeResueResultsNumberThree.setVisibility(View.VISIBLE);
            String recueNumS = String.valueOf(recueNum);
            int one = Integer.parseInt(String.valueOf(recueNumS.charAt(0)));
            int two = Integer.parseInt(String.valueOf(recueNumS.charAt(recueNumS.length() - 1)));
            setResultsNumer(ivChallengeResueResultsNumberThree, two);
            setResultsNumer(ivChallengeResueResultsNumberTwo, one);

        } else if (recueNum >= 100) {
            ivChallengeResueResultsNumberOne.setVisibility(View.VISIBLE);
            ivChallengeResueResultsNumberTwo.setVisibility(View.VISIBLE);
            ivChallengeResueResultsNumberThree.setVisibility(View.VISIBLE);
            String recueNumS = String.valueOf(recueNum);
            int one = Integer.parseInt(String.valueOf(recueNumS.charAt(0)));
            int two = Integer.parseInt(String.valueOf(recueNumS.charAt(recueNumS.length() - 1)));
            int three = Integer.parseInt(String.valueOf(recueNumS.charAt(recueNumS.length() - 2)));
            setResultsNumer(ivChallengeResueResultsNumberThree, two);
            setResultsNumer(ivChallengeResueResultsNumberTwo, one);
            setResultsNumer(ivChallengeResueResultsNumberOne, three);
        }
    }

    //设置救援票的救援过程 UI
    public void setResultsNumer(ImageView imageView, int threeNum) {
        switch (threeNum) {
            case 1:
                imageView.setBackgroundResource(R.drawable.jyp_1);
                break;
            case 2:
                imageView.setBackgroundResource(R.drawable.jyp_2);
                break;
            case 3:
                imageView.setBackgroundResource(R.drawable.jyp_3);
                break;
            case 4:
                imageView.setBackgroundResource(R.drawable.jyp_4);
                break;
            case 5:
                imageView.setBackgroundResource(R.drawable.jyp_5);
                break;
            case 6:
                imageView.setBackgroundResource(R.drawable.jyp_6);
                break;
            case 7:
                imageView.setBackgroundResource(R.drawable.jyp_7);
                break;
            case 8:
                imageView.setBackgroundResource(R.drawable.jyp_8);
                break;
            case 9:
                imageView.setBackgroundResource(R.drawable.jyp_9);
                break;
            case 0:
                imageView.setBackgroundResource(R.drawable.jyp_0);
                break;
        }
    }

    //显示救援结果
    public void showRescueResults(RescueResultsEntity rescueResultsEntity) {
        rlChallengeRecueResults.setVisibility(View.GONE); //救援过程UI
        rlChallengeRankingList.setVisibility(View.GONE);
        llChallengeMeddle.setVisibility(View.VISIBLE);
        rlChallengeGradeTime.setVisibility(View.GONE);
        rlGradingResults.setVisibility(View.GONE);
        rlChallengeRescueSend.setVisibility(View.GONE); //送救援票
        rlChallengeRecueResults.setVisibility(View.VISIBLE);
        boolean blshow = true;
        if (blshow) {
            rlChallengeRecueResults.startAnimation(AnimationUtil.startAnimation(1.0f, 0.0f, 0.0f, 0.0f));
            rlGradingResults.startAnimation(AnimationUtil.startAnimation(0.0f, -1.0f, 0.0f, 0.0f));
            blshow = false;
        }
        if (time != null) {
            time.onFinish();
        }

        int recueNum = rescueResultsEntity.getData().getRescueNum();
        //显示救援结果
        if (rescueResultsEntity.getData().getResult().equals("A")) {
        }
        rbChallengeResueResultsTitle.setVisibility(View.VISIBLE);
        if (liveActivity.isAudience) {
            btnChallengeRescueReultsOk.setVisibility(View.GONE);
            //显示救援结果
            if (rescueResultsEntity.getData().getResult().equals("A")) {
                Drawable nav_up = getResources().getDrawable(R.drawable.icon_challenge_jiuyuanchenggong);
                nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                Drawable nav_up_xin = getResources().getDrawable(R.drawable.icon_challenge_hongxin);
                nav_up_xin.setBounds(0, 0, nav_up_xin.getMinimumWidth(), nav_up_xin.getMinimumHeight());
                rbChallengeResueResultsTitle.setCompoundDrawables(nav_up_xin, null, nav_up, null);
            } else {
                Drawable nav_up = getResources().getDrawable(R.drawable.icon_challenge_jiuyuanshibai);
                nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                Drawable nav_up_xin = getResources().getDrawable(R.drawable.icon_challenge_hongxin_error);
                nav_up_xin.setBounds(0, 0, nav_up_xin.getMinimumWidth(), nav_up_xin.getMinimumHeight());
                rbChallengeResueResultsTitle.setCompoundDrawables(nav_up_xin, null, nav_up, null);
            }
        } else {
            btnChallengeRescueReultsOk.setVisibility(View.VISIBLE);
            //显示救援结果
            if (rescueResultsEntity.getData().getResult().equals("A")) {
                btnChallengeRescueReultsOk.setText("退出挑战");
                Drawable nav_up = getResources().getDrawable(R.drawable.icon_challenge_jiuyuanchenggong);
                nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                Drawable nav_up_xin = getResources().getDrawable(R.drawable.icon_challenge_hongxin);
                nav_up_xin.setBounds(0, 0, nav_up_xin.getMinimumWidth(), nav_up_xin.getMinimumHeight());
                rbChallengeResueResultsTitle.setCompoundDrawables(nav_up_xin, null, nav_up, null);
            } else {
                btnChallengeRescueReultsOk.setText("退出挑战");
                btnChallengeRescueReultsOk.setBackgroundResource(R.drawable.shb_zaicitiaozhan);
//                btnChallengeRescueReultsOk.setBackgroundResource(R.drawable.icon_challenge_meddle_);
                Drawable nav_up = getResources().getDrawable(R.drawable.icon_challenge_jiuyuanshibai);
                nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                Drawable nav_up_xin = getResources().getDrawable(R.drawable.icon_challenge_hongxin_error);
                nav_up_xin.setBounds(0, 0, nav_up_xin.getMinimumWidth(), nav_up_xin.getMinimumHeight());
                rbChallengeResueResultsTitle.setCompoundDrawables(nav_up_xin, null, nav_up, null);
            }
        }

        if (recueNum < 10) {
            ivChallengeResueResultsNumberOne.setVisibility(View.GONE);
            ivChallengeResueResultsNumberTwo.setVisibility(View.GONE);
            ivChallengeResueResultsNumberThree.setVisibility(View.VISIBLE);
            setResultsNumer(ivChallengeResueResultsNumberThree, recueNum);

        } else if (recueNum >= 10 && recueNum < 100) {
            ivChallengeResueResultsNumberOne.setVisibility(View.GONE);
            ivChallengeResueResultsNumberTwo.setVisibility(View.VISIBLE);
            ivChallengeResueResultsNumberThree.setVisibility(View.VISIBLE);
            String recueNumS = String.valueOf(recueNum);
            int one = Integer.parseInt(String.valueOf(recueNumS.charAt(0)));
            int two = Integer.parseInt(String.valueOf(recueNumS.charAt(recueNumS.length() - 1)));
            setResultsNumer(ivChallengeResueResultsNumberThree, two);
            setResultsNumer(ivChallengeResueResultsNumberTwo, one);

        } else if (recueNum >= 100) {
            ivChallengeResueResultsNumberOne.setVisibility(View.VISIBLE);
            ivChallengeResueResultsNumberTwo.setVisibility(View.VISIBLE);
            ivChallengeResueResultsNumberThree.setVisibility(View.VISIBLE);
            String recueNumS = String.valueOf(recueNum);
            int one = Integer.parseInt(String.valueOf(recueNumS.charAt(0)));
            int two = Integer.parseInt(String.valueOf(recueNumS.charAt(recueNumS.length() - 1)));
            int three = Integer.parseInt(String.valueOf(recueNumS.charAt(recueNumS.length() - 2)));
            setResultsNumer(ivChallengeResueResultsNumberThree, two);
            setResultsNumer(ivChallengeResueResultsNumberTwo, one);
            setResultsNumer(ivChallengeResueResultsNumberOne, three);
        }
    }


    //显示富豪榜
    public void showRankList(RankEntity rankEntity) {
        tvChallengeRankContributionNum.setText(rankEntity.getData().getGiftTotal() + "");
        GridLayoutManager leftFridLayoutMan = new GridLayoutManager(getActivity(), 3);
        rlvChallengeMeddleRankingListLeft.setLayoutManager(leftFridLayoutMan);
        GridLayoutManager reigthFridLayoutMan = new GridLayoutManager(getActivity(), 3);
        rlvChallengeMeddleRankingListReight.setLayoutManager(reigthFridLayoutMan);
        tvChallengeTopGift.setText(rankEntity.getData().getGiftNum() + "");
        leftRankList.clear();
        reightRankList.clear();
        List<RankEntity.Data.Rank> rankList = rankEntity.getData().getRank();
        for (int i = 0; i < rankList.size(); i++) {
            if (i < 3) {
                leftRankList.add(rankList.get(i));
            } else if (i >= 3 && i < 6) {
                reightRankList.add(rankList.get(i));
            } else if (i >= 6 && i < 9) {
                leftRankList.add(rankList.get(i));
            } else if (i >= 9 && i < 12) {
                reightRankList.add(rankList.get(i));
            }
        }

        ChallengeRankAdapter leftRankAdapter = new ChallengeRankAdapter(R.layout.item_challenge_ranking, leftRankList);
        rlvChallengeMeddleRankingListLeft.setAdapter(leftRankAdapter);

        ChallengeRankAdapter regihtRankAdapter = new ChallengeRankAdapter(R.layout.item_challenge_ranking, reightRankList);
        rlvChallengeMeddleRankingListReight.setAdapter(regihtRankAdapter);

    }


    //显示点赞数
    public void showLoveNum(ChallengeLove challengeLove) {
        tvChallengeTopLike.setText(challengeLove.getData().getLoveNum() + "");
    }

    /**
     * 倒计时
     */
    class TimeCounts extends CountDownTimer {
        public TimeCounts(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            rlGradingResults.setVisibility(View.GONE);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            tvChallengeRescueSendTime.setText(millisUntilFinished / 1000 + "");
            btnChallengeRecueSend.setOnClickListener(v -> {
                giftPresenter.onCreate();
                giftPresenter.getUserUserVc();
                giftPresenter.attachView(userVCView);

            });
        }
    }

    //查询用户金币
    public ITestView userVCView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            EntityObject<UserVC> userVCEntity = (EntityObject<UserVC>) object;
            if (userVCEntity.getData().getUserBalance() >= 60) {
                challengeTypePresenter.onCreate();
                challengeTypePresenter.getChallengeRescue(PullLiveModel.getRoomid() + "", PullLiveModel.getChallengeNo() + "");
                challengeTypePresenter.attachView(ChallengeRescueView);
            } else {
                ToastUtils.showLong("金币余额不足,请充值!!!");
                startActivity(new Intent(getActivity(), MyPurseActivity.class));
            }
        }

        @Override
        public void onError(Object object) {
            ToastUtils.showLong(object.toString());

        }
    };

    //投送救援票
    public ITestView ChallengeRescueView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            JsonEntity jsonEntity = (JsonEntity) object;
            ToastUtils.showLong(jsonEntity.getMsg());
            if (jsonEntity.getCode().equals("200")) {
                btnChallengeRecueSend.setText("已救援.");
                btnChallengeRecueSend.setEnabled(false);
            }
        }

        @Override
        public void onError(Object object) {
            ToastUtils.showLong(object.toString());
        }
    };

    //观看挑战
    public ITestView challengeView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            challengeStratEntityEntity = (EntityObject<ChallengeStratEntity>) object;
            challengeTypePresenter.onCreate();
            challengeTypePresenter.getChallengeGiftList(challengeStratEntityEntity.getData().getMenuNo() + "");
            challengeTypePresenter.attachView(challengeGiftListView);
        }

        @Override
        public void onError(Object object) {

        }
    };

    private String giftMoney; //礼物数量
    //挑战礼物
    public ITestView challengeGiftListView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            liveActivity.setGiftVibility();

            giftEntity = (Entity<Gift>) object;
            List<String> giftNum = new ArrayList<>();
            giftNum.add("1");
            giftNum.add("10");
            giftNum.add("88");
            giftNum.add("520");
            giftNum.add("666");
            giftNum.add("1314");

            ChallengeGiftNumAdapter challengeGiftNumAdapter = new ChallengeGiftNumAdapter(R.layout.item_challenge_gift_num, giftNum);
            rvChallengeGiftList.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvChallengeGiftList.setAdapter(challengeGiftNumAdapter);
            challengeGiftNumAdapter.setOnItemClickListener((adapter, view, position) -> {
                giftMoney = giftNum.get(position);
                giftPresenter.onCreate();
                giftPresenter.getUserUserVc();
                giftPresenter.attachView(getUserVcView);
            });

            Picasso.with(getContext()).load(giftEntity.getData().getList().get(0).getGiftPic()).into(ibChallengeBottomGift);
            ibChallengeBottomGift.setOnClickListener(v -> {
                if (rlChallengeGift.getVisibility() != View.VISIBLE) {
                    rlChallengeGift.setVisibility(View.VISIBLE);
                } else {
                    rlChallengeGift.setVisibility(View.GONE);
                }

            });
        }

        @Override
        public void onError(Object object) {

        }
    };

    private EntityObject<UserVC> userVCEntity; //礼物列表

    //赠送礼物
    public ITestView getUserVcView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            userVCEntity = (EntityObject<UserVC>) object;
            if (userVCEntity.getData().getUserBalance() <= 0 || userVCEntity.getData().getUserBalance() < Integer.parseInt(giftMoney)) {
                ToastUtils.showLong("余额不足 .请充值");
                startActivity(new Intent(getActivity(), MyPurseActivity.class));
            } else {

                giftPresenter.onCreate();
                giftPresenter.getGiveGift(giftEntity.getData().getList().get(0).getId(), "1", PullLiveModel.getLiveNo(),
                        giftMoney, PullLiveModel.getUserNo());
                giftPresenter.attachView(giveGift);
            }
        }

        @Override
        public void onError(Object object) {
            ToastUtils.showLong("赠送失败");

        }
    };

    //礼物赠送成功
    public ITestView giveGift = new ITestView() {
        @Override
        public void onSuccess(Object object) {

        }

        @Override
        public void onError(Object object) {
        }
    };

    //退出直播
    public ITestView finishLive = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            liveActivity.finish();
        }

        @Override
        public void onError(Object object) {

        }
    };


}
