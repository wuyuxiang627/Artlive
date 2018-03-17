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
import com.connxun.elinetv.entity.user.UserDataEntity;
import com.connxun.elinetv.entity.user.UserFollowEntity;
import com.connxun.elinetv.entity.user.UserIllegalQueryEntity;
import com.connxun.elinetv.entity.user.UserInfoEntity;
import com.connxun.elinetv.presenter.Presenter;
import com.connxun.elinetv.view.View;
import com.connxun.elinetv.view.user.ITestView;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 个人用户消息
 */
public class UserinfoPresenter implements Presenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private ITestView testView;
    private JsonEntity jsonEntity;
    private RegisterEntity mregisterEntity;
    private EntityObject<UserInfoEntity> userInfoEntityEntityObject; //用户资料
    private Entity<UserFollowEntity> followEntityEntity; //用户关注列表
    private Entity<UserIllegalQueryEntity> userIllegalQueryEntity;//违规查询
    private Entity<UserDataEntity> userdataEntity;//编辑信息
    private String TAG = "UserinfoPresenter";


    public UserinfoPresenter(Context context){
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

    //获取用户的个人信息
    public void getUserUserinfo(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("time", BaseApplication.getTimeDate() + "");
        BaseApplication.setSign(hashMap);

        mCompositeSubscription.add(manager.getUserUserinfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<EntityObject<UserInfoEntity>>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if(userInfoEntityEntityObject != null) LogUtil.Log(TAG,LogUtil.LOG_E,"getUserUserinfo 失败了请求回来的参数： "+ userInfoEntityEntityObject.toString());
                        if(userInfoEntityEntityObject != null){
                            if(!userInfoEntityEntityObject.getCode().equals("200")){
                                testView.onError(userInfoEntityEntityObject.getMsg());
                                return;
                            }
                            testView.onSuccess(userInfoEntityEntityObject);

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        if(userInfoEntityEntityObject != null) LogUtil.Log(TAG,LogUtil.LOG_E,"失败了请求回来的参数： "+ userInfoEntityEntityObject.toString());
                        testView.onError("请求失败！！");

                    }

                    @Override
                    public void onNext(EntityObject<UserInfoEntity> registerEntity) {
                        userInfoEntityEntityObject = registerEntity;
                    }

                })
        );

    }
    //获取用户的个人信息
    public void getUserUserinfo(String toUserNo){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("toUserNo", toUserNo);
        hashMap.put("time", BaseApplication.getTimeDate() + "");
        BaseApplication.setSign(hashMap);

        mCompositeSubscription.add(manager.getToUserInfo(toUserNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<EntityObject<UserInfoEntity>>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if(userInfoEntityEntityObject != null) LogUtil.Log(TAG,LogUtil.LOG_E,"getUserUserinfo 失败了请求回来的参数： "+ userInfoEntityEntityObject.toString());
                        if(userInfoEntityEntityObject != null){
                            if(!userInfoEntityEntityObject.getCode().equals("200")){
                                testView.onError(userInfoEntityEntityObject.getMsg());
                                return;
                            }
                            testView.onSuccess(userInfoEntityEntityObject);

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        if(userInfoEntityEntityObject != null) LogUtil.Log(TAG,LogUtil.LOG_E,"失败了请求回来的参数： "+ userInfoEntityEntityObject.toString());
                        testView.onError("请求失败！！");

                    }

                    @Override
                    public void onNext(EntityObject<UserInfoEntity> registerEntity) {
                        userInfoEntityEntityObject = registerEntity;
                    }

                })
        );

    }



    //获取用户关注列表
    public void getAttentionFollowList(String page,String length){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("page", page);
        hashMap.put("length", length);
        hashMap.put("time", BaseApplication.getTimeDate() + "");
        BaseApplication.setSign(hashMap);

        mCompositeSubscription.add(manager.getAttentionFollowList(page,length)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Entity<UserFollowEntity>>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if(followEntityEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"getAttentionFollowList 失败了请求回来的参数： "+ followEntityEntity.toString());
                        if(followEntityEntity != null){
                            if(!followEntityEntity.getCode().equals("200")){
                                testView.onError(followEntityEntity.getMsg());
                                return;
                            }
                            testView.onSuccess(followEntityEntity);

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        if(mregisterEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"getAttentionFollowList 失败了请求回来的参数： "+ followEntityEntity.toString());
                        testView.onError("请求失败！！");

                    }

                    @Override
                    public void onNext(Entity<UserFollowEntity> registerEntity) {
                        followEntityEntity = registerEntity;
                    }

                })
        );

    }

    //编辑资料
    public void getAttentionDataList(String userNo,String signature,String nickName,String avatar,String city,String birthday,int sex){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("userNo", userNo);
        hashMap.put("nickName", nickName);
        hashMap.put("signature", signature);
        hashMap.put("avatar", avatar);
        hashMap.put("city", city);
        hashMap.put("birthday", birthday);
        hashMap.put("sex", sex+"");
        hashMap.put("time", BaseApplication.getTimeDate() + "");
        BaseApplication.setSign(hashMap);

        mCompositeSubscription.add(manager.getUserUpdateInfo(userNo,signature,nickName,avatar,city,birthday,sex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<JsonEntity>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if(jsonEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"getAttentionFollowList 失败了请求回来的参数： "+ jsonEntity.toString());
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
                        if(jsonEntity!= null) LogUtil.Log(TAG,LogUtil.LOG_E,"getAttentionFollowList 失败了请求回来的参数： "+ jsonEntity.toString());
                        testView.onError("请求失败！！");
                    }

                    @Override
                    public void onNext(JsonEntity registerEntity) {
                        jsonEntity = registerEntity;
                    }

                })
        );

    }
//
//    //违规查询
//    public void getAttentionIllegalQueryEntityList(String page,String length){
//        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put("page", page);
//        hashMap.put("length", length);
//        hashMap.put("time", BaseApplication.getTimeDate() + "");
//        BaseApplication.setSign(hashMap);
//
//        mCompositeSubscription.add(manager.getAttentionIllegalQueryEntity(page,length)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseSubscriber<Entity<UserIllegalQueryEntity>>(BaseActivity.context) {
//                    @Override
//                    public void onCompleted() {
//                        super.onCompleted();
//                        if(userIllegalQueryEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"getAttentionFollowList 失败了请求回来的参数： "+ userIllegalQueryEntity.toString());
//                        if(userIllegalQueryEntity != null){
//                            if(!userIllegalQueryEntity.getCode().equals("200")){
//                                testView.onError(userIllegalQueryEntity.getMsg());
//                                return;
//                            }
//                            testView.onSuccess(userIllegalQueryEntity);
//
//                        }
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        if(mregisterEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"getAttentionFollowList 失败了请求回来的参数： "+ userIllegalQueryEntity.toString());
//                        testView.onError("请求失败！！");
//
//                    }
//
//                    @Override
//                    public void onNext(Entity<UserIllegalQueryEntity> registerEntity) {
//
//                        userIllegalQueryEntity = registerEntity;
//                    }
//
//
//
//
//                })
//        );
//
//    }

    //我的资料
    public void getAttentionFollowerList(String page,String length){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("page", page);
        hashMap.put("length", length);
        hashMap.put("time", BaseApplication.getTimeDate() + "");
        BaseApplication.setSign(hashMap);

        mCompositeSubscription.add(manager.getAttentionFollowerList(page,length)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Entity<UserFollowEntity>>(BaseActivity.context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if(followEntityEntity != null) LogUtil.Log(TAG,LogUtil.LOG_E,"getAttentionFollowList 失败了请求回来的参数： "+ followEntityEntity.toString());
                        if(followEntityEntity != null){
                            if(!followEntityEntity.getCode().equals("200")){
                                testView.onError(followEntityEntity.getMsg());
                                return;
                            }
                            testView.onSuccess(followEntityEntity);

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        if(mregisterEntity!= null) LogUtil.Log(TAG,LogUtil.LOG_E,"getAttentionFollowList 失败了请求回来的参数： "+ followEntityEntity.toString());
                        testView.onError("请求失败！！");

                    }

                    @Override
                    public void onNext(Entity<UserFollowEntity> registerEntity) {
                        followEntityEntity = registerEntity;
                    }

                })
        );

    }


}
