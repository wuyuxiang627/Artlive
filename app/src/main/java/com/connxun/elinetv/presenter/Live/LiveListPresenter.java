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
import com.connxun.elinetv.entity.JsonEntity;
import com.connxun.elinetv.entity.OpenLive;
import com.connxun.elinetv.entity.WatchLive;
import com.connxun.elinetv.presenter.Presenter;
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
public class LiveListPresenter implements Presenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private ITestView testView;
    private OpenLive openLive;
    private JsonEntity jsonEntity;
    private WatchLive watchLive;
    private String TAG = "LivePresenter";


    public LiveListPresenter(Context context) {
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

    //推荐主播
    public void getliveList(String length,String page) throws UnsupportedEncodingException {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("length", length);
        hashMap.put("page", page);
        hashMap.put("time", BaseApplication.getTimeDate() + "");

        BaseApplication.setSign(hashMap);


        mCompositeSubscription.add(manager.getliveList(page,length)
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
