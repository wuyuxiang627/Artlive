package com.connxun.elinetv.presenter.video;

import android.content.Context;
import android.content.Intent;

import com.connxun.elinetv.app.BaseApplication;
import com.connxun.elinetv.base.network.BaseSubscriber;
import com.connxun.elinetv.base.network.DataManager;
import com.connxun.elinetv.base.ui.BaseActivity;
import com.connxun.elinetv.base.util.LogUtil;
import com.connxun.elinetv.entity.Entity;
import com.connxun.elinetv.entity.EntityObject;
import com.connxun.elinetv.entity.GetVideoInfo;
import com.connxun.elinetv.entity.JsonEntity;
import com.connxun.elinetv.entity.Video.Talk;
import com.connxun.elinetv.entity.VideoEtivity;
import com.connxun.elinetv.presenter.Presenter;
import com.connxun.elinetv.util.ToastUtils;
import com.connxun.elinetv.view.View;
import com.connxun.elinetv.view.user.ITestView;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by connxun-16 on 2018/2/8.
 */

public class VideoPresenter  implements Presenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private ITestView View;
    private Entity<VideoEtivity> videoEntity; //视频列表
    private Entity<Talk> talkEntity; //评论列表
    private EntityObject<GetVideoInfo> videoInfoEntity; //视频列表

    private JsonEntity sendTalkEntitiy;


    private String TAG = "VideoPresenter";


    public VideoPresenter(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        manager = new DataManager(mContext);
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void onStop() {
        if (mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void attachView(View view) {
        View = (ITestView) view;
    }

    @Override
    public void attachIncomingIntent(Intent intent) {
    }


    /**
     * 获取视频列表
     * @param page
     * @param length
     * @param touserNO
     * @throws UnsupportedEncodingException
     */
    public void getVideoLIst(String page,String length,String touserNO){
        mCompositeSubscription.add(manager.getVideoLIst(page,length,touserNO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Entity<VideoEtivity>>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if (videoEntity != null) LogUtil.Log(TAG, LogUtil.LOG_E, "getVideoLIst 成功请求回来的参数： " + videoEntity.toString());
                        View.onSuccess(videoEntity);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        ToastUtils.showLong("请求失败");
                    }

                    @Override
                    public void onNext(Entity<VideoEtivity> videoEtivityEntity) {
                        super.onNext(videoEtivityEntity);
                        videoEntity = videoEtivityEntity;
                    }
                })
        );
    }


    public void getVideoInfo(String videoNo){
        mCompositeSubscription.add(manager.getVideoInfo(videoNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<EntityObject<GetVideoInfo>>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if (videoInfoEntity != null) LogUtil.Log(TAG, LogUtil.LOG_E, "getVideoInfo成功请求回来的参数： " + videoInfoEntity.toString());
                        View.onSuccess(videoInfoEntity);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        ToastUtils.showLong("请求失败");
                    }

                    @Override
                    public void onNext(EntityObject<GetVideoInfo> videoEtivity) {
                        super.onNext(videoEtivity);
                        videoInfoEntity = videoEtivity;
                    }
                })
        );

    }


    /**
     * 评论列表
     * @param type
     * @param extendNo
     */
    public void getContentList(int page,String type, String extendNo){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("page", page+"");
        hashMap.put("length", "20");
        hashMap.put("type", type);
        hashMap.put("extendNo", extendNo);
        hashMap.put("time", BaseApplication.getTimeDate() + "");

        BaseApplication.setSign(hashMap);

        mCompositeSubscription.add(manager.getContentList(page+"",type,extendNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Entity<Talk>>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if (talkEntity != null) LogUtil.Log(TAG, LogUtil.LOG_E, "getVideoInfo成功请求回来的参数： " + talkEntity.toString());
                        if(talkEntity.getCode().equals("200")){
                            View.onSuccess(talkEntity);
                        }else {
                            ToastUtils.showLong(talkEntity.getMsg());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        ToastUtils.showLong("请求失败");
                    }

                    @Override
                    public void onNext(Entity<Talk> nextEtivity) {
                        talkEntity = nextEtivity;
                    }
                })
        );

    }



    //发表评论
    public void getContentAdd(String type, String content, String toUserNo, String extendNo){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("type", type);
        hashMap.put("content", content);
        hashMap.put("toUserNo", toUserNo);
        hashMap.put("extendNo", extendNo);
        hashMap.put("time", BaseApplication.getTimeDate() + "");

        BaseApplication.setSign(hashMap);

        mCompositeSubscription.add(manager.getContentAdd( type,  content,  toUserNo,  extendNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<JsonEntity>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if (sendTalkEntitiy != null) LogUtil.Log(TAG, LogUtil.LOG_E, "getVideoInfo成功请求回来的参数： " + sendTalkEntitiy.toString());
                        View.onSuccess(sendTalkEntitiy);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        ToastUtils.showLong("请求失败");
                    }

                    @Override
                    public void onNext(JsonEntity nextEtivity) {
                        sendTalkEntitiy = nextEtivity;
                    }
                })
        );
    }


    //点赞
    public void getLoveSumAdd(String videoNo){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("videoNo", videoNo);
        hashMap.put("time", BaseApplication.getTimeDate() + "");

        BaseApplication.setSign(hashMap);

        mCompositeSubscription.add(manager.getLoveSumAdd( videoNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<JsonEntity>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if (sendTalkEntitiy != null) LogUtil.Log(TAG, LogUtil.LOG_E, "getVideoInfo成功请求回来的参数： " + sendTalkEntitiy.toString());
                        View.onSuccess(sendTalkEntitiy);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        ToastUtils.showLong("请求失败");
                    }

                    @Override
                    public void onNext(JsonEntity nextEtivity) {
                        sendTalkEntitiy = nextEtivity;
                    }
                })
        );
    }
    //取消点赞
    public void getNolove(String videoNo){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("videoNo", videoNo);
        hashMap.put("time", BaseApplication.getTimeDate() + "");

        BaseApplication.setSign(hashMap);

        mCompositeSubscription.add(manager.getNolove( videoNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<JsonEntity>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if (sendTalkEntitiy != null) LogUtil.Log(TAG, LogUtil.LOG_E, "getVideoInfo成功请求回来的参数： " + sendTalkEntitiy.toString());
                        View.onSuccess(sendTalkEntitiy);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        ToastUtils.showLong("请求失败");
                    }

                    @Override
                    public void onNext(JsonEntity nextEtivity) {
                        sendTalkEntitiy = nextEtivity;
                    }
                })
        );
    }


    /**
     * 点赞的视频
     * @throws UnsupportedEncodingException
     */
    public void getVideoUserLoveVideo(String page,String length,String userNo){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("userNo", userNo);
        hashMap.put("page", page);
        hashMap.put("length", length);
        hashMap.put("time", BaseApplication.getTimeDate() + "");
        BaseApplication.setSign(hashMap);

        mCompositeSubscription.add(manager.getVideoUserLoveVideo(page,length,userNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Entity<VideoEtivity>>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if (videoEntity != null) LogUtil.Log(TAG, LogUtil.LOG_E, "getVideoUserLoveVideo 成功请求回来的参数： " + videoEntity.toString());
                        View.onSuccess(videoEntity);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        ToastUtils.showLong("请求失败");
                    }

                    @Override
                    public void onNext(Entity<VideoEtivity> videoEtivityEntity) {
                        super.onNext(videoEtivityEntity);
                        videoEntity = videoEtivityEntity;
                    }
                })
        );
    }

}
