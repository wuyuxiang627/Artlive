package com.connxun.elinetv.view.LiveBroadcast;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.connxun.elinetv.R;
import com.connxun.elinetv.adapter.Beauty.BeautyTypeAdaper;
import com.connxun.elinetv.adapter.Live.CheetahStaffAdapter;
import com.connxun.elinetv.app.BaseApplication;
import com.connxun.elinetv.base.ui.BaseFragment;
import com.connxun.elinetv.base.ui.CustomRoundView;
import com.connxun.elinetv.base.ui.MagicTextView;
import com.connxun.elinetv.entity.BeautyType;
import com.connxun.elinetv.entity.IMGift;
import com.connxun.elinetv.observer.INotifyListener;
import com.connxun.elinetv.observer.NotifyListenerManager;
import com.connxun.elinetv.observer.NotifyObject;
import com.connxun.elinetv.presenter.Live.LivePresenter;
import com.connxun.elinetv.util.DialogUtil;
import com.connxun.elinetv.util.FileUploadUtil;
import com.connxun.elinetv.util.ToastUtils;
import com.connxun.elinetv.util.ui.SeekBarRelativeLayout;
import com.connxun.elinetv.view.MediaPreview.LIveRoomActivity;
import com.connxun.elinetv.view.Music.MusicFragment;
import com.connxun.elinetv.view.user.ITestView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.netease.LSMediaCapture.lsMessageHandler;
import com.netease.fulive.FuVideoEffect;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMember;
import com.netease.vcloud.video.effect.VideoEffect;
import com.netease.vcloud.video.render.NeteaseView;
import com.squareup.picasso.Picasso;

import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhukkun on 1/5/17.
 * 直播采集推流Fragment
 */
public class CaptureFragment extends BaseFragment implements INotifyListener,lsMessageHandler, CapturePreviewContract.CapturePreviewUi{

    private static String TAG = "CaptureFragment";
    /**
     * Ui 基础控件
     */

    private LivePresenter presenter = new LivePresenter(getActivity());

    private static int REUST_CODE_IMAGE_COVER = 1001; //封面选择
    private SimpleDraweeView iv_live_cover;
    private EditText et_live_title;
    private Button btn_live_start_live;
    private SimpleDraweeView iv_media_preview_user_photo; //头像
    private TextView tv_media_preview_user_name; //名字
    private static TextView tv_media_preview_watch_number; //多少人在线
    private ImageButton ib_media_preview_watch_comment; //关注
    private ImageView iv_meidapreview_gift; //礼物
    private ImageView iv_live_camera_conversion; //摄像头
    private ImageView iv_live_finish; //未开始: 关闭
    private ImageView iv_media_preview_finish; //g关闭
    private String path;
    private String imageUrl;
    private RelativeLayout il_start;
    private RelativeLayout il_setting;
    private LinearLayout ll_liaotianshi;
    private ImageView  iv_liaotianshi;
    private ImageView ivMusic;

    private FrameLayout layoutMusic;

    //聊天室
    static List<ChatRoomMember> result = new ArrayList<>();
    private GridLayoutManager gridLayoutManager;
    private static CheetahStaffAdapter cheetahStaffAdapter;
    private RecyclerView rlvCheetahStaff;

    ArrayList<BeautyType> beautyTypes; //美颜类别
    BeautyTypeAdaper anchorAdapter;

    RecyclerView rlBeautyType; //美颜-类别

    RelativeLayout ilStart; //直播功能页面
    RelativeLayout ilBeauty; //直播美颜页面

    @ViewInject(R.id.seek_face_lift) //瘦脸
            SeekBarRelativeLayout seekBarRelativeFaceLiftLayout;
    @ViewInject(R.id.seek_bar) //美白
            SeekBarRelativeLayout seekBarRelativeWhiteningLayout;
    private ImageView ivBeauty;
    private TextView tvBeauty;
    RelativeLayout rl_beauty_rl;
    private MusicFragment musicFragment; //音乐界面


    /**
     * 绑定Ui控件
     * @param view
     */
    private void bindView(View view) {
        layoutMusic = view.findViewById(R.id.layout_live_music);
        ivMusic = view.findViewById(R.id.iv_music);
        ilBeauty = view.findViewById(R.id.il_beauty);
        ilStart = view.findViewById(R.id.il_start);
        rl_beauty_rl = view.findViewById(R.id.rl_beauty_rl);
        tvBeauty = view.findViewById(R.id.tv_live_start_beauty);
        ivBeauty = view.findViewById(R.id.iv_live_start_beauty);
        seekBarRelativeFaceLiftLayout = view.findViewById(R.id.seek_face_lift);
        seekBarRelativeWhiteningLayout = view.findViewById(R.id.seek_bar);
        rlBeautyType = view.findViewById(R.id.rl_beauty_type);
        iv_media_preview_user_photo = view.findViewById(R.id.iv_media_preview_user_photo);
        tv_media_preview_user_name = view.findViewById(R.id.tv_media_preview_user_name);
        iv_media_preview_finish = view.findViewById(R.id.iv_media_preview_finish);
        tv_media_preview_watch_number = view.findViewById(R.id.tv_media_preview_watch_number);
        rlvCheetahStaff = view.findViewById(R.id.rlv_cheetah_staff);
        iv_live_cover = view.findViewById(R.id.iv_live_cover);
        et_live_title = view.findViewById(R.id.et_live_title);
        btn_live_start_live = view.findViewById(R.id.btn_live_start_live);
        iv_live_camera_conversion = view.findViewById(R.id.iv_live_camera_conversion);
        iv_live_finish = view.findViewById(R.id.iv_live_finish);
        filterSurfaceView = view.findViewById(R.id.live_videoview);
        normalSurfaceView = view.findViewById(R.id.live_videovie_heng);
        il_start = view.findViewById(R.id.il_start);
        il_setting = view.findViewById(R.id.il_setting);
        iv_liaotianshi = view.findViewById(R.id.iv_liaotianshi);
        ll_liaotianshi = view.findViewById(R.id.ll_liaotianshi);
//        iv_meidapreview_gift = view.findViewById(R.id.iv_meidapreview_gift);
        ib_media_preview_watch_comment = view.findViewById(R.id.ib_media_preview_watch_comment);
//        iv_meidapreview_gift.setVisibility(View.GONE);
        ib_media_preview_watch_comment.setVisibility(View.INVISIBLE);
        clearTiming();
    }

//    MixAudioLayout mixAudioLayout = null; //伴音控制布局

    boolean canUse4GNetwork = false; //是否能使用4G网络进行直播

    /**
     * 滤镜模式的SurfaceView
     */
    private NeteaseView filterSurfaceView;

    /**
     * 普通模式的SurfaceView
     */
    private NeteaseView normalSurfaceView;

    /**
     * 控制器
     */
    CapturePreviewController controller;

//    LiveRoomActivity liveActivity;

//    LiveBottomBar liveBottomBar;

    private long lastClickTime;
    private LIveRoomActivity liveActivity;
    View view;



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



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_live, container, false);
        registerListener();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(view);
//        loadFragment();
        controller = getCaptureController();
        controller.handleIntent(getActivity().getIntent());
    }


    /**
     * 加载布局
     */
    private void loadFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        PushFlowFragment testFragment = new PushFlowFragment();
        transaction.replace(R.id.layou, testFragment);
        musicFragment = new MusicFragment();
        transaction.replace(R.id.layout_live_music,musicFragment);
        transaction.commit();
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
        LinearLayoutManager ms= new LinearLayoutManager(getActivity());
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);
        rlBeautyType.setLayoutManager(ms);
        anchorAdapter = new BeautyTypeAdaper(getActivity(),beautyTypes);
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
    public void onAttach(Context context) {
        super.onAttach(context);
        liveActivity = (LIveRoomActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        liveActivity = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        controller.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        controller.onPause();
    }

    /**
     * 初始化Ui
     */
    private void initView(View view) {
        Button butto = view.findViewById(R.id.btn_text);
        final FrameLayout frameLayout = view.findViewById(R.id.layou);
        giftNumAnim = new NumAnim();
        inAnim = (TranslateAnimation) AnimationUtils.loadAnimation(getActivity(), R.anim.gift_in);
        outAnim = (TranslateAnimation) AnimationUtils.loadAnimation(getActivity(), R.anim.gift_out);
        llgiftcontent = view.findViewById(R.id.llgiftcontent);
        rlvCheetahStaff = view.findViewById(R.id.rlv_cheetah_staff);
        bindView(view);
        setUserDate();
        setBeauty(); //美颜相关
        clickView();
        setChatRoom();

        butto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frameLayout.setVisibility(View.VISIBLE);
            }
        });

    }

    private void setUserDate() {
        tv_media_preview_user_name.setText(BaseApplication.getUserSp().getString("nickName","0"));
        String avatar = BaseApplication.getUserSp().getString("avatar",null);
        if(avatar != null){
            //创建将要下载的图片的URI
            Uri imageUri = Uri.parse(avatar);
            //开始下载
            iv_media_preview_user_photo.setImageURI(imageUri);
        }
    }

    private FuVideoEffect mFuEffect; //FU的滤镜

    /**
     * 设置Ui点击事件
     */
    private void clickView() {
        //点击音乐
        ivMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        rl_beauty_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ilBeauty.setVisibility(View.GONE);
                ilStart.setVisibility(View.VISIBLE);
            }
        });

        ivBeauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //美颜
                ilStart.setVisibility(View.INVISIBLE);
                ilBeauty.setVisibility(View.VISIBLE);
            }
        });
        tvBeauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //美颜
                ilStart.setVisibility(View.INVISIBLE);
                ilBeauty.setVisibility(View.VISIBLE);
            }
        });


        //美白
        seekBarRelativeWhiteningLayout.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                if(controller != null){
                    int param;
                    param = progress/20;
//                    mFuEffect.setCOlorLevel(param);
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
                if(controller != null){
                    float param;
                    param = (float)progress/100;
                    controller.setCheekThinning(param);
                    Log.e(TAG, "onProgressChanged: 滤镜： "+ param);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });





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
                        controller.setFilterType(VideoEffect.FilterType.none);
//                        controller.setFilterType();
                        break;
                    case "粉嫩":
                        controller.setFilterType(VideoEffect.FilterType.tender);
                        break;
                    case "黑白":
                        break;
                    case "怀旧":
                        controller.setFilterType(VideoEffect.FilterType.brooklyn);
                        break;
                    case "自然":
                        controller.setFilterType(VideoEffect.FilterType.nature);
                        break;
                    default:
                        break;
                }
            }
        });




        iv_media_preview_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //界面销毁
        iv_live_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        //切换摄像头
        iv_live_camera_conversion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long currentTime = System.currentTimeMillis();
                if(currentTime - lastClickTime> 1000) {
                    controller.switchCam();
                    lastClickTime = currentTime;
                }
            }
        });


        iv_liaotianshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liveActivity.showInputPanel();
            }
        });

        btn_live_start_live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(et_live_title.getText())){
                    ToastUtils.showLong(R.string.live_title_null);
                    return;
                }
                DialogUtil.startWaitingDialog(getActivity(),"开启直播中....");
                if(path != null && ! path.equals("")){
                    FileUploadUtil.getUploadImageToken(getActivity(),path,1001);
                }
            }
        });

        iv_live_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageGridActivity.class);
                startActivityForResult(intent, REUST_CODE_IMAGE_COVER);
            }
        });

    }




    /**
     * 设置显示的SurfaceView
     * @param hasFilter 是否带滤镜功能
     */
    @Override
    public void setSurfaceView(boolean hasFilter) {
        if(hasFilter){
            filterSurfaceView.setVisibility(View.VISIBLE);
            normalSurfaceView.setVisibility(View.GONE);
        }else{
            normalSurfaceView.setVisibility(View.VISIBLE);
            filterSurfaceView.setVisibility(View.GONE);
        }
    }

    /**
     * 设置预览画面 大小
     * @param hasFilter 是否有滤镜
     */
    @Override
    public void setPreviewSize(boolean hasFilter, int previewWidth, int previewHeight) {
//
    }

    /**
     * 获取正在显示的SurfaceView
     * @return
     */
    @Override
    public View getDisplaySurfaceView(boolean hasFilter) {
        if(hasFilter){
            return filterSurfaceView;
        }else{
            return normalSurfaceView;
        }
    }

    /**
     * 设置直播开始按钮, 是否可点击
     * @param clickable
     */
    @Override
    public void setStartBtnClickable(boolean clickable) {
        btn_live_start_live.setClickable(clickable);
    }

    /**
     * 正常开始直播
     */
    @Override
    public void onStartLivingFinished() {
//        btnAudio.setVisibility(View.VISIBLE);
//        btnStartLive.setVisibility(View.GONE);
        btn_live_start_live.setText("开始直播");
        DialogUtil.dismissWaitingDialog();
        il_start.setVisibility(View.INVISIBLE);
        il_setting.setVisibility(View.VISIBLE);
        ll_liaotianshi.setVisibility(View.VISIBLE);
        if(liveActivity!=null) {
            liveActivity.onStartLivingFinished();
        }
        DialogMaker.dismissProgressDialog();
    }

    /**
     * 停止直播完成时回调
     */
    @Override
    public void onStopLivingFinished() {
        //btnRestart.setVisibility(View.GONE);
    }

    /**
     * 设置audio按钮状态
     * @param isPlay 是否正在开启
     */
    @Override
    public void setAudioBtnState(boolean isPlay) {
//        if(isPlay){
//            btnAudio.setImageResource(R.drawable.btn_audio_on_n);
//        }else{
//            btnAudio.setImageResource(R.drawable.btn_audio_off_n);
//        }
    }

    /**
     * 设置Video按钮状态
     * @param isPlay 是否正在开启
     */
    @Override
    public void setVideoBtnState(boolean isPlay) {
//        if(isPlay){
//            btnVideo.setImageResource(R.drawable.btn_camera_on_n);
//        }else{
//            btnVideo.setImageResource(R.drawable.btn_camera_off_n);
//        }
    }

    @Override
    public void setFilterSeekBarVisible(boolean visible) {
//        if(visible){
//            //fixme 如要显示滤镜强度进度条,解除注释
//            //filterSeekBar.setVisibility(View.VISIBLE);
//        }else{
//            filterSeekBar.setVisibility(View.GONE);
//        }
    }

    @Override
    public void checkInitVisible(PublishParam mPublishParam) {
//        if(mPublishParam.openVideo){
//            btnCamSwitch.setVisibility(View.VISIBLE);
////            if(liveBottomBar!=null) {
////                liveBottomBar.getFilterView().setVisibility(View.VISIBLE);
////                liveBottomBar.getCaptureView().setVisibility(View.VISIBLE);
////            }
//        }
    }

    /**
     *  获取Ui对应的controller
     * @return
     */
    private CapturePreviewController getCaptureController() {
        return new CapturePreviewController(getActivity(), this);
    }

    /**
     * 按下返回键
     */
    public void onBackPressed() {
        liveActivity.normalFinishLive();
    }

    @Override
    public void showAudioAnimate(boolean b) {
//        if(audioAnimate!=null){
//            if(b){
//                audioAnimate.setVisibility(View.VISIBLE);
//            }else{
//                audioAnimate.setVisibility(View.GONE);
//            }
//        }
    }

    @Override
    public void onDisconnect() {
//        btnAudio.setVisibility(View.GONE);
        btn_live_start_live.setVisibility(View.VISIBLE);

        //liveActivity为空,则已关闭直播页面
        if(liveActivity!=null) {
            liveActivity.onLiveDisconnect();
            controller.liveStartStop();
        }
    }

    @Override
    public void normalFinish() {
        if(liveActivity!=null) {
            liveActivity.normalFinishLive();
        }
    }

    @Override
    public void onStartInit() {
        btn_live_start_live.setText("准备中...");
    }

    @Override
    public void onCameraPermissionError() {
        ToastUtils.showLong("无法打开相机\", \"可能没有相关的权限,请开启权限后重试");
    }

    @Override
    public void onAudioPermissionError() {
        ToastUtils.showLong("无法开启录音\", \"可能没有相关的权限,请开启权限后重试");
    }

    public void destroyController() {
        controller.tryToStopLivingStreaming();
        controller.onDestroy();
    }


    protected void showConfirmDialog(String title, String message, DialogInterface.OnClickListener okEvent, DialogInterface.OnClickListener cancelEvent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.ok,
                okEvent);
        if (cancelEvent != null) {
            builder.setNegativeButton(R.string.cancel,
                    cancelEvent);
        }
        builder.setCancelable(false);
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        List<Uri> mSelected = new ArrayList<>();
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == REUST_CODE_IMAGE_COVER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                path = images.get(0).path;
                Picasso.with(getContext()).load(new File(path)).into(iv_live_cover);
                SharedPreferences.Editor editor= BaseApplication.getUserSp().edit();
                editor.putString("liveCoverpath",path);
                editor.commit();
//                Log.e(TAG,"你知道的: "+ images.get(0).path);
            }
        }


    }

    //选择照片
    private void setDialog_paizhao() {

        Intent intent = new Intent(getActivity(), ImageGridActivity.class);
        startActivityForResult(intent, REUST_CODE_IMAGE_COVER);



    }


    @Override
    public void handleMessage(int i, Object o) {




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
                presenter.onCreate();
                try {
                    presenter.getLiveStartLive(et_live_title.getText().toString(),imageUrl,getActivity().getIntent().getStringExtra("liveNo"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                presenter.attachView(mStartLive);


                break;

        }
    }

    /**
     * 开始直播
     */
    public ITestView mStartLive = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            controller.liveStartStop();
        }

        @Override
        public void onError(Object object) {

        }
    };


    @Override
    public void registerListener() {
        NotifyListenerManager.getInstance().registerListener(this);

    }

    @Override
    public void unRegisterListener() {
        NotifyListenerManager.getInstance().unRegisterListener(this);
    }
    private void setChatRoom() {
        gridLayoutManager = new GridLayoutManager(getActivity(),1);
        gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        rlvCheetahStaff.setLayoutManager(gridLayoutManager);
        cheetahStaffAdapter = new CheetahStaffAdapter(getActivity(),result);
        rlvCheetahStaff.setAdapter(cheetahStaffAdapter);
    }


    public static void setUSerPhotoNumber(List<ChatRoomMember> results){
        Log.e(TAG,"人员集合数据集合:"+results.size());
        result.clear();
        result .addAll(results);
        cheetahStaffAdapter.notifyDataSetChanged();
        tv_media_preview_watch_number.setText(results.size()+"人在线");
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
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                removeView.startAnimation(outAnim);
            }
        });
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
    public void showGift(IMGift imGift) {
        String tag = imGift.getData().getGiftNo()+"";
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

}
