package com.connxun.elinetv.view.VideoShort;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.connxun.elinetv.R;
import com.connxun.elinetv.app.BaseApplication;
import com.connxun.elinetv.base.ui.BaseActivity;
import com.connxun.elinetv.entity.AttentionEntity;
import com.connxun.elinetv.entity.EntityObject;
import com.connxun.elinetv.entity.GetVideoInfo;
import com.connxun.elinetv.entity.VideoEtivity;
import com.connxun.elinetv.observer.NotifyListenerManager;
import com.connxun.elinetv.observer.NotifyObject;
import com.connxun.elinetv.presenter.user.AttentionPresenter;
import com.connxun.elinetv.presenter.video.VideoPresenter;
import com.connxun.elinetv.receiver.NEPhoneCallStateObserver;
import com.connxun.elinetv.receiver.NEScreenStateObserver;
import com.connxun.elinetv.receiver.Observer;
import com.connxun.elinetv.util.ToastUtils;
import com.connxun.elinetv.view.MediaPreview.media.NEMediaController;
import com.connxun.elinetv.view.MediaPreview.media.NEVideoView;
import com.connxun.elinetv.view.Video.fragment.SquareFragment;
import com.connxun.elinetv.view.user.ITestView;
import com.connxun.elinetv.view.user.login.LoginActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.netease.neliveplayer.sdk.constant.NEType;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by connxun-16 on 2018/2/9.
 */

@ContentView(R.layout.activity_video_play)
public class VideoPlayActivity extends BaseActivity {


    private String TAG = "VideoPlayActivity";

    @ViewInject(R.id.rl_video_play_talk)
    RelativeLayout rlVideoPlayTalk; //评论
    @ViewInject(R.id.rl_video_play_like)
    RelativeLayout rlVideoPlayLike;//点赞


    @ViewInject(R.id.nv_video_view)
    NEVideoView mVideoView;
    @ViewInject(R.id.iv_video_play_user_photo)
    SimpleDraweeView ivUserPhoto;
    @ViewInject(R.id.iv_video_play_finish)
    ImageButton ibFinish;
    @ViewInject(R.id.ib_play_video_follow)
    ImageButton ibUserFollow;
    @ViewInject(R.id.tv_video_play_user_name)
    TextView tvUsername;
    @ViewInject(R.id.tv_ll_play_video_title)
    TextView tvVideoTitle;
    @ViewInject(R.id.tv_video_play_like_number)
    TextView tvLikeNumber;

    @ViewInject(R.id.ib_play_video_follow)
    ImageButton ivFollow; //关注
    @ViewInject(R.id.iv_play_video_like)
    ImageView ivLike; //点赞

    private View mBuffer; //用于指示缓冲状态
    private NEMediaController mMediaController; //用于控制播放

    private String mVideoPath; //文件路径
    private String mDecodeType;//解码类型，硬解或软解
    private String mMediaType; //媒体类型
    private boolean mHardware = true;
    private ImageButton mPlayBack;
    private TextView mFileName; //文件名称
    private String mTitle;
    private Uri mUri;
    private VideoEtivity positionVideoEntity;
    private EntityObject<GetVideoInfo> videoInfoEntity;

    /**
     * mEnableBackgroundPlay 控制退到后台或者锁屏时是否继续播放，开发者可根据实际情况灵活开发,我们的示例逻辑如下：
     * <p>
     * mEnableBackgroundPlay 为 false时，
     * 使用软件编码或者硬件解码，点播进入后台暂停，进入前台恢复播放，直播进入后台停止播放，进入前台重新拉流播放
     * <p>
     * mEnableBackgroundPlay 为 true时，
     * 使用软件编码，点播和直播进入后台不做处理，继续播放
     * 使用硬件解码，点播和直播进入后台统一停止播放，进入前台的话重新拉流播放
     */

    private boolean mEnableBackgroundPlay = true;
    private boolean mBackPressed;

    private RelativeLayout mPlayToolbar;
    private NEScreenStateObserver mScreenStateObserver;
    private boolean isScreenOff;
    private boolean isBackground;

    //是否关注
    EntityObject<AttentionEntity> attentionEntityEntityObject;
    AttentionPresenter attentionPresenter = new AttentionPresenter(this);
    VideoPresenter videoPresenter = new VideoPresenter(this);



//    protected void onCreate(@Nullable Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
//        setOnClick();
//        registerListener();
//    }






    @Override
    protected void onStart() {
        Log.i(TAG, "NEVideoPlayerActivity onStart");
        super.onStart();
        initIntent();

        setUserDate();

        initPlayVideoParameters();

        //判断时候关注
        if(BaseApplication.getUserNo() != null){
//            ToastUtils.showLong("开始判断");
            attentionPresenter.onCreate();
            attentionPresenter.getAttentionIsUserAttention(positionVideoEntity.getUserNo(),"0");
            attentionPresenter.attachView(IsUserArttentionView);
        }
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "NEVideoPlayerActivity onPause");

        super.onPause();
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "NEVideoPlayerActivity onDestroy");
        mMediaController.destroy();
        mVideoView.destroy();
        super.onDestroy();
        NEPhoneCallStateObserver.getInstance().observeLocalPhoneObserver(localPhoneObserver, false);
        mScreenStateObserver.observeScreenStateObserver(screenStateObserver, false);
        mScreenStateObserver = null;
        Intent intent = new Intent();
        intent.putExtra("positionVideoEntity",positionVideoEntity);
        this.setResult(100,intent);
    }

    public ITestView AttentionAddView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            attentionPresenter.onCreate();
            attentionPresenter.getAttentionIsUserAttention(positionVideoEntity.getUserNo(),"0");
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
            attentionPresenter.getAttentionIsUserAttention(positionVideoEntity.getUserNo(),"0");
            attentionPresenter.attachView(IsUserArttentionView);        }

        @Override
        public void onError(Object object) {
            String msg = (String) object;
            ToastUtils.showLong(msg);

        }
    };


    public ITestView IsUserArttentionView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            attentionEntityEntityObject = (EntityObject<AttentionEntity>) object;
            int stats = attentionEntityEntityObject.getData().getStatus();
            if(stats == 1){
                ivFollow.setBackgroundResource(R.drawable.icon_media_preview_attention_ok);
            }else {
                ivFollow.setBackgroundResource(R.drawable.icon_media_preview_attention);
            }

        }

        @Override
        public void onError(Object object) {

        }
    };




    private void setUserDate() {
        //创建将要下载的图片的URI
        if(positionVideoEntity.getAvatar() != null){
            Uri imageUri = Uri.parse(positionVideoEntity.getAvatar());
//        开始下载
            ivUserPhoto.setImageURI(imageUri);
        }

        String state = videoInfoEntity.getData().getUser().getState();
        if(state.equals("1")){
            ivLike.setBackgroundResource(R.drawable.icon_play_video_like_ok);
        }else{
            ivLike.setBackgroundResource(R.drawable.icon_play_video_like_no);
        }




        tvUsername.setText(positionVideoEntity.getNickName());

        tvLikeNumber.setText(positionVideoEntity.getLoveNum()+"");

        tvVideoTitle.setText(positionVideoEntity.getTitle());

    }

    @Override
    protected void onResume() {
        Log.i(TAG, "NEVideoPlayerActivity onResume");

        super.onResume();
        if (!mBackPressed && !isScreenOff && isBackground) {
//			mVideoView.restorePlayWithForeground();
            isBackground = false;
        }
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "NEVideoPlayerActivity onStop");
        super.onStop();

        if (!mBackPressed && !isScreenOff) {
            mVideoView.stopPlayWithBackground();
            isBackground = true;
        }
    }


    @Override
    protected void onRestart() {
        Log.i(TAG, "NEVideoPlayerActivity onRestart");
        super.onRestart();
    }


    @Override
    public void setOnClick() {
        super.setOnClick();
        rlVideoPlayTalk.setOnClickListener(this);
        ivFollow.setOnClickListener(this);
        rlVideoPlayLike.setOnClickListener(this);
        ibFinish.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId())
        {
            case R.id.iv_video_play_finish:
                finish();
                break;
            case R.id.rl_video_play_talk:
                Intent intent = new Intent(this,VideoPlayTalkActivity.class);
                intent.putExtra("videomodel",positionVideoEntity);
                intent.putExtra("videoNO",positionVideoEntity.getVideoNo()+"");
                startActivity(intent);
                break;
            case R.id.ib_play_video_follow:
                if(BaseApplication.getUserNo() == null){
                    startActivity(new Intent(this, LoginActivity.class));
                    return ;
                }
                if(attentionEntityEntityObject == null){
                    return;
                }
                        int stats = attentionEntityEntityObject.getData().getStatus();
                        if(BaseApplication.getUserNo() != null){
                            if(stats == 1){
                                //已关注
                                attentionPresenter.onCreate();
                                attentionPresenter.getAttentionDelAttention(positionVideoEntity.getUserNo(),"0");
                                attentionPresenter.attachView(AttentionDelView);
                            }else {
                                //未关注
                                attentionPresenter.onCreate();
                                attentionPresenter.getAttentionAddAttention(positionVideoEntity.getUserNo(),"0");
                                attentionPresenter.attachView(AttentionAddView);

                            }

                        }
                break;
            case R.id.rl_video_play_like:
                if(videoInfoEntity.getData().getUser().getState().equals("1")){
                    //已点赞
                    videoPresenter.onCreate();
                    videoPresenter.getNolove(positionVideoEntity.getVideoNo()+"");
                    videoPresenter.attachView(NoloveView);
//                    rlVideoPlayLike.setBackgroundResource(R.drawable.back_orange);
                }else {
                    //未点赞
                    videoPresenter.onCreate();
                    videoPresenter.getLoveSumAdd(positionVideoEntity.getVideoNo()+"");
                    videoPresenter.attachView(LoveSumAddView);

//                    rlVideoPlayLike.setBackgroundResource(R.drawable.back_white);
                }
                break;
        }
    }


    public ITestView LoveSumAddView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            videoInfoEntity.getData().getUser().setState("1");
            long num =  Long.parseLong(tvLikeNumber.getText().toString());
            positionVideoEntity.setLoveNum((int) (num+1));
            tvLikeNumber.setText(num+1+"");
            ivLike.setBackgroundResource(R.drawable.icon_play_video_like_ok);
            NotifyListenerManager.getInstance().notifyListener(new NotifyObject(1), SquareFragment.class);//传参数通知
        }

        @Override
        public void onError(Object object) {

        }
    };

    public ITestView NoloveView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            videoInfoEntity.getData().getUser().setState("2");
            long num =  Long.parseLong(tvLikeNumber.getText().toString());
            tvLikeNumber.setText(num-1+"");
            positionVideoEntity.setLoveNum((int) (num-1));
            ivLike.setBackgroundResource(R.drawable.icon_play_video_like_no);
            NotifyListenerManager.getInstance().notifyListener(new NotifyObject(2), SquareFragment.class);//传参数通知

        }

        @Override
        public void onError(Object object) {

        }
    };





    private void initIntent() {
        mDecodeType   = "software";
        mMediaType = "videoondemand";
        mVideoPath  = getIntent().getStringExtra("videoPath");
        videoInfoEntity  = (EntityObject<GetVideoInfo>) getIntent().getSerializableExtra("videoInfoEntity");
        positionVideoEntity = (VideoEtivity) getIntent().getSerializableExtra("positionVideoEntity");
    }

    private void initPlayVideoParameters() {

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //保持屏幕常亮
        NEPhoneCallStateObserver.getInstance().observeLocalPhoneObserver(localPhoneObserver, true);
        mScreenStateObserver = new NEScreenStateObserver(this);
        mScreenStateObserver.observeScreenStateObserver(screenStateObserver,true);

        mHardware = false;

        mMediaController = new NEMediaController(this);

        mVideoView.setBufferStrategy(NEType.NELPANTIJITTER); //点播抗抖动

        mVideoView.setMediaController(mMediaController);
        mVideoView.setBufferingIndicator(mBuffer);
        mVideoView.setMediaType(mMediaType);
        mVideoView.setHardwareDecoder(mHardware);
        mVideoView.setEnableBackgroundPlay(mEnableBackgroundPlay);
        mVideoView.setVideoPath(mVideoPath);
        mVideoView.requestFocus();
        mVideoView.start();

        mMediaController.setOnShownListener(mOnShowListener); //监听mediacontroller是否显示
        mMediaController.setOnHiddenListener(mOnHiddenListener); //监听mediacontroller是否隐藏

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

    View.OnClickListener mOnClickEvent = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            }
        }
    };

    NEMediaController.OnShownListener mOnShowListener = new NEMediaController.OnShownListener() {

        @Override
        public void onShown() {
            mVideoView.invalidate();
        }
    };

    NEMediaController.OnHiddenListener mOnHiddenListener = new NEMediaController.OnHiddenListener() {

        @Override
        public void onHidden() {
        }
    };


    @Override
    public void onBackPressed() {
        Log.i(TAG, "onBackPressed");
        mBackPressed = true;
        finish();

        super.onBackPressed();
    }


}
