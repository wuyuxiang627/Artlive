package com.connxun.elinetv.view.LiveBroadcast;

/**
 * Created by connxun-16 on 2017/12/20.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.connxun.elinetv.IM.NimContract;
import com.connxun.elinetv.IM.NimController;
import com.connxun.elinetv.R;
import com.connxun.elinetv.adapter.Beauty.BeautyTypeAdaper;
import com.connxun.elinetv.adapter.Live.CheetahStaffAdapter;
import com.connxun.elinetv.adapter.VideoShortAdapter.VideoUniversalAdapter;
import com.connxun.elinetv.app.BaseApplication;
import com.connxun.elinetv.app.ConfigSystem;
import com.connxun.elinetv.base.Dialog.ActionSheetDialog;
import com.connxun.elinetv.base.ui.BaseActivity;
import com.connxun.elinetv.entity.BeautyType;
import com.connxun.elinetv.entity.OpenLive;
import com.connxun.elinetv.observer.NotifyObject;
import com.connxun.elinetv.presenter.Live.LivePresenter;
import com.connxun.elinetv.util.DialogUtil;
import com.connxun.elinetv.util.FileUploadUtil;
import com.connxun.elinetv.util.ToastUtils;
import com.connxun.elinetv.util.ui.SeekBarRelativeLayout;
import com.connxun.elinetv.view.user.ITestView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.netease.LSMediaCapture.Statistics;
import com.netease.LSMediaCapture.lsLogUtil;
import com.netease.LSMediaCapture.lsMediaCapture;
import com.netease.LSMediaCapture.lsMessageHandler;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomInfo;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMember;
import com.netease.vcloud.video.effect.VideoEffect;
import com.netease.vcloud.video.render.NeteaseView;
import com.squareup.picasso.Picasso;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.netease.LSMediaCapture.lsMediaCapture.StreamType.AUDIO;

/**
 * 直播界面
 */
@ContentView(R.layout.activity_live)
public class LiveActivity extends BaseActivity implements lsMessageHandler, NimContract.Ui{
    private String TAG = "LiveActivity";

    private LivePresenter presenter = new LivePresenter(this);

    @ViewInject(R.id.btn_text)
    Button btn_test;


    //美颜
    @ViewInject(R.id.seek_face_lift) //瘦脸
    SeekBarRelativeLayout seekBarRelativeFaceLiftLayout;
    @ViewInject(R.id.seek_bar) //美白
    SeekBarRelativeLayout seekBarRelativeWhiteningLayout;
    @ViewInject(R.id.iv_live_start_beauty)
    ImageView ivBeauty; //美颜
    @ViewInject(R.id.tv_live_start_beauty)
    TextView tvBeauty; //美颜
    @ViewInject(R.id.rl_beauty_type)
    RecyclerView rlBeautyType; //美颜-类别
    @ViewInject(R.id.rl_beauty_rl)
    RelativeLayout rl_beauty_rl;

    //聊天室
    @ViewInject(R.id.rlv_cheetah_staff)
    RecyclerView rlvCheetahStaff;
    @ViewInject(R.id.tv_media_preview_watch_number)
    TextView tvWatchNumber;


    //功能
    @ViewInject(R.id.iv_live_window)
    ImageView ivWindow; //横竖屏
    @ViewInject(R.id.iv_live_camera_conversion)
    ImageView ivCameraConversion; //切换摄像头
    @ViewInject(R.id.iv_live_finish)
    ImageView ivFinish; //销毁
    @ViewInject(R.id.iv_live_cover)
    SimpleDraweeView ivCover; //封面
    @ViewInject(R.id.tv_live_address)
    TextView tvAddress; //定位
    @ViewInject(R.id.et_live_title)
    EditText etLiveTitle; //标题
    @ViewInject(R.id.ib_live_wechat)
    ImageButton ibWeCaht; //分享微信
    @ViewInject(R.id.ib_live_webo)
    ImageButton ibWeBo; //分享微博
    @ViewInject(R.id.ib_live_wechat_friend)
    ImageButton ibWeChatFriend; //分享朋友圈
    @ViewInject(R.id.btn_live_start_live)
    Button btnStartLive; //开始直播

    @ViewInject(R.id.il_start)
    RelativeLayout ilStart; //直播功能页面
    @ViewInject(R.id.il_beauty)
    RelativeLayout ilBeauty; //直播美颜页面

    @ViewInject(R.id.il_setting)
    RelativeLayout ilSetting;

    @ViewInject(R.id.live_videoview)
    NeteaseView live_videoview; //图像显示控件

    ArrayList<BeautyType> beautyTypes; //美颜类别
    BeautyTypeAdaper anchorAdapter;

    boolean blWiondw = true; //true:竖屏 false:横屏
    lsMediaCapture.LsMediaCapturePara lsMediaCapturePara;

    //逻辑对象
    private ActionSheetDialog mDialogss;
    private static int REUST_CODE_IMAGE_COVER = 1001; //封面选择


    /**相关控件**/
    boolean frontCamera = true; // 是否前置摄像头
    boolean mScale_16x9 = false; //是否强制16:9
    lsMediaCapture.VideoQuality videoQuality;//视频模板

    /** SDK 相关参数 **/
    private boolean mUseFilter = true;
    private boolean mNeedWater = false;
    private boolean mNeedGraffiti = false;
    private lsMediaCapture mLSMediaCapture = null;
    private lsMediaCapture.LiveStreamingPara mLiveStreamingPara;

    private boolean mVideoCallback = false; //是否对相机采集的数据进行回调（用户在这里可以进行自定义滤镜等）
    private boolean mAudioCallback = false; //是否对麦克风采集的数据进行回调（用户在这里可以进行自定义降噪等）

    /**状态变量**/
    private String mliveStreamingURL = "rtmp://pc7a65f99.live.126.net/live/86e0e127e1dc4cd59543f33d" +
            "b5632396?wsSecret=a38c634ec713988c3dfb2f7146bd3986&wsTime=1513678107"; //推流的url
    private boolean m_liveStreamingOn;
    private boolean m_tryToStopLivestreaming;
    private boolean m_liveStreamingInitFinished;
    private Intent mIntentLiveStreamingStopFinished = new Intent("LiveStreamingStopFinished");
    private DateFormat formatter_file_name = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault());
    private boolean m_startVideoCamera;

    //视频截图相关变量
    private String mScreenShotFilePath = "/sdcard/";//视频截图文件路径
    private String mScreenShotFileName = "test.jpg";//视频截图文件名

    //伴音相关
    private AudioManager mAudioManager;
    private long mLastVideoProcessErrorAlertTime = 0;
    private long mLastAudioProcessErrorAlertTime = 0;

    //按钮控制相关
    private boolean mFlashOn = false;


    long clickTime = 0L;
    private Thread mThread;
    private Handler mHandler;

    private Toast mToast;
    private String path;
    private String imageUrl; //图片的访问位置



    //聊天室
    List<ChatRoomMember> result = new ArrayList<>();
    private GridLayoutManager gridLayoutManager;
    private CheetahStaffAdapter cheetahStaffAdapter;


    private void showToast(final String text){
        if(mToast == null){
            mToast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
        }
        if(Thread.currentThread() != Looper.getMainLooper().getThread()){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mToast.setText(text);
                    mToast.show();
                }
            });
        }else {
            mToast.setText(text);
            mToast.show();
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView(); //控件初始化

        setLSMedia(); //设置直播相关参数

        staticsHandle();//初始化handler对象

        setBeauty(); //美颜相关

        setListener(); //设置控件监听

        setParmeters(); //设置初始化控件参数





    }



    private void setParmeters() {
        tvAddress.setText(BaseApplication.getUserSp().getString("city","beijingshi"));

        path = BaseApplication.getUserSp().getString("liveCoverpath","");
        Picasso.with(context).load(new File(path)).into(ivCover);

    }

    //美颜类别
    private void setBeauty() {
        beautyTypes = new ArrayList<>();
        BeautyType typeNO = new BeautyType();
        typeNO.setImg(R.drawable.icon_type_no);
        typeNO.setText("无滤镜");
        beautyTypes.add(typeNO);
        BeautyType type = new BeautyType();
        type.setImg(R.drawable.icon_type_pink);
        type.setText("粉嫩");
        beautyTypes.add(type);
        BeautyType type_black = new BeautyType();
        type_black.setImg(R.drawable.icon_type_blck_white);
        type_black.setText("黑白");
        beautyTypes.add(type_black);
        BeautyType typeNostalgia = new BeautyType();
        typeNostalgia.setImg(R.drawable.icon_type_pink);
        typeNostalgia.setText("怀旧");
        beautyTypes.add(typeNostalgia);
        BeautyType typeNatural = new BeautyType();
        typeNatural.setImg(R.drawable.icon_type_pink);
        typeNatural.setText("自然");
        beautyTypes.add(typeNatural);

        //类别
        LinearLayoutManager ms= new LinearLayoutManager(this);
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);
        rlBeautyType.setLayoutManager(ms);
        anchorAdapter = new BeautyTypeAdaper(this,beautyTypes);
        rlBeautyType.setAdapter(anchorAdapter);
        anchorAdapter.setSelectedPosition(4);
        ilBeauty.setVisibility(View.INVISIBLE);

        //瘦脸
        seekBarRelativeFaceLiftLayout.initSeekBar();
        seekBarRelativeFaceLiftLayout.setProgress(0);

        seekBarRelativeWhiteningLayout.initSeekBar();
        seekBarRelativeWhiteningLayout.setProgress(0);

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId())
        {
            case R.id.iv_live_window:
                //横竖屏切换
                setWindowRequestedOrientation();
                break;
            case R.id.iv_live_camera_conversion:
                //切换摄像头
                switchCamera();
                break;
            case R.id.iv_live_finish:
                finish();
                break;
            case R.id.tv_live_start_beauty:
            case R.id.iv_live_start_beauty:
                //美颜
                ilStart.setVisibility(View.INVISIBLE);
                ilBeauty.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_live_cover:
                //封面
                setDialog_paizhao();
                break;
            case R.id.ib_live_wechat:
                //微信分享
                break;
            case R.id.ib_live_webo:
                //微博分享
                break;
            case R.id.ib_live_wechat_friend:
                //微信朋友圈分享
                break;
            case R.id.btn_live_start_live:
                //开始直播
                if(TextUtils.isEmpty(etLiveTitle.getText())){
                    ToastUtils.showLong(R.string.live_title_null);
                    return;
                }
                DialogUtil.startWaitingDialog(this,"开启直播中....");
                if(path != null && ! path.equals("")){
                    FileUploadUtil.getUploadImageToken(this,path,1001);
                }
                break;
        }



    }



    private void setListener() {
        rl_beauty_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ilBeauty.setVisibility(View.GONE);
                ilStart.setVisibility(View.VISIBLE);
            }
        });

        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onCreate();
                try {
                    presenter.getHeartBeat(liveNo);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                presenter.attachView(mStartLive);
            }
        });

        //横竖屏
        ivWindow.setOnClickListener(this);
        //切换摄像头
        ivCameraConversion.setOnClickListener(this);
        //销毁
        ivFinish.setOnClickListener(this);
        //美颜
        ivBeauty.setOnClickListener(this);
        tvBeauty.setOnClickListener(this);
        //封面
        ivCover.setOnClickListener(this);
        //微信
        ibWeCaht.setOnClickListener(this);
        //微博
        ibWeBo.setOnClickListener(this);
        //朋友圈
        ibWeChatFriend.setOnClickListener(this);
        //开始直播
        btnStartLive.setOnClickListener(this);

        //类别
        anchorAdapter.setOnItemClickListener(new BeautyTypeAdaper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                anchorAdapter.setSelectedPosition(position);
                anchorAdapter.notifyDataSetChanged();
                seekBarRelativeWhiteningLayout.setProgress(0);
                seekBarRelativeFaceLiftLayout.setProgress(0);
                switch (beautyTypes.get(position).getText())
                {
                    case "无滤镜":
                        mLSMediaCapture.setFilterType(VideoEffect.FilterType.none);
                        break;
                    case "粉嫩":
                        mLSMediaCapture.setFilterType(VideoEffect.FilterType.tender);
                        break;
                    case "黑白":
                        break;
                    case "怀旧":
                        mLSMediaCapture.setFilterType(VideoEffect.FilterType.brooklyn);
                        break;
                    case "自然":
                        mLSMediaCapture.setFilterType(VideoEffect.FilterType.nature);
                        break;
                    default:
                        break;
                }
            }
        });

        //美白
        seekBarRelativeWhiteningLayout.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                if(mLSMediaCapture != null){
                    int param;
                    param = progress/20;
                    mLSMediaCapture.setBeautyLevel(param);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        //瘦脸
        seekBarRelativeFaceLiftLayout.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                if(mLSMediaCapture != null){
                    float param;
                    param = (float)progress/100;
                    mLSMediaCapture.setFilterStrength(param);
                    Log.e(TAG, "onProgressChanged: 滤镜： "+ param);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

//        //闪光灯
//        flashBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(mLSMediaCapture != null){
//                    mFlashOn = !mFlashOn;
//                    mLSMediaCapture.setCameraFlashPara(mFlashOn);
//                    if(mFlashOn){
//                        flashBtn.setImageResource(R.drawable.flashstop);
//                    }else {
//                        flashBtn.setImageResource(R.drawable.flashstart);
//                    }
//                }
//            }
//        });
//
//        //截图按钮
//        captureBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                capture();
//            }
//        });

    }




    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //切换横竖屏，需要在manifest中设置 android:configChanges="orientation|keyboardHidden|screenSize"
        //防止Activity重新创建而断开推流
        if(mLSMediaCapture != null){
            mLSMediaCapture.onConfigurationChanged();
        }
    }

    //横竖屏切换
    private void setWindowRequestedOrientation(){
        if(mScale_16x9){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            mLSMediaCapture.startVideoPreview(live_videoview,frontCamera,true,videoQuality,true);
            mScale_16x9= false;
        }else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            mLSMediaCapture.startVideoPreview(live_videoview,frontCamera,true,videoQuality,false);
            mScale_16x9 = true;
        }
        mLSMediaCapture.setCameraFocus();
    }


    //开始直播/停止直播
    private  void startStopLive(){
        long time = System.currentTimeMillis();
        if(time - clickTime < 1000){
            return;
        }
        clickTime = time;
//        startPauseResumeBtn.setClickable(false);
        if(!m_liveStreamingOn)
        {
            //8、初始化直播推流
            if(mThread != null){
                showToast("正在开启直播，请稍后。。。");
                return;
            }
            showToast("初始化中。。。");
            mThread = new Thread(){
                public void run(){
                    //正常网络下initLiveStream 1、2s就可完成，当网络很差时initLiveStream可能会消耗5-10s，因此另起线程防止UI卡住
                    if(!startAV()){
                        showToast("直播开启失败，请仔细检查推流地址, 正在退出当前界面。。。");
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                LiveActivity.this.finish();
                            }
                        },5000);
                    }
                    mThread = null;
                }
            };
            mThread.start();
//            startPauseResumeBtn.setImageResource(R.drawable.stop);
        }else {
            showToast("停止直播中，请稍等。。。");
            stopAV();
//            startPauseResumeBtn.setImageResource(R.drawable.restart);
        }
    }


    //设置直播相关参数
    private void setLSMedia() {
        //应用运行时，保持屏幕高亮，不锁屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.screenBrightness = 0.7f;
        getWindow().setAttributes(params);

        m_liveStreamingOn = false;
        m_tryToStopLivestreaming = false;

        //1、创建直播实例
        lsMediaCapturePara = new lsMediaCapture.LsMediaCapturePara();
        lsMediaCapturePara.setContext(getApplicationContext()); //设置SDK上下文（建议使用ApplicationContext）
        lsMediaCapturePara.setMessageHandler(this); //设置SDK消息回调
        lsMediaCapturePara.setLogLevel(lsLogUtil.LogLevel.INFO); //日志级别
        lsMediaCapturePara.setUploadLog(true);//是否上传SDK日志
        mLSMediaCapture = new lsMediaCapture(lsMediaCapturePara);

        //2、设置直播参数
        mLiveStreamingPara = new lsMediaCapture.LiveStreamingPara();
        mLiveStreamingPara.setStreamType(lsMediaCapture.StreamType.AV); // 推流类型 AV、AUDIO、VIDEO
        mLiveStreamingPara.setFormatType(lsMediaCapture.FormatType.RTMP); // 推流格式 RTMP、MP4、RTMP_AND_MP4
        mLiveStreamingPara.setRecordPath(ConfigSystem.MEI_FILE_PATH + formatter_file_name.format(new Date()) + ".mp4");//formatType 为 MP4 或 RTMP_AND_MP4 时有效
        mLiveStreamingPara.setQosOn(true);

        videoQuality = lsMediaCapture.VideoQuality.SUPER; //视频模板（SUPER_HIGH 1280*720、SUPER 960*540、HIGH 640*480、MEDIUM 480*360、LOW 352*288）
        mLSMediaCapture.startVideoPreview(live_videoview,frontCamera,true,videoQuality,mScale_16x9);

        m_startVideoCamera = true;

        //默认开启滤镜
//        if(mUseFilter){ //demo中默认设置为干净滤镜
            mLSMediaCapture.setBeautyLevel(0); //磨皮强度为5,共5档，0为关闭
            mLSMediaCapture.setFilterStrength(0.2f); //滤镜强度
            mLSMediaCapture.setFilterType(VideoEffect.FilterType.nature);
//        }
    }
    private void initView() {

    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mLSMediaCapture != null && m_liveStreamingOn) {
            if(mLiveStreamingPara.getStreamType() != AUDIO) {
                //关闭推流固定图像，正常推流
                mLSMediaCapture.resumeVideoEncode();
            }
            else  {
                //关闭推流静音帧
                mLSMediaCapture.resumeAudioEncode();
            }
        }
//        setLSMedia(); //设置直播相关参数
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //停止直播调用相关API接口
        if(mLSMediaCapture != null && m_liveStreamingOn) {

            //停止直播，释放资源
            stopAV();

            //如果音视频或者单独视频直播，需要关闭视频预览
            if(m_startVideoCamera)
            {
                mLSMediaCapture.stopVideoPreview();
                mLSMediaCapture.destroyVideoPreview();
            }

            //反初始化推流实例，当它与stopLiveStreaming连续调用时，参数为false
            mLSMediaCapture.uninitLsMediaCapture(false);
            mLSMediaCapture = null;

            mIntentLiveStreamingStopFinished.putExtra("LiveStreamingStopFinished", 2);
            sendBroadcast(mIntentLiveStreamingStopFinished);
        }

    }


    //开始直播
    private boolean startAV(){
        //6、初始化直播
        m_liveStreamingInitFinished = mLSMediaCapture.initLiveStream(mLiveStreamingPara,mliveStreamingURL);
        if(mLSMediaCapture != null && m_liveStreamingInitFinished) {
            //7、开始直播
            mLSMediaCapture.startLiveStreaming();
            m_liveStreamingOn = true;

            if(mNeedWater){
                //8、设置视频水印参数（可选）
                addWaterMark();
                //9、设置视频动态水印参数（可选）
                addDynamicWaterMark();
            }
            if(mNeedGraffiti){
                //10、设置视频涂鸦参数（可选）
                addGraffiti();
            }
            return true;
        }
        return m_liveStreamingInitFinished;
    }


    //停止直播
    private void stopAV(){
        mGraffitiOn = false;
        if(mGraffitiThread != null){
            try {
                mGraffitiThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(mLSMediaCapture != null){
            mLSMediaCapture.stopLiveStreaming();
        }
    }

    //切换前后摄像头
    private void switchCamera() {
        if(mLSMediaCapture != null) {
            mLSMediaCapture.switchCamera();
        }
    }

    //截图功能
    private void capture(){
        if(mLSMediaCapture != null){
            mLSMediaCapture.enableScreenShot();
        }
    }



    private void addWaterMark(){
        if(mLSMediaCapture != null){
            Bitmap water = BitmapFactory.decodeResource(getResources(),R.drawable.water);
            int x = 120;
            int y = 60;
            mLSMediaCapture.setWaterMarkPara(water, VideoEffect.Rect.leftTop,x,y);
        }
    }

    Bitmap[] bitmaps;
    private void addDynamicWaterMark(){
        if(mLSMediaCapture != null){
            int x = 0;
            int y = 0;
            int fps = 1; //水印的帧率
            boolean looped = true; //是否循环
            String[] waters;
            try {
                waters = getAssets().list("dynamicWaterMark");
                bitmaps = new Bitmap[waters.length];
                for(int i = 0; i< waters.length;i++){
                    waters[i] = "dynamicWaterMark/" + waters[i];
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    Bitmap tmp = BitmapFactory.decodeStream(getAssets().open(waters[i]));
                    bitmaps[i] = tmp;
                }
                mLSMediaCapture.setDynamicWaterMarkPara(bitmaps,VideoEffect.Rect.center,x,y,fps,looped);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Thread mGraffitiThread;
    private boolean mGraffitiOn = false;

    private void addGraffiti(){
        if(mGraffitiThread != null){
            return;
        }
        mGraffitiOn = true;
        mGraffitiThread = new Thread(){
            @Override
            public void run() {
                int x = 180;
                int y = 180;
                while (mGraffitiOn && bitmaps != null && mLSMediaCapture != null){
                    for(Bitmap bitmap:bitmaps){
                        if(!mGraffitiOn){
                            break;
                        }
                        SystemClock.sleep(1000);
                        if(mLSMediaCapture != null){
                            mLSMediaCapture.setGraffitiPara(bitmap,x,y);
                        }
                    }
                }
            }
        };
        mGraffitiThread.start();
    }

    @Override
    public void handleMessage(int msg, Object object) {
        switch (msg) {
            case MSG_INIT_LIVESTREAMING_OUTFILE_ERROR://初始化直播出错
            case MSG_INIT_LIVESTREAMING_VIDEO_ERROR:
            case MSG_INIT_LIVESTREAMING_AUDIO_ERROR:
            {
                showToast("初始化直播出错，正在退出当前界面");
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LiveActivity.this.finish();
                    }
                },3000);
                break;
            }
            case MSG_START_LIVESTREAMING_ERROR://开始直播出错
            {
                showToast("开始直播出错：" + object);
                break;
            }
            case MSG_STOP_LIVESTREAMING_ERROR://停止直播出错
            {
                if(m_liveStreamingOn)
                {
                    showToast("MSG_STOP_LIVESTREAMING_ERROR  停止直播出错");
                }
                break;
            }
            case MSG_AUDIO_PROCESS_ERROR://音频处理出错
            {
                if(m_liveStreamingOn && System.currentTimeMillis() - mLastAudioProcessErrorAlertTime >= 10000)
                {
                    showToast("音频处理出错");
                    mLastAudioProcessErrorAlertTime = System.currentTimeMillis();
                }

                break;
            }
            case MSG_VIDEO_PROCESS_ERROR://视频处理出错
            {
                if(m_liveStreamingOn && System.currentTimeMillis() - mLastVideoProcessErrorAlertTime >= 10000)
                {
                    showToast("视频处理出错");
                    mLastVideoProcessErrorAlertTime = System.currentTimeMillis();
                }
                break;
            }
            case MSG_START_PREVIEW_ERROR://视频预览出错，可能是获取不到camera的使用权限
            {
                Log.i(TAG, "test: in handleMessage, MSG_START_PREVIEW_ERROR");
                showToast("无法打开相机，可能没有相关的权限或者自定义分辨率不支持");
                break;
            }
            case MSG_AUDIO_RECORD_ERROR://音频采集出错，获取不到麦克风的使用权限
            {
                showToast("无法开启；录音，可能没有相关的权限");
                Log.i(TAG, "test: in handleMessage, MSG_AUDIO_RECORD_ERROR");
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LiveActivity.this.finish();
                    }
                },3000);
                break;
            }
            case MSG_RTMP_URL_ERROR://断网消息
            {
                Log.i(TAG, "test: in handleMessage, MSG_RTMP_URL_ERROR");
                showToast("MSG_RTMP_URL_ERROR，推流已停止,正在退出当前界面");
//                mHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        LiveActivity.this.finish();
//                    }
//                },3000);
                break;
            }
            case MSG_URL_NOT_AUTH://直播URL非法，URL格式不符合视频云要求
            {
                showToast("MSG_URL_NOT_AUTH  直播地址不合法");
                break;
            }
            case MSG_SEND_STATICS_LOG_ERROR://发送统计信息出错
            {
                //Log.i(TAG, "test: in handleMessage, MSG_SEND_STATICS_LOG_ERROR");
                break;
            }
            case MSG_SEND_HEARTBEAT_LOG_ERROR://发送心跳信息出错
            {
                //Log.i(TAG, "test: in handleMessage, MSG_SEND_HEARTBEAT_LOG_ERROR");
                break;
            }
            case MSG_AUDIO_SAMPLE_RATE_NOT_SUPPORT_ERROR://音频采集参数不支持
            {
                Log.i(TAG, "test: in handleMessage, MSG_AUDIO_SAMPLE_RATE_NOT_SUPPORT_ERROR");
                break;
            }
            case MSG_AUDIO_PARAMETER_NOT_SUPPORT_BY_HARDWARE_ERROR://音频参数不支持
            {
                Log.i(TAG, "test: in handleMessage, MSG_AUDIO_PARAMETER_NOT_SUPPORT_BY_HARDWARE_ERROR");
                break;
            }
            case MSG_NEW_AUDIORECORD_INSTANCE_ERROR://音频实例初始化出错
            {
                Log.i(TAG, "test: in handleMessage, MSG_NEW_AUDIORECORD_INSTANCE_ERROR");
                break;
            }
            case MSG_AUDIO_START_RECORDING_ERROR://音频采集出错
            {
                Log.i(TAG, "test: in handleMessage, MSG_AUDIO_START_RECORDING_ERROR");
                break;
            }
            case MSG_QOS_TO_STOP_LIVESTREAMING://网络QoS极差，视频码率档次降到最低
            {
                showToast("MSG_QOS_TO_STOP_LIVESTREAMING");
                Log.i(TAG, "test: in handleMessage, MSG_QOS_TO_STOP_LIVESTREAMING");
                break;
            }
            case MSG_HW_VIDEO_PACKET_ERROR://视频硬件编码出错反馈消息
            {
                break;
            }
            case MSG_WATERMARK_INIT_ERROR://视频水印操作初始化出错
            {
                break;
            }
            case MSG_WATERMARK_PIC_OUT_OF_VIDEO_ERROR://视频水印图像超出原始视频出错
            {
                //Log.i(TAG, "test: in handleMessage: MSG_WATERMARK_PIC_OUT_OF_VIDEO_ERROR");
                break;
            }
            case MSG_WATERMARK_PARA_ERROR://视频水印参数设置出错
            {
                //Log.i(TAG, "test: in handleMessage: MSG_WATERMARK_PARA_ERROR");
                break;
            }
            case MSG_CAMERA_PREVIEW_SIZE_NOT_SUPPORT_ERROR://camera采集分辨率不支持
            {
                //Log.i(TAG, "test: in handleMessage: MSG_CAMERA_PREVIEW_SIZE_NOT_SUPPORT_ERROR");
                break;
            }
            case MSG_CAMERA_NOT_SUPPORT_FLASH:
                showToast("不支持闪光灯");
                break;
            case MSG_START_PREVIEW_FINISHED://camera采集预览完成
            {
                Log.i(TAG, "test: MSG_START_PREVIEW_FINISHED");
                break;
            }
            case MSG_START_LIVESTREAMING_FINISHED://开始直播完成
            {
                Log.i(TAG, "test: MSG_START_LIVESTREAMING_FINISHED");
//                showToast("直播开始");
                DialogUtil.dismissWaitingDialog();
                m_liveStreamingOn = true;
//                startPauseResumeBtn.setClickable(true);
                break;
            }
            case MSG_STOP_LIVESTREAMING_FINISHED://停止直播完成
            {
                Log.i(TAG, "test: MSG_STOP_LIVESTREAMING_FINISHED");
                showToast("停止直播已完成");
                m_liveStreamingOn = false;
//                startPauseResumeBtn.setClickable(true);
                {
                    mIntentLiveStreamingStopFinished.putExtra("LiveStreamingStopFinished", 1);
                    sendBroadcast(mIntentLiveStreamingStopFinished);
                }

                break;
            }
            case MSG_STOP_VIDEO_CAPTURE_FINISHED:
            {
                Log.i(TAG, "test: in handleMessage: MSG_STOP_VIDEO_CAPTURE_FINISHED");
                break;
            }
            case MSG_STOP_AUDIO_CAPTURE_FINISHED:
            {
                Log.i(TAG, "test: in handleMessage: MSG_STOP_AUDIO_CAPTURE_FINISHED");
                break;
            }
            case MSG_SWITCH_CAMERA_FINISHED://切换摄像头完成
            {
                int cameraId = (Integer) object;//切换之后的camera id
                break;
            }
            case MSG_SEND_STATICS_LOG_FINISHED://发送统计信息完成
            {
                break;
            }
            case MSG_SERVER_COMMAND_STOP_LIVESTREAMING://服务器下发停止直播的消息反馈，暂时不使用
            {
                break;
            }
            case MSG_GET_STATICS_INFO://获取统计信息的反馈消息
            {
                Message message = Message.obtain(mHandler, MSG_GET_STATICS_INFO);
                Statistics statistics = (Statistics) object;

                Bundle bundle = new Bundle();
                bundle.putInt("FR", statistics.videoEncodeFrameRate);
                bundle.putInt("VBR", statistics.videoRealSendBitRate);
                bundle.putInt("ABR", statistics.audioRealSendBitRate);
                bundle.putInt("TBR", statistics.totalRealSendBitRate);
                bundle.putInt("networkLevel", statistics.networkLevel);
                bundle.putString("resolution", statistics.videoEncodeWidth + " x " + statistics.videoEncodeHeight);
                message.setData(bundle);

                if(mHandler != null) {
                    mHandler.sendMessage(message);
                }
                break;
            }
            case MSG_BAD_NETWORK_DETECT://如果连续一段时间（10s）实际推流数据为0，会反馈这个错误消息
            {
                showToast("MSG_BAD_NETWORK_DETECT");
                break;
            }
            case MSG_SCREENSHOT_FINISHED://视频截图完成后的消息反馈
            {
                getScreenShotByteBuffer((Bitmap) object);

                break;
            }
            case MSG_SET_CAMERA_ID_ERROR://设置camera出错（对于只有一个摄像头的设备，如果调用了不存在的摄像头，会反馈这个错误消息）
            {
                break;
            }
            case MSG_SET_GRAFFITI_ERROR://设置涂鸦出错消息反馈
            {
                break;
            }
            case MSG_MIX_AUDIO_FINISHED://伴音一首MP3歌曲结束后的反馈
            {
                break;
            }
            case MSG_URL_FORMAT_NOT_RIGHT://推流url格式不正确
            {
                showToast("MSG_URL_FORMAT_NOT_RIGHT");
                break;
            }
            case MSG_URL_IS_EMPTY://推流url为空
            {
                break;
            }

            case MSG_SPEED_CALC_SUCCESS:
            case MSG_SPEED_CALC_FAIL: //测速
                Message message = Message.obtain(mHandler, msg);
                message.obj = object;
                mHandler.sendMessage(message);
//                mSpeedCalcRunning = false;
                break;

            default:
                break;

        }
    }


    public void staticsHandle() {

    }

    //获取截屏图像的数据
    public void getScreenShotByteBuffer(Bitmap bitmap) {
        FileOutputStream outStream = null;
        String screenShotFilePath = mScreenShotFilePath + mScreenShotFileName;
        try {

            outStream = new FileOutputStream(String.format(screenShotFilePath));
            bitmap.compress(Bitmap.CompressFormat.PNG,100,outStream);
            showToast("截图已保存到SD下的test.jpg");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(outStream != null){
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //选择照片
    private void setDialog_paizhao() {
//        PhotoUtil.selectPictureFromAlbum(LiveActivity.this);

        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, REUST_CODE_IMAGE_COVER);
//        ImageMatisee.getImagePath(this,1,REUST_CODE_IMAGE_COVER);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Uri> mSelected = new ArrayList<>();
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == REUST_CODE_IMAGE_COVER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                path = images.get(0).path;
                Picasso.with(context).load(new File(path)).into(ivCover);
                SharedPreferences.Editor editor= BaseApplication.getUserSp().edit();
                editor.putString("liveCoverpath",path);
                editor.commit();
                Log.e(TAG,"你知道的: "+ images.get(0).path);
            }

//        if (requestCode == REUST_CODE_IMAGE_COVER && resultCode == RESULT_OK) {
//
//
//            if (data != null ) {
//                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
//                Log.e(TAG,"你知道的: "+ images.size());
//            }

//            mSelected = Matisse.obtainResult(data);
//            Logger.d( "mSelected: " + mSelected.get(0).toString());
//           path =  ImageMatisee.getRealPathFromUri(this,mSelected.get(0));
//            Logger.d("文件路径: "+path );
//            Picasso.with(context).load(new File(path)).into(ivCover);
//            SharedPreferences.Editor editor= BaseApplication.getUserSp().edit();
//            editor.putString("liveCoverpath",path);
//            editor.commit();
        }
    }

    @Override
    public void notifyUpdate(NotifyObject obj) {

        switch (obj.what)
        {
            case 1:
                //直播封面上传成功
               imageUrl =  obj.str;


                if(path == null && path.equals("")){
                    return;
                }
                //C:\Program Files (x86)\Java\jdk1.8.0_144


                String cityCode = BaseApplication.getUserSp().getString("cityCode","");

                if(cityCode.equals("")){
                    return;
                }

                presenter.onCreate();
                try {
                    presenter.getLiveOpenLive(cityCode,"0");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                presenter.attachView(mOpenLive);

                break;

        }
    }

    //直播频道
    private String liveNo;
    private NimController nimController;
    /**
     * 创建直播
     */
    public ITestView mOpenLive  = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            OpenLive openLive = (OpenLive) object;
            //直播频道
            liveNo = openLive.getData().getLiveNo();
            //首先加入聊天室
            nimController = new NimController(LiveActivity.this, LiveActivity.this,openLive.getData().getRoomid()+"");
            nimController.onHandleIntent(getIntent());
            //推流地址
            mliveStreamingURL = openLive.getData().getPushUrl();
            Log.e("LivePresenter","图片访问地址: "+ imageUrl);
            try {
                presenter.onCreate();
                presenter.getLiveStartLive(etLiveTitle.getText().toString(),imageUrl,liveNo);
                presenter.attachView(mStartLive);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onError(Object object) {

        }
    };

    /**
     * 开始直播
     */
    public ITestView mStartLive = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            ilStart.setVisibility(View.GONE);
            ilSetting.setVisibility(View.VISIBLE);
            //开始直播
            startAV();

//            TestThread tt = new TestThread();
//            tt.start();

        }

        @Override
        public void onError(Object object) {

        }
    };

    @Override
    public void onEnterChatRoomSuc(String roomId) {

    }

    @Override
    public void refreshRoomInfo(ChatRoomInfo roomInfo) {

    }

    @Override
    public void refreshRoomMember(List<ChatRoomMember> result) {
        Log.e(TAG,"人员集合数据集合:"+result.size());
        cheetahStaffAdapter.getList().addAll(result);
        cheetahStaffAdapter.notifyDataSetChanged();
        tvWatchNumber.setText(result.size()+"人在线");
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