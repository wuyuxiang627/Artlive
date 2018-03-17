package com.connxun.elinetv.view.user;

import com.connxun.elinetv.view.View;

/**
 * Created by connxun-16 on 2017/12/19.
 */

public interface ITestView extends View{


    /**
     * 成功回调
     * @param object
     */
    void onSuccess(Object object);


    /**
     * 失败回调
     * @param object
     */
    void onError(Object object);




}
