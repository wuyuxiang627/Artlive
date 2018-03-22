package com.connxun.elinetv.base.network;

/**
 * Created by connxun-16 on 2018/2/6.
 */

import android.util.Log;

import com.connxun.elinetv.entity.ImageDNS;
import com.connxun.elinetv.observer.NotifyListenerManager;
import com.connxun.elinetv.observer.NotifyObject;
import com.connxun.elinetv.util.ToastUtils;
import com.connxun.elinetv.util.URLFactory;
import com.connxun.elinetv.view.LiveBroadcast.CaptureFragment;
import com.connxun.elinetv.view.MediaPreview.fragment.FreeLiveFragment;
import com.connxun.elinetv.view.My.MyEditingActivity;
import com.connxun.elinetv.view.VideoShort.VideoEditingActivity;
import com.connxun.elinetv.view.user.MyOpinion.MyOpinionActivity;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
//import com.netease.nim.uikit.common.util.log.LogUtil;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//import static com.netease.nimlib.sdk.msg.constant.MsgTypeEnum.file;

/**
 * 网易上传图片相关的网络请求工具类
 */
public class ImageRetrofitHelper {
    private static String TAG = "ImageRetrofitHelper";
    private static String LBS_VERSION = "1.0";

    private static String BUCKETNAME = "art"; //桶名
    private static String OFFSET = "0"; //
    private static String COMPLETE = "true"; //是否最后一片

    private static int RESULT_CODE; //区分上传位置
    private static String Url; //图片位置


    private static final int RESULT_CODE_LIVE_COVER = 1001; //直播封面
    private static final int RESULT_CODE_SHORT_VIDEO_COVER = 1002; //短视频封面
    private static final int RESULT_CODE_OPINION = 1003; //意见反馈
    private static final int REUST_CODE_USER_PHOTO_COVER = 1008; //修改资料


    public static void uploadImageDNS( int code, final String token, final String filePath,String imageUrl){
        RESULT_CODE = code;
        Url = imageUrl;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLFactory.IMAGE_DNS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<ImageDNS> mCall = retrofitService.getImageDNS(LBS_VERSION, BUCKETNAME);

        mCall.enqueue(new Callback<ImageDNS>() {
            public void onResponse(Call<ImageDNS> call, Response<ImageDNS> response) {
                for(String ip : response.body().getUpload()){
//                    LogUtil.e(TAG,"onResponse ResponseBody2: "+ ip.toString());
                    try{

                        newUPLoad(ip,filePath,token);
                    }catch (Exception e){
                        Log.e(TAG,e.toString());
                    }
                    break;

                }
            }

            @Override
            public void onFailure(Call<ImageDNS> call, Throwable t) {
//                LogUtil.e(TAG,"onFailure ResponseBody: "+t.toString());
            }
        });
    }



    static String fileNAme;

    public static void newUPLoad(String url,  String filePath, final String token){
         String fileName  = filePath.substring(filePath.lastIndexOf("/") + 1);
        final String urlIP = url.substring(url.lastIndexOf("/")+1);
        url = url + "/"+BUCKETNAME+"/"+fileName+"?"+"offset=0&complete=true&version=1.0";
        File file = new File(filePath);
        RequestParams paramsxUtils = new RequestParams(url);
        paramsxUtils.setUseCookie(true);
        paramsxUtils.addHeader("Host",urlIP);
        paramsxUtils.addHeader("x-nos-token",token);
        paramsxUtils.addBodyParameter("file",file);

        Log.e("imgupload","url"+ url);
        org.xutils.common.Callback.Cancelable post = x.http().post(paramsxUtils, new org.xutils.common.Callback.ProgressCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Log.e(TAG, "成功了" + s);

                switch (RESULT_CODE) {
                    case RESULT_CODE_LIVE_COVER:
                        NotifyListenerManager.getInstance().notifyListener(new NotifyObject(1, Url), FreeLiveFragment.class);//传参数通知
//                        NotifyListenerManager.getInstance().notifyListener(new NotifyObject(1, Url), CaptureFragment.class);//传参数通知
                        break;
                    case RESULT_CODE_SHORT_VIDEO_COVER:
                        NotifyListenerManager.getInstance().notifyListener(new NotifyObject(RESULT_CODE_SHORT_VIDEO_COVER), VideoEditingActivity.class);//传参数通知
                        break;
                    case RESULT_CODE_OPINION:
                        NotifyListenerManager.getInstance().notifyListener(new NotifyObject(1, Url), MyOpinionActivity.class);//传参数通知
                        break;
                    case REUST_CODE_USER_PHOTO_COVER:
                        NotifyListenerManager.getInstance().notifyListener(new NotifyObject(1, Url), MyEditingActivity.class);//传参数通知
                        break;
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                DialogMaker.dismissProgressDialog();
                ToastUtils.showLong("开始直播失败,请稍后重试");
                Log.e(TAG, "失败了" + throwable.toString());
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(long l, long l1, boolean b) {

            }
        });


    }


}
