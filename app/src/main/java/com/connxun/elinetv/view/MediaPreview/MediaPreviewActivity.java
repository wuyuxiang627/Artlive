package com.connxun.elinetv.view.MediaPreview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.connxun.elinetv.IM.NimContract;
import com.connxun.elinetv.IM.NimController;
import com.connxun.elinetv.R;
import com.connxun.elinetv.base.ui.BaseActivity;
import com.connxun.elinetv.entity.LiveModel;
import com.connxun.elinetv.receiver.NEPhoneCallStateObserver;
import com.connxun.elinetv.receiver.NEScreenStateObserver;
import com.connxun.elinetv.receiver.Observer;
import com.connxun.elinetv.view.MediaPreview.media.NEMediaController;
import com.connxun.elinetv.view.MediaPreview.media.NEVideoView;
import com.netease.neliveplayer.sdk.constant.NEType;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomInfo;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMember;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by connxun-16 on 2017/12/19.
 */

/**
 * 直播播放
 */
@ContentView(R.layout.activity_mediapreview)
public class MediaPreviewActivity extends BaseActivity implements View.OnClickListener, NimContract.Ui{


    public final static String TAG = MediaPreviewActivity.class.getSimpleName();
    /**控件**/
    @ViewInject(R.id.video_view)
    NEVideoView mVideoView;  //用于画面显示

    @ViewInject(R.id.tv_media_preview_user_name)
    TextView tvUserName; //用户名称
    @ViewInject(R.id.tv_media_preview_watch_number)
    TextView tvWatchNumber; //多少人观看
    @ViewInject(R.id.tv_media_preview_number_number)
    TextView tvNumberNumber; //直播间号


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


    //聊天室相关
    private NimController nimController;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mVideoPath = getIntent().getStringExtra("mVideoPath");
        mLiveModel = (LiveModel) getIntent().getSerializableExtra("mLiveModel");
        mScreenStateObserver = new NEScreenStateObserver(this);
        mScreenStateObserver.observeScreenStateObserver(screenStateObserver,true);

        Log.e(TAG,"mLIveModel: "+ mLiveModel.toString());



        //控件初始化
        initView();

        //设置用户参数
        setUserData();


        //设置监听
        setListener();

        //播放相关设置
        setMediaPlayer();

        //首先加入聊天室
        nimController = new NimController(this, this);
        nimController.onHandleIntent(getIntent());

    }

    private void setUserData() {
//        tvUserName.setText(mLiveModel.getNickName());
//        tvNumberNumber.setText(mLiveModel.getLiveId());
//        tvWatchNumber.setText(mLiveModel.getViewNum()+"人在线");
    }

    //播放相关设置
    private void setMediaPlayer() {

        mMediaController = new NEMediaController(this);

        if (mMediaType.equals("localaudio")) { //本地音频文件采用软件解码
            mDecodeType = "software";
        }

        Intent intent = getIntent();
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
           // setFileName(name);//显示标题
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

//        //步骤一：添加一个FragmentTransaction的实例
//        FragmentManager fragmentManager =getFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//
//        //步骤二：用add()方法加上Fragment的对象rightFragment
//        ChatRoomMessageFragment chatRoomMessageFragment = new ChatRoomMessageFragment();
//        transaction.add(R.id.ll_liaotianshi,chatRoomMessageFragment);
//
//        //步骤三：调用commit()方法使得FragmentTransaction实例的改变生效
//        transaction.commit();

    }

    //设置控件监听
    private void setListener() {

    }

    //控件初始化
    private void initView() {
        mVideoView = findViewById(R.id.video_view);
    }


    /**
     * 点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {

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
    protected void onDestroy() {
        mMediaController.destroy();
        mVideoView.destroy();
        super.onDestroy();
        NEPhoneCallStateObserver.getInstance().observeLocalPhoneObserver(localPhoneObserver, false);

        mScreenStateObserver.observeScreenStateObserver(screenStateObserver,false);
        mScreenStateObserver = null;

    }

    @Override
    public void onEnterChatRoomSuc(String roomId) {

    }

    @Override
    public void refreshRoomInfo(ChatRoomInfo roomInfo) {

    }

    @Override
    public void refreshRoomMember(List<ChatRoomMember> result) {

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
}
