package com.connxun.elinetv.base.network;

/**
 * Created by connxun-16 on 2017/12/19.
 */

import com.connxun.elinetv.entity.AD;
import com.connxun.elinetv.entity.AttentionEntity;
import com.connxun.elinetv.entity.ChallengeTypeEntity;
import com.connxun.elinetv.entity.Entity;
import com.connxun.elinetv.entity.EntityObject;
import com.connxun.elinetv.entity.GetVideoInfo;
import com.connxun.elinetv.entity.Gift;
import com.connxun.elinetv.entity.ImageDNS;
import com.connxun.elinetv.entity.JsonEntity;
import com.connxun.elinetv.entity.ObjectEntity;
import com.connxun.elinetv.entity.OpenLive;
import com.connxun.elinetv.entity.RegisterEntity;
import com.connxun.elinetv.entity.Video.Talk;
import com.connxun.elinetv.entity.live.ChallengeTypeThree;
import com.connxun.elinetv.entity.live.EndLiveEntitiy;
import com.connxun.elinetv.entity.order.UserVC;
import com.connxun.elinetv.entity.UserVideoEntity;
import com.connxun.elinetv.entity.order.VC;
import com.connxun.elinetv.entity.VideoEtivity;
import com.connxun.elinetv.entity.WatchLive;
import com.connxun.elinetv.entity.order.Order;
import com.connxun.elinetv.entity.order.WeChatEntity;
import com.connxun.elinetv.entity.order.WithdrawalsList;
import com.connxun.elinetv.entity.user.LabelEntity;
import com.connxun.elinetv.entity.user.UserDataEntity;
import com.connxun.elinetv.entity.user.UserFollowEntity;
import com.connxun.elinetv.entity.user.UserIllegalQueryEntity;
import com.connxun.elinetv.entity.user.UserInfoEntity;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Retrofit 的接口地址
 */
public interface RetrofitService {


    /**
     * 测试用例
     * @param name
     * @param tag
     * @param start
     * @param count
     * @return
     */
    @GET("book/search")
    Observable<String> getSearchBooks(@Query("q") String name,
                                      @Query("tag") String tag, @Query("start") int start,
                                      @Query("count") int count);


    /**
     *
     *  艺线直播-接口
     *
     */


    //上传图片-API
    //1.DNS查询
    @GET("lbs")
    Call<ImageDNS> getImageDNS(@Query("version") String version, @Query("bucketname") String bucketname);

    @FormUrlEncoded
    @POST("")
    Call<String> getUploadImage(@Field("bucketName") String bucketName,
                                @Field("objectName") String objectName,
                                @Field("offset") String offset,
                                @Field("complete") String complete,
                                @Field("x-nos-token")String token);

    @POST()
    Call<ResponseBody> upLoad(
            @Url() String url,
            @Body RequestBody Body);

    /**
     * 上传文件
     * @return
     */
    @Multipart
    @POST()
    Call<ResponseBody> uploadFile(@Url() String url,
                                  @Body RequestBody Body);


    /**
     * 获取图片上传权限
     * @param objectname
     * @param time
     * @return
     */
    @FormUrlEncoded
    @POST("user/objectToken")
    Observable<ObjectEntity> getObjectToken(@Field("objectname")String objectname, @Field("time")String time);

    /**
     * 获取视频文件上传权限
     * @return
     */
    @FormUrlEncoded
    @POST("user/videoToken")
    Observable<UserVideoEntity> getUserVideoToken(@Field("time")String time);



    /**
     * 用户模块
     */




    /**
     * 获取验证码
     * @param phone 电话号码
     * @return 实体
     */
    @FormUrlEncoded
    @POST("user/captch")
    Observable<JsonEntity> getUserCaptch(@Field("phone") String phone);


    /**
     * 判断手机号是否注册
     * @param phone
     * @return
     */
    @FormUrlEncoded
    @POST("user/updatePwdcaptch")
    Observable<JsonEntity> getpdatePwdcaptch(@Field("phone") String phone);



    /**
     * 用户注册
     * @param phone 电话号码
     * @param captch 验证码
     * @return 实体
     */
    @FormUrlEncoded
    @POST("user/regPhone")
    Observable<RegisterEntity> getUserRegister(@Field("phone") String phone,
                                               @Field("captch") String captch,
                                               @Field("password")String password);
    /**
     * 用户修改密码
     * @param phone 电话号码
     * @param captch 验证码
     * @return 实体
     */
    @FormUrlEncoded
    @POST("user/updatePWD")
    Observable<JsonEntity> getUserUpdatePWD(@Field("phone") String phone,
                                               @Field("captch") String captch,
                                               @Field("newPWD")String password);
    /**
     * 用户登录
     * @param phone 电话号码
     * @param captch 验证码
     * @return 实体
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<RegisterEntity> getUserLogin(@Field("phone") String phone,@Field("captch") String captch);


    /**
     * 用户手机密码登录
     * @param phone 账号
     * @param password 密码
     * @return
     */
    @FormUrlEncoded
    @POST("user/loginPwd")
    Observable<RegisterEntity> getUserLoginPwd(@Field("phone") String phone,@Field("password") String password);

    /**
     * 用户第三方登录
     * @return 实体
     */
    @FormUrlEncoded
    @POST("user/threeLogin")
    Observable<RegisterEntity> getUserThreeLogin(@Field("registerType") String registerType,@Field("uid") String uid);

    /**
     * 用户第三方登录注册
     * @return 实体
     */
    @FormUrlEncoded
    @POST("user/regThree")
    Observable<RegisterEntity> getUserRegThree(@Field("uid") String uid,
                                               @Field("registerType") String registerType,
                                               @Field("nickName") String nickName,
                                               @Field("avatar") String avatar,
                                               @Field("city") String city,
                                               @Field("birthday") String birthday,
                                               @Field("sex") String sex);




    /**
     * 用户资料
     * @return 实体
     */
    @FormUrlEncoded
    @POST("user/userInfo")
    Observable<EntityObject<UserInfoEntity>> getUserUserinfo(@Field("time") String time);

    /**
     * 用户资料
     * @return 实体
     */
    @FormUrlEncoded
    @POST("user/toUserInfo")
    Observable<EntityObject<UserInfoEntity>> getToUserInfo(@Field("toUserNo") String toUserNo,@Field("time") String time);





    /**
     * 修改资料
     * @param userNo 用户标识
     * @param nickName 用户名称
     * @param avatar 头像
     * @param city 地址
     * @param birthday 生日
     * @param sex 性别
     * @return
     */
    @FormUrlEncoded
    @POST("user/updateInfo")
    Observable<JsonEntity> getUserUpdateInfo(@Field("userNo") String userNo,
                                         @Field("signature") String signature,
                                         @Field("nickName") String nickName,
                                         @Field("avatar") String avatar,
                                         @Field("city") String city,
                                         @Field("birthday") String birthday,
                                         @Field("sex") int sex,
                                         @Field("time") String time);


    /**
     * 意见反馈
     * @param content 内容
     * @param pic 图片
     * @param phone 手机号
     * @param time 时间戳
     * @return
     */
    @FormUrlEncoded
    @POST("feedback/add")
    Observable<JsonEntity> submitOpinion(@Field("content") String content,
                                     @Field("pic") String pic,
                                     @Field("phone") String phone,
                                     @Field("time") String time);


     /**
      * 用户关系
      */


    /**
     * 判断用户关系
     * @param toUserNo
     * @param type 0 关注 1 黑名单
     * @param time
     * @return
     */
    @FormUrlEncoded
    @POST("attention/isUserAttention")
    Observable<EntityObject<AttentionEntity>> getAttentionIsUserAttention(@Field("toUserNo") String toUserNo,
                                                                          @Field("type") String type,
                                                                          @Field("time") String time);
    /**
     * 添加用户关系
     * @param toUserNo
     * @param type 0 关注 1 黑名单
     * @param time
     * @return
     */
    @FormUrlEncoded
    @POST("attention/addAttention")
    Observable<JsonEntity> getAttentionAddAttention(@Field("toUserNo") String toUserNo,
                                         @Field("type") String type,
                                         @Field("time") String time);

    /**
     * 删除用户关系
     * @param toUserNo
     * @param type
     * @param time
     * @return
     */
    @FormUrlEncoded
    @POST("attention/delAttention")
    Observable<JsonEntity> getAttentionDelAttention(@Field("toUserNo") String toUserNo,
                                                @Field("type") String type,
                                                @Field("time") String time);



    /**
     * 获取关注用户
     * @param time
     * @return
     */
    @FormUrlEncoded
    @POST("attention/followList")
    Observable<Entity<UserFollowEntity>> getAttentionFollowList(@Field("page")String page,
                                                                @Field("length")String length,
                                                                @Field("time") String time);

    /**
     * 获取粉丝
     * @param time
     * @return
     */
    @FormUrlEncoded
    @POST("attention/followerList")
    Observable<Entity<UserFollowEntity>> getAttentionFollowerList(@Field("page")String page,
                                                                @Field("length")String length,
                                                                @Field("time") String time);



    /**
     * 主播标签
     * @param time
     * @return
     */
    @FormUrlEncoded
    @POST("userLabel/list")
    Observable<Entity<LabelEntity>> getUserLabelList(@Field("toUserNo")String toUserNo,
                                                     @Field("page")String page,
                                                     @Field("length")String length,
                                                     @Field("time") String time);
    /**
     * 用户对主播的标签
     * @param time
     * @return
     */
    @FormUrlEncoded
    @POST("userLabel/userToUserLabelList")
    Observable<EntityObject<List<LabelEntity>>> getUserLabelTOuserLabelIst(@Field("toUserNo")String toUserNo,
                                                              @Field("page")String page,
                                                              @Field("length")String length,
                                                              @Field("time") String time);
    /**
     * 对主播添加标签
     * @param time
     * @return
     */
    @FormUrlEncoded
    @POST("userLabel/add")
    Observable<JsonEntity> getuserLabelAdd(@Field("toUserNo")String toUserNo,
                                                     @Field("menuNo")String menuNo,
                                                     @Field("time") String time);
    /**
     * 标签列表
     * @param time
     * @return
     */
    @FormUrlEncoded
    @POST("menu/labelList")
    Observable<Entity<LabelEntity>> getMenuLabelList(@Field("page")String page,
                                                     @Field("length")String length,
                                                     @Field("time") String time);



    /**
     * 直播管理
     */

    /**
     * 创建直播
     * @param position 位置
     * @param menuNo 菜单id
     * @return
     */
    @FormUrlEncoded
    @POST("live/openLive")
    Observable<OpenLive> getLiveOpenLive(@Field("position")String position,
                                         @Field("menuNo")String menuNo,
                                         @Field("time")String time);

    /**
     * 开始直播
     * @param liveNo 频道名称
     * @return
     */
    @FormUrlEncoded
    @POST("live/startLive")
    Observable<JsonEntity> getLiveStartLive(@Field("name")String name,
                                            @Field("cover")String fileName,
                                            @Field("liveNo")String liveNo,
                                            @Field("time")String time);

    /**
     * 停止直播
     * @param liveNo 频道名称
     * @return
     */
    @FormUrlEncoded
    @POST("live/endLive")
    Observable<EntityObject<EndLiveEntitiy>> getLiveEndLive(@Field("liveNo")String liveNo,
                                                            @Field("time")String time);


    /**
     * 直播心跳
     * @param liveNo
     * @param time
     * @return
     */
    @FormUrlEncoded
    @POST("live/heartbeat")
    Observable<JsonEntity> getHeartBeat(@Field("liveNo")String liveNo,
                                          @Field("time")String time);




    /**
     * 直播心跳
     * @param liveNo 频道名称
     * @return
     */
    @FormUrlEncoded
    @POST("live/heartbeat")
    Observable<JsonEntity> getLiveHeartbeat(@Field("liveNo")String liveNo,
                                          @Field("time")String time);

    /**
     * 推荐列表
     * @param page 频道名称
     * @return
     */
    @FormUrlEncoded
    @POST("hotLive/list")
    Observable<WatchLive> getliveHotLiveList(@Field("page")String page,
                                              @Field("length")String length,
                                              @Field("time")String time);


    /**
     * 人气主播
     * @param page 频道名称
     * @return
     */
    @FormUrlEncoded
    @POST("live/rankingList")
    Observable<WatchLive> getliveRanKingList(@Field("page")String page,
                                             @Field("length")String length,
                                             @Field("time")String time);
    /**
     * 直播列表
     * @param page 频道名称
     * @return
     */
    @FormUrlEncoded
    @POST("live/getList")
    Observable<WatchLive> getliveList(@Field("page")String page,
                                             @Field("length")String length,
                                             @Field("time")String time);


    /**
     * 礼物管理
     */

    /**
     * 礼物列表
     * @return
     */
    @FormUrlEncoded
    @POST("gift/getlist")
    Observable<Entity<Gift>> getGiftList(@Field("page")String page,
                                         @Field("length")String length);
     /*
     * 送礼物
     * @return
     */
    @FormUrlEncoded
    @POST("giftLog/giveGift")
    Observable<JsonEntity> getGiveGift(@Field("giftNo")String giftNo,
                                         @Field("type")String type,
                                         @Field("externalNo")String externalNo,
                                         @Field("giftNum")String giftNum,
                                         @Field("toUserNo")String toUserNo,
                                         @Field("time")String time);


    /**
     * 用户金币
     * @return
     */
    @FormUrlEncoded
    @POST("user/userVc")
    Observable<EntityObject<UserVC>> getUserVc(@Field("time")String time);



    /**
     * 订单管理
     */
    //金币列表
    @FormUrlEncoded
    @POST("vc/list")
    Observable<Entity<VC>> getVcList(@Field("page")String page,
                                     @Field("length")String length);

    /**
     * 订单添加
     * @param type 商品类型 0 金币
     * @param isIntegral 是否使用积分 0 不用 1用
     * @param isVouchers 是否使用代金券 0 不用 1用
     * @param extendNo 对应type 所属的商品id
     * @param time
     * @return
     */
    @FormUrlEncoded
    @POST("order/add")
    Observable<EntityObject<Order>> getOrderAdd(@Field("type")String type,
                                                @Field("isIntegral")String isIntegral,
                                                @Field("isVouchers")String isVouchers,
                                                @Field("extendNo")String extendNo,
                                                @Field("time")String time);

    /**
     * 生成支付订单--支付宝
     * @param page 频道名称
     * @return
     */
    @FormUrlEncoded
    @POST("order/createAliyun")
    Observable<JsonEntity> getOrderCreateAliyum(@Field("orderNo")String page,
                                                @Field("time")String time);

    /**
     * 生成微信支付订单--微信
     * @param page
     * @param type
     * @param time
     * @return
     */
    @FormUrlEncoded
    @POST("wxPay/doUnifiedOrder")
    Observable<EntityObject<WeChatEntity>> getWxPayDoUnifiedOrder(@Field("orderNo")String page,
                                                                  @Field("type")String type,
                                                                  @Field("time")String time);

    /**
     * 提现
     * @param cashCoin 金币数
     * @param alipay 支付宝账号
     * @param name 名字
     * @param phone 电话
     * @param time
     * @return
     */
    @FormUrlEncoded
    @POST("userWithdrawals/withdrawals")
    Observable<JsonEntity> getUserWithdrawals(@Field("cashCoin")String cashCoin,
                                                                  @Field("alipay")String alipay,
                                                                  @Field("name")String name,
                                                                  @Field("phone")String phone,
                                                                  @Field("time")String time);

    /**
     * 提现记录
     * @param page
     * @param length
     * @param time
     * @return
     */
    @FormUrlEncoded
    @POST("userWithdrawals/userList")
    Observable<Entity<WithdrawalsList>> getWithdrawalsList(@Field("page")String page,
                                                  @Field("length")String length,
                                                           @Field("time") String time);





    /**
     * 评论管理
     */
    //评论列表
    @FormUrlEncoded
    @POST("content/list")
    Observable<Entity<Talk>> getContentList(@Field("page")String page,
                                            @Field("length")String length,
                                            @Field("type")String type,
                                            @Field("extendNo")String extendNo,
                                            @Field("time")String time);
    //发送评论
    @FormUrlEncoded
    @POST("content/add")
    Observable<JsonEntity> getContentAdd(@Field("type")String type,
                                           @Field("content")String content,
                                           @Field("toUserNo")String toUserNo,
                                           @Field("extendNo")String extendNo,
                                               @Field("time")String time);


    //点赞
    @FormUrlEncoded
    @POST("video/loveSumAdd")
    Observable<JsonEntity> getLoveSumAdd(@Field("videoNo")String videoNo,
                                               @Field("time")String time);
    //取消点赞
    @FormUrlEncoded
    @POST("video/noLove")
    Observable<JsonEntity> getNolove(@Field("videoNo")String videoNo,
                                               @Field("time")String time);








    /**
     * 观看管理
     */

    /**
     * 进入观看
     * @param liveNo
     * @param time
     * @return
     */
    @FormUrlEncoded
    @POST("view/startView")
    Observable<JsonEntity> getViewStartView(@Field("liveNo")String liveNo,
                                          @Field("time")String time);
    /**
     * 停止观看
     * @param liveNo
     * @param time
     * @return
     */
    @FormUrlEncoded
    @POST("view/endView")
    Observable<JsonEntity> getViewEndView(@Field("liveNo")String liveNo,
                                            @Field("time")String time);


    /**
     * 视频管理
     */

    /**
     * 视频列表
     * @param page
     * @param length
     * @param toUserNo
     * @return
     */
    @FormUrlEncoded
    @POST("video/list")
    Observable<Entity<VideoEtivity>> getVideoList(@Field("page")String page,
                                                  @Field("length")String length,
                                                  @Field("toUserNo")String toUserNo);

    /**
     * 观看视频  -  视频详情
     * @return
     */
    @FormUrlEncoded
    @POST("video/info")
    Observable<EntityObject<GetVideoInfo>> getVideoInfo(@Field("videoNo")String videoNo,
                                                        @Field("time") String time);


    /**
     * 点赞的视频
     * @return
     */
    @FormUrlEncoded
    @POST("video/userLoveVideo")
    Observable<Entity<VideoEtivity>> getVideoUserLoveVideo(@Field("page")String page,
                                                           @Field("length")String length,
                                                           @Field("userNo")String userNo,
                                                        @Field("time") String time);



    /**
     *其他
     */

    /**
     * 轮播图
     * @return
     */
    @FormUrlEncoded
    @POST("ad/getList")
    Observable<AD> getAdList(@Field("page")String page);


    /**
     * 挑战
     */

    /**
     * 挑战一二级菜单列表
     * @return
     */
    @FormUrlEncoded
    @POST("menu/challengeList")
    Observable<Entity<ChallengeTypeEntity>> getChallengeList(@Field("page")String page,
                                                             @Field("length")String length,
                                                             @Field("time") String time);
    /**
     * 挑战三级菜单
     * @return
     */
    @FormUrlEncoded
    @POST("challengeResource/list")
    Observable<Entity<ChallengeTypeThree>> getChallengeResourceList(@Field("menuNo")String menuNo,
                                                                    @Field("time") String time);










}
