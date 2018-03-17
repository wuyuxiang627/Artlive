package com.connxun.elinetv.base.network;

import android.content.Context;


import com.connxun.elinetv.app.BaseApplication;
import com.connxun.elinetv.entity.AD;
import com.connxun.elinetv.entity.AttentionEntity;
import com.connxun.elinetv.entity.Entity;
import com.connxun.elinetv.entity.EntityObject;
import com.connxun.elinetv.entity.GetVideoInfo;
import com.connxun.elinetv.entity.Gift;
import com.connxun.elinetv.entity.JsonEntity;
import com.connxun.elinetv.entity.ObjectEntity;
import com.connxun.elinetv.entity.OpenLive;
import com.connxun.elinetv.entity.RegisterEntity;
import com.connxun.elinetv.entity.Video.Talk;
import com.connxun.elinetv.entity.live.EndLiveEntitiy;
import com.connxun.elinetv.entity.order.UserVC;
import com.connxun.elinetv.entity.UserVideoEntity;
import com.connxun.elinetv.entity.order.VC;
import com.connxun.elinetv.entity.VideoEtivity;
import com.connxun.elinetv.entity.WatchLive;
import com.connxun.elinetv.entity.order.Order;
import com.connxun.elinetv.entity.order.WeChatEntity;
import com.connxun.elinetv.entity.user.LabelEntity;
import com.connxun.elinetv.entity.user.UserDataEntity;
import com.connxun.elinetv.entity.order.WithdrawalsList;
import com.connxun.elinetv.entity.user.UserFollowEntity;
import com.connxun.elinetv.entity.user.UserIllegalQueryEntity;
import com.connxun.elinetv.entity.user.UserInfoEntity;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import rx.Observable;

/**
 * Created by win764-1 on 2016/12/12.
 */

public class DataManager {
    private RetrofitService mRetrofitService;

    public DataManager(Context context){
        this.mRetrofitService = RetrofitHelper.getInstance(context).getServer();
    }


    //测试数据
    public Observable<String> getSearchBooks(String name, String tag, int start, int count){
        return mRetrofitService.getSearchBooks(name,tag,start,count);
    }


    /**
     *
     * 艺线直播接口
     *
     */

    /*
     *用户管理
     */

    // 获取验证码
    public Observable<JsonEntity> getUserCaptch(String phone){
        return mRetrofitService.getUserCaptch(phone);
    }
    // 是否注册
    public Observable<JsonEntity> getpdatePwdcaptch(String phone){
        return mRetrofitService.getpdatePwdcaptch(phone);
    }

    //用户注册
    public Observable<RegisterEntity> getUserRegister(String phone,String captch,String passwoard){
        return mRetrofitService.getUserRegister(phone,captch,passwoard);
    }
    //用户修改密码
    public Observable<JsonEntity> getUserUpdatePWD(String phone,String captch,String passwoard){
        return mRetrofitService.getUserUpdatePWD(phone,captch,passwoard);
    }
    //用户修改资料
    public Observable<JsonEntity> getUserUpdateInfo(String userNo,String signature,String nickName,String avatar,String city,String birthday,int sex){
        return mRetrofitService.getUserUpdateInfo(userNo,signature,nickName,avatar,city,birthday,sex,BaseApplication.getTimeDate()+"");
    }

    //用户验证码
    // 登录
    public Observable<RegisterEntity> getUserLogin(String phone,String captch){
        return mRetrofitService.getUserLogin(phone,captch);
    }

    //用户账号密码登录
    public Observable<RegisterEntity> getUserLoginPassword(String phone,String password){
        return mRetrofitService.getUserLoginPwd(phone,password);
    }
    //第三方登录
    public Observable<RegisterEntity> getUserThreeLogin(String registerType,String uid){
        return mRetrofitService.getUserThreeLogin(registerType,uid);
    }
    //第三方登录完善资料
    public Observable<RegisterEntity> getUserRegThree(String uid,String registerType,String nickName,String avatar,String city,String birthday,String sex){
        return mRetrofitService.getUserRegThree(uid,registerType,nickName,avatar,city,birthday,sex);
    }


    //个人用户信息
    public Observable<EntityObject<UserInfoEntity>> getUserUserinfo(){
        return mRetrofitService.getUserUserinfo(BaseApplication.getTimeDate()+"");
    }
    //个人用户信息
    public Observable<EntityObject<UserInfoEntity>> getToUserInfo(String toUserNo){
        return mRetrofitService.getToUserInfo(toUserNo,BaseApplication.getTimeDate()+"");
    }
    //获取图片上传的token
    public Observable<ObjectEntity> getObjectToken(String objcetname){
        return mRetrofitService.getObjectToken(objcetname,BaseApplication.getTimeDate()+"");
    }
    //获取图片上传的token
    public Observable<UserVideoEntity> getUserVideoToken(){
        return mRetrofitService.getUserVideoToken(BaseApplication.getTimeDate()+"");
    }
    //上传用户意见反馈内容
    public Observable<JsonEntity> submitOpinion(String content,String pic,String phone){
        return mRetrofitService.submitOpinion(content,pic,phone,BaseApplication.getTimeDate()+"");
    }


    /**
     * 用户关系
     */

    //判断用户关系
    public Observable<EntityObject<AttentionEntity>> getAttentionIsUserAttention(String toUserNo, String type){
        return mRetrofitService.getAttentionIsUserAttention(toUserNo,type,BaseApplication.getTimeDate()+"");
    }
    //添加用户关系
    public Observable<JsonEntity> getAttentionAddAttention(String toUserNo,String type){
        return mRetrofitService.getAttentionAddAttention(toUserNo,type,BaseApplication.getTimeDate()+"");
    }
    //删除用户关系
    public Observable<JsonEntity> getAttentionDelAttention(String toUserNo,String type){
        return mRetrofitService.getAttentionDelAttention(toUserNo,type,BaseApplication.getTimeDate()+"");
    }

    //用户关注列表
    public Observable<Entity<UserFollowEntity>> getAttentionFollowList(String page, String length){
        return mRetrofitService.getAttentionFollowList(page,length,BaseApplication.getTimeDate()+"");
    }

    //用户粉丝
    public Observable<Entity<UserFollowEntity>> getAttentionFollowerList(String page, String length){
        return mRetrofitService.getAttentionFollowerList(page,length,BaseApplication.getTimeDate()+"");
    }

    //主播标签列表
    public Observable<Entity<LabelEntity>> getUserLabelList(String toUserNo ,String page, String length){
        return mRetrofitService.getUserLabelList(toUserNo,page,length,BaseApplication.getTimeDate()+"");
    }
    //对主播添加标签
    public Observable<JsonEntity> getuserLabelAdd(String toUserNo ,String menuNo){
        return mRetrofitService.getuserLabelAdd(toUserNo,menuNo,BaseApplication.getTimeDate()+"");
    }
    //用户对主播的标签
    public Observable<EntityObject<List<LabelEntity>>> getUserLabelTOuserLabelIst(String toUserNo , String page, String length){
        return mRetrofitService.getUserLabelTOuserLabelIst(toUserNo,page,length,BaseApplication.getTimeDate()+"");
    }
    //主播标签列表
    public Observable<Entity<LabelEntity>> getMenuLabelList(String page, String length){
        return mRetrofitService.getMenuLabelList(page,length,BaseApplication.getTimeDate()+"");
    }


    /**
     *直播管理
     */

    //创建直播
    public Observable<OpenLive> getLiveOpenLive( String position, String menuNo){
        return mRetrofitService.getLiveOpenLive( position, menuNo,BaseApplication.getTimeDate()+"");
    }



    //开始直播
    public Observable<JsonEntity> getLiveStartLive(String name, String fileName,String liveNo){
        return mRetrofitService.getLiveStartLive( name, fileName, liveNo,BaseApplication.getTimeDate()+"");
    }

    //停止直播
    public Observable<EntityObject<EndLiveEntitiy>> getLiveEndLive(String liveNo){
        return mRetrofitService.getLiveEndLive( liveNo,BaseApplication.getTimeDate()+"");
    }
    //直播心跳
    public Observable<JsonEntity> getHeartBeat(String liveNo){
        return mRetrofitService.getHeartBeat( liveNo,BaseApplication.getTimeDate()+"");
    }

    //推荐直播
    public Observable<WatchLive> getliveHotLiveList(String page,String length){
        return mRetrofitService.getliveHotLiveList(page,length,BaseApplication.getTimeDate()+"");
    }
    //人气直播
    public Observable<WatchLive> getliveRanKingList(String page, String length){
        return mRetrofitService.getliveRanKingList(page,length,BaseApplication.getTimeDate()+"");
    }
    //人气直播
    public Observable<WatchLive> getliveList(String page, String length){
        return mRetrofitService.getliveList(page,length,BaseApplication.getTimeDate()+"");
    }

    //推荐直播
    public Observable<AD> getAdList(){
        return mRetrofitService.getAdList("1");
    }


    /**
     * 礼物管理
     */

    //礼物列表
    public Observable<Entity<Gift>> getGiftList(String page, String length){
        return mRetrofitService.getGiftList(page,length);
    }

    //送礼物
    public Observable<JsonEntity> getGiveGift(String giftNo, String type, String externalNo, String giftNum, String toUserNo){
        return mRetrofitService.getGiveGift( giftNo,  type,  externalNo,  giftNum,  toUserNo, BaseApplication.getTimeDate()+"");
    }
    //用户金币
    public Observable<EntityObject<UserVC>> getUserVc(){
        return mRetrofitService.getUserVc(BaseApplication.getTimeDate()+"");
    }


    /**
     * 订单管理
     */

    //金币列表
    public Observable<Entity<VC>> getVcList(String page, String length){
        return mRetrofitService.getVcList(page,length);
    }
    //添加订单
    public Observable<EntityObject<Order>> getOrderAdd(String type, String isIntegral, String isVouchers, String extendNo){
        return mRetrofitService.getOrderAdd( type,  isIntegral,  isVouchers,  extendNo,  BaseApplication.getTimeDate()+"");
    }
    //生成支付订单
    public Observable<JsonEntity> getOrderCreateAliyum(String orderNo){
        return mRetrofitService.getOrderCreateAliyum( orderNo,  BaseApplication.getTimeDate()+"");
    }
     //生成支付订单-微信
    public Observable<EntityObject<WeChatEntity>> getWxPayDoUnifiedOrder(String orderNo){
        return mRetrofitService.getWxPayDoUnifiedOrder( orderNo, "0", BaseApplication.getTimeDate()+"");
    }

    //提现
    public Observable<JsonEntity> getUserWithdrawals(String cashCoin,String alipay,String name,String phone){
        return mRetrofitService.getUserWithdrawals( cashCoin, alipay, name, phone,BaseApplication.getTimeDate()+"");
    }
    //提现记录
    public Observable<Entity<WithdrawalsList>> getWithdrawalsList(String page, String length){
        return mRetrofitService.getWithdrawalsList(page,length,BaseApplication.getTimeDate()+"");
    }




    /**
     * 视频管理
     */

    //视频列表
    public Observable<Entity<VideoEtivity>> getVideoLIst(String page, String length, String toUserNO){
        return mRetrofitService.getVideoList(page,length,toUserNO);
    }

    //观看视频
    public Observable<EntityObject<GetVideoInfo>> getVideoInfo(String videoNo){
        return mRetrofitService.getVideoInfo(videoNo,BaseApplication.getTimeDate()+"");
    }

    //点赞的视频
    public Observable<Entity<VideoEtivity>> getVideoUserLoveVideo(String page,String length,String userNo){
        return mRetrofitService.getVideoUserLoveVideo(page,length,userNo,BaseApplication.getTimeDate()+"");
    }




    /**
     * 评论管理
     */
    //评论列表
    public Observable<Entity<Talk>> getContentList(String page,String type, String extendNo){
        return mRetrofitService.getContentList(page,"20",type,extendNo,BaseApplication.getTimeDate()+"");
    }

    //发表评论
    public Observable<JsonEntity> getContentAdd(String type, String content, String toUserNo, String extendNo){
        return mRetrofitService.getContentAdd( type,  content,  toUserNo,  extendNo, BaseApplication.getTimeDate()+"" );
    }
    //点赞
    public Observable<JsonEntity> getLoveSumAdd(String videoNo){
        return mRetrofitService.getLoveSumAdd( videoNo, BaseApplication.getTimeDate()+"" );
    }
    //取消点赞
    public Observable<JsonEntity> getNolove(String videoNo){
        return mRetrofitService.getNolove( videoNo, BaseApplication.getTimeDate()+"" );
    }








}
