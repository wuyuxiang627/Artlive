package com.connxun.elinetv.presenter.Gift;

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
import com.connxun.elinetv.entity.Entity;
import com.connxun.elinetv.entity.EntityObject;
import com.connxun.elinetv.entity.Gift;
import com.connxun.elinetv.entity.JsonEntity;
import com.connxun.elinetv.entity.order.UserVC;
import com.connxun.elinetv.presenter.Presenter;
import com.connxun.elinetv.util.ToastUtils;
import com.connxun.elinetv.view.View;
import com.connxun.elinetv.view.user.ITestView;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 礼物模块
 */
public class GiftPresenter implements Presenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private ITestView testView;
    private Entity<Gift> giftEntity; //礼物列表
    private EntityObject<UserVC> userVCEntity; //礼物列表
    private JsonEntity jsonEntity;
    private String TAG = "GiftPresenter";


    public GiftPresenter(Context context){
        mContext = context;
    }

    @Override
    public void onCreate() {
        BaseApplication.isDialogShow = false;
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

    //礼物列表
    public void getGiftList(){

        mCompositeSubscription.add(manager.getGiftList("1","20")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Entity<Gift>>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        LogUtil.Log(TAG,LogUtil.LOG_E,giftEntity.toString());
                        if(giftEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"getAdList请求回来的参数： "+ giftEntity.toString());
                        if(giftEntity.getData()!= null){
                            testView.onSuccess(giftEntity);
                        }else{
                            ToastUtils.showLong(giftEntity.getMsg());
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        if(giftEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"getAdList 失败了请求回来的参数： "+ giftEntity.toString());
                        testView.onError("请求失败！！");
                    }

                    @Override
                    public void onNext(Entity<Gift> nextEntity) {
                        giftEntity = nextEntity;
                    }

                })
        );

    }

    //用户金币
    public void getUserUserVc(){

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("time", BaseApplication.getTimeDate() + "");
        BaseApplication.setSign(hashMap);


        mCompositeSubscription.add(manager.getUserVc()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<EntityObject<UserVC>>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        LogUtil.Log(TAG,LogUtil.LOG_E,userVCEntity.toString());
                        if(userVCEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"getUserUserVc： "+ userVCEntity.toString());
                        if(userVCEntity.getData()!= null){
                            UserVC userVC = userVCEntity.getData();
                            testView.onSuccess(userVCEntity);
                        }else{
                            ToastUtils.showLong(userVCEntity.getMsg());
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        if(userVCEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"getUserUserVc 失败了请求回来的参数： "+ userVCEntity.toString());
                        testView.onError("请求失败！！");
                    }

                    @Override
                    public void onNext(EntityObject<UserVC> nextEntity) {
                        userVCEntity = nextEntity;
                    }

                })
        );

    }

    //赠送礼物
    public void getGiveGift(String giftNo, String type, String externalNo, String giftNum, String toUserNo){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("giftNo", giftNo);
        hashMap.put("type", type);
        hashMap.put("externalNo", externalNo);
        hashMap.put("giftNum", giftNum);
        hashMap.put("toUserNo", toUserNo);
        hashMap.put("time", BaseApplication.getTimeDate() + "");

        BaseApplication.setSign(hashMap);
        mCompositeSubscription.add(manager.getGiveGift( giftNo,  type,  externalNo,  giftNum,  toUserNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<JsonEntity>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        LogUtil.Log(TAG,LogUtil.LOG_E,jsonEntity.toString());
                        if(jsonEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"getAdList请求回来的参数： "+ jsonEntity.toString());
                        if(jsonEntity.getData()!= null && jsonEntity.getCode().equals("200")){
                            testView.onSuccess(jsonEntity);
//                            ToastUtils.showLong(jsonEntity.getMsg());
                        }else{
                            ToastUtils.showLong(jsonEntity.getMsg());
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        if(jsonEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"getAdList 失败了请求回来的参数： "+ giftEntity.toString());
                        testView.onError("请求失败！！");
                    }

                    @Override
                    public void onNext(JsonEntity nextEntity) {
                        jsonEntity = nextEntity;
                    }

                })
        );

    }


}
