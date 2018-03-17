package com.connxun.elinetv.presenter.Order;

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
import com.connxun.elinetv.entity.order.Order;
import com.connxun.elinetv.entity.order.UserVC;
import com.connxun.elinetv.entity.order.VC;
import com.connxun.elinetv.entity.order.WeChatEntity;
import com.connxun.elinetv.entity.order.WithdrawalsList;
import com.connxun.elinetv.presenter.Presenter;
import com.connxun.elinetv.util.ToastUtils;
import com.connxun.elinetv.view.View;
import com.connxun.elinetv.view.user.ITestView;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 *订单模块
 */
public class OrderPresenter implements Presenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private ITestView testView;
    private Entity<VC> vcEntity; //金币列表
    private Entity<WithdrawalsList> withdrawalsListEntity; //提现记录
    private EntityObject<UserVC> userVCEntity; //用户金币
    private EntityObject<Order> orderEntityObject; // 生成订单信息
    private JsonEntity jsonEntity;
    private EntityObject<WeChatEntity> weChatEntityEntityObject;
    private String TAG = "OrderPresenter";


    public OrderPresenter(Context context){
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

    //金币列表
    public void getVcList(){

        mCompositeSubscription.add(manager.getVcList("1","20")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Entity<VC>>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        LogUtil.Log(TAG,LogUtil.LOG_E,vcEntity.toString());
                        if(vcEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"getVcList请求回来的参数： "+ vcEntity.toString());
                        if(vcEntity.getData()!= null){
                            testView.onSuccess(vcEntity);
                        }else{
                            ToastUtils.showLong(vcEntity.getMsg());
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(vcEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"getVcList失败了请求回来的参数： "+ vcEntity.toString());
                        testView.onError("请求失败！！");
                    }

                    @Override
                    public void onNext(Entity<VC> nextEntity) {
                        vcEntity = nextEntity;
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

    //添加订单
    public void getOrderAdd(String type, String isIntegral, String isVouchers, String extendNo){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("type", type);
        hashMap.put("isIntegral", isIntegral);
        hashMap.put("isVouchers", isVouchers);
        hashMap.put("extendNo", extendNo);
        hashMap.put("time", BaseApplication.getTimeDate() + "");

        BaseApplication.setSign(hashMap);
        mCompositeSubscription.add(manager.getOrderAdd(type,  isIntegral,  isVouchers,  extendNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<EntityObject<Order>>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        LogUtil.Log(TAG,LogUtil.LOG_E,orderEntityObject.toString());
                        if(orderEntityObject != null) LogUtil.Log(TAG,LogUtil.LOG_E,"getOrderAdd请求回来的参数： "+ orderEntityObject.toString());
                        if(orderEntityObject.getData()!= null && orderEntityObject.getCode().equals("200")){
                            testView.onSuccess(orderEntityObject);
//                            ToastUtils.showLong(jsonEntity.getMsg());
                        }else{
                            ToastUtils.showLong(orderEntityObject.getMsg());
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        if(orderEntityObject != null) LogUtil.Log(TAG,LogUtil.LOG_E,"getOrderAdd 失败了请求回来的参数： "+ orderEntityObject.toString());
                        testView.onError("请求失败！！");
                    }

                    @Override
                    public void onNext(EntityObject<Order> nextEntity) {
                        orderEntityObject = nextEntity;
                    }

                })
        );

    }

    //生成支付订单
    public void getOrderCreateAliyum(String orderNo){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("orderNo", orderNo);
        hashMap.put("time", BaseApplication.getTimeDate() + "");

        BaseApplication.setSign(hashMap);
        mCompositeSubscription.add(manager.getOrderCreateAliyum(orderNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<JsonEntity>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        LogUtil.Log(TAG,LogUtil.LOG_E,jsonEntity.toString());
                        if(jsonEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"getOrderCreateAliyum请求回来的参数： "+ jsonEntity.toString());
                        if(jsonEntity.getData()!= null && jsonEntity.getCode().equals("200")){
                            testView.onSuccess(jsonEntity);
//                            ToastUtils.showLong(jsonEntity.getMsg());
                        }else{
                            ToastUtils.showLong(jsonEntity.getMsg());
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        if(jsonEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"getOrderCreateAliyum 失败了请求回来的参数： "+ jsonEntity.toString());
                        testView.onError("请求失败！！");
                    }

                    @Override
                    public void onNext(JsonEntity nextEntity) {
                        jsonEntity = nextEntity;
                    }

                })
        );

    }

    //生成支付订单
    public void getWxPayDoUnifiedOrder(String orderNo){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("orderNo", orderNo);
        hashMap.put("type", "0");
        hashMap.put("time", BaseApplication.getTimeDate() + "");

        BaseApplication.setSign(hashMap);
        mCompositeSubscription.add(manager.getWxPayDoUnifiedOrder(orderNo)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseSubscriber<EntityObject<WeChatEntity>>(BaseActivity.context) {
                            @Override
                            public void onCompleted() {
                                super.onCompleted();
                                LogUtil.Log(TAG,LogUtil.LOG_E,weChatEntityEntityObject.toString());
                                if(weChatEntityEntityObject != null) LogUtil.Log(TAG,LogUtil.LOG_E,"getWxPayDoUnifiedOrder请求回来的参数： "+ weChatEntityEntityObject.toString());
                                if(weChatEntityEntityObject.getData()!= null && weChatEntityEntityObject.getCode().equals("200")){
                                    testView.onSuccess(weChatEntityEntityObject);
//                            ToastUtils.showLong(weChatEntityEntityObject.toString());
                                }else{
                                    ToastUtils.showLong(weChatEntityEntityObject.getMsg());
                                }
                            }
                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                if(weChatEntityEntityObject != null) LogUtil.Log(TAG,LogUtil.LOG_E,"getWxPayDoUnifiedOrder 失败了请求回来的参数： "+ weChatEntityEntityObject.toString());
                                testView.onError("请求失败！！");
                            }

                            @Override
                            public void onNext(EntityObject<WeChatEntity> nextEntity) {
                                weChatEntityEntityObject = nextEntity;
                            }

                        })
        );

    }

    //提现
    public void getUserWithdrawals(String cashCoin,String alipay,String name,String phone){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("cashCoin", cashCoin);
        hashMap.put("alipay", alipay);
        hashMap.put("name", name);
        hashMap.put("phone", phone);
        hashMap.put("time", BaseApplication.getTimeDate() + "");

        BaseApplication.setSign(hashMap);
        mCompositeSubscription.add(manager.getUserWithdrawals(cashCoin, alipay, name, phone)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseSubscriber<JsonEntity>(BaseActivity.context) {
                            @Override
                            public void onCompleted() {
                                super.onCompleted();
                                LogUtil.Log(TAG,LogUtil.LOG_E,jsonEntity.toString());
                                if(jsonEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"getUserWithdrawals 请求回来的参数： "+ jsonEntity.toString());
                                if(jsonEntity.getData()!= null && jsonEntity.getCode().equals("200")){
                                    testView.onSuccess(jsonEntity);
//                            ToastUtils.showLong(weChatEntityEntityObject.toString());
                                }else{
                                    ToastUtils.showLong(jsonEntity.getMsg());
                                }
                            }
                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                if(jsonEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"getUserWithdrawals 失败了请求回来的参数： "+ jsonEntity.toString());
                                testView.onError("请求失败！！");
                            }

                            @Override
                            public void onNext(JsonEntity nextEntity) {
                                jsonEntity = nextEntity;
                            }

                        })
        );

    }


    //提现
    public void getWithdrawalsList(String page,String length){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("page", page);
        hashMap.put("length", length);
        hashMap.put("time", BaseApplication.getTimeDate() + "");

        BaseApplication.setSign(hashMap);
        mCompositeSubscription.add(manager.getWithdrawalsList(page, length)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseSubscriber<Entity<WithdrawalsList>>(BaseActivity.context) {
                            @Override
                            public void onCompleted() {
                                super.onCompleted();
                                LogUtil.Log(TAG,LogUtil.LOG_E,withdrawalsListEntity.toString());
                                if(withdrawalsListEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"getUserWithdrawals 请求回来的参数： "+ withdrawalsListEntity.toString());
                                if(withdrawalsListEntity.getData()!= null && withdrawalsListEntity.getCode().equals("200")){
                                    testView.onSuccess(withdrawalsListEntity);
//                            ToastUtils.showLong(weChatEntityEntityObject.toString());
                                }else{
                                    ToastUtils.showLong(withdrawalsListEntity.getMsg());
                                }
                            }
                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                if(withdrawalsListEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"getUserWithdrawals 失败了请求回来的参数： "+ withdrawalsListEntity.toString());
                                testView.onError("请求失败！！");
                            }

                            @Override
                            public void onNext(Entity<WithdrawalsList> nextEntity) {
                                withdrawalsListEntity = nextEntity;
                            }

                        })
        );

    }



}
