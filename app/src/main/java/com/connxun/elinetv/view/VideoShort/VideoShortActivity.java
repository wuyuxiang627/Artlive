package com.connxun.elinetv.view.VideoShort;


import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

//import com.daimajia.numberprogressbar.NumberProgressBar;
import com.connxun.elinetv.adapter.Beauty.BeautyTypeAdaper;
import com.connxun.elinetv.base.Dialog.ActionSheetDialog;
import com.connxun.elinetv.base.ui.BaseActivity;
import com.connxun.elinetv.base.util.LogUtil;
import com.connxun.elinetv.entity.BeautyType;
import com.connxun.elinetv.entity.VideoItem;
import com.connxun.elinetv.util.SteppingProgressBar;
import com.connxun.elinetv.util.ToastUtils;
import com.connxun.elinetv.util.ui.SeekBarRelativeLayout;
import com.netease.LSMediaCapture.lsMessageHandler;
import com.connxun.elinetv.R;
import com.connxun.elinetv.shortvideo.MediaCaptureController;
import com.connxun.elinetv.shortvideo.model.MediaCaptureOptions;
import com.connxun.elinetv.shortvideo.model.ResolutionType;
import com.connxun.elinetv.shortvideo.model.VideoCaptureParams;
import com.connxun.elinetv.shortvideo.videoprocess.VideoProcessController;
import com.netease.fulive.EffectAndFilterSelectAdapter;
import com.netease.fulive.FuVideoEffect;
import com.netease.nim.uikit.common.util.storage.StorageType;
import com.netease.nim.uikit.common.util.storage.StorageUtil;
import com.netease.nim.uikit.support.permission.MPermission;
import com.netease.nim.uikit.support.permission.annotation.OnMPermissionDenied;
import com.netease.nim.uikit.support.permission.annotation.OnMPermissionGranted;
import com.netease.nim.uikit.support.permission.annotation.OnMPermissionNeverAskAgain;
import com.netease.transcoding.record.VideoCallback;
import com.netease.vcloud.video.render.NeteaseView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by connxun-16 on 2018/1/19.
 * https://github.com/Faceunity/FULiveDemoDroid
 * 短视频
 */
@ContentView(R.layout.activity_video_short)
public class VideoShortActivity extends BaseActivity implements MediaCaptureController.MediaCaptureControllerCallback,
        View.OnClickListener, VideoProcessController.VideoProcessCallback {

    public static VideoShortActivity videoShortActivity_instance = null;

    @ViewInject(R.id.camerasurfaceview)
    private NeteaseView videoView; //控件

    //功能布局-
    @ViewInject(R.id.il_layout_video_short_features)
    private  RelativeLayout ilFeatures; //功能布局
    @ViewInject(R.id.rl_layout_video_short_expression)
    private RelativeLayout faceuBtn; //第三方美颜
    @ViewInject(R.id.rl_layout_video_short_beauty)//美颜
    private RelativeLayout beautyBtn;
    @ViewInject(R.id.rl_faceu_finiish) //表情销毁
    private RelativeLayout rlFaceuFiniish;
    @ViewInject(R.id.rl_layout_video_short_soundtrack)
    private RelativeLayout rlSoundTrack; //配乐
    @ViewInject(R.id.pb_layout_video_short_schedule)
    SteppingProgressBar mProgressBar;

    //顶部
    @ViewInject(R.id.iv_layout_video_short_finish)
    private ImageView ivFinish; //销毁
    @ViewInject(R.id.iv_layout_video_short_camera_conversion)
    private ImageView ivCameraConversion; //切换摄像头


    //底部
    @ViewInject(R.id.btn_video_short_features_start_stop)
    private Button btnStartStop; //开始/暂停
    @ViewInject(R.id.iv_video_short_features_ok)
    private ImageView ivOk; //确认
    @ViewInject(R.id.iv_video_short_features_back)
    private ImageView ivDeleteVideo;  //删除上一段视频


    //中部
    @ViewInject(R.id.rg_video_short_ffeatures_speed)
    private RadioGroup rgSpeed; //速度
    @ViewInject(R.id.rb_video_short_features_very_slow)
    private RadioButton rbSpeedVerySlow; //极慢
    @ViewInject(R.id.rb_video_short_features_slow)
    private RadioButton rbSpeedSlow; //慢
    @ViewInject(R.id.video_short_features_very_fast)
    private RadioButton rbSpeedVeryFse; //极快
    @ViewInject(R.id.video_short_features_fast)
    private RadioButton rbSpeedFast; //快
    @ViewInject(R.id.video_short_features_standard)
    private RadioButton rbSpeedStandard; //标准

    //美颜
    @ViewInject(R.id.seek_face_lift) //瘦脸
    SeekBarRelativeLayout seekBarRelativeFaceLiftLayout;
    @ViewInject(R.id.seek_bar) //美白
    SeekBarRelativeLayout seekBarRelativeWhiteningLayout;
    @ViewInject(R.id.rl_beauty_type)
    RecyclerView rlBeautyType; //美颜-类别
    @ViewInject(R.id.rl_beauty_rl)
    RelativeLayout rl_beauty_rl;
    @ViewInject(R.id.il_beauty)
    RelativeLayout ilBeauty; //直播美颜页面

    //faceU 布局
    private RecyclerView effectRecyclerView; // 道具

    //第三方滤镜
    private FuVideoEffect mFuEffect; //FU的滤镜
    ArrayList<BeautyType> beautyTypes; //美颜类别
    BeautyTypeAdaper anchorAdapter;

    Runnable mRunnable;

    int videoSchedule = 0; //拍摄进度
    boolean blVideoStaus = true; //拍摄状态 true:开始 false: 暂停

    //录制相关
    private MediaCaptureController mediaCaptureController; // 录制视频控制器
    private MediaCaptureOptions mediaCaptureOptions; // 视频录制参数配置
    private VideoCaptureParams videoCaptureParams; // 录制视频的参数（界面显示，用户操作配置的），分几段，时间等
    private List<String> videoPathList = new ArrayList<>(); // 录制的分段视频地址
    private String outputPath; // 拼接后的视频地址
    private VideoItem videoItem; // 拼接后的video
    private String displayName; // 视频名称

    //拍摄进度相关
    private int mProgress;
    private boolean mIsStopped = true;
    private int maxTime =15000; //最长时间


    private final int BASIC_PERMISSION_REQUEST_CODE = 100;
    private Thread thread = null;
    private ActionSheetDialog mDialog = null;

    /**
     * 代码流程
     * 1.检查权限
     * 2.获得权限-初始化界面
     * 3.显示预览界面
     * 4.美颜参数初始化
     * 5.设置美颜参数
     * 6.设置控件监听
     * 4. 拍摄  暂停
     * 4.1 拍摄
     *
     * @param savedInstanceState
     */



    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            setProgress();
        }
    };

    private void setProgress() {
        if (mProgress > maxTime) {
            mIsStopped = true;
            stopRecordding();
            btnStartStop.setText("开始");
            mHandler.removeMessages(0);
            ivOk.setVisibility(View.VISIBLE);
            ivDeleteVideo.setVisibility(View.GONE);
        } else if (!mIsStopped) {
            mProgress += 100;
            mProgressBar.setProgress(mProgress);
            mHandler.sendEmptyMessageDelayed(0, 100);
        }
    }


    /**
     * 生命周期
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoShortActivity_instance = this;
        //检查权限
        requestBasicPermission();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
    protected void onDestroy() {
        super.onDestroy();
        if (mediaCaptureController != null) {
            doneRecording();
            mediaCaptureController = null;
            mediaCaptureOptions = null;
        }
    }


    /**
     *开始
     */

    /**
     * 基本权限管理
     */
    private final String[] BASIC_PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECORD_AUDIO
    };

    /**
     * 检查权限
     */
    private void requestBasicPermission() {
        MPermission.printMPermissionResult(true, this, BASIC_PERMISSIONS);
        MPermission.with(VideoShortActivity.this)
                .setRequestCode(BASIC_PERMISSION_REQUEST_CODE)
                .permissions(BASIC_PERMISSIONS)
                .request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    /**
     * 授权成功
     */
    @OnMPermissionGranted(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionSuccess() {
        ToastUtils.showLong(R.string.permission_ok);
        MPermission.printMPermissionResult(false, this, BASIC_PERMISSIONS);

        //初始化界面
        initView();
        //美颜
        setBeauty();

        //设置监听
        setListener();
        //视频参数
        initVideoParams();
        //视频录制参数配置
        initMediaCapture();

    }
    @OnMPermissionDenied(BASIC_PERMISSION_REQUEST_CODE)
    @OnMPermissionNeverAskAgain(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionFailed() {
//        Toast.makeText(this, "未全部授权，部分功能可能无法正常运行！", Toast.LENGTH_SHORT).show();
        ToastUtils.showLong(R.string.permission_error);
        MPermission.printMPermissionResult(false, this, BASIC_PERMISSIONS);
    }


    /**
     * 视频录制参数配置
     */
    private void initMediaCapture() {
        //视频录制参数
        mediaCaptureOptions = new MediaCaptureOptions();
        //创建视频文件
        initCaptureOptions();

        setResolution();

        //短视频录制控制器
        mediaCaptureController = new MediaCaptureController(this, this, mediaCaptureOptions);
        // faceU要在startPreview之前初始化
        fuLiveEffect();
        //创建本地视频预览模板
        mediaCaptureController.startPreview(videoView);
    }

    //FU的滤镜
    private void fuLiveEffect(){
        mediaCaptureController.getMediaRecord().setCaptureRawDataCB(new VideoCallback() {
            @Override
            public int onVideoCapture(byte[] data, int width, int height,int orientation) {
                //SDK回调的线程已经创建了GLContext
                if(mFuEffect == null){
                    mFuEffect = new FuVideoEffect();
                    mFuEffect.filterInit(VideoShortActivity.this);
                }
                int result = mFuEffect.ifilterNV21Image(data, width, height);
                return result;
            }
        });
    }


    //创建视频文件-视频文件的尺寸-清晰度设置
    private void initCaptureOptions() {
        //创建视频文件
        mediaCaptureOptions.mFilePath = StorageUtil.getWritePath(System.currentTimeMillis() + ".mp4", StorageType.TYPE_VIDEO);
        LogUtil.Log("video_delet",LogUtil.LOG_E,"创建时文件路径: "+ mediaCaptureOptions.mFilePath);
        videoPathList.add(mediaCaptureOptions.mFilePath);
    }

    private void setResolution(){
        //视频文件的尺寸-清晰度设置
        mediaCaptureOptions.mVideoPreviewWidth = 720;
        mediaCaptureOptions.mVideoPreviewHeight = 1280;
        mediaCaptureOptions.resolutionType = ResolutionType.HD;
    }


    /**
     * 视频参数
     */
    private void initVideoParams() {
        videoCaptureParams = new VideoCaptureParams(3, 30*1000, ResolutionType.HD);
        videoCaptureParams.setResolutionType( ResolutionType.HD);
    }



    /**
     *控件初始化
     */
    private void initView() {
        videoView = findViewById(R.id.camerasurfaceview); //显示控件
        rbSpeedStandard.setChecked(true);
        mProgressBar.setMax(maxTime);
        //美颜布局
        initFaceULayout();

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

    /**
     * 第三方美颜
     */
    private void initFaceULayout() {
        effectRecyclerView = findViewById(R.id.effect_recycle_view);
        effectRecyclerView.setVisibility(View.VISIBLE);
        effectRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        EffectAndFilterSelectAdapter effectAndFilterSelectAdapter = new EffectAndFilterSelectAdapter(effectRecyclerView,
                EffectAndFilterSelectAdapter.VIEW_TYPE_EFFECT);
        effectAndFilterSelectAdapter.setOnItemSelectedListener(new EffectAndFilterSelectAdapter.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int itemPosition) {
                if (mFuEffect != null) {
                    mFuEffect.onEffectItemSelected(itemPosition);
                }
            }
        });
        effectRecyclerView.setAdapter(effectAndFilterSelectAdapter);
        showOrHideFaceULayout(false);
    }

    /**
     * 美颜布局数据集合
     * @param show
     */
    private void showOrHideFaceULayout(boolean show) {
        ViewGroup vp = findViewById(R.id.faceu_layout);
        vp.setVisibility(View.VISIBLE);
        for (int i = 0; i < vp.getChildCount(); i++) {
            vp.getChildAt(i).setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 设置监听器
     */
    private void setListener() {
        faceuBtn.setOnClickListener(this);
        rlFaceuFiniish.setOnClickListener(this);
        beautyBtn.setOnClickListener(this);
        rl_beauty_rl.setOnClickListener(this);
        rlSoundTrack.setOnClickListener(this);
        btnStartStop.setOnClickListener(this);
        ivCameraConversion.setOnClickListener(this);
        ivFinish.setOnClickListener(this);
        ivDeleteVideo.setOnClickListener(this);
        ivOk.setOnClickListener(this);

        //类别
        anchorAdapter.setOnItemClickListener(new BeautyTypeAdaper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                anchorAdapter.setSelectedPosition(position);
                anchorAdapter.notifyDataSetChanged();
                switch (beautyTypes.get(position).getText())
                {
                    case "无滤镜":
                        mFuEffect.setFilterName(0);
                        break;
                    case "粉嫩":
                        mFuEffect.setFilterName(1);
                        break;
                    case "黑白":
                        mFuEffect.setFilterName(2);
                        break;
                    case "怀旧":
                        mFuEffect.setFilterName(3);
                        break;
                    case "自然":
                        mFuEffect.setFilterName(4);
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
                if(mediaCaptureController != null){
                    float param;
                    param = (float)progress/100;
                    mFuEffect.setCOlorLevel(param);
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
                if(mediaCaptureController != null){
                    float param;
                    param = (float)progress/100;
                    mFuEffect.setCheekThinning(param);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

    }



    /**
     * 点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.iv_layout_video_short_finish: //关闭
                if(videoPathList.size()>0){
                    setDialogFinish();
                return;
                }
                finish();
                break;
            case R.id.iv_layout_video_short_camera_conversion: //切换摄像头
                if (mediaCaptureController != null) {
                    mediaCaptureController.switchCamera();
                }
                break;
            case R.id.rl_layout_video_short_expression: //表情
                ilFeatures.setVisibility(View.INVISIBLE);
                showOrHideFaceULayout(true);
                break;
            case R.id.rl_faceu_finiish: //表情空白
                ilFeatures.setVisibility(View.VISIBLE);
                showOrHideFaceULayout(false);
                break;
            case R.id.rl_layout_video_short_beauty: //美颜
                ilBeauty.setVisibility(View.VISIBLE);
                ilFeatures.setVisibility(View.INVISIBLE);
                break;
            case R.id.rl_beauty_rl: //美颜空白
                ilBeauty.setVisibility(View.GONE);
                ilFeatures.setVisibility(View.VISIBLE);
                break;
            case R.id.rl_layout_video_short_soundtrack: //配乐
                startActivity(new Intent(this,SoundtrackActivity.class));
                break;
            case R.id.btn_video_short_features_start_stop: //开始暂停
                if(mIsStopped){
                    startRecording();
                    onStartClick();
                    btnStartStop.setText(R.string.video_short_features_stop);
                    mIsStopped = false;

                }else {
                    stopRecordding();
                    onStopClick();
                    btnStartStop.setText(R.string.video_short_features_start);
                    mIsStopped = true;
                }
                break;
            case R.id.iv_video_short_features_back: //删除上一段视频
                if(videoPathList.size()>1) {
                    onDeleteClick();
                }
                break;
            case R.id.iv_video_short_features_ok: //进入预览界面
                //删除最后一个资源文件
                videoPathList.remove(videoPathList.size()-1);

                VideoEditingActivity.startActivityForResult(VideoShortActivity.this, videoPathList,
                        videoCaptureParams.getTime() / videoCaptureParams.getCount() * videoPathList.size(),
                        mediaCaptureOptions);

                break;
        }

    }

    //停止录制
    public void onStopClick( ) {
        ivDeleteVideo.setVisibility(View.VISIBLE);
        if(mProgress >= 5000){
            ivOk.setVisibility(View.VISIBLE);
        }
        mIsStopped = true;
        mProgressBar.setTimeStamp(true);
    }

    //开始录制
    public void onStartClick( ) {
        ivDeleteVideo.setVisibility(View.GONE);
        ivOk.setVisibility(View.GONE);
        if (!mIsStopped) {
            return;
        }

        mIsStopped = false;
        mHandler.removeMessages(0);
        mHandler.sendEmptyMessage(0);
    }

    //删除上一段
    public void onDeleteClick( ) {
        ivDeleteVideo.setBackgroundResource(R.drawable.icon_back_ok);
        if (!mIsStopped) {
            return;
        }

        mProgressBar.deleteLastStep();
        mProgressBar.setOnDeleteCallbackListener(new SteppingProgressBar.SteppingProgressBarCallbackListener() {

            @Override
            public void deleteDone(float percent) {
                mProgress = (int) percent;
                if(videoPathList.size()>1){
                    deleteFile(videoPathList.get(videoPathList.size()-2));
                    LogUtil.Log("video_delet",LogUtil.LOG_E,"文件路径: "+ videoPathList.get(videoPathList.size()-2));
                    videoPathList.remove(videoPathList.size()-2);
                    ivDeleteVideo.setBackgroundResource(R.drawable.icon_back);
                }
            }
        });
    }

    public  boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }


    /**
     * 是否放弃视频
     */
    public void setDialogFinish(){
        mDialog = new ActionSheetDialog(this).builder();
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setTitle("是否放弃录制视频");
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
    }




    /**
     * 开始录制
     */
    private void startRecording() {
        mediaCaptureController.startRecording();
    }

    /**
     * 停止录制
     */
    private void stopRecordding(){
        if(mediaCaptureController !=  null &&mProgress <maxTime){
            initCaptureOptions();
            mediaCaptureController.stopRecording();
        }else if (mediaCaptureController != null){
            doneRecording();
        }
    }


    /**
     * 释放资源
     */
    private void doneRecording() {
        if (mediaCaptureController != null) {
            // 顺序不能错
            mediaCaptureController.stopRecording();
            mediaCaptureController.stopPreview();
            //释放美颜资源
            releaseFuEffect();

            mediaCaptureController.release();
        }
    }

    /**
     * 释放第三方美颜资源
     */
    private void releaseFuEffect(){
        if(mFuEffect != null){
            mediaCaptureController.getMediaRecord().postOnGLThread(new Runnable() {
                @Override
                public void run() {
                    mFuEffect.filterUnInit();
                    mFuEffect = null;
                }
            });
        }
    }



    /**
     * 预览设置完成
     */
    @Override
    public void onPreviewInited() {

    }

    /**
     * 设置预览画面大小
     */
    @Override
    public void setPreviewSize(int videoPreviewWidth, int videoPreviewHeight) {

    }

    /**
     * 获取画布SurfaceView
     */
    @Override
    public SurfaceView getSurfaceView() {
        return videoView;
    }

    /**
     * 可以开始录制了
     */
    @Override
    public void onStartRecording() {

    }

    /**
     * 资源释放完毕
     */
    @Override
    public void onRelease() {

    }

    /**
     * 异常
     */
    @Override
    public void onError(int code) {
        if (code == lsMessageHandler.MSG_AUDIO_RECORD_ERROR) {
            Toast.makeText(this, "录音模块异常", Toast.LENGTH_SHORT).show();
            ToastUtils.showLong("录音模块异常");
        } else {
            Toast.makeText(this, "录制异常:" + code, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 视频处理
     */
    /**
     * 转码已完成
     */
    @Override
    public void onVideoProcessSuccess() {

    }

    @Override
    public void onVideoProcessFailed(int code) {

    }

    @Override
    public void onVideoSnapshotSuccess(Bitmap bitmap) {

    }

    @Override
    public void onVideoSnapshotFailed(int code) {

    }

    @Override
    public void onVideoProcessUpdate(int process, int total) {

    }
}
