package com.connxun.elinetv.presenter.user;

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
import com.connxun.elinetv.entity.AttentionEntity;
import com.connxun.elinetv.entity.EntityObject;
import com.connxun.elinetv.entity.JsonEntity;
import com.connxun.elinetv.entity.RegisterEntity;
import com.connxun.elinetv.presenter.Presenter;
import com.connxun.elinetv.view.View;
import com.connxun.elinetv.view.user.ITestView;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 用户关系
 */
public class AttentionPresenter implements Presenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private ITestView testView;
    private JsonEntity JsonEntity;
    private EntityObject<AttentionEntity> attentionEntityEntityObject;
    private String TAG = "AttentionPresenter";


    public AttentionPresenter(Context context){
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

    //判断用户关系
    public void getAttentionIsUserAttention(String toUserNo,String type){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("toUserNo", toUserNo);
        hashMap.put("type", type);
        hashMap.put("time", BaseApplication.getTimeDate() + "");

        BaseApplication.setSign(hashMap);

        mCompositeSubscription.add(manager.getAttentionIsUserAttention(toUserNo,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<EntityObject<AttentionEntity>>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if(attentionEntityEntityObject != null) LogUtil.Log(TAG,LogUtil.LOG_E,"失败了请求回来的参数： "+ attentionEntityEntityObject.toString());
                        if(attentionEntityEntityObject != null){
                            if(!attentionEntityEntityObject.getCode().equals("200")){
                                testView.onError(attentionEntityEntityObject.getMsg());
                                return;
                            }
                            testView.onSuccess(attentionEntityEntityObject);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(attentionEntityEntityObject != null) LogUtil.Log(TAG,LogUtil.LOG_E,"失败了请求回来的参数： "+ attentionEntityEntityObject.toString());
                        testView.onError("请求失败！！");

                    }

                    @Override
                    public void onNext(EntityObject<AttentionEntity> registerEntity) {
                        attentionEntityEntityObject = registerEntity;
                    }

                })
        );

    }

    //添加用户关系
    public void getAttentionAddAttention(String toUserNo,String type){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("toUserNo", toUserNo);
        hashMap.put("type", type);
        hashMap.put("time", BaseApplication.getTimeDate() + "");

        BaseApplication.setSign(hashMap);
        mCompositeSubscription.add(manager.getAttentionAddAttention(toUserNo,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<JsonEntity>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if(JsonEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"失败了请求回来的参数： "+ JsonEntity.toString());
                        if(JsonEntity != null){
                            if(!JsonEntity.getCode().equals("200")){
                                testView.onError(JsonEntity.getMsg());
                                return;
                            }
                            testView.onSuccess(JsonEntity);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(JsonEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"失败了请求回来的参数： "+ JsonEntity.toString());
                        testView.onError("请求失败！！");

                    }

                    @Override
                    public void onNext(JsonEntity registerEntity) {
                        JsonEntity = registerEntity;
                    }

                })
        );

    }

    //删除用户关系
    public void getAttentionDelAttention(String toUserNo,String type){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("toUserNo", toUserNo);
        hashMap.put("type", type);
        hashMap.put("time", BaseApplication.getTimeDate() + "");

        BaseApplication.setSign(hashMap);
        mCompositeSubscription.add(manager.getAttentionDelAttention(toUserNo,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<JsonEntity>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if(JsonEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"失败了请求回来的参数： "+ JsonEntity.toString());
                        if(JsonEntity != null){
                            if(!JsonEntity.getCode().equals("200")){
                                testView.onError(JsonEntity.getMsg());
                                return;
                            }
                            testView.onSuccess(JsonEntity);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(JsonEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"失败了请求回来的参数： "+ JsonEntity.toString());
                        testView.onError("请求失败！！");

                    }

                    @Override
                    public void onNext(JsonEntity registerEntity) {
                        JsonEntity = registerEntity;
                    }

                })
        );

    }





}
