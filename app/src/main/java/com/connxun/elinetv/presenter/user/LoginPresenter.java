package com.connxun.elinetv.presenter.user;

/**
 * Created by connxun-16 on 2017/12/22.
 */

import android.content.Context;
import android.content.Intent;

import com.connxun.elinetv.base.network.BaseSubscriber;
import com.connxun.elinetv.base.network.DataManager;
import com.connxun.elinetv.base.ui.BaseActivity;
import com.connxun.elinetv.base.util.LogUtil;
import com.connxun.elinetv.entity.JsonEntity;
import com.connxun.elinetv.entity.RegisterEntity;
import com.connxun.elinetv.presenter.Presenter;
import com.connxun.elinetv.view.View;
import com.connxun.elinetv.view.user.ITestView;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 用户登录
 */
public class LoginPresenter implements Presenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private ITestView testView;
    private RegisterEntity mregisterEntity;
    private JsonEntity jsonEntity;
    private String TAG = "LoginPresenter";


    public LoginPresenter(Context context){
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

    //验证码登录
    public void getUserLogin(String phone,String captch){
        mCompositeSubscription.add(manager.getUserLogin(phone,captch)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<RegisterEntity>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if(mregisterEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"失败了请求回来的参数： "+ mregisterEntity.toString());
                        if(mregisterEntity != null){
                            if(!mregisterEntity.getCode().equals("200")){
                                testView.onError(mregisterEntity.getMsg());
                                return;
                            }
                            testView.onSuccess(mregisterEntity);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(mregisterEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"失败了请求回来的参数： "+ mregisterEntity.toString());
                        testView.onError("请求失败！！");

                    }

                    @Override
                    public void onNext(RegisterEntity registerEntity) {
                        mregisterEntity = registerEntity;
                    }

                })
        );

    }

    //用户是否注册
    public void getpdatePwdcaptch(String phone){
        mCompositeSubscription.add(manager.getpdatePwdcaptch(phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<JsonEntity>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if(jsonEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"失败了请求回来的参数： "+ jsonEntity.toString());
                        if(jsonEntity != null){
                            if(!jsonEntity.getCode().equals("200")){
                                testView.onError(jsonEntity.getMsg());
                                return;
                            }
                            testView.onSuccess(jsonEntity);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(jsonEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"失败了请求回来的参数： "+ jsonEntity.toString());
                        testView.onError("请求失败！！");

                    }

                    @Override
                    public void onNext(JsonEntity registerEntity) {
                        jsonEntity = registerEntity;
                    }

                })
        );

    }


    //登录
    public void getUserLoginPwd(String phone,String pwd){
        mCompositeSubscription.add(manager.getUserLoginPassword(phone,pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<RegisterEntity>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if(mregisterEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"失败了请求回来的参数： "+ mregisterEntity.toString());
                        if(mregisterEntity != null){
                            if(!mregisterEntity.getCode().equals("200")){
                                testView.onError(mregisterEntity.getMsg());
                                return;
                            }
                            testView.onSuccess(mregisterEntity);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(mregisterEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"失败了请求回来的参数： "+ mregisterEntity.toString());
                        testView.onError("请求失败！！");

                    }

                    @Override
                    public void onNext(RegisterEntity registerEntity) {
                        mregisterEntity = registerEntity;
                    }

                })
        );

    }

    /**
     * 第三方登录
     * @param registerType
     * @param uid
     */
    public void getUserThreeLogin(String registerType,String uid){
        mCompositeSubscription.add(manager.getUserThreeLogin(registerType,uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<RegisterEntity>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if(mregisterEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"失败了请求回来的参数： "+ mregisterEntity.toString());
                        if(mregisterEntity != null){
                            if(!mregisterEntity.getCode().equals("200")){
                                testView.onError(mregisterEntity.getMsg());
                                return;
                            }
                            testView.onSuccess(mregisterEntity);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(mregisterEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"失败了请求回来的参数： "+ mregisterEntity.toString());
                        testView.onError("请求失败！！");

                    }

                    @Override
                    public void onNext(RegisterEntity registerEntity) {
                        mregisterEntity = registerEntity;
                    }

                })
        );

    }


    /**
     * 第三方完善资料
     * @param registerType
     * @param uid
     * @param nickName
     * @param avatar
     * @param city
     * @param birthday
     * @param sex
     */
    public void getUserRegThree(String registerType,String uid,String nickName,String avatar,String city,String birthday,String sex){
        mCompositeSubscription.add(manager.getUserRegThree(uid,registerType,nickName,avatar,city,birthday,sex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<RegisterEntity>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if(mregisterEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"失败了请求回来的参数： "+ mregisterEntity.toString());
                        if(mregisterEntity != null){
                            if(!mregisterEntity.getCode().equals("200")){
                                testView.onError(mregisterEntity.getMsg());
                                return;
                            }
                            testView.onSuccess(mregisterEntity);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(mregisterEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"失败了请求回来的参数： "+ mregisterEntity.toString());
                        testView.onError("请求失败！！");

                    }

                    @Override
                    public void onNext(RegisterEntity registerEntity) {
                        mregisterEntity = registerEntity;
                    }

                })
        );

    }





}
