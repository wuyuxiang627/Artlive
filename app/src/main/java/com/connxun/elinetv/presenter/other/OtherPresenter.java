package com.connxun.elinetv.presenter.other;

/**
 * Created by connxun-16 on 2017/12/22.
 */

import android.content.Context;
import android.content.Intent;

import com.connxun.elinetv.base.network.BaseSubscriber;
import com.connxun.elinetv.base.network.DataManager;
import com.connxun.elinetv.base.ui.BaseActivity;
import com.connxun.elinetv.base.util.LogUtil;
import com.connxun.elinetv.entity.AD;
import com.connxun.elinetv.presenter.Presenter;
import com.connxun.elinetv.util.ToastUtils;
import com.connxun.elinetv.view.View;
import com.connxun.elinetv.view.user.ITestView;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 其他模块
 */
public class OtherPresenter implements Presenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private ITestView testView;
    private AD adEntity;
    private String TAG = "OtherPresenter";


    public OtherPresenter(Context context){
        mContext = context;
    }


    @Override
    public void onCreate() {
        manager = new DataManager(mContext);
        mCompositeSubscription = new CompositeSubscription();


    }


    @Override
    public void onStop() {
        if(mCompositeSubscription.hasSubscriptions()){
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

    //人气主播
    public void getAdList(){

        mCompositeSubscription.add(manager.getAdList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<AD>(BaseActivity.context) {

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        LogUtil.Log(TAG,LogUtil.LOG_E,adEntity.toString());
                        if(adEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"请求回来的参数： "+ adEntity.toString());
                        if(adEntity.getData()!= null){
                            testView.onSuccess(adEntity);
                        }else{
                            ToastUtils.showLong(adEntity.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(adEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"失败了请求回来的参数： "+ adEntity.toString());
                        testView.onError("请求失败！！");

                    }

                    @Override
                    public void onNext(AD s) {
                        adEntity = s;
                    }



                })
        );

    }





}
