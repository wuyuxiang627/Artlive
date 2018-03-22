package com.connxun.elinetv.presenter.Live;

/**
 * Created by connxun-16 on 2017/12/22.
 */

import android.content.Context;
import android.content.Intent;

import com.connxun.elinetv.app.BaseApplication;
import com.connxun.elinetv.base.network.BaseSubscriber;
import com.connxun.elinetv.base.network.DataManager;
import com.connxun.elinetv.base.ui.BaseActivity;
import com.connxun.elinetv.base.util.LogUtil;
import com.connxun.elinetv.entity.EntityObject;
import com.connxun.elinetv.entity.JsonEntity;
import com.connxun.elinetv.entity.OpenLive;
import com.connxun.elinetv.entity.WatchLive;
import com.connxun.elinetv.entity.live.EndLiveEntitiy;
import com.connxun.elinetv.presenter.Presenter;
import com.connxun.elinetv.util.ToastUtils;
import com.connxun.elinetv.view.View;
import com.connxun.elinetv.view.user.ITestView;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 用户直播
 */
public class LivePresenter implements Presenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private ITestView testView;
    private OpenLive openLive;
    private JsonEntity jsonEntity;
    private EntityObject<EndLiveEntitiy> entitiyEntityObject;
    private WatchLive watchLive;
    private String TAG = "LivePresenter";


    public LivePresenter(Context context) {
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
        testView = (ITestView) view;
    }

    @Override
    public void attachIncomingIntent(Intent intent) {

    }

    //创建直播
    public void getLiveOpenLive( String position, String menuNo) throws UnsupportedEncodingException {

        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("position", position);
        hashMap.put("menuNo", menuNo);
        hashMap.put("time", BaseApplication.getTimeDate() + "");

        BaseApplication.setSign(hashMap);

        mCompositeSubscription.add(manager.getLiveOpenLive( position, menuNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<OpenLive>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if (openLive != null)
                            LogUtil.Log(TAG, LogUtil.LOG_E, "getLiveOpenLive成功请求回来的参数： " + openLive);
                        if (openLive.getData() != null) {
                            testView.onSuccess(openLive);
                        } else {
                            ToastUtils.showLong(openLive.getMsg());
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (openLive != null) LogUtil.Log(TAG, LogUtil.LOG_E, "getLiveOpenLive失败了请求回来的参数： " + openLive.toString());
                        testView.onError("请求失败！！");
                    }
                    @Override
                    public void onNext(OpenLive registerEntity) {
                        openLive = registerEntity;
                    }
                })
        );

    }


    //开始直播
    public void getLiveStartLive(String name, String fileName,String liveNo) throws UnsupportedEncodingException {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name", name);
        hashMap.put("cover", fileName);
        hashMap.put("liveNo", liveNo);
        hashMap.put("time", BaseApplication.getTimeDate() + "");

        BaseApplication.setSign(hashMap);

        mCompositeSubscription.add(manager.getLiveStartLive(name, fileName,liveNo)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseSubscriber<JsonEntity>(BaseActivity.context) {
                            @Override
                            public void onCompleted() {
                                super.onCompleted();
                                if (jsonEntity != null) LogUtil.Log(TAG, LogUtil.LOG_E, "getLiveStartLive成功请求回来的参数： " + jsonEntity.toString());
                                if(jsonEntity.getCode().equals("200")){
                                    testView.onSuccess(jsonEntity);
                                }else {
                                    ToastUtils.showLong(jsonEntity.getMsg());
                                }

                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                if (jsonEntity != null)
                                    LogUtil.Log(TAG, LogUtil.LOG_E, "getLiveStartLive失败了请求回来的参数： " + jsonEntity.toString());
                                ToastUtils.showLong(e.toString());
                            }

                            @Override
                            public void onNext(JsonEntity registerEntity) {
                                jsonEntity = registerEntity;
                            }

                        })
        );

    }

    //停止直播
    public void getLiveEndLive(String liveNo) throws UnsupportedEncodingException {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("liveNo", liveNo);
        hashMap.put("time", BaseApplication.getTimeDate() + "");

        BaseApplication.setSign(hashMap);

        mCompositeSubscription.add(manager.getLiveEndLive(liveNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<EntityObject<EndLiveEntitiy>>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if (entitiyEntityObject != null) LogUtil.Log(TAG, LogUtil.LOG_E, "getLiveEndLive 成功请求回来的参数： " + entitiyEntityObject.toString());
                        testView.onSuccess(entitiyEntityObject);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (entitiyEntityObject != null)
                            LogUtil.Log(TAG, LogUtil.LOG_E, "getLiveEndLive 失败了请求回来的参数： " + entitiyEntityObject.toString());
                    }

                    @Override
                    public void onNext(EntityObject<EndLiveEntitiy> registerEntity) {
                        entitiyEntityObject = registerEntity;
                    }

                })
        );

    }

    //直播心跳
    public void getHeartBeat(String liveNo) throws UnsupportedEncodingException {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("liveNo", liveNo);
        hashMap.put("time", BaseApplication.getTimeDate() + "");

        BaseApplication.setSign(hashMap);

        mCompositeSubscription.add(manager.getHeartBeat(liveNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<JsonEntity>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if (jsonEntity != null) LogUtil.Log(TAG, LogUtil.LOG_E, "getHeartBeat成功请求回来的参数： " + jsonEntity.toString());
                        testView.onSuccess(jsonEntity);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (jsonEntity != null)
                            LogUtil.Log(TAG, LogUtil.LOG_E, "getHeartBeat失败了请求回来的参数： " + jsonEntity.toString());
                    }

                    @Override
                    public void onNext(JsonEntity registerEntity) {
                        jsonEntity = registerEntity;
                    }

                })
        );

    }

    //人气主播
    public void getliveRanKingList(String length,String page) throws UnsupportedEncodingException {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("length", length);
        hashMap.put("page", page);
        hashMap.put("time", BaseApplication.getTimeDate() + "");

        BaseApplication.setSign(hashMap);

        mCompositeSubscription.add(manager.getliveRanKingList(page,length)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<WatchLive>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if (watchLive != null) LogUtil.Log(TAG, LogUtil.LOG_E, "getliveRanKingList成功请求回来的参数： " + watchLive.toString());
                        testView.onSuccess(watchLive);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                            LogUtil.Log(TAG, LogUtil.LOG_E, "getliveRanKingList失败了请求回来的参数： " + e.toString());
                    }

                    @Override
                    public void onNext(WatchLive jsonentitiy) {
                        watchLive = jsonentitiy;
                    }

                })
        );

    }

    //人气主播
    public void getliveHotLiveList(String length,String page) throws UnsupportedEncodingException {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("length", length);
        hashMap.put("page", page);
        hashMap.put("time", BaseApplication.getTimeDate() + "");

        BaseApplication.setSign(hashMap);

        mCompositeSubscription.add(manager.getliveHotLiveList(page,length)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<WatchLive>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if (watchLive != null) LogUtil.Log(TAG, LogUtil.LOG_E, "getliveHotLiveList 成功请求回来的参数： " + watchLive.toString());
                        testView.onSuccess(watchLive);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        LogUtil.Log(TAG, LogUtil.LOG_E, "getliveHotLiveList 失败了请求回来的参数： " + e.toString());
                    }

                    @Override
                    public void onNext(WatchLive jsonentitiy) {
                        watchLive = jsonentitiy;
                    }

                })
        );

    }




}
