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
import com.connxun.elinetv.entity.Entity;
import com.connxun.elinetv.entity.EntityObject;
import com.connxun.elinetv.entity.JsonEntity;
import com.connxun.elinetv.entity.RegisterEntity;
import com.connxun.elinetv.entity.user.LabelEntity;
import com.connxun.elinetv.entity.user.UserDataEntity;
import com.connxun.elinetv.entity.user.UserFollowEntity;
import com.connxun.elinetv.entity.user.UserIllegalQueryEntity;
import com.connxun.elinetv.entity.user.UserInfoEntity;
import com.connxun.elinetv.presenter.Presenter;
import com.connxun.elinetv.view.View;
import com.connxun.elinetv.view.user.ITestView;

import java.util.HashMap;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 个人用户消息
 */
public class UserLabelPresenter implements Presenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private ITestView testView;
    private Entity<LabelEntity> labelEntityEntity; //用户关注列表
    EntityObject<List<LabelEntity>> labelList;
    private JsonEntity jsonEntity; //用户关注列表
    private String TAG = "UserLabelPresenter";


    public UserLabelPresenter(Context context){
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
    //获取用户关注列表
    public void getUserLabelList(String toUserNo,String page,String length){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("toUserNo", toUserNo);
        hashMap.put("page", page);
        hashMap.put("length", length);
        hashMap.put("time", BaseApplication.getTimeDate() + "");
        BaseApplication.setSign(hashMap);

        mCompositeSubscription.add(manager.getUserLabelList(toUserNo,page,length)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Entity<LabelEntity>>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if(labelEntityEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"getAttentionFollowList 失败了请求回来的参数： "+ labelEntityEntity.toString());
                        if(labelEntityEntity != null){
                            if(!labelEntityEntity.getCode().equals("200")){
                                testView.onError(labelEntityEntity.getMsg());
                                return;
                            }
                            testView.onSuccess(labelEntityEntity);

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        if(labelEntityEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"getAttentionFollowList 失败了请求回来的参数： "+ labelEntityEntity.toString());
                        testView.onError("请求失败！！");

                    }

                    @Override
                    public void onNext(Entity<LabelEntity> registerEntity) {
                        labelEntityEntity = registerEntity;
                    }

                })
        );

    }
    //查看用户对主播的标签
    public void getUserLabelTOuserLabelIst(String toUserNo,String page,String length){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("toUserNo", toUserNo);
        hashMap.put("page", page);
        hashMap.put("length", length);
        hashMap.put("time", BaseApplication.getTimeDate() + "");
        BaseApplication.setSign(hashMap);

        mCompositeSubscription.add(manager.getUserLabelTOuserLabelIst(toUserNo,page,length)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<EntityObject<List<LabelEntity>>>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if(labelList != null) LogUtil.Log(TAG,LogUtil.LOG_E,"getUserLabelTOuserLabelIst 失败了请求回来的参数： "+ labelList.toString());
                        if(labelList != null){
                            if(!labelList.getCode().equals("200")){
                                testView.onError(labelList.getMsg());
                                return;
                            }
                            testView.onSuccess(labelList);

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        if(labelList != null) LogUtil.Log(TAG,LogUtil.LOG_E,"getUserLabelTOuserLabelIst 失败了请求回来的参数： "+ labelList.toString());
                        testView.onError("请求失败！！");

                    }

                    @Override
                    public void onNext(EntityObject<List<LabelEntity>> registerEntity) {
                        labelList = registerEntity;
                    }

                })
        );

    }
    //给主播的标签
    public void getuserLabelAdd(String toUserNo,String menNo){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("toUserNo", toUserNo);
        hashMap.put("menuNo", menNo);
        hashMap.put("time", BaseApplication.getTimeDate() + "");
        BaseApplication.setSign(hashMap);

        mCompositeSubscription.add(manager.getuserLabelAdd(toUserNo,menNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<JsonEntity>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if(jsonEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"getUserLabelTOuserLabelIst 失败了请求回来的参数： "+ jsonEntity.toString());
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
                        if(jsonEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"getUserLabelTOuserLabelIst 失败了请求回来的参数： "+ jsonEntity.toString());
                        testView.onError("请求失败！！");

                    }

                    @Override
                    public void onNext(JsonEntity registerEntity) {
                        jsonEntity = registerEntity;
                    }

                })
        );

    }


    //标签列表
    public void getMenuLabelList(String page,String length){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("page", page);
        hashMap.put("length", length);
        hashMap.put("time", BaseApplication.getTimeDate() + "");
        BaseApplication.setSign(hashMap);

        mCompositeSubscription.add(manager.getMenuLabelList(page,length)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Entity<LabelEntity>>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if(labelEntityEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"getAttentionFollowList 失败了请求回来的参数： "+ labelEntityEntity.toString());
                        if(labelEntityEntity != null){
                            if(!labelEntityEntity.getCode().equals("200")){
                                testView.onError(labelEntityEntity.getMsg());
                                return;
                            }
                            testView.onSuccess(labelEntityEntity);

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        if(labelEntityEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"getAttentionFollowList 失败了请求回来的参数： "+ labelEntityEntity.toString());
                        testView.onError("请求失败！！");

                    }

                    @Override
                    public void onNext(Entity<LabelEntity> registerEntity) {
                        labelEntityEntity = registerEntity;
                    }

                })
        );

    }

}
