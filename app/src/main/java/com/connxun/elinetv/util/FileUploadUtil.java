package com.connxun.elinetv.util;

/**
 * Created by connxun-16 on 2018/1/22.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.connxun.elinetv.app.BaseApplication;
import com.connxun.elinetv.app.ConfigSystem;
import com.connxun.elinetv.base.network.ImageRetrofitHelper;
import com.connxun.elinetv.entity.ObjectEntity;
import com.connxun.elinetv.entity.UserVideoEntity;
import com.connxun.elinetv.observer.NotifyListenerManager;
import com.connxun.elinetv.observer.NotifyObject;
import com.connxun.elinetv.presenter.user.ObjectPresenter;
import com.connxun.elinetv.view.LiveBroadcast.LiveActivity;
import com.connxun.elinetv.view.VideoShort.VideoEditingActivity;
import com.connxun.elinetv.view.user.ITestView;
import com.netease.cloud.nos.android.core.CallRet;
import com.netease.cloud.nos.android.core.UploadTaskExecutor;
import com.netease.vcloudnosupload.NOSUpload;
import com.netease.vcloudnosupload.NOSUploadHandler;

import java.io.File;


/**
 * 文件上传
 */
public class FileUploadUtil {
    private static String LOGTAG = FileUploadUtil.class.getCanonicalName();
    private static String accessKey = "be6b7c8b0f844b4b9d07f2ab3ee5b05c";
    private static String secretKey = "f971415b81484b90b1c3ae66ea7fa1ba";
    //上传文件状态值
    int RESULT_CODE_START_LIVE = 1; //直播间上传封面
    int RESULT_CODE_SHORT_VIDEO = 1; //短视频上传封面
    private static UploadTaskExecutor executor;
    private static NOSUpload.UploadExecutor uploadExecutor = null;
    private static ObjectPresenter objectPresenter; //网络请求
    static Context contextt;
    private static String fileName; //文件名称
    private static String FilePath; //文件路径

    private static String mNosToken; //上传凭证
    private static String mBucket; // 桶名
    private static String mObject; //文件名称
    private static NOSUpload nosUpload ;
    private static String Title; //用户输入的标题
    private static int RESULT_CODE ; //区分值

    private static String coverName;
    private static int coverHeight;
    private static int coverWeight;


    private static File mFile;


    @SuppressLint("HandlerLeak")
    static Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (RESULT_CODE)
            {
                case 1: //直播封面上传
                    NotifyListenerManager.getInstance().notifyListener(new NotifyObject(1), LiveActivity.class);//传参数通知
                    break;
                case 2: //短视频封面上传

                    break;

            }
        }
    };

    /**
     * 创建视频文件
     * @param fileName
     * @return
     */
    public static String setFilePath(String fileName){
        File file = new File(ConfigSystem.MEI_FILE_VIdeo_PATH+fileName);
        if (file != null && !file.exists()) {
            file.mkdirs();
        }
        return file.getPath();
    }


    /**
     * 获取图片上传的token
     */
    public static void getUploadImageToken(Context context,String filePath,int ResultCode) {
        contextt = context;
        FilePath = filePath;
        RESULT_CODE = ResultCode;
        fileName  = filePath.substring(filePath.lastIndexOf("/") + 1);
        objectPresenter = new ObjectPresenter(context);
        objectPresenter.onCreate();
        objectPresenter.getObjcetToken(fileName);
        objectPresenter.attachView(testView);


    }

    /**
     * 上传MP4短视频文件
     * @param context context 对象
     * @param filePath 文件路径
     * @param title 文件名称
     * @param code 返回值区分值
     */
    public static void getUploadFileToken(Context context ,int coverHtight,int coverWeigth,String UploadcoverName,String filePath,String title,int code){
        contextt = context;
        FilePath = filePath;
        fileName  = filePath.substring(filePath.lastIndexOf("/") + 1);
        Title = title;
        RESULT_CODE = code;

        coverHeight = coverHtight;
        coverWeight = coverWeigth;
        coverName = UploadcoverName;


        objectPresenter = new ObjectPresenter(context);
        objectPresenter.onCreate();
        objectPresenter.getUserVideoToken();
        objectPresenter.attachView(FIleUpload);
    }

    /**
     * 文件上传返回的token
     */
    public static ITestView testView = new ITestView() {
        @Override
        public void onSuccess(Object object) {

            ObjectEntity jsonEntity = (ObjectEntity) object;
            String token = jsonEntity.getData().getToken();
            String url = jsonEntity.getData().getUrl();
            ImageRetrofitHelper.uploadImageDNS(RESULT_CODE,token,FilePath,url);
        }

        @Override
        public void onError(Object object) {

        }
    };
    /**
     * 文件上传返回的token
     */
    public static ITestView FIleUpload = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            UserVideoEntity jsonEntity = (UserVideoEntity) object;
            com.connxun.elinetv.base.util.LogUtil.Log(LOGTAG, com.connxun.elinetv.base.util.LogUtil.LOG_E,"文件上传: "+jsonEntity);

            String token = jsonEntity.getData().getVideoToken().getToken();
            String accid = jsonEntity.getData().getVideoToken().getAccid();
            nosUpload = NOSUpload.getInstace(contextt);
            if (nosUpload != null) {
                /** 这里的accid,token需要用户根据文档 http://dev.netease.im/docs/product/%E9%80%9A%E7%94%A8/%E7%82%B9%E6%92%AD%E9%80%9A%E7%94%A8/%E7%A7%BB%E5%8A%A8%E7%AB%AF%E4%B8%8A%E4%BC%A0%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E
                 中的/app/vod/thirdpart/user/create 接口创建 **/
                NOSUpload.Config config = new NOSUpload.Config();
                config.appKey = "48ff43fa171120daec40375eddcbfb06";
                config.accid = accid;
                config.token = token;
                nosUpload.setConfig(config);
                uploadInit(contextt,FilePath,Title);
            }
        }

        @Override
        public void onError(Object object) {

        }
    };




    public static void uploadInit(Context context, String filePath,String title) {

        mFile = new File(filePath);
        if(mFile == null){
            ToastUtils.showLong("please select file first!");
        }

        String name = mFile.getName();

        String userDefInfo = BaseApplication.getUserNo()+","+coverName+"_"+coverWeight+"_"+coverHeight+","+BaseApplication.getUserSp().getString("cityCode","0")+",0,"+title;
        Log.e("videoEditingACtivity" ,"文件上传:" + userDefInfo);

        nosUpload.fileUploadInit(name, null, -1, -1, null, null, -1, userDefInfo, new NOSUploadHandler.UploadInitCallback() {
            @Override
            public void onSuccess(String nosToken, String bucket, String object) {
                mNosToken = nosToken;
                mBucket = bucket;
                mObject = object;
                fileUpload();
                com.netease.cloud.nos.android.utils.LogUtil.e(
                        LOGTAG,
                        "context onSuccess: "
                                + nosToken
                                + ", bucket: "
                                + bucket+ ", object: "
                                + object);
            }

            @Override
            public void onFail(int code, String msg) {
                com.netease.cloud.nos.android.utils.LogUtil.e(
                        LOGTAG,
                        "context onFail: "
                                + code
                                + ", newUploadContext: "
                                + msg);
            }
        });
    }




    //视频上传
    public static void fileUpload(){
        if(mFile == null){
            ToastUtils.showLong("please select file first!");
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                {
                    String uploadContext = null;
                    /**
                     *  查看一个这个文件是否已经上传过，如果上传过就取出它的uploadcontext, 传给NosUpload.putFileByHttp进行断点续传
                     *  当uploadContext是null时, 就从头开始往上传
                     */
                    String tmp = nosUpload.getUploadContext(mFile);
                    if (tmp != null && !tmp.equals("")) {
                        uploadContext = tmp;
                    }
                    try {

                        uploadExecutor = nosUpload.putFileByHttp(mFile,
                                uploadContext, mBucket, mObject, mNosToken, new NOSUploadHandler.UploadCallback() {
                                    @Override
                                    public void onUploadContextCreate(
                                            String oldUploadContext,
                                            String newUploadContext) {
                                        /**
                                         *  将新的uploadcontext保存起来
                                         */
                                        nosUpload.setUploadContext(mFile, newUploadContext);
                                    }

                                    @Override
                                    public void onProcess(long current, long total) {
//                                        int pro = (int)((1.0f* current / total) * progressBar.getMax());
//                                        progressBar.setProgress(pro);
                                    }

                                    @Override
                                    public void onSuccess(CallRet ret) {
                                        uploadExecutor = null;
                                        /**
                                         *  清除该文件对应的uploadcontext
                                         */
                                        nosUpload.setUploadContext(mFile, "");
                                        Log.e(LOGTAG,"视频文件上传成功: " + ret.getCallbackRetMsg());
                                        NotifyListenerManager.getInstance().notifyListener(new NotifyObject(1003), VideoEditingActivity.class);//传参数通知
                                        ToastUtils.showLong("视频上传成功");
                                    }

                                    @Override
                                    public void onFailure(CallRet ret) {
                                        uploadExecutor = null;
                                        ToastUtils.showLong("视频文件上传失败");
                                        Log.e(LOGTAG,"视频上传失败: " + ret.getCallbackRetMsg());
//                                        progressBar.setProgress(0);
                                    }

                                    @Override
                                    public void onCanceled(CallRet ret) {
                                        uploadExecutor = null;

                                        ToastUtils.showLong("upload cancel");
                                    }
                                });
                        uploadExecutor.join();
                    } catch (Exception ex) {
                    }

                }
            }
        }).start();



    }







}
