package com.connxun.elinetv.base.util;

/**
 * Created by connxun-16 on 2017/12/19.
 */

import android.util.Log;

/**
 *
 * 日志打印工具
 */
public class LogUtil {
    public static int LOGTYPE = 0; //log 类型

    public static final int LOG_E = 1; //error
    public static final int LOG_D = 2; //error
    public static final int LOG_I = 3; //error
    public static final int LOG_W = 4; //error

    public static final boolean BL_ISHOWLOG = true; //是否开启log 默认：开启


    public LogUtil(){

    }

    /**
     *
     * @param TAG log名称
     * @param logType log 类型
     * @param msg log属性值
     */
    public static void Log(String TAG,int logType,String msg){
        if(!BL_ISHOWLOG){
            return;
        }
        switch (logType)
        {
            case LOG_E:
                Log.e(TAG, msg);
                break;

            case LOG_D:
                Log.d(TAG, msg);
                break;

            case LOG_I:
                Log.i(TAG, msg);
                break;

            case LOG_W:
                Log.w(TAG, msg);
                break;


        }

    }
}
