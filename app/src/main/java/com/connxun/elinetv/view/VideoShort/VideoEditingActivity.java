package com.connxun.elinetv.view.VideoShort;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.connxun.elinetv.R;
import com.connxun.elinetv.adapter.VideoShortAdapter.CoverAdapter;
import com.connxun.elinetv.app.ConfigSystem;
import com.connxun.elinetv.base.ui.BaseActivity;
import com.connxun.elinetv.entity.VideoItem;
import com.connxun.elinetv.observer.NotifyObject;
import com.connxun.elinetv.shortvideo.model.MediaCaptureOptions;
import com.connxun.elinetv.shortvideo.videoprocess.VideoProcessController;
import com.connxun.elinetv.shortvideo.videoprocess.model.VideoProcessOptions;
import com.connxun.elinetv.util.DialogUtil;
import com.connxun.elinetv.util.FileUploadUtil;
import com.connxun.elinetv.util.ImageMatisee;
import com.connxun.elinetv.util.ToastUtils;
import com.netease.LSMediaCapture.util.sys.TimeUtil;
import com.netease.cloud.nos.android.utils.LogUtil;
import com.netease.nim.uikit.common.util.storage.StorageUtil;
import com.netease.transcoding.TranscodingAPI;
import com.netease.transcoding.player.MediaPlayerAPI;
import com.netease.vcloud.video.render.NeteaseView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by connxun-16 on 2018/1/30.
 */

@ContentView(R.layout.activity_video_editing)
public class VideoEditingActivity extends BaseActivity implements VideoProcessController.VideoProcessCallback {

    //相关静态值
    public static final String EXTRA_PATH_LIST = "path_list";
    public static final String EXTRA_TOTAL_TIME = "total_time";
    public static final String EXTRA_MEDIA_OPTIONS = "media_options";
    public static final int EXTRA_REQUEST_CODE = 1010;
    public static final int RESULT_UPLOAD_COVER = 1002; //上传封面
    public static final int RESULT_UPLOAD_VIDEO = 1003;//上传视频文件

    //相关控件
    @ViewInject(R.id.video_editing)
    NeteaseView videoView;
    @ViewInject(R.id.rl_layout_video_editing_finish)
    RelativeLayout rlFinish; //返回
    @ViewInject(R.id.tv_layhout_video_edit_save_loca)
    TextView tvSaveLoca; //保存本地
    @ViewInject(R.id.iv_layhout_video_edit_save_loca)
    ImageView ivSaveLoca; //保存本地
    @ViewInject(R.id.iv_layout_video_edit_cover)
    ImageView ivCover; //封面
    @ViewInject(R.id.il_video_edit_cover)
    RelativeLayout ilCover; //封面布局
    @ViewInject(R.id.il_video_edit_features)
    RelativeLayout ilFeatures; //功能

    @ViewInject(R.id.rlv_layout_video_edit_cover)
    RecyclerView rlvCover; //封面布局

    @ViewInject(R.id.btn_layout_edit_video_release)
    Button btnRelease; //发布
    @ViewInject(R.id.et_layout_edit_features_title)
    EditText etTitle; //标题


    //播放控制
    private MediaPlayerAPI mPlayer;

    //预览视频数据
    private String[] arr;
    private List<String> pathList;
    private float totalTime;
    private MediaCaptureOptions mediaCaptureOptions;

    //封面数据
    private List<Bitmap> coverBitmapList;

    //相关逻辑控制
    boolean blSaveLoca = false ;//保存本地

    // 调整布局参数，亮度，对比度等
    private float brightness = 0.0f;
    private float contrast = 1.0f;
    private float saturation = 1.0f;
    private float colorTemperature = 0;
    private float sharpness = 0;

    // accompany_sound
    private int currentSoundLayout = -1;
    private String accompanySoundPath;
    private float accompanyVolume = 0.3f; // 伴音大小

    //设置视频数据
    private VideoProcessController videoProcessController; // 视频编辑控制器
    private VideoItem videoItem;
    private float volume;
    private String displayName; //文件名
    private boolean isTrasition = true ; // 是否过渡
    private String videoLocaPath ; //合成视频的本地路径
    private CoverAdapter coverAdapter;
    private LinearLayoutManager ms;

    private int coverHeight; //封面的高
    private int coverWeigth; //封面的宽

    private Date date;



    //发布相关逻辑
    int positionCover = -1; ///bitmap的位置
    private long time;


    public static void startActivityForResult(Context context, List<String> pathList,
                                              float totalTime, MediaCaptureOptions mediaCaptureOptions) {
        Intent intent = new Intent();
        intent.setClass(context, VideoEditingActivity.class);
        intent.putStringArrayListExtra(EXTRA_PATH_LIST, (ArrayList<String>) pathList);
        intent.putExtra(EXTRA_TOTAL_TIME, totalTime);
        intent.putExtra(EXTRA_MEDIA_OPTIONS, (Serializable) mediaCaptureOptions);
        ((Activity) context).startActivityForResult(intent, EXTRA_REQUEST_CODE);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handleIntent(getIntent());//获取预览数据

        initVideoView(); //开启预览画面



    }

    @Override
    protected void onStart() {
        super.onStart();
        //合成视频
        startCombination();

    }

    @Override
    protected void onDestroy() {
        stopPlayer();
        super.onDestroy();

        //清空bitmap
        if(coverBitmapList != null && coverBitmapList.size() != 0){
            for(Bitmap bitmap : coverBitmapList){
                bitmap.recycle();
            }
        }

    }

    @Override
    public void setOnClick() {
        super.setOnClick();
        ivSaveLoca.setOnClickListener(this);
        rlFinish.setOnClickListener(this);
        tvSaveLoca.setOnClickListener(this);
        ivCover.setOnClickListener(this);
        btnRelease.setOnClickListener(this);
        ms= new LinearLayoutManager(this);
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId())
        {
            case R.id.rl_layout_video_editing_finish:
                //返回
            this.finish();
            break;
            case R.id.iv_layout_video_edit_cover:
                //设置封面

                rlvCover.setLayoutManager(ms);
                if(coverBitmapList == null || coverBitmapList.size() == 0){
                    return;
                }
                ilCover.setVisibility(View.VISIBLE);
                ilFeatures.setVisibility(View.GONE);
                coverAdapter = new CoverAdapter(this,coverBitmapList);
                rlvCover.setAdapter(coverAdapter);
                coverAdapter.setOnItemClickListener(new CoverAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        positionCover = position;
                        ilFeatures.setVisibility(View.VISIBLE);
                        Drawable drawable = new BitmapDrawable(coverBitmapList.get(position));
                        ivCover.setBackground(drawable);
                        ilCover.setVisibility(View.GONE);
                    }
                });
                break;
            case R.id.iv_layhout_video_edit_save_loca:
            case R.id.tv_layhout_video_edit_save_loca:
                //保存本地
                if(!blSaveLoca){
                    //不保存本地
                    ivSaveLoca.setBackgroundResource(R.drawable.icon_video_edit_no_ok);
                    blSaveLoca = true;
                }else {
                    blSaveLoca = false;
                    ivSaveLoca.setBackgroundResource(R.drawable.icon_video_edit_ok);
                }
                break;

            case R.id.btn_layout_edit_video_release:
                //发布
                if(positionCover == -1 ){
                    ToastUtils.showLong(R.string.video_editing_parameter_cover);
                    return;
                }

                coverHeight = coverBitmapList.get(positionCover).getHeight();
                coverWeigth = coverBitmapList.get(positionCover).getWidth();
                date = new Date();
                time =date.getTime();
                ImageMatisee.saveBitmapToSD(coverBitmapList.get(positionCover),time);

                File coverFile = new File(ConfigSystem.MEI_FILE_COVER_PATH+time+".jpg");
                if(coverFile == null || etTitle.getText().toString().length() == 0){
                    ToastUtils.showLong(R.string.video_editing_parameter);
                    return;
                }
                releaseResources();


                //上传封面
                DialogUtil.startWaitingDialog(this,"视频上传中....");
                FileUploadUtil.getUploadImageToken(this,ConfigSystem.MEI_FILE_COVER_PATH+time+".jpg",RESULT_UPLOAD_COVER);



                break;


        }
    }

    private void releaseResources() {
    }

    //获取预览数据
    protected void handleIntent(Intent intent) {
        pathList = intent.getStringArrayListExtra(EXTRA_PATH_LIST);
        totalTime = intent.getFloatExtra(EXTRA_TOTAL_TIME, 0);
        mediaCaptureOptions = (MediaCaptureOptions) intent.getSerializableExtra(EXTRA_MEDIA_OPTIONS);

        if (pathList != null) {
            LogUtil.i("video_edit", "how many videos:" + pathList.size());
        }
        videoProcessController = new VideoProcessController(this, this);
    }

    //开启预览
    private void initVideoView() {
        arr = new String[pathList.size()];
        arr = pathList.toArray(arr);
        mPlayer = MediaPlayerAPI.getInstance();
        mPlayer.init(getApplicationContext(), arr, videoView);
        mPlayer.start();
    }

    //停止播放
    private void stopPlayer() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.unInit();
        }
        mPlayer = null;
    }

    /**
     * 合成视频
     */
    private void startCombination(){

        VideoProcessOptions videoProcessOptions;
        String outputPath;
        try {
            videoProcessOptions = new VideoProcessOptions(mediaCaptureOptions);
            // 设置待拼接的文件
            TranscodingAPI.TranSource inputFilePara = videoProcessOptions.getSource();
            arr = new String[pathList.size()];
            arr = pathList.toArray(arr);
            inputFilePara.setFilePaths(arr);
            inputFilePara.setMergeWidth(mediaCaptureOptions.mVideoPreviewWidth);
            inputFilePara.setMergeHeight(mediaCaptureOptions.mVideoPreviewHeight);
            // 原声大小
            inputFilePara.setAudioVolume(volume);
            videoProcessOptions.setSource(inputFilePara);
            // 过渡
            if (isTrasition) {
                inputFilePara.setVideoFadeDuration(0);
            } else {
                inputFilePara.setVideoFadeDuration(0);
            }

            // 设置拼接后文件存储地址
            if(TextUtils.isEmpty(displayName)){
                displayName = new Date().getTime()+"";
            }
            videoLocaPath = StorageUtil.getWritePath(displayName + ".mp4", com.netease.nim.uikit.common.util.storage.StorageType.TYPE_VIDEO);
            Log.e("路径" ,"视频合成路径 : "+ videoLocaPath);
            TranscodingAPI.TranOut outputFilePara = videoProcessOptions.getOutputFilePara();
            outputFilePara.setFilePath(videoLocaPath);
            videoProcessOptions.setOutputFilePara(outputFilePara);
            // 设置视频亮度，对比度等
            TranscodingAPI.TranFilter filter = videoProcessOptions.getFilter();
            filter.setBrightness(brightness);
            filter.setContrast(contrast);
            filter.setSaturation(saturation);
            filter.setHue(colorTemperature);
            filter.setSharpenness(sharpness);
            videoProcessOptions.setFilter(filter);
            // 伴音
            TranscodingAPI.TranMixAudio mixAudioPara = videoProcessOptions.getMixAudio();
            if (!TextUtils.isEmpty(accompanySoundPath)) {
                mixAudioPara.setFilePath(accompanySoundPath);
                mixAudioPara.setMixVolume(accompanyVolume);
            }
//            // 贴图
//            setTexture(videoProcessOptions);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        // videoItem
        videoItem = new VideoItem();
        videoItem.setId("local" + System.currentTimeMillis());
        videoItem.setFilePath(videoLocaPath);
        videoItem.setDisplayName(displayName);
        videoItem.setDateTaken(TimeUtil.getNowDatetime());

        // 开始拼接
        videoProcessController.startCombination(videoProcessOptions);
        DialogUtil.startWaitingDialog(this,"视频合成中");

    }

    /*
     *获取视频截图
     */
    private List<Bitmap> getCoverBitmapList( MediaMetadataRetriever mmr){
        coverBitmapList = new ArrayList<>();
        int max=6000;
        int min=1000;
        for(int i = 0;i<10;i++){
            Random random = new Random();
            int s = random.nextInt(max)%(max-min+1) + min;
            Bitmap bitmap = mmr.getFrameAtTime(1000*s ,MediaMetadataRetriever.OPTION_CLOSEST);
            coverBitmapList.add(bitmap);
        }
        return  coverBitmapList;
    }



    @Override
    public void onVideoProcessSuccess() {
        Log.e("路径" ,"合成完毕 : ");

        //获取封面
        //创建MediaMetadataRetriever对象
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        //绑定资源
        mmr.setDataSource(videoLocaPath);
        getCoverBitmapList(mmr);
        DialogUtil.dismissWaitingDialog();
        Log.e("路径" ,"图片集合 : "+ coverBitmapList.size());
    }

    @Override
    public void onVideoProcessFailed(int code) {
        Log.e("路径" ,"合成失败 : ");
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

    @Override
    public void notifyUpdate(NotifyObject obj) {
        super.notifyUpdate(obj);
        switch (obj.what)
        {
            case RESULT_UPLOAD_COVER:
                ToastUtils.showLong("封面上传成功");
                Log.e("videoEditingACtivity","封面上传成功");
                Log.e("videoEditingACtivity","视频名称"+etTitle.getText().toString());
                Log.e("videoEditingACtivity","视频路径"+videoLocaPath);
                String fileName = videoLocaPath.substring(videoLocaPath.lastIndexOf("/") + 1);
                FileUploadUtil.getUploadFileToken(this,coverHeight,coverWeigth,time+".jpg",videoLocaPath,etTitle.getText().toString(),RESULT_UPLOAD_VIDEO);
                break;
            case  RESULT_UPLOAD_VIDEO:
                //视频上传成功
                DialogUtil.dismissWaitingDialog();
                VideoShortActivity.videoShortActivity_instance.finish();
                this.finish();

            break;
        }


    }
}
