package com.connxun.elinetv.view.Index;

/**
 * Created by connxun-16 on 2017/12/28.
 */

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.connxun.elinetv.IM.NimController;
import com.connxun.elinetv.R;
import com.connxun.elinetv.app.BaseApplication;
import com.connxun.elinetv.base.ui.BaseActivity;
import com.connxun.elinetv.base.ui.NoScrollViewPager;
import com.connxun.elinetv.base.util.LogUtil;
import com.connxun.elinetv.entity.OpenLive;
import com.connxun.elinetv.presenter.Live.LivePresenter;
import com.connxun.elinetv.util.ToastUtils;
import com.connxun.elinetv.view.Find.FindFragment;
import com.connxun.elinetv.view.Live.fragment.HotFragment;
import com.connxun.elinetv.view.LiveBroadcast.PublishParam;
import com.connxun.elinetv.view.MediaPreview.LIveRoomActivity;
import com.connxun.elinetv.view.Video.VideoFragment;
import com.connxun.elinetv.view.user.ITestView;
import com.connxun.elinetv.view.user.MindFragment;
import com.connxun.elinetv.view.user.login.LoginActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static com.connxun.elinetv.view.LiveBroadcast.CapturePreviewController.EXTRA_PARAMS;

/**
 * 首页
 */
@ContentView(R.layout.activity_home_page)
public class  HomePageActivity extends BaseActivity{
    /**控件*/
    @ViewInject(R.id.rg_home_group)
    RadioGroup radioGroupHome;
    @ViewInject(R.id.rb_home_live_broadcast)
    RadioButton rbHomeLiveBroadcase; //直播
    @ViewInject(R.id.rb_home_video)
    RadioButton rbHomeVideo; //视频
    @ViewInject(R.id.rb_home_find)
    RadioButton rbHomeFind; //发现
    @ViewInject(R.id.rb_home_mind)
    RadioButton rbHomeMind; //我的
    @ViewInject(R.id.vp_home_viewapger)
    NoScrollViewPager vpHomeViewpager;
    @ViewInject(R.id.ib_home_start_live)
    ImageButton ibStartLive; //开始直播

    //滑动相关数据
    ArrayList<Fragment> fragments ; //framgnet d的集合
    MainPagerAdapter pagerAdapter; //viewpager的adapter

    private LivePresenter presenter = new LivePresenter(this);

    //传递参数
    PublishParam publishParam = null;

    private final int WRITE_PERMISSION_REQ_CODE = 100;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    LogUtil.Log("MAIN",LogUtil.LOG_E,"结果来源: "+aMapLocation.getLocationType());
                    aMapLocation.getLatitude();//获取纬度
                    LogUtil.Log("MAIN",LogUtil.LOG_E,"获取纬度: "+aMapLocation.getLatitude());
                    aMapLocation.getLongitude();//获取经度
                    LogUtil.Log("MAIN",LogUtil.LOG_E,"获取经度: "+aMapLocation.getLongitude());
                    aMapLocation.getAccuracy();//获取精度信息
                    LogUtil.Log("MAIN",LogUtil.LOG_E,"获取精度信息: "+aMapLocation.getAccuracy());
                    aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    LogUtil.Log("MAIN",LogUtil.LOG_E,"地址: "+aMapLocation.getAddress());
                    aMapLocation.getCountry();//国家信息
                    LogUtil.Log("MAIN",LogUtil.LOG_E,"国家信息: "+aMapLocation.getCountry());
                    aMapLocation.getProvince();//省信息
                    LogUtil.Log("MAIN",LogUtil.LOG_E,"省信息: "+aMapLocation.getProvince());
                    aMapLocation.getCity();//城市信息
                    LogUtil.Log("MAIN",LogUtil.LOG_E,"城市信息: "+aMapLocation.getCity());
                    aMapLocation.getDistrict();//城区信息
                    LogUtil.Log("MAIN",LogUtil.LOG_E,"城区信息: "+aMapLocation.getDistrict());
                    aMapLocation.getStreet();//街道信息
                    LogUtil.Log("MAIN",LogUtil.LOG_E,"街道信息: "+aMapLocation.getStreet());
                    aMapLocation.getStreetNum();//街道门牌号信息
                    LogUtil.Log("MAIN",LogUtil.LOG_E,"街道门牌号信息: "+aMapLocation.getStreetNum());
                    aMapLocation.getCityCode();//城市编码
                    LogUtil.Log("MAIN",LogUtil.LOG_E,"城市编码: "+aMapLocation.getCityCode());
                    aMapLocation.getAdCode();//地区编码
                    LogUtil.Log("MAIN",LogUtil.LOG_E,"地区编码: "+aMapLocation.getAdCode());
                    aMapLocation.getAoiName();//获取当前定位点的AOI信息
                    LogUtil.Log("MAIN",LogUtil.LOG_E,"获取当前定位点的AOI信息: "+aMapLocation.getAoiName());
                    aMapLocation.getBuildingId();//获取当前室内定位的建筑物Id
                    LogUtil.Log("MAIN",LogUtil.LOG_E,"获取当前室内定位的建筑物Id: "+aMapLocation.getBuildingId());
                    aMapLocation.getFloor();//获取当前室内定位的楼层
                    LogUtil.Log("MAIN",LogUtil.LOG_E,"获取当前室内定位的楼层: "+aMapLocation.getFloor());
//                    aMapLocation.getGpsStatus();//获取GPS的当前状态
                    SharedPreferences.Editor editer = BaseApplication.getUserSp().edit();
                    editer.putString("city",aMapLocation.getCity());
                    editer.putString("cityCode",aMapLocation.getAdCode());
                    editer.commit();

                }else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError","location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }
    };



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置Adapter
        setAdapter();
        //请求文件权限
//        FileUploadUtil.getUploadFileToken(this,"/storage/emulated/0/Artlive/video/1517047330922.mp4");
        presenter.onCreate();
        try {
            presenter.getliveRanKingList("10","1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        presenter.attachView(mOpenLive);

        //设置Listener
        setClickListener();
        //设置定位
        setLocation();

    }



    public ITestView mOpenLive  = new ITestView() {
        @Override
        public void onSuccess(Object object) {

        }

        @Override
        public void onError(Object object) {

        }
    };

    private String roomId;
    private String push_url;
    private String quality = QUALITY_SD;
    public static final String QUALITY_SD = "SD";
    public static final String IS_AUDIENCE = "is_audience";
    private String liveNo;
    public ITestView mOpensLive = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            OpenLive openLive = (OpenLive) object;
            roomId = openLive.getData().getRoomid()+"";
            push_url = openLive.getData().getPushUrl();
            liveNo = openLive.getData().getLiveNo();
            PublishParam publishParam = new PublishParam();
            publishParam.pushUrl = push_url;
            publishParam.definition = quality;
            publishParam.openVideo = true;
            publishParam.openAudio = true;
            publishParam.useFilter = true;
            publishParam.faceBeauty = false;

            Intent intent = new Intent(context, LIveRoomActivity.class);
            intent.putExtra(IS_AUDIENCE, false);
            intent.putExtra(NimController.EXTRA_ROOM_ID, roomId);
            intent.putExtra(EXTRA_PARAMS, publishParam);
            intent.putExtra("liveNo", liveNo);
            intent.putExtra("liveMOdel", openLive.getData());
            startActivity(intent);
//            LIveRoomActivity.startLive(HomePageActivity.this, roomId, publishParam);

        }

        @Override
        public void onError(Object object) {

        }
    };



    private void setLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(3600000);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(30000);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] paramArrayOfInt) {
        super.onRequestPermissionsResult(requestCode, permissions, paramArrayOfInt);
        if (requestCode == PERMISSON_REQUESTCODE) {
                setLocation();
        }
    }

    /**
     * 设置Listener
     */
    private void setClickListener() {
        ibStartLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkPublishPermission()){
                    ToastUtils.showLong("请先允许app所需要的权限");
                    return;
                }

//                NimUIKit.startP2PSession(context, "wuyuxiang1");

//                Intent intent = new Intent(HomePageActivity.this, LiveActivity.class);
//                intent.putExtra("data", publishParam);
//                isLogin_startActivity(intent);

                if(BaseApplication.getUserNo() != null){
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
                    presenter.attachView(mOpensLive);
                }else {
                    startActivity(new Intent(HomePageActivity.this, LoginActivity.class));
                }





            }
        });

        radioGroupHome.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_home_live_broadcast:
                        vpHomeViewpager.setCurrentItem(0);
                        break;
                    case R.id.rb_home_video:

                        vpHomeViewpager.setCurrentItem(1);
                        break;
                    case R.id.rb_home_find:
                        vpHomeViewpager.setCurrentItem(2);
                        break;
                    case R.id.rb_home_mind:
                        vpHomeViewpager.setCurrentItem(3);
                        break;
                }
            }
        });
    }
    /**
     * 设置ViewPager的Adapter
     */
    private void setAdapter() {
        fragments = new ArrayList<Fragment>();
        fragments.add(new HotFragment());
        fragments.add(new VideoFragment());
        fragments.add(new FindFragment());
        fragments.add(new MindFragment());

        //构建Adapter
        pagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), fragments);
        vpHomeViewpager.setAdapter(pagerAdapter);
        vpHomeViewpager.setOffscreenPageLimit(4);

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


//    public static class PublishParam implements Serializable {
//        String pushUrl = null; //推流地址
//        lsMediaCapture.StreamType streamType = AV;  // 推流类型
//        lsMediaCapture.FormatType formatType = RTMP; // 推流格式
//        String recordPath; //文件录制地址，当formatType 为 MP4 或 RTMP_AND_MP4 时有效
//        lsMediaCapture.VideoQuality videoQuality = lsMediaCapture.VideoQuality.SUPER; //清晰度
//        boolean isScale_16x9 = false; //是否强制16:9
//        boolean useFilter = true; //是否使用滤镜
//        VideoEffect.FilterType filterType = VideoEffect.FilterType.clean; //滤镜类型
//        boolean frontCamera = true; //是否默认前置摄像头
//        boolean watermark = false; //是否添加水印
//        boolean qosEnable = true;  //是否开启QOS
//        boolean graffitiOn = false; //是否添加涂鸦
//        boolean uploadLog = true; //是否上传SDK运行日志
//    }


    private boolean checkPublishPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(HomePageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(HomePageActivity.this, Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(HomePageActivity.this, Manifest.permission.RECORD_AUDIO)) {
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(HomePageActivity.this, Manifest.permission.READ_PHONE_STATE)) {
                permissions.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(HomePageActivity.this,
                        (String[]) permissions.toArray(new String[0]),
                        WRITE_PERMISSION_REQ_CODE);
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
        mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
    }

    //退出时的时间
    private long mExitTime;
    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(HomePageActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            System.exit(0);
            finish();
        }
    }


}

