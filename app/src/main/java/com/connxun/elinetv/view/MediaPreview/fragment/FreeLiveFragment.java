package com.connxun.elinetv.view.MediaPreview.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.connxun.elinetv.R;
import com.connxun.elinetv.app.BaseApplication;
import com.connxun.elinetv.base.ui.BaseFragment;
import com.connxun.elinetv.observer.INotifyListener;
import com.connxun.elinetv.observer.NotifyListenerManager;
import com.connxun.elinetv.observer.NotifyObject;
import com.connxun.elinetv.presenter.Live.LivePresenter;
import com.connxun.elinetv.util.DialogUtil;
import com.connxun.elinetv.util.FileUploadUtil;
import com.connxun.elinetv.util.ToastUtils;
import com.connxun.elinetv.view.LiveBroadcast.CapturePreviewController;
import com.connxun.elinetv.view.LiveBroadcast.PushFlowFragment;
import com.connxun.elinetv.view.user.ITestView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.squareup.picasso.Picasso;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018\3\9 0009.
 */


/**
 * 自由播
 */
public class FreeLiveFragment extends BaseFragment implements INotifyListener {
    private LivePresenter presenter = new LivePresenter(getActivity());
    View view;
    @BindView(R.id.iv_live_cover)
    SimpleDraweeView ivLiveCover;
    @BindView(R.id.et_live_title)
    EditText etLiveTitle;
    @BindView(R.id.ib_live_wechat_friend)
    ImageButton ibLiveWechatFriend;
    @BindView(R.id.ib_live_webo)
    ImageButton ibLiveWebo;
    @BindView(R.id.ib_live_wechat)
    ImageButton ibLiveWechat;
    @BindView(R.id.rl_live_back)
    RelativeLayout rlLiveBack;
    @BindView(R.id.iv_live_start_beauty)
    ImageView ivLiveStartBeauty;
    @BindView(R.id.tv_live_start_beauty)
    TextView tvLiveStartBeauty;
    @BindView(R.id.btn_live_start_live)
    Button btnLiveStartLive;
    Unbinder unbinder;

    private static int REUST_CODE_IMAGE_COVER = 1001; //封面选择
    private String LivePath; //直播间封面
    private String imageUrl; //封面上传成功-返回的url

    CapturePreviewController controller; //控制器

    PushFlowFragment pushFlowFragment;

    private boolean blLiveType = false; //判断是否在直播




    public FreeLiveFragment(){

    }
    @SuppressLint("ValidFragment")
    public FreeLiveFragment(BaseFragment baseFragment){
        pushFlowFragment = (PushFlowFragment) baseFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_free_live, null);
        unbinder = ButterKnife.bind(this, view);
        registerListener();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_live_cover, R.id.btn_live_start_live})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_live_cover: //封面
                Intent intent = new Intent(getActivity(), ImageGridActivity.class);
                startActivityForResult(intent, REUST_CODE_IMAGE_COVER);
//                new RxPermissions(getActivity()).request(
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        Manifest.permission.CAMERA
//                ).subscribe(granded -> {
//                    if (granded) {
//                        Matisse.from(this)
//                                .choose(MimeType.ofAll(), false)
//                                .countable(true)
//                                .capture(true)  //拍照
//                                .captureStrategy(
//                                        new CaptureStrategy(true, "com.connxun.ltcx.app.fileprovider"))
//                                .maxSelectable(6)
//                                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
//                                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
//                                .thumbnailScale(0.85f)
//                                .imageEngine(new GlideEngine())
//                                .forResult(REQUEST_CODE_CHOOSE);
//                    } else {
//                        com.blankj.utilcode.util.ToastUtils.showShort("请先授予应用相关权限");
//                        mOperation.showBasicDialog(R.string.error, R.string.grant_permission, (dialog, which) -> {
////                            Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);    //飞行模式，无线网和网络设置界面
//                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);   //跳转位置服务界面
//                            startActivity(intent);
//                        });
//                    }
//                });
                break;
            case R.id.btn_live_start_live: //开始直播
                if(TextUtils.isEmpty(etLiveTitle.getText())){
                    ToastUtils.showLong(R.string.live_title_null);
                    return;
                }
//                DialogUtil.startWaitingDialog(getActivity(),"开启直播中....");
                btnLiveStartLive.setText("直播开启中....");
                if(LivePath != null && ! LivePath.equals("")){
                    FileUploadUtil.getUploadImageToken(getActivity(),LivePath,REUST_CODE_IMAGE_COVER);
                }
                blLiveType = true; //开启直播
                BaseApplication.blLiveTypeLiveOrChallenge = true;
                BaseApplication.blLIveStart = true;
                break;
        }
    }

    @Override
    public void notifyUpdate(NotifyObject obj) {
        switch (obj.what)
        {
            case 1:
                Log.e("====","广播回来了");
                //直播封面上传成功
                imageUrl =  obj.str;


                if(LivePath == null && LivePath.equals("")){
                    return;
                }
                presenter.onCreate();
                try {
                    presenter.getLiveStartLive(etLiveTitle.getText().toString(),imageUrl,getActivity().getIntent().getStringExtra("liveNo"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                presenter.attachView(mStartLive);


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

    /**
     * 开始直播
     */
    public ITestView mStartLive = new ITestView() {
        @Override
        public void onSuccess(Object object) {
//            controller.liveStartStop();
            pushFlowFragment.contrellerLiveStartStop();
        }

        @Override
        public void onError(Object object) {

        }
    };






    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Uri> mSelected = new ArrayList<>();
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == REUST_CODE_IMAGE_COVER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                LivePath = images.get(0).path;
                Picasso.with(getContext()).load(new File(LivePath)).into(ivLiveCover);
                SharedPreferences.Editor editor= BaseApplication.getUserSp().edit();
                editor.putString("liveCoverpath",LivePath);
                editor.commit();
            }
        }
    }

}
