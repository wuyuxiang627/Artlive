package com.connxun.elinetv.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;

import com.connxun.elinetv.base.util.LogUtil;
import com.connxun.elinetv.util.PicassoImageLoader;
import com.connxun.elinetv.util.im.NimSDKOptionConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.mob.MobSDK;
import com.netease.cloud.nos.android.core.AcceleratorConf;
import com.netease.cloud.nos.android.core.WanAccelerator;
import com.netease.cloud.nos.android.exception.InvalidChunkSizeException;
import com.netease.cloud.nos.android.exception.InvalidParameterException;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.impl.NimUIKitImpl;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.util.NIMUtil;
import com.orhanobut.logger.Logger;

import org.xutils.x;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by connxun-16 on 2017/12/19.
 */

public class BaseApplication extends MultiDexApplication {
    public static Context baseContext = null;

    private static BaseApplication baseApplication; //context对象

    public static String token; //用户token
    public static String userNo; //用户标识
    public static String MD5sign; //加密串

    public static boolean blDialog = true; //是否显示加载框;


    public static Long timeStamp; //时间

    public static SharedPreferences userSharedPreferences = null; //sp对象

    public static SharedPreferences.Editor editor = null; //sp存取对象
    public static boolean isDialogShow = true; //是否显示dialog
    private String MY_TAG = "CONN_XUNN";
    private static String mToken = "12399123";


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);


        //context对象
        Context context = getApplicationContext();
        baseContext = context;

        //IM登录
        IMLogion();

        //context
        baseApplication = this;
        //图片加载-初始化
        Fresco.initialize(this);
        //初始化第三方
        MobSDK.init(this);


        //初始化文件储存
        try {
            initNOS();
        } catch (InvalidChunkSizeException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        }

        //解决7.0拍照问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        //获取系统时间
        Date dt = new Date();
        timeStamp = dt.getTime();

        //sp对象
        SharedPreferences sp = baseContext.getSharedPreferences("User", MODE_MULTI_PROCESS);
        userNo = sp.getString("userNo", "");
        token = sp.getString("token", "");

        Logger.d("开始了哟");
        //创建文件
        fileCreat();


        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new PicassoImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(false);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素


    }


    /**
     * 对NOS上传加速Android-SDK进行配置，请在初始化时设置配置，初始化完成后修改配置是无效的
     */
    private void initNOS() throws InvalidChunkSizeException, InvalidParameterException {
        AcceleratorConf conf = new AcceleratorConf();
        // SDK会根据网络类型自动调整上传分块大小，如果网络类型无法识别，将采用设置的上传分块大小
        // 默认32K，如果网络环境较差，可以设置更小的分块
        // ChunkSize的取值范围为：[4K, 4M]，不在范围内将抛异常InvalidChunkSizeException
        conf.setChunkSize(1024 * 32);

        // 设置分块上传失败时的重试次数，默认2次
        // 如果设置的值小于或等于0，将抛异常InvalidParameterException
        conf.setChunkRetryCount(2);

        // 设置文件上传socket连接超时，默认为10s
        // 如果设置的值小于或等于0，将抛异常InvalidParameterException
        conf.setConnectionTimeout(10 * 1000);

        // 设置文件上传socket读写超时，默认30s
        // 如果设置的值小于或等于0，将抛异常InvalidParameterException
        conf.setSoTimeout(30 * 1000);

        // 设置LBS查询socket连接超时，默认为10s
        // 如果设置的值小于或等于0，将抛异常InvalidParameterException
        conf.setLbsConnectionTimeout(10 * 1000);

        // 设置LBS查询socket读写超时，默认10s
        // 如果设置的值小于或等于0，将抛异常InvalidParameterException
        conf.setLbsSoTimeout(10 * 1000);

        // 设置刷新上传边缘节点的时间间隔，默认2小时
        // 合法值为大于或等于60s，设置非法将采用默认值
        // 注：当发生网络切换，Android-SDK会在下次上传文件时做一次接入点刷新
        conf.setRefreshInterval(DateUtils.HOUR_IN_MILLIS * 2);

        // 设置统计监控程序统计发送间隔，默认120s
        // 合法值为大于或等于60s，设置非法将采用默认值
        conf.setMonitorInterval(120 * 1000);

        // 设置httpClient，默认值为null
        // 非null：使用设置的httpClient进行文件上传和统计信息上传
        // null：使用sdk内部的机制进行文件上传和统计信息上传
        //        conf.setHttpClient(null);

        // 设置是否用线程进行统计信息上传，默认值为false
        // true：创建线程进行统计信息上传
        // false：使用service进行统计信息上传
        conf.setMonitorThread(true);

        // 配置赋值给上传加速类
        WanAccelerator.setConf(conf);

    }

    //创建文件
    private void fileCreat() {
        File file = new File(ConfigSystem.MEI_FILE_PATH);
        if (!file.exists()) file.mkdirs();
    }


    //获取用户token
    public static final String getToken() {
            SharedPreferences sp = baseContext.getSharedPreferences("User", MODE_MULTI_PROCESS);
            String token = sp.getString("token", "");
            return token;
    }

    public static final void setToken(String tokens) {
        token = tokens;
    }

    //获取用户标识
    public static final String getUserNo() {
        SharedPreferences sp = baseContext.getSharedPreferences("User", MODE_MULTI_PROCESS);
        String userNo = sp.getString("userNo", null);
        return userNo;
    }

    public static final void setUserNo(String userNos) {
        userNo = userNos;
    }

    /**
     * 获取context 对象
     *
     * @return
     */
    public static final BaseApplication getContext() {
        return baseApplication;
    }

    /**
     * 获取系统时间戳
     *
     * @return
     */
    public static long getTimeDate() {

        return timeStamp;

    }


    /**
     * 获取sharedPreferences对象
     *
     * @return
     */
    @SuppressLint("WrongConstant")
    public static final SharedPreferences getUserSp() {
        if (userSharedPreferences == null) {
            return baseContext.getSharedPreferences("User", 0);
        }
        return userSharedPreferences;
    }


    public static String getUserLoginNo() {
        return getUserSp().getString("userNo", "");
    }

    /**
     * 获取sp存取对象
     *
     * @return
     */
    public static SharedPreferences.Editor getEditor() {
        if (editor == null) {
            return getUserSp().edit();
        }
        return editor;

    }

    /**
     * 判断手机号是否正确
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^(13[0-9]|14[57]|15[0-35-9]|17[6-8]|18[0-9])[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }


    public static void setSign(HashMap<String,String> hashMap){
        //获取ASCLL编码排序字符串
        String asccllString =  AscllMap(hashMap);
        //添加token
        String sign = asccllString+"&key="+ BaseApplication.token;
        Log.e("sign","sign  "+sign);
        //MD5加密
        try {
            MD5sign =MD5(sign).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("sign","MD5sign  "+MD5sign);
    }
    //MD5加密-pwd
//    public static String MD5(String string)  {
//        if (TextUtils.isEmpty(string)) {
//            return "";
//        }
//        MessageDigest md5 = null;
//
//        try {
//            md5 = MessageDigest.getInstance("MD5");
//            byte[] bytes = md5.digest(string.getBytes());
//            String result = "";
//            for (byte b :bytes){
//                String temp = Integer.toHexString(b & 0xff);
//                if(temp.length() == 1){
//                    temp = "0" +temp;
//                }
//                result += temp;
//            }
//            return result;
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }

    public static String MD5(String data) throws Exception {
        java.security.MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }





    //ASCLL编码排序
    public static String AscllMap(HashMap map){
        Collection<String> keyset= map.keySet();

        List list=new ArrayList<String>(keyset);

        Collections.sort(list);
        //这种打印出的字符串顺序和微信官网提供的字典序顺序是一致的
        String stringBuilder  = new String();
        for(int i=0;i<list.size();i++){
            stringBuilder = stringBuilder+list.get(i)+"="+map.get(list.get(i))+"&";
//            System.out.println(list.get(i)+"="+map.get(list.get(i)));
        }
        LogUtil.Log("helper",LogUtil.LOG_E,stringBuilder.substring(0,stringBuilder.length()-1));
        return stringBuilder.substring(0,stringBuilder.length()-1);
    }


    /**
     * IM登录
     */
    public static void IMLogion(){
        // SDK初始化（启动后台服务，若已经存在用户登录信息， SDK 将完成自动登录）
        NIMClient.init(BaseApplication.baseContext,null, NimSDKOptionConfig.getSDKOptions(BaseApplication.baseContext));

        // 以下逻辑只在主进程初始化时执行
        if (NIMUtil.isMainProcess(BaseApplication.baseContext)) {

            // 初始化
            NimUIKit.init(BaseApplication.baseContext);
            //405090083973103616
            //405090083973103616
            final String userNo = BaseApplication.baseContext.getSharedPreferences("User",0).getString("userNo","");
            LoginInfo info = new LoginInfo(userNo,mToken); // config...
            RequestCallback<LoginInfo> callback =
                    new RequestCallback<LoginInfo>() {
                        @Override
                        public void onSuccess(LoginInfo loginInfo) {
                            NimUIKitImpl.loginSuccess(userNo);
                            Log.e("application" , "onSuccess");
                            Log.e("application" , "onSuccess");
                        }

                        @Override
                        public void onFailed(int i) {
                            Log.e("application" , "onFailed: "+i);
                        }

                        @Override
                        public void onException(Throwable throwable) {
                            Log.e("application" , "onException:" + throwable.toString());
                        }
                        // 可以在此保存LoginInfo到本地，下次启动APP做自动登录用
                    };
            NIMClient.getService(AuthService.class).login(info)
                    .setCallback(callback);

        }
    }











}
