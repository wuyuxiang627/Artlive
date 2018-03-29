package com.connxun.elinetv.view.MediaPreview;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.connxun.elinetv.IM.ChatRoomMsgListPanel;
import com.connxun.elinetv.IM.NimContract;
import com.connxun.elinetv.IM.NimController;
import com.connxun.elinetv.IM.activity.InputActivity;
import com.connxun.elinetv.IM.config.InputConfig;
import com.connxun.elinetv.IM.fragment.ChatRoomMessageFragment;
import com.connxun.elinetv.R;
import com.connxun.elinetv.adapter.GridViewAdapter;
import com.connxun.elinetv.adapter.ViewPagerAdapter;
import com.connxun.elinetv.app.BaseApplication;
import com.connxun.elinetv.base.Dialog.ActionSheetDialog;
import com.connxun.elinetv.base.ui.BaseActivity;
import com.connxun.elinetv.base.ui.BubbleView;
import com.connxun.elinetv.entity.ChallengeLove;
import com.connxun.elinetv.entity.Entity;
import com.connxun.elinetv.entity.EntityObject;
import com.connxun.elinetv.entity.Gift;
import com.connxun.elinetv.entity.IMGift;
import com.connxun.elinetv.entity.Live;
import com.connxun.elinetv.entity.LiveModel;
import com.connxun.elinetv.entity.live.challenge.ChallengeEntity;
import com.connxun.elinetv.entity.live.challenge.GradingResultsEntity;
import com.connxun.elinetv.entity.live.challenge.RankEntity;
import com.connxun.elinetv.entity.live.challenge.RescueEntity;
import com.connxun.elinetv.entity.live.challenge.RescueResultsEntity;
import com.connxun.elinetv.entity.order.UserVC;
import com.connxun.elinetv.presenter.Gift.GiftPresenter;
import com.connxun.elinetv.presenter.Live.LivePresenter;
import com.connxun.elinetv.util.ToastUtils;
import com.connxun.elinetv.view.LiveBroadcast.CaptureFragment;
import com.connxun.elinetv.view.LiveBroadcast.PublishParam;
import com.connxun.elinetv.view.LiveBroadcast.PushFlowFragment;
import com.connxun.elinetv.view.MediaPreview.MediaPreviewFragment;
import com.connxun.elinetv.view.MediaPreview.fragment.OneFragment;
import com.connxun.elinetv.view.MediaPreview.fragment.UserLiveFragment;
import com.connxun.elinetv.view.user.ITestView;
import com.google.gson.Gson;
import com.netease.nim.uikit.business.chatroom.viewholder.ChatRoomMsgViewHolderText;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomInfo;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMember;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMessage;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.orhanobut.logger.Logger;

import org.xutils.view.annotation.ContentView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.connxun.elinetv.view.LiveBroadcast.CapturePreviewController.EXTRA_PARAMS;

/**
 * Created by connxun-16 on 2018/2/25.
 */

@ContentView(R.layout.activity_live_room)
public class LIveRoomActivity extends BaseActivity implements View.OnClickListener, NimContract.Ui{
    public static LIveRoomActivity lIveRoomActivity= null;
    public static final String IS_AUDIENCE = "is_audience";
    private GiftPresenter giftPresenter = new GiftPresenter(this); //礼物请求


    //各大板块容器

    private MediaPreviewFragment mediaPreviewFragment; //观众
    private CaptureFragment captureFragment;//主播
    FrameLayout layout_main_content;
    FrameLayout layout_room_info;
    RelativeLayout rlLayout ;// 父容器

    //聊天室相关
    private FrameLayout chatLayout;
    private ChatRoomMessageFragment chatRoomFragment;
    private NimController nimController;
    ImageView iv_gift;



    String roomId; //聊天室id
    private ActionSheetDialog mDialog; //弹出框
    public boolean isAudience; //类别区分 :主播-观众

    private ViewPager viewPager;
    private LinearLayout idotLayout;//知识圆点
    private List<View> mPagerList;//页面集合
    private List<Gift> mDataList;//数据集合；
    private LayoutInflater mInflater;
    private static RelativeLayout ll_liwu;
    private TextView tvUserMoney;
    private Button btnSendGift;
    private long userMoney =0; //默认金币

    private Context mContext;

    //点赞
    BubbleView bvView;
    long mLastTime = 0;
    long mCurTime = 0;
    private final int DELAY = 1000;//连续点击的临界点
    BubbleView bubbleView;
    private int mClickCount = 0;
    private int currLikeCount;
    private TextView likeCount;
    private Timer delayTimer;
    private TimerTask timeTask;


    //礼物
    /*总的页数*/
    private int pageCount;
    /*每一页显示的个数*/
    private int pageSize = 8;
    /*当前显示的是第几页*/
    private int curIndex = 0;
    Entity<Gift> giftEntity; //礼物列表
    UserVC userVC;//用户金币
    private GridViewAdapter[] arr = new GridViewAdapter[3];
    private Gift gModel; //选择的礼物
    private LiveModel liveModel;
    private Live CaptureLive;
    private Map<String, Object> map;


    LivePresenter livePresenter = new LivePresenter(this);



    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            addLiveLick(mClickCount);
            delayTimer.cancel();
            super.handleMessage(msg);
        }
    };
    private UserLiveFragment userLiveFragment;
    private WatchFragment watchFragment;

    private void delay() {
        if (timeTask != null)
            timeTask.cancel();

        timeTask = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                mHandler.sendMessage(message);
            }
        };
        delayTimer = new Timer();
        delayTimer.schedule(timeTask, DELAY);
    }

    /**
     * 点赞请求网络
     */
    private void addLiveLick(int count) {

    }


    /**
     * 静态方法 启动主播
     * @param context 上下文
     */
    public static void startLive(Context context, String roomId, PublishParam param){
        Intent intent = new Intent(context, LIveRoomActivity.class);
        intent.putExtra(IS_AUDIENCE, false);
        intent.putExtra(NimController.EXTRA_ROOM_ID, roomId);
        intent.putExtra(EXTRA_PARAMS, param);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());
        mContext = this;

        initView();  //初始化

//        setViewPager();


        loadFragment(isAudience); //显示界面


        setListener(); //设置监听

        lIveRoomActivity = this;
        //应用运行时，保持屏幕高亮，不锁屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //首先加入聊天室
        nimController = new NimController(this, this);
        nimController.onHandleIntent(getIntent());

    }

    private void setViewPager() {
        ViewPager viewPager = findViewById(R.id.vp_video_viewpager);
        ArrayList fragments = new ArrayList<Fragment>();
        fragments.add(new OneFragment());
//        fragments.add(new TwoFragment());

        //构建Adapter
        MainPagerAdapter pagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(4);



    }


    public class MainPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragments;

        public MainPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }






    @Override
    protected void onResume() {
        super.onResume();
        //布局
        ChatRoomMsgViewHolderText.blVisible = true;
    }

    private void setListener() {
        //显示点赞


//        chatLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mCurTime = System.currentTimeMillis();
//                if (mCurTime - mLastTime < DELAY) {
//                    mClickCount++;
//                } else {
//                    mClickCount = 1;
//                }
//                currLikeCount++;
////                likeCount.setText(String.valueOf(currLikeCount));
//                delay();
//                mLastTime = mCurTime;
//                bvView.startAnimation(bvView.getWidth(), bvView.getHeight());
//            }
//        });


        //显示礼物布局
        iv_gift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowGift();
            }
        });


        //隐藏礼物布局
        layout_main_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_liwu.setVisibility(View.GONE);
//                mCurTime = System.currentTimeMillis();
//                if (mCurTime - mLastTime < DELAY) {
//                    mClickCount++;
//                } else {
//                    mClickCount = 1;
//                }
//                currLikeCount++;
////                likeCount.setText(String.valueOf(currLikeCount));
//                delay();
//                mLastTime = mCurTime;
//                bvView.startAnimation(bvView.getWidth(), bvView.getHeight());
            }
        });

        //点击送礼
        btnSendGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gModel != null && liveModel != null && isAudience ){
                    if(userMoney != 0 && userMoney >= gModel.getGiftPrice()){
                        //送礼物
                        giftPresenter.onCreate();
                        giftPresenter.getGiveGift(gModel.getId(),"1",liveModel.getLiveNo(),"1",liveModel.getUserNo());
                        giftPresenter.attachView(SendGiftVIew);
                    }else {
                        ToastUtils.showLong("账户余额不足.请充值");
                    }
                }else {
                    ToastUtils.showLong("请选择礼物...");
                }
            }
        });
    }

    private void initView() {
        layout_room_info = findViewById(R.id.layout_room_info);
        bvView = findViewById(R.id.bv_bubbleView);
        bvView.setDefaultDrawableList();
        rlLayout = findViewById(R.id.layout_live_root);
        iv_gift = findViewById(R.id.iv_gift);
        layout_main_content = findViewById(R.id.layout_main_content);
        chatLayout = findViewById(R.id.layout_chat_room);
        viewPager = (ViewPager) findViewById(R.id.liwu_viewpager);
        idotLayout = (LinearLayout) findViewById(R.id.ll_dot);
        ll_liwu = findViewById(R.id.ll_liwu);
        tvUserMoney = findViewById(R.id.tv_gift_user_money);
        btnSendGift = findViewById(R.id.btn_gift_send);
        //礼物列表
        giftPresenter.onCreate();
        giftPresenter.getGiftList();
        giftPresenter.attachView(GiftVIew);


    }

    //礼物赠送成功
    public ITestView SendGiftVIew = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            if(BaseApplication.getUserNo()!= null){
                giftPresenter.onStop();
                giftPresenter.onCreate();
                giftPresenter.getUserUserVc();
                giftPresenter.attachView(UserVcVIew);
            }
        }

        @Override
        public void onError(Object object) {

        }
    };


    //用户金币
    public ITestView UserVcVIew = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            try{
                EntityObject<UserVC>  userVC = (EntityObject<UserVC>) object;
                userMoney = userVC.getData().getUserBalance();
                tvUserMoney.setText(userMoney+" 金币 ");
            }catch (Exception e){
            }

        }

        @Override
        public void onError(Object object) {

        }
    };


    //礼物列表
    public ITestView GiftVIew = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            mDataList = new ArrayList<>();
            giftEntity = (Entity<Gift>) object;
            mDataList = giftEntity.getData().getList();
            initValues();
            if(BaseApplication.getUserNo()!= null){
                giftPresenter.onCreate();
                giftPresenter.getUserUserVc();
                giftPresenter.attachView(UserVcVIew);
            }
        }

        @Override
        public void onError(Object object) {

        }
    };

    private void handleIntent(Intent intent) {
        isAudience = intent.getBooleanExtra(IS_AUDIENCE, true);
        roomId = intent.getStringExtra(NimController.EXTRA_ROOM_ID);
        liveModel = (LiveModel) intent.getSerializableExtra("mLiveModel");
        CaptureLive = (Live) intent.getSerializableExtra("liveMOdel");
    }


    /**A
     * 根据是否为观众,加载不同的Fragment
     */
    PushFlowFragment captureFragments;
    private void loadFragment(boolean isAudience) {

        FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(isAudience){
//            ll_liwu.setVisibility(View.VISIBLE);
            if(!BaseApplication.blLiveTypeLiveOrChallenge){
                iv_gift.setVisibility(View.GONE);
            }else {
                iv_gift.setVisibility(View.VISIBLE);
            }
//        mediaPreviewFragment = new MediaPreviewFragment();
         watchFragment = new WatchFragment();
        transaction.replace(R.id.layout_main_content, watchFragment);
    }else{
        iv_gift.setVisibility(View.GONE);
        ll_liwu.setVisibility(View.INVISIBLE);
//        captureFragment = new CaptureFragment();

            captureFragments = new PushFlowFragment();
        transaction.replace(R.id.layout_main_content, captureFragments);
        chatLayout.setVisibility(View.INVISIBLE);
    }
    userLiveFragment = new UserLiveFragment();
        transaction.replace(R.id.layout_room_info, userLiveFragment);
        transaction.commit();
}


    /**
     * 显示聊天输入布局 展开键盘
     */
    public void showInputPanel(){
        startInputActivity();
    }

    /**
     * ***************************** 部分机型键盘弹出会造成布局挤压的解决方案 ***********************************
     */
    private InputConfig inputConfig = new InputConfig(false, false, false);
    private String cacheInputString = "";
    public final static String EXTRA_TEXT = "EXTRA_TEXT";
    public final static String EXTRA_MODE = "EXTRA_MODE";
    private final static String EXTRA_INPUT_CONFIG = "EXTRA_INPUT_CONFIG";
    public final static int REQ_CODE = 20;
    private void startInputActivity() {
        InputActivity.startActivityForResult(LIveRoomActivity.this, cacheInputString,
                inputConfig, new InputActivity.InputActivityProxy() {
                    @Override
                    public void onSendMessage(String text) {
                        if(chatRoomFragment != null){
                            chatRoomFragment.onTextMessageSendButtonPressed(text);
                        }
                    }
                });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == InputActivity.REQ_CODE) {
            // 设置EditText显示的内容
            cacheInputString = data.getStringExtra(InputActivity.EXTRA_TEXT);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        mDialog = new ActionSheetDialog(this).builder();
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(true);
        if(isAudience){

            mDialog.setTitle("是否退出观看直播?");
            mDialog.addSheetItem("是", ActionSheetDialog.SheetItemColor.Red, new ActionSheetDialog.OnSheetItemClickListener() {

                @Override
                public void onClick(int which) {
                    finish();
                }
            });
            mDialog.addSheetItem("否", ActionSheetDialog.SheetItemColor.Red, new ActionSheetDialog.OnSheetItemClickListener() {
                @Override
                public void onClick(int which) {
                }
            }).show();
        }else {
            mDialog.setTitle("是否停止直播?");
            mDialog.addSheetItem("是", ActionSheetDialog.SheetItemColor.Red, new ActionSheetDialog.OnSheetItemClickListener() {

                @Override
                public void onClick(int which) {

                    livePresenter.onCreate();
                    if(CaptureLive != null){
                        try {
                            livePresenter.getLiveEndLive(CaptureLive.getLiveNo()+"");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        livePresenter.attachView(finishLive);
                    }


                }
            });
            mDialog.addSheetItem("否", ActionSheetDialog.SheetItemColor.Red, new ActionSheetDialog.OnSheetItemClickListener() {
                @Override
                public void onClick(int which) {
                }
            }).show();
        }



        return false;
    }



    @Override
    public void onEnterChatRoomSuc(final String roomId) {
        this.roomId = roomId;
        chatRoomFragment = (ChatRoomMessageFragment) getSupportFragmentManager().findFragmentById(R.id.chat_room_fragment);
        if (chatRoomFragment != null) {
            initChatRoomFragment();
        } else {
            // 如果Fragment还未Create完成，延迟初始化
            getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onEnterChatRoomSuc(roomId);
                }
            }, 50);
        }
    }


    /**
     * 刷新房间信息
     * @param roomInfo
     */
    @Override
    public void refreshRoomInfo(ChatRoomInfo roomInfo) {

    }

    /**
     * 刷新人员列表
     * @param result
     */
    @Override
    public void refreshRoomMember(List<ChatRoomMember> result) {
        if(isAudience){
            watchFragment.setUSerPhotoNumber(result);
        }else {
            captureFragments.setUSerPhotoNumber(result);
        }

    }

    public void setView(String userNo){
        layout_room_info.setVisibility(View.VISIBLE);
        userLiveFragment.setUserInfo(userNo);
    }
    public void setInVisible(){
        layout_room_info.setVisibility(View.GONE);
    }

    /**
     * 隐藏人员操作布局
     */
    @Override
    public void dismissMemberOperateLayout() {

    }

    /**
     * 聊天室结束回调
     * @param reason  结束原因
     */
    @Override
    public void onChatRoomFinished(String reason) {

    }

    @Override
    public void showTextToast(String text) {

    }

    /**
     * 初始化聊天室Fragment
     */
    private void initChatRoomFragment() {
        chatRoomFragment.init(roomId);
        chatRoomFragment.setMsgExtraDelegate(new ChatRoomMsgListPanel.ExtraDelegate() {

            @Override
            public void onReceivedCustomAttachment(ChatRoomMessage msg) {
                // 收到礼物消息
//                String jsonType =  msg.getAttachment().toString();
//
                try{
                    map = msg.getRemoteExtension();
                    int msgType = (int) map.get("msgType");
                    NIMClient.getService(MsgService.class).sendMessageReceipt(msg.getSessionId(), msg);
                    Logger.e("msgtype :" + map.get("msgType"));
                    Gson gson = new Gson();
                    switch (msgType)
                    {
                        case 2:
                            //赠送礼物
                            //聊天室礼物
                            IMGift imGift = gson.fromJson(gson.toJson(map),IMGift.class);
                            if(isAudience){
                                watchFragment.showGift(imGift);
                            }else {
                                captureFragments.showGift(imGift);
                            }
                            break;
                        case 103:
                            //显示点赞数
                            String json = gson.toJson(map);
                            ChallengeLove challengeLove = gson.fromJson(json,ChallengeLove.class);
                            if(isAudience){
                                watchFragment.showLoveNum(challengeLove);
                            }else {
                                captureFragments.showLoveNum(challengeLove);
                            }
                            break;
                        case 104:
                            //排行榜

                            RankEntity rankEntity = gson.fromJson(gson.toJson(map),RankEntity.class);
                            if(isAudience){
                                watchFragment.showRankList(rankEntity);
                            }else {
                                captureFragments.showRankList(rankEntity);
                            }



                            break;
                        case 111:
                            //开始评分
                            watchFragment.showGradingView();
                            break;

                        case 121:
                            //用户投送救援票

                            RescueEntity rescueEntity = gson.fromJson(gson.toJson(map),RescueEntity.class);
                            if(isAudience){
                                watchFragment.showRecue(rescueEntity);
                            }else {
                                captureFragments.showRecue(rescueEntity);
                            }



                            break;
                        case 115:
                            String jsona = gson.toJson(map);
                            //评分结果推送
                            GradingResultsEntity gradingResult = gson.fromJson(gson.toJson(map),GradingResultsEntity.class);
                            if(isAudience){
                                watchFragment.showFradingResult(gradingResult);
                            }else {
                                captureFragments.showFradingResult(gradingResult);
                            }
                            break;
                        case 131:
                            //救援结果
                            RescueResultsEntity rescueResultsEntity = gson.fromJson(gson.toJson(map),RescueResultsEntity.class);
                            if(isAudience){
                                watchFragment.showRescueResults(rescueResultsEntity);
                            }else {
                                captureFragments.showRescueResults(rescueResultsEntity);
                            }

                            break;




                    }







                }catch (Exception e){

                }
            }

            @Override
            public void onMessageClick(IMMessage imMessage) {
                if(imMessage.getMsgType() == MsgTypeEnum.text){
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 直播开始完成的回调
     */
    public void onStartLivingFinished() {
//        isLiveStart = true;
        chatLayout.setVisibility(View.VISIBLE);
//        liveBottomBar.setVisibility(View.VISIBLE);
//        roomInfoLayout.setVisibility(View.VISIBLE);
    }
    /**
     * 直播断开时的回调
     */
    public void onLiveDisconnect(){
//        isLiveStart = false;
        chatLayout.setVisibility(View.GONE);
//        liveBottomBar.setVisibility(View.GONE);
//        roomInfoLayout.setVisibility(View.GONE);
    }

    /**
     * 正常结束直播
     */
    public void normalFinishLive() {
        //主播发送离开房间请求
        if(!isAudience) {

            mDialog = new ActionSheetDialog(this).builder();
            mDialog.setCancelable(false);
            mDialog.setCanceledOnTouchOutside(true);
            mDialog.setTitle("是否停止直播?");
            mDialog.addSheetItem("是", ActionSheetDialog.SheetItemColor.Red, new ActionSheetDialog.OnSheetItemClickListener() {

                @Override
                public void onClick(int which) {

                    livePresenter.onCreate();
                    if(CaptureLive != null){
                        try {
                            livePresenter.getLiveEndLive(CaptureLive.getLiveNo()+"");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        livePresenter.attachView(finishLive);
                    }


                }
            });
            mDialog.addSheetItem("否", ActionSheetDialog.SheetItemColor.Red, new ActionSheetDialog.OnSheetItemClickListener() {
                @Override
                public void onClick(int which) {
                }
            }).show();
//            DemoServerHttpClient.getInstance().anchorLeave(roomId, new DemoServerHttpClient.DemoServerHttpCallback<Void>() {
//                @Override
//                public void onSuccess(Void aVoid) {
//                    //正常离开时,服务端会发送解散消息通知,此时根据解散时发送的被踢消息离开房间.
//                }
//
//                @Override
//                public void onFailed(int code, String errorMsg) {
//                }
//            });
        }
//        finish();
    }
    private void initValues() {


        mInflater = LayoutInflater.from(mContext);
        //总的页数=总数/每页数量，并取整
        pageCount = (int) Math.ceil(mDataList.size() * 1.0 / pageSize);

        //viewpager
        mPagerList = new ArrayList<View>();
        for (int j = 0; j < pageCount; j++) {
            final GridView gridview = (GridView) mInflater.inflate(R.layout.bottom_vp_gridview, viewPager, false);
            final GridViewAdapter gridAdapter = new GridViewAdapter(mContext, mDataList, j);
            gridview.setAdapter(gridAdapter);
            arr[j] = gridAdapter;
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Toast.makeText(mContext, "点击位置position：" + mDataList.get((int) id).getGiftName() + "..id:" + id, Toast.LENGTH_SHORT).show();
                    gModel =  mDataList.get((int) id);
                    for (int i = 0; i < mDataList.size(); i++) {
                        Gift model = mDataList.get(i);
                        if (i == id) {
                            if (model.isSelected()) {
                                model.setSelected(false);
                            } else {
                                model.setSelected(true);
                                Log.i("tag", "==点击位置名字：" + model.getGiftName() + "..id:" + id);
                            }
                            Log.i("tag", "==点击位置：" + i + "..id:" + id);
                        } else {
                            model.setSelected(false);
//                            Log.i("tag","==位置2："+i+"..id:"+id);
                        }
                    }
                    Log.i("tag", "状态：" + mDataList.toString());
//                    gridAdapter.notifyDataSetChanged();
                }
            });
            mPagerList.add(gridview);
        }

        viewPager.setAdapter(new ViewPagerAdapter(mPagerList, LIveRoomActivity.this));
//        setOvalLayout();
    }

    /**
     * 设置圆点
     */
    public void setOvalLayout() {
        for (int i = 0; i < pageCount; i++) {
            idotLayout.addView(mInflater.inflate(R.layout.dot, null));
        }
        // 默认显示第一页
        idotLayout.getChildAt(0).findViewById(R.id.v_dot)
                .setBackgroundResource(R.drawable.dot_selected);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
                for(int i = 0;i<arr.length-1;i++){
                    arr[i].notifyDataSetChanged();
                }
                // 取消圆点选中
                idotLayout.getChildAt(curIndex)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_normal);
                // 圆点选中
                idotLayout.getChildAt(position)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_selected);
                curIndex = position;
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }


    public static void ShowGift(){
        ll_liwu.setVisibility(View.VISIBLE);
    }


    public ITestView finishLive = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            finish();
        }

        @Override
        public void onError(Object object) {

        }
    };

    public void setLiaotianshiFragmentWH(float width,float height){
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) chatLayout.getLayoutParams();
//        params.width = dip2px(getActivity(), width);
        params.height = dip2px(this, height);
        chatLayout.setLayoutParams(params);

    }

    /**
     * dp转为px
     * @param context  上下文
     * @param dipValue dp值
     * @return
     */
    private int dip2px(Context context, float dipValue)
    {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dipValue, r.getDisplayMetrics());
    }


    public void setGiftVibility(){
        iv_gift.setVisibility(View.GONE);
    }




}
