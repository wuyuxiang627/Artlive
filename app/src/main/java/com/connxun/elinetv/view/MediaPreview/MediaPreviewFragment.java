package com.connxun.elinetv.view.MediaPreview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.connxun.elinetv.IM.NimContract;
import com.connxun.elinetv.IM.NimController;
import com.connxun.elinetv.R;
import com.connxun.elinetv.adapter.Live.CheetahStaffAdapter;
import com.connxun.elinetv.app.BaseApplication;
import com.connxun.elinetv.base.Dialog.ActionSheetDialog;
import com.connxun.elinetv.base.ui.BaseFragment;
import com.connxun.elinetv.base.ui.CustomRoundView;
import com.connxun.elinetv.base.ui.MagicTextView;
import com.connxun.elinetv.entity.AttentionEntity;
import com.connxun.elinetv.entity.EntityObject;
import com.connxun.elinetv.entity.IMGift;
import com.connxun.elinetv.entity.LiveModel;
import com.connxun.elinetv.observer.INotifyListener;
import com.connxun.elinetv.observer.NotifyListenerManager;
import com.connxun.elinetv.observer.NotifyObject;
import com.connxun.elinetv.presenter.user.AttentionPresenter;
import com.connxun.elinetv.receiver.NEPhoneCallStateObserver;
import com.connxun.elinetv.receiver.NEScreenStateObserver;
import com.connxun.elinetv.receiver.Observer;
import com.connxun.elinetv.util.ToastUtils;
import com.connxun.elinetv.view.MediaPreview.media.NEMediaController;
import com.connxun.elinetv.view.MediaPreview.media.NEVideoView;
import com.connxun.elinetv.view.user.ITestView;
import com.connxun.elinetv.view.user.login.LoginActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.netease.neliveplayer.sdk.constant.NEType;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.business.chatroom.viewholder.ChatRoomMsgViewHolderText;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomInfo;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMember;
import com.squareup.picasso.Picasso;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by connxun-16 on 2017/12/19.
 */

/**
 * 直播播放
 */
public class MediaPreviewFragment extends BaseFragment implements View.OnClickListener, NimContract.Ui,INotifyListener{


    public final static String TAG = "MediaPreviewFragment";

    AttentionPresenter attentionPresenter = new AttentionPresenter(getActivity());

    View view;

    /**控件**/
    NEVideoView mVideoView;  //用于画面显示
    ImageView ivBack; //背景

    TextView tvUserName; //用户名称
    static TextView tvWatchNumber; //多少人观看
    TextView tvNumberNumber; //直播间号
    SimpleDraweeView ivUserPhoto;
    ImageButton ibFinish;
    ImageView ivGift; //礼物
    ImageButton ivComment; //关注


    ImageView iv_liaotianshi;
    //聊天室
    RecyclerView rlvCheetahStaff;


    ImageButton mPlayBack;
    RelativeLayout mPlayToolbar;
    TextView mFileName; //文件名称

    /**相关参数**/
    private View mBuffer; //用于指示缓冲状态
    private NEMediaController mMediaController; //用于控制播放

    private String mVideoPath= "rtmp://vc7a65f99.live.126.net/live/86e0e127e1dc4cd59543f33db5632396"; //文件路径
    private LiveModel mLiveModel; //直播参数
    private String mDecodeType="software";//解码类型，硬解或软解
    private String mMediaType="livestream"; //媒体类型
    private boolean mHardware = true;
    private String mTitle;
    private Uri mUri;

    private boolean mEnableBackgroundPlay = true;
    private boolean mBackPressed;

    private NEScreenStateObserver mScreenStateObserver;
    private boolean isScreenOff;
    private boolean isBackground;
    private LIveRoomActivity liveActivity;
    public static String roomId = null;


    //聊天室相关
    private NimController nimController;
    static List<ChatRoomMember> result = new ArrayList<>();
    private GridLayoutManager gridLayoutManager;
    private static CheetahStaffAdapter cheetahStaffAdapter;
    private ActionSheetDialog mDialog;

    private LinearLayout llgiftcontent;
    /**
     * 动画相关
     */
    private NumAnim giftNumAnim;
    private TranslateAnimation inAnim;
    private TranslateAnimation outAnim;
    private AnimatorSet animatorSetHide = new AnimatorSet();
    private AnimatorSet animatorSetShow = new AnimatorSet();
    private Timer timer;

    /**
     * 数据相关
     */
    private List<View> giftViewCollection = new ArrayList<View>();
    private List<String> messageData=new LinkedList<>();

    //是否关注
    EntityObject<AttentionEntity> attentionEntityEntityObject;


    public String getRoomId() {
        return roomId;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        view = inflater.inflate(R.layout.activity_mediapreview, null);
        mVideoPath = getActivity().getIntent().getStringExtra("mVideoPath");
        mLiveModel = (LiveModel) getActivity().getIntent().getSerializableExtra("mLiveModel");
        roomId = getActivity().getIntent().getStringExtra("roomId");
        mScreenStateObserver = new NEScreenStateObserver(getActivity());
        mScreenStateObserver.observeScreenStateObserver(screenStateObserver, true);

        Log.e(TAG, "mLIveModel: " + mLiveModel.toString());

        //注册广播
        registerListener();

        //控件初始化
        initView(view);

        //设置用户参数
        setUserData();


        //播放相关设置
        setMediaPlayer();


        setChatRoom();


        //设置监听
        setListener();


        return view;
    }

    private void setUserData() {
        tvUserName.setText(mLiveModel.getNickName());
        tvNumberNumber.setText(mLiveModel.getLiveId());

        String userAcatar = mLiveModel.getAvatar();
        if(userAcatar != null){
            Uri uri = Uri.parse(userAcatar);
            ivUserPhoto.setImageURI(uri);
//            Picasso.with(getActivity())
//                    .load(userAcatar)
//                    .placeholder(R.drawable.iocn_login_logo)
//                    .error(R.drawable.iocn_login_logo)
//                    .into(ivBack);
        }else {
//            ivBack.setBackgroundResource(R.drawable.iocn_login_logo);
            ivUserPhoto.setBackgroundResource(R.drawable.iocn_login_logo);
        }
    }

    //播放相关设置
    private void setMediaPlayer() {

        mMediaController = new NEMediaController(getActivity());
        Drawable drawable = mVideoView.getBackground();
        mVideoView.isInBackground();
        if (mMediaType.equals("localaudio")) { //本地音频文件采用软件解码
            mDecodeType = "software";
        }

        Intent intent = getActivity().getIntent();
        String intentAction = intent.getAction();
        if (!TextUtils.isEmpty(intentAction) && intentAction.equals(Intent.ACTION_VIEW)) {
            mVideoPath = intent.getDataString();
            Log.i(TAG, "videoPath = "+ mVideoPath);
        }

        if (mDecodeType.equals("hardware")) {
            mHardware = true;
        }
        else if (mDecodeType.equals("software")) {
            mHardware = false;
        }

        mUri = Uri.parse(mVideoPath);
        if (mUri != null) { //获取文件名，不包括地址
            List<String> paths = mUri.getPathSegments();
            String name = paths == null || paths.isEmpty() ? "null" : paths.get(paths.size() - 1);
        }
//
        if (mMediaType.equals("livestream")) {
            mVideoView.setBufferStrategy(NEType.NELPLOWDELAY); //直播低延时
        }
        else {
            mVideoView.setBufferStrategy(NEType.NELPANTIJITTER); //点播抗抖动
        }


        mVideoView.setMediaController(mMediaController);
        mVideoView.setMediaType(mMediaType);
        mVideoView.setHardwareDecoder(mHardware);
        mVideoView.setEnableBackgroundPlay(mEnableBackgroundPlay);
        mVideoView.setVideoPath(mVideoPath);
        mVideoView.requestFocus();
        mVideoView.start();

    }

    //设置控件监听
    private void setListener() {

        ivUserPhoto.setOnClickListener(this);

        iv_liaotianshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liveActivity.showInputPanel();
            }
        });
        ibFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDialogFinish();
            }
        });
//        ivGift.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ToastUtils.showLong("点击了");
//                LIveRoomActivity.ShowGift();
//            }
//        });
        ivComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(BaseApplication.getUserNo() == null){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return ;
                }
                if(attentionEntityEntityObject == null){
                    return;
                }
                int stats = attentionEntityEntityObject.getData().getStatus();
                if(BaseApplication.getUserNo() != null){
//                    ChatRoomMsgViewHolderText.blVisible = false;
//                    NimUIKit.startP2PSession(getActivity(), mLiveModel.getUserNo());

                    if(stats == 1){
                        //已关注
                        attentionPresenter.onCreate();
                        attentionPresenter.getAttentionDelAttention(mLiveModel.getUserNo(),"0");
                        attentionPresenter.attachView(AttentionDelView);
                    }else {
                        //未关注
                        attentionPresenter.onCreate();
                        attentionPresenter.getAttentionAddAttention(mLiveModel.getUserNo(),"0");
                        attentionPresenter.attachView(AttentionAddView);

                    }


                }
            }
        });

    }

    //控件初始化
    private void initView(View view) {
        giftNumAnim = new NumAnim();
        inAnim = (TranslateAnimation) AnimationUtils.loadAnimation(getActivity(), R.anim.gift_in);
        outAnim = (TranslateAnimation) AnimationUtils.loadAnimation(getActivity(), R.anim.gift_out);
        llgiftcontent = view.findViewById(R.id.llgiftcontent);
        rlvCheetahStaff = view.findViewById(R.id.rlv_cheetah_staff);
        tvWatchNumber = view.findViewById(R.id.tv_media_preview_watch_number);
        mVideoView = view.findViewById(R.id.video_view);
        iv_liaotianshi = view.findViewById(R.id.iv_liaotianshi);
        tvUserName = view.findViewById(R.id.tv_media_preview_user_name);
        tvNumberNumber = view.findViewById(R.id.tv_media_preview_number_number);
        ivUserPhoto = view.findViewById(R.id.iv_media_preview_user_photo);
        ibFinish = view.findViewById(R.id.iv_media_preview_finish);
//        ivGift = view.findViewById(R.id.iv_meidapreview_gift);
        ivComment = view.findViewById(R.id.ib_media_preview_watch_comment);
        ivBack = view.findViewById(R.id.iv_media_back);
        clearTiming();

    }

    private void setChatRoom() {
        //判断时候关注
        if(BaseApplication.getUserNo() != null){
//            ToastUtils.showLong("开始判断");
            attentionPresenter.onCreate();
            attentionPresenter.getAttentionIsUserAttention(mLiveModel.getUserNo(),"0");
            attentionPresenter.attachView(IsUserArttentionView);
        }

        gridLayoutManager = new GridLayoutManager(getActivity(),1);
        gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        rlvCheetahStaff.setLayoutManager(gridLayoutManager);
        cheetahStaffAdapter = new CheetahStaffAdapter(getActivity(),result);
        rlvCheetahStaff.setAdapter(cheetahStaffAdapter);
    }

    public ITestView AttentionAddView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
           attentionPresenter.onCreate();
            attentionPresenter.getAttentionIsUserAttention(mLiveModel.getUserNo(),"0");
            attentionPresenter.attachView(IsUserArttentionView);
        }

        @Override
        public void onError(Object object) {

        }
    };
    public ITestView AttentionDelView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            attentionPresenter.onCreate();
            attentionPresenter.getAttentionIsUserAttention(mLiveModel.getUserNo(),"0");
            attentionPresenter.attachView(IsUserArttentionView);        }

        @Override
        public void onError(Object object) {

        }
    };


    public ITestView IsUserArttentionView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            attentionEntityEntityObject = (EntityObject<AttentionEntity>) object;
            int stats = attentionEntityEntityObject.getData().getStatus();
            if(stats == 1){
                ivComment.setBackgroundResource(R.drawable.icon_media_preview_attention_ok);
            }else {
                ivComment.setBackgroundResource(R.drawable.icon_media_preview_attention);
            }

        }

        @Override
        public void onError(Object object) {

        }
    };




    /**
     * 点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.iv_media_preview_user_photo:
//                liveActivity.setView();
                break;

        }

    }





    Observer<Integer> localPhoneObserver = new Observer<Integer>() {
        @Override
        public void onEvent(Integer phoneState) {
            if (phoneState == TelephonyManager.CALL_STATE_IDLE) {
                mVideoView.restorePlayWithCall();
            } else if (phoneState == TelephonyManager.CALL_STATE_RINGING) {
                mVideoView.stopPlayWithCall();
            } else {
                Log.i(TAG, "localPhoneObserver onEvent " + phoneState);
            }

        }
    };


    Observer<NEScreenStateObserver.ScreenStateEnum> screenStateObserver = new Observer<NEScreenStateObserver.ScreenStateEnum>() {
        @Override
        public void onEvent(NEScreenStateObserver.ScreenStateEnum screenState) {
            if (screenState == NEScreenStateObserver.ScreenStateEnum.SCREEN_ON) {
                Log.i(TAG, "onScreenOn ");
                if (isScreenOff) {
                    mVideoView.restorePlayWithForeground();
                }
                isScreenOff = false;
            } else if (screenState == NEScreenStateObserver.ScreenStateEnum.SCREEN_OFF) {
                Log.i(TAG, "onScreenOff ");
                isScreenOff = true;
                if (!isBackground) {
                    mVideoView.stopPlayWithBackground();
                }
            } else {
                Log.i(TAG, "onUserPresent ");
//				isScreenOff = false;
            }

        }
    };




    @Override
    public void onDestroy() {
        mMediaController.destroy();
        mVideoView.destroy();
        super.onDestroy();
        NEPhoneCallStateObserver.getInstance().observeLocalPhoneObserver(localPhoneObserver, false);

        mScreenStateObserver.observeScreenStateObserver(screenStateObserver,false);
        mScreenStateObserver = null;

    }

    @Override
    public void finish() {

    }

    @Override
    public void onEnterChatRoomSuc(String roomId) {

    }

    @Override
    public void refreshRoomInfo(ChatRoomInfo roomInfo) {

    }

    @Override
    public void refreshRoomMember(List<ChatRoomMember> result) {
        //设置用户参数

    }


    public static void setUSerPhotoNumber(List<ChatRoomMember> results){
        Log.e(TAG,"人员集合数据集合:"+results.size());
//        cheetahStaffAdapter.getList().addAll(results);
        result.clear();
        result .addAll(results);
        cheetahStaffAdapter.notifyDataSetChanged();
        tvWatchNumber.setText(results.size()+"人在线");
    }


    @Override
    public void dismissMemberOperateLayout() {

    }

    @Override
    public void onChatRoomFinished(String reason) {

    }

    @Override
    public void showTextToast(String text) {

    }



    /**
     * 是否放弃视频
     */
    public void setDialogFinish(){
        mDialog = new ActionSheetDialog(getActivity()).builder();
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setTitle("是否退出观看直播");
        mDialog.addSheetItem("是", ActionSheetDialog.SheetItemColor.Red, new ActionSheetDialog.OnSheetItemClickListener() {

            @Override
            public void onClick(int which) {
                LIveRoomActivity.lIveRoomActivity.finish();
            }
        });
        mDialog.addSheetItem("否", ActionSheetDialog.SheetItemColor.Red, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {

            }
        }).show();
    }



    /**
     * 数字放大动画
     */
    public class NumAnim {
        private Animator lastAnimator = null;
        public void start(View view) {
            if (lastAnimator != null) {
                lastAnimator.removeAllListeners();
                lastAnimator.end();
                lastAnimator.cancel();
            }
            ObjectAnimator anim1 = ObjectAnimator.ofFloat(view, "scaleX",1.3f, 1.0f);
            ObjectAnimator anim2 = ObjectAnimator.ofFloat(view, "scaleY",1.3f, 1.0f);
            AnimatorSet animSet = new AnimatorSet();
            lastAnimator = animSet;
            animSet.setDuration(200);
            animSet.setInterpolator(new OvershootInterpolator());
            animSet.playTogether(anim1, anim2);
            animSet.start();
        }
    }

    /**
     * 定时清除礼物
     */
    private void clearTiming() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                int count = llgiftcontent.getChildCount();
                for (int i = 0; i < count; i++) {
                    View view = llgiftcontent.getChildAt(i);
                    CustomRoundView crvheadimage = (CustomRoundView) view.findViewById(R.id.crvheadimage);
                    long nowtime = System.currentTimeMillis();
                    long upTime = (Long) crvheadimage.getTag();
                    if ((nowtime - upTime) >= 3000) {
                        removeGiftView(i);
                        return;
                    }
                }
            }
        };
        timer = new Timer();
        timer.schedule(task, 0, 3000);
    }

    /**
     * 删除礼物view
     */
    private void removeGiftView(final int index) {
        final View removeView = llgiftcontent.getChildAt(index);
        outAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }
            @Override
            public void onAnimationEnd(Animation animation) {
                llgiftcontent.removeViewAt(index);
            }
            @Override
            public void onAnimationRepeat(Animation animation) { }
        });
        if(getActivity() != null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    removeView.startAnimation(outAnim);
                }
            });
        }

    }

    /**
     * 添加礼物view,(考虑垃圾回收)
     */
    private View addGiftView() {
        View view = null;
        if (giftViewCollection.size() <= 0) {
            /*如果垃圾回收中没有view,则生成一个*/
            view = LayoutInflater.from(getActivity()).inflate(R.layout.item_gift, null);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.topMargin = 10;
            view.setLayoutParams(lp);
            llgiftcontent.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View view) { }
                @Override
                public void onViewDetachedFromWindow(View view) {
                    giftViewCollection.add(view);
                }
            });
        } else {
            view = giftViewCollection.get(0);
            giftViewCollection.remove(view);
        }
        return view;
    }



    /**
     * 显示礼物的方法
     */
    void showGift(IMGift imGift) {
        String tag = imGift.getData().getGiftNo()+""+imGift.getData().getSendUser().getUserNo();
        View giftView = llgiftcontent.findViewWithTag(tag);
        if (giftView == null) {/*该用户不在礼物显示列表*/

            if (llgiftcontent.getChildCount() > 2) {/*如果正在显示的礼物的个数超过两个，那么就移除最后一次更新时间比较长的*/
                View giftView1 = llgiftcontent.getChildAt(0);
                CustomRoundView picTv1 = (CustomRoundView) giftView1.findViewById(R.id.crvheadimage);
                long lastTime1 = (Long) picTv1.getTag();
                View giftView2 = llgiftcontent.getChildAt(1);
                CustomRoundView picTv2 = (CustomRoundView) giftView2.findViewById(R.id.crvheadimage);
                long lastTime2 = (Long) picTv2.getTag();
                if (lastTime1 > lastTime2) {/*如果第二个View显示的时间比较长*/
                    removeGiftView(1);
                } else {/*如果第一个View显示的时间长*/
                    removeGiftView(0);
                }
            }

            giftView = addGiftView();/*获取礼物的View的布局*/
            giftView.setTag(tag);/*设置view标识*/

            CustomRoundView crvheadimage = (CustomRoundView) giftView.findViewById(R.id.crvheadimage);
            final MagicTextView giftNum = (MagicTextView) giftView.findViewById(R.id.giftNum);/*找到数量控件*/
            final SimpleDraweeView ivGift = (SimpleDraweeView) giftView.findViewById(R.id.ivgift);/*找到数量控件*/
            final TextView tvUserName = (TextView) giftView.findViewById(R.id.tv_gift_user_name);/*找到数量控件*/
            final TextView tvGiftName = (TextView) giftView.findViewById(R.id.tv_gift_send_gift_name);/*找到数量控件*/
            giftNum.setText("x1");/*设置礼物数量*/
            crvheadimage.setTag(System.currentTimeMillis());/*设置时间标记*/
            giftNum.setTag(1);/*给数量控件设置标记*/
            String coverPath = imGift.getData().getSendUser().getAvatar();
            String giftPath = imGift.getData().getGiftPic();
            String username = imGift.getData().getSendUser().getNickName();
            String giftName = imGift.getData().getGiftName();

            tvGiftName.setText("送了"+giftName+"给主播 ");
            tvUserName.setText(username);
            Picasso.with(getActivity())
                    .load(coverPath)
                    .placeholder(R.drawable.iocn_login_logo)
                    .error(R.drawable.iocn_login_logo)
                    .into(crvheadimage);

            if(giftPath != null){
                //创建将要下载的图片的URI
                Uri imageUri = Uri.parse(giftPath);
                //开始下载
                ivGift.setImageURI(imageUri);
            }


            llgiftcontent.addView(giftView);/*将礼物的View添加到礼物的ViewGroup中*/
            llgiftcontent.invalidate();/*刷新该view*/
            giftView.startAnimation(inAnim);/*开始执行显示礼物的动画*/
            inAnim.setAnimationListener(new Animation.AnimationListener() {/*显示动画的监听*/
                @Override
                public void onAnimationStart(Animation animation) { }
                @Override
                public void onAnimationEnd(Animation animation) {
                    giftNumAnim.start(giftNum);
                }
                @Override
                public void onAnimationRepeat(Animation animation) { }
            });
        } else {/*该用户在礼物显示列表*/
            CustomRoundView crvheadimage = (CustomRoundView) giftView.findViewById(R.id.crvheadimage);/*找到头像控件*/
            MagicTextView giftNum = (MagicTextView) giftView.findViewById(R.id.giftNum);/*找到数量控件*/
            int showNum = (Integer) giftNum.getTag() + 1;
            giftNum.setText("x"+showNum);
            giftNum.setTag(showNum);
            crvheadimage.setTag(System.currentTimeMillis());
            giftNumAnim.start(giftNum);
        }
    }



    @Override
    public void notifyUpdate(NotifyObject obj) {
        switch (obj.what)
        {
            case 1:
                //开始播放了
//                ivBack.setVisibility(View.GONE);
                break;
        }

    }

    @Override
    public void registerListener() {
        NotifyListenerManager.getInstance().registerListener(this);
    }

    @Override
    public void unRegisterListener() {
        NotifyListenerManager.getInstance().unRegisterListener(this);
    }





}
