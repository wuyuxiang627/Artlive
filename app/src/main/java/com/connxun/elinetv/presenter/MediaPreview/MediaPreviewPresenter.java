package com.connxun.elinetv.presenter.MediaPreview;

/**
 * Created by connxun-16 on 2017/12/22.
 */

import android.content.Context;
import android.content.Intent;

import com.connxun.elinetv.base.network.DataManager;
import com.connxun.elinetv.entity.JsonEntity;
import com.connxun.elinetv.entity.OpenLive;
import com.connxun.elinetv.presenter.Presenter;
import com.connxun.elinetv.view.View;
import com.connxun.elinetv.view.user.ITestView;

import rx.subscriptions.CompositeSubscription;

/**
 * 用户观看直播
 */
public class MediaPreviewPresenter implements Presenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private ITestView testView;
    private OpenLive openLive;
    private JsonEntity jsonEntity;
    private String TAG = "LivePresenter";


    public MediaPreviewPresenter(Context context) {
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

//    //进入直播
//    public void getLiveOpenLive(String name, String fileName, String position, String menuNo) throws UnsupportedEncodingException {
//
//        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put("name", name);
//        hashMap.put("cover", fileName);
//        hashMap.put("position", position);
//        hashMap.put("menuNo", menuNo);
//        hashMap.put("time", BaseApplication.getTimeDate() + "");
//
//        BaseApplication.setSign(hashMap);
//
//        mCompositeSubscription.add(manager.getLiveOpenLive(name, fileName, position, menuNo)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseSubscriber<OpenLive>(BaseActivity.context) {
//                    @Override
//                    public void onCompleted() {
//                        super.onCompleted();
//                        if (openLive != null)
//                            LogUtil.Log(TAG, LogUtil.LOG_E, "成功请求回来的参数： " + openLive.toString());
//                        if (openLive.getData() != null) {
//                            testView.onSuccess(openLive);
//                        } else {
//                            ToastUtils.showLong(openLive.getMsg());
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        if (openLive != null) LogUtil.Log(TAG, LogUtil.LOG_E, "失败了请求回来的参数： " + openLive.toString());
//                        testView.onError("请求失败！！");
//
//                    }
//
//                    @Override
//                    public void onNext(OpenLive registerEntity) {
//                        openLive = registerEntity;
//                    }
//
//                })
//        );
//
//    }
//
//
//    //开始直播
//    public void getLiveStartLive(String liveNo) throws UnsupportedEncodingException {
//
//        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put("liveNo", liveNo);
//        hashMap.put("time", BaseApplication.getTimeDate() + "");
//
//        BaseApplication.setSign(hashMap);
//
//        mCompositeSubscription.add(manager.getLiveStartLive(liveNo)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new BaseSubscriber<JsonEntity>(BaseActivity.context) {
//                            @Override
//                            public void onCompleted() {
//                                super.onCompleted();
//                                if (jsonEntity != null) LogUtil.Log(TAG, LogUtil.LOG_E, "getLiveStartLive成功请求回来的参数： " + openLive.toString());
//                                testView.onSuccess(jsonEntity);
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                if (jsonEntity != null)
//                                    LogUtil.Log(TAG, LogUtil.LOG_E, "getLiveStartLive失败了请求回来的参数： " + openLive.toString());
//                            }
//
//                            @Override
//                            public void onNext(JsonEntity registerEntity) {
//                                jsonEntity = registerEntity;
//                            }
//
//                        })
//        );
//
//    }
//    //停止直播
//    public void getLiveEndLive(String liveNo) throws UnsupportedEncodingException {
//
//        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put("liveNo", liveNo);
//        hashMap.put("time", BaseApplication.getTimeDate() + "");
//
//        BaseApplication.setSign(hashMap);
//
//        mCompositeSubscription.add(manager.getLiveEndLive(liveNo)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseSubscriber<JsonEntity>(BaseActivity.context) {
//                    @Override
//                    public void onCompleted() {
//                        super.onCompleted();
//                        if (jsonEntity != null) LogUtil.Log(TAG, LogUtil.LOG_E, "getLiveStartLive成功请求回来的参数： " + openLive.toString());
//                        testView.onSuccess(jsonEntity);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        if (jsonEntity != null)
//                            LogUtil.Log(TAG, LogUtil.LOG_E, "getLiveStartLive失败了请求回来的参数： " + openLive.toString());
//                    }
//
//                    @Override
//                    public void onNext(JsonEntity registerEntity) {
//                        jsonEntity = registerEntity;
//                    }
//
//                })
//        );
//
//    }

}
