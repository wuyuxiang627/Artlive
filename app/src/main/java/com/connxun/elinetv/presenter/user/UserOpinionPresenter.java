package com.connxun.elinetv.presenter.user;

/**
 * Created by connxun-16 on 2017/12/22.
 */

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.connxun.elinetv.app.BaseApplication;
import com.connxun.elinetv.base.network.BaseSubscriber;
import com.connxun.elinetv.base.network.DataManager;
import com.connxun.elinetv.base.ui.BaseActivity;
import com.connxun.elinetv.base.util.LogUtil;
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
 * 用户登录
 */
public class UserOpinionPresenter implements Presenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private ITestView testView;
    private JsonEntity mjsonEntity;
    private String TAG = "UserOpinionPresenter";


    public UserOpinionPresenter(Context context){
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
    //提交意见反馈
    public void submitOpinion(String title,String pic,String phone){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("title", title);
        hashMap.put("pic", pic);
        hashMap.put("phone",phone);
        hashMap.put("time", BaseApplication.getTimeDate() + "");
        BaseApplication.setSign(hashMap);

        mCompositeSubscription.add(manager.submitOpinion(title,pic,phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<JsonEntity>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();

                        if(mjsonEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"成功了请求回来的参数： "+ mjsonEntity.toString());
                        if(mjsonEntity != null){
                            if(!mjsonEntity.getCode().equals("200")){
                                testView.onError(mjsonEntity.getMsg());
                                return;
                            }
                            testView.onSuccess(mjsonEntity);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(mjsonEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"失败了请求回来的参数： "+ mjsonEntity.toString());
                        testView.onError("请求失败！！");
                    }

                    @Override
                    public void onNext(JsonEntity jsonEntity) {
                        mjsonEntity = jsonEntity;
                    }

                })
        );

    }
}
