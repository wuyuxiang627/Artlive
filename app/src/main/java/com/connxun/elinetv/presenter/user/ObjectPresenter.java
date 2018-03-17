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
import com.connxun.elinetv.entity.ObjectEntity;
import com.connxun.elinetv.entity.RegisterEntity;
import com.connxun.elinetv.entity.UserVideoEntity;
import com.connxun.elinetv.presenter.Presenter;
import com.connxun.elinetv.view.View;
import com.connxun.elinetv.view.user.ITestView;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 上传文件获取token
 */
public class ObjectPresenter implements Presenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private ITestView testView;
    private RegisterEntity mregisterEntity;
    private UserVideoEntity userVideoEntity;
    private String TAG = "ObjectPresenter";


    public ObjectPresenter(Context context){
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

    //获取图片上传的权限
    public void getObjcetToken(String objectname){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("objectname",objectname);
        hashMap.put("time", BaseApplication.getTimeDate()+"");
        BaseApplication.setSign(hashMap);
        mCompositeSubscription.add(manager.getObjectToken(objectname)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ObjectEntity>(BaseActivity.context) {
                    public ObjectEntity s;

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        LogUtil.Log(TAG,LogUtil.LOG_E,s.toString());
                        if(s != null) LogUtil.Log(TAG,LogUtil.LOG_E,"请求回来的参数： "+ s.toString());
                        if(s != null){
                            if(!s.getCode().equals("200")){
                                testView.onError(s.getMsg());
                                return;
                            }
                            testView.onSuccess(s);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(mregisterEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"失败了请求回来的参数： "+ mregisterEntity.toString());
                        testView.onError("请求失败！！");

                    }

                    @Override
                    public void onNext(ObjectEntity s) {
                        this.s = s;
                    }
                })
        );

    }

    //获取文件上传的权限
    public void getUserVideoToken(){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("time", BaseApplication.getTimeDate()+"");
        BaseApplication.setSign(hashMap);
        mCompositeSubscription.add(manager.getUserVideoToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<UserVideoEntity>(BaseActivity.context) {

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        LogUtil.Log(TAG,LogUtil.LOG_E,userVideoEntity.toString());
                        if(userVideoEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"请求回来的参数： "+ userVideoEntity.toString());
                        if(userVideoEntity != null){
                            if(!userVideoEntity.getCode().equals("200")){
                                testView.onError(userVideoEntity.getMsg());
                                return;
                            }
                            testView.onSuccess(userVideoEntity);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(mregisterEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"失败了请求回来的参数： "+ mregisterEntity.toString());
                        testView.onError("请求失败！！");

                    }

                    @Override
                    public void onNext(UserVideoEntity videoEntity) {
                        userVideoEntity = videoEntity;
                    }
                })
        );

    }

}
