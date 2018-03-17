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
 * 用户注册
 */
public class RegisterPresenter implements Presenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private ITestView testView;
    private RegisterEntity mregisterEntity;
    private String TAG = "RegisterPresenter";
    private JsonEntity jsonEntity;

    public  RegisterPresenter(Context context){
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


    public void getUserRegister(String phone,String captch,String password){
        mCompositeSubscription.add(manager.getUserRegister(phone,captch,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<RegisterEntity>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if(mregisterEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"成功了请求回来的参数： "+ mregisterEntity.toString());
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
                        LogUtil.Log(TAG,LogUtil.LOG_E,"失败了请求回来的参数： "+ e.toString());
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
     * 修改密码
     * @param phone
     * @param captch
     * @param password
     */
    public void getUserUpdatePWD(String phone,String captch,String password){
        mCompositeSubscription.add(manager.getUserUpdatePWD(phone,captch,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<JsonEntity>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if(jsonEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"成功了请求回来的参数： "+ jsonEntity.toString());
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
                        LogUtil.Log(TAG,LogUtil.LOG_E,"失败了请求回来的参数： "+ e.toString());
                        testView.onError("请求失败！！");

                    }

                    @Override
                    public void onNext(JsonEntity registerEntity) {
                        jsonEntity = registerEntity;
                    }
                })
        );

    }






}
