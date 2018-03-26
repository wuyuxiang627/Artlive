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
import com.connxun.elinetv.entity.ChallengeTypeEntity;
import com.connxun.elinetv.entity.Entity;
import com.connxun.elinetv.entity.EntityObject;
import com.connxun.elinetv.entity.JsonEntity;
import com.connxun.elinetv.entity.OpenLive;
import com.connxun.elinetv.entity.WatchLive;
import com.connxun.elinetv.entity.live.ChallengeTypeThree;
import com.connxun.elinetv.entity.live.challenge.ChallengeModelEntity;
import com.connxun.elinetv.presenter.Presenter;
import com.connxun.elinetv.util.ToastUtils;
import com.connxun.elinetv.view.View;
import com.connxun.elinetv.view.user.ITestView;
import com.orhanobut.logger.Logger;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 用户挑战
 */
public class ChallengeTypePresenter implements Presenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private ITestView testView;
    private JsonEntity jsonEntity;
    private Entity<ChallengeTypeEntity> ChallengeType;
    private Entity<ChallengeTypeThree> challengeTypeThree;
    private EntityObject<ChallengeModelEntity> challengeModelEntityEntityObject;
    private String TAG = "ChallengeTypePresenter";


    public ChallengeTypePresenter(Context context) {
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

    //挑战一二级菜单列表
    public void getChallengeList(String length,String page)  {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("length", length);
        hashMap.put("page", page);
        hashMap.put("time", BaseApplication.getTimeDate() + "");

        BaseApplication.setSign(hashMap);

        mCompositeSubscription.add(manager.getChallengeList(page,length)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Entity<ChallengeTypeEntity>>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if (ChallengeType != null) LogUtil.Log(TAG, LogUtil.LOG_E, "getChallengeList 成功请求回来的参数： " + ChallengeType.toString());
                        if(ChallengeType.getCode().equals("200")){
                            testView.onSuccess(ChallengeType.getData().getList());
//
                            return;
                        }
                        testView.onError(ChallengeType.getMsg());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.Log(TAG, LogUtil.LOG_E, "getChallengeList 失败了请求回来的参数： " + e.toString());
                        ToastUtils.showLong(e.toString());
                    }

                    @Override
                    public void onNext(Entity<ChallengeTypeEntity> jsonentitiy) {
                        ChallengeType = jsonentitiy;
                    }

                })
        );

    }

    //三级菜单
    public void getChallengeResourceList(String menuNO)  {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("menuNo", menuNO);
        hashMap.put("time", BaseApplication.getTimeDate() + "");

        BaseApplication.setSign(hashMap);

        mCompositeSubscription.add(manager.getChallengeResourceList(menuNO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Entity<ChallengeTypeThree>>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if (challengeTypeThree != null) LogUtil.Log(TAG, LogUtil.LOG_E, "getChallengeList 成功请求回来的参数： " + challengeTypeThree.toString());
                        if(challengeTypeThree.getCode().equals("200")){
                            testView.onSuccess(challengeTypeThree.getData().getList());
//
                            return;
                        }
                        testView.onError(challengeTypeThree.getMsg());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.Log(TAG, LogUtil.LOG_E, "getChallengeList 失败了请求回来的参数： " + e.toString());
                        ToastUtils.showLong(e.toString());
                    }

                    @Override
                    public void onNext(Entity<ChallengeTypeThree> jsonentitiy) {
                        challengeTypeThree = jsonentitiy;
                    }

                })
        );

    }


    //创建挑战
    public void getChallengeCreatChallenge(String menuNo, String challengeResourceNo, String liveNo)  {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("menuNo", menuNo);
        hashMap.put("challengeResourceNo", challengeResourceNo);
        hashMap.put("liveNo", liveNo);
        hashMap.put("time", BaseApplication.getTimeDate() + "");

        BaseApplication.setSign(hashMap);

        mCompositeSubscription.add(manager.getChallengeCreatChallenge(menuNo, challengeResourceNo,liveNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<EntityObject<ChallengeModelEntity>>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if (challengeModelEntityEntityObject != null) LogUtil.Log(TAG, LogUtil.LOG_E, "getChallengeCreatChallenge 成功请求回来的参数： " + challengeModelEntityEntityObject.toString());
                        if(challengeModelEntityEntityObject.getCode().equals("200")){
                            testView.onSuccess(challengeModelEntityEntityObject.getData());
//
                            return;
                        }
                        testView.onError(challengeModelEntityEntityObject.getMsg());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.Log(TAG, LogUtil.LOG_E, "getChallengeCreatChallenge 失败了请求回来的参数： " + e.toString());
                        ToastUtils.showLong(e.toString());
                    }

                    @Override
                    public void onNext(EntityObject<ChallengeModelEntity> jsonentitiy) {
                        challengeModelEntityEntityObject = jsonentitiy;
                    }

                })
        );

    }


    //开始挑战
    public void getChallengeStartChallenge( String challengeNo)  {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("challengeNo", challengeNo);
        hashMap.put("time", BaseApplication.getTimeDate() + "");
        BaseApplication.setSign(hashMap);
        Logger.e("challenge","changeno: "+ challengeNo);
        Logger.e("challenge","time: "+ BaseApplication.getTimeDate() + "");

        mCompositeSubscription.add(manager.getChallengeStartChallenge( challengeNo )
                .subscribeOn(Schedulers.io())

                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<JsonEntity>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if (jsonEntity != null) LogUtil.Log(TAG, LogUtil.LOG_E, "getChallengeCreatChallenge 成功请求回来的参数： " + jsonEntity.toString());
                        if(jsonEntity.getCode().equals("200")){
                            testView.onSuccess(jsonEntity);
                            return;
                        }
                        testView.onError(jsonEntity.getMsg());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.Log(TAG, LogUtil.LOG_E, "getChallengeCreatChallenge 失败了请求回来的参数： " + e.toString());
                        ToastUtils.showLong(e.toString());
                    }

                    @Override
                    public void onNext(JsonEntity jsonentitiy) {
                        jsonEntity = jsonentitiy;
                    }

                })
        );

    }

    //评分挑战
    public void getChallengeScore(String score,String challengeNo)  {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("score", score);
        hashMap.put("challengeNo", challengeNo);
        hashMap.put("time", BaseApplication.getTimeDate() + "");
        BaseApplication.setSign(hashMap);
        Logger.e("challenge","score: "+ score);
        Logger.e("challenge","changeno: "+ challengeNo);
        Logger.e("challenge","time: "+ BaseApplication.getTimeDate() + "");

        mCompositeSubscription.add(manager.getChallengeScore(score, challengeNo )
                .subscribeOn(Schedulers.io())

                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<JsonEntity>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if (jsonEntity != null) LogUtil.Log(TAG, LogUtil.LOG_E, "getChallengeScore 成功请求回来的参数： " + jsonEntity.toString());
                        if(jsonEntity.getCode().equals("200")){
                            testView.onSuccess(jsonEntity);
                            return;
                        }
                        testView.onError(jsonEntity.getMsg());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.Log(TAG, LogUtil.LOG_E, "getChallengeScore 失败了请求回来的参数： " + e.toString());
                        ToastUtils.showLong(e.toString());
                    }

                    @Override
                    public void onNext(JsonEntity jsonentitiy) {
                        jsonEntity = jsonentitiy;
                    }

                })
        );

    }




}
