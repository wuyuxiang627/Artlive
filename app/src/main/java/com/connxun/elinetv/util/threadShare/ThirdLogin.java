package com.connxun.elinetv.util.threadShare;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.connxun.elinetv.app.BaseApplication;
import com.connxun.elinetv.base.ui.BaseActivity;
import com.connxun.elinetv.view.user.login.ThreadLogin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by Administrator on 2017/5/12.
 * 第三方登录专用类
 */

public class ThirdLogin extends BaseActivity implements PlatformActionListener {
    private int MSG_AUTH_COMPLETE = 0; //第三方登陆成功
    private int MSG_AUTH_ERROR = 1;  //第三方登录失败
    private int MSG_AUTH_CANCEL = 2;//第三方登录取消
    private int MSG_SMSSDK_CALLBACK = 3;//第三方回调
//    private List<ShareResponseEntity> list = new ArrayList<>();
    private List<Object> slist = new ArrayList<>();
    private ThreadLogin thirdLogin;



    private ShareResponseEntity shareResponseEntity  = new ShareResponseEntity();//第三方登录成功后数据存放的实体类

    private Context context;
    private String TAG = "ThirdLogin";

    public ThirdLogin(Context context,ThreadLogin thirdLogin){
        this.context = context;
        this.thirdLogin = thirdLogin;
    }
    /**
     * 第三方登录结果返回进行处理
     */
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_AUTH_CANCEL) {
                //取消授权
                thirdLogin.ShareSDkError("取消登录");
            } else if (msg.what == MSG_AUTH_ERROR) {
                thirdLogin.ShareSDkError("登录失败");
            } else if (msg.what == MSG_AUTH_COMPLETE) {
                shareResponseEntity = (ShareResponseEntity) msg.obj;
                //授权成功
                slist.add(shareResponseEntity);
                thirdLogin.ThirdLoginSucces(shareResponseEntity);
            } else if (msg.what == MSG_SMSSDK_CALLBACK) {
                //分享回调
                thirdLogin.ShareItSuccess();
                Toast.makeText(BaseApplication.baseContext, "分享回调", Toast.LENGTH_LONG).show();
            }
        }
    };
    /**
     * 微信第三方登录
     */
    public void weixinLogoin() {
        final Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        wechat.SSOSetting(false);
        if (!wechat.isClientValid()) {
            Toast.makeText(BaseApplication.baseContext,
                    "微信未安装,请先安装微信",
                    Toast.LENGTH_LONG).show();
        }
        authorize(wechat);
    }

    /**
     * QQ第三方登录
     */
    public void qqLogin() {
        final Platform plat = ShareSDK.getPlatform(QQ.NAME);
        authorize(plat);
    }

    /**
     * 执行授权,获取用户信息
     *
     * @param plat
     */
    public void authorize(final Platform plat) {
        Log.e("=====","======="+plat.getName());
        if (plat.isAuthValid()) {
            //popupOthers();
            plat.removeAccount(true);
        }
        plat.setPlatformActionListener(this);
        //关闭SSO授权
        plat.SSOSetting(false);
        plat.showUser(null);
    }



    /**
     * 第三方登录成功
     *
     * @param platform
     * @param i
     * @param hashMap
     */
    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Log.e(TAG,"回调了: "+ platform.getDb());
        if (i == Platform.ACTION_USER_INFOR) {
            Message msg = new Message();
            msg.what = MSG_AUTH_COMPLETE;

            PlatformDb platDB = platform.getDb();//获取数平台数据DB
            //通过DB获取各种数据
            String s=platDB.getToken();
            String r=platDB.getUserGender()+"";
            String e=platDB.getUserIcon();
            String w=platDB.getUserId();
            String q=platDB.getUserName();

            shareResponseEntity.setUserIcon(platform.getDb().getUserIcon());
            shareResponseEntity.setUserID(platform.getDb().getUserId());

            if(r.equals("null")){
                shareResponseEntity.setUserSex(2);
            }
            else if (platform.getDb().getUserGender().equals("m")) {
                shareResponseEntity.setUserSex(0);
            } else if (platform.getDb().getUserGender().equals("f")) {
                shareResponseEntity.setUserSex(1);
            }

            shareResponseEntity.setUserName(platform.getDb().getUserName());
            msg.obj = shareResponseEntity;
            Log.e(TAG,"shareResponseEntity: "+shareResponseEntity.toString() );
            handler.sendMessage(msg);
        }
    }

    /**
     * 第三方登录失败
     *
     * @param platform
     * @param i
     * @param throwable
     */
    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Log.e("原因"+i,"原因"+throwable);
        if (i == Platform.ACTION_USER_INFOR) {
            handler.sendEmptyMessage(MSG_AUTH_ERROR);
        }
        throwable.printStackTrace();

    }

    /**
     * 第三方登录取消
     *
     * @param platform
     * @param i
     */
    @Override
    public void onCancel(Platform platform, int i) {
        if (i == Platform.ACTION_USER_INFOR) {
            handler.sendEmptyMessage(MSG_AUTH_CANCEL);
        }
    }
}
