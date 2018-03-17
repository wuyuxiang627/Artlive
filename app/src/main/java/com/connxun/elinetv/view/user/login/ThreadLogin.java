package com.connxun.elinetv.view.user.login;

/**
 * Created by connxun-16 on 2018/2/26.
 */

import com.connxun.elinetv.util.threadShare.ShareResponseEntity;

/**
 * 第三方登录
 */
public interface ThreadLogin {

    //授权成功
    public  void AuthorizedSuccess();

    //授权失败
    public void AuthorizedFailure();

    //第三方登录成功
    public void ThirdLoginSucces(ShareResponseEntity shareResponseEntity);

    //第三方登录失败
    public void ThirdLoginFailure();

    //分享成功
    public void ShareItSuccess();

    //分享失败
    public void ShareItFailure();

    //发生错误
    public void ShareSDkError(String messageException);







}
