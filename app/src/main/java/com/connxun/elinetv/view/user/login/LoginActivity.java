package com.connxun.elinetv.view.user.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.connxun.elinetv.R;
import com.connxun.elinetv.app.BaseApplication;
import com.connxun.elinetv.base.ui.BaseActivity;
import com.connxun.elinetv.entity.RegisterEntity;
import com.connxun.elinetv.presenter.user.LoginPresenter;
import com.connxun.elinetv.presenter.user.ObjectPresenter;
import com.connxun.elinetv.util.ToastUtils;
import com.connxun.elinetv.util.threadShare.ShareResponseEntity;
import com.connxun.elinetv.util.threadShare.ThirdLogin;
import com.connxun.elinetv.view.Setting.AccountSeccurityActivity;
import com.connxun.elinetv.view.Setting.SettingActivity;
import com.connxun.elinetv.view.user.ITestView;
import com.netease.cloud.nos.android.core.UploadTaskExecutor;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by connxun-16 on 2018/1/9.
 */

@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity implements View.OnClickListener ,ThreadLogin{
    public static LoginActivity loginActivity_instance = null;
    @ViewInject(R.id.rl_login_finish)
    RelativeLayout rlLoginFinish;
    @ViewInject(R.id.rl_login_register)
    RelativeLayout rlRegister;
    @ViewInject(R.id.iv_login_setting)
    ImageView ivSetting;
    @ViewInject(R.id.iv_login_weibo)
    ImageView ivLoginWeibo;
    @ViewInject(R.id.iv_login_wechat)
    ImageView ivLoginWeChat;
    @ViewInject(R.id.iv_login_qq)
    ImageView ivLoginQQ;
    @ViewInject(R.id.iv_login_phone)
    ImageView ivLoginPhone;
    @ViewInject(R.id.et_login_phone)
    EditText etPhone;
    @ViewInject(R.id.et_login_pwd)
    EditText etPwd;
    @ViewInject(R.id.tv_no_login)
    TextView tvNoLogin;
    @ViewInject(R.id.tv_feedback)
    TextView tvFeedback;
    @ViewInject(R.id.btn_login)
    Button btnLogin;

    @ViewInject(R.id.iv_login_logo)
    ImageView ivLogo;

    private UploadTaskExecutor executor;
    RegisterEntity registerEntity;

    private LoginPresenter loginPresenter = new LoginPresenter(this); //登录
    private ObjectPresenter objectPresenter = new ObjectPresenter(this);
    private String LOGTAG = "LoginActivity";
    private ThirdLogin thirdLogin;

    //登录相关
    private int ThreedLogintype = 0;

    //第三方登录
    private ThreadLogin threadLogin=this;
    ShareResponseEntity shareResponseEntity;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginActivity_instance = this;
        setListener();
    }

    private void setListener() {
        rlLoginFinish.setOnClickListener(this);
        ivLoginPhone.setOnClickListener(this);
        ivSetting.setOnClickListener(this);
        tvNoLogin.setOnClickListener(this);
        tvFeedback.setOnClickListener(this);
        rlRegister.setOnClickListener(this);
        ivLogo.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        ivLoginQQ.setOnClickListener(this);
        ivLoginWeChat.setOnClickListener(this);
        ivLoginWeibo.setOnClickListener(this);


    }

    /**
     * 点击事件
     *
     * @param view
     */
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.rl_login_finish:
                //返回
                finish();
                break;
            case R.id.iv_login_setting:
                intent = new Intent(this, SettingActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
            case R.id.tv_no_login:
                startActivity(new Intent(this, AccountSeccurityActivity.class));
                break;
            case R.id.tv_feedback:
                ToastUtils.showLong("有啥意见你说...");
                break;
            case R.id.iv_login_weibo:
                ToastUtils.showLong("暂未开放");
                break;
            case R.id.iv_login_wechat:
//                ToastUtils.showLong("暂未开放");
                thirdLogin = new ThirdLogin(BaseApplication.baseContext,threadLogin);
                thirdLogin.weixinLogoin();
                ThreedLogintype = 2;
                break;
            case R.id.iv_login_qq: //QQ登录
                thirdLogin = new ThirdLogin(BaseApplication.baseContext,threadLogin);
                thirdLogin.qqLogin();
                ThreedLogintype = 1;
                break;
            case R.id.iv_login_phone:
                //手机号登录
                startActivity(new Intent(this, PhoneLoginActivity.class));
                break;
            case R.id.rl_login_register:
                //注册
                startActivity(new Intent(this, PhoneLoginActivity.class));
                break;
            case R.id.btn_login:
                Login();
                loginPresenter.onCreate();
                loginPresenter.getUserLoginPwd(etPhone.getText().toString(),etPwd.getText().toString());
                loginPresenter.attachView(mLoginView);
                break;
            case R.id.iv_login_logo:
                SharedPreferences sharedPreferences = BaseApplication.getContext().getSharedPreferences("User",MODE_MULTI_PROCESS);
                final String userNo = BaseApplication.getUserNo();
                final String token = BaseApplication.getToken();

                break;
        }
    }



    public ITestView mLoginView  = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            registerEntity = (RegisterEntity) object;
            if(ThreedLogintype != 0){
                if(registerEntity.getData().getStatus() == 2) {
                    //未注册
                    loginPresenter.onCreate();
                    loginPresenter.getUserRegThree(ThreedLogintype+"",
                            shareResponseEntity.getUserID(),
                            shareResponseEntity.getUserName(),
                            shareResponseEntity.getUserIcon(),"中国","2018-02-26",
                            shareResponseEntity.getUserSex()+"");
                    Log.e(LOGTAG,"请求参数: ThreedLogintype: "+ ThreedLogintype+ "   uid: "+shareResponseEntity.getUserID());
                    loginPresenter.attachView(mLoginView);
                    ThreedLogintype = 0;
                    return;
                }
            }

                BaseApplication.setToken(registerEntity.getData().getToken());
                BaseApplication.setUserNo(registerEntity.getData().getUserNo());
                SharedPreferences sharedPreferences = BaseApplication.baseContext.getSharedPreferences("User",MODE_MULTI_PROCESS);
                //2.实例化SharedPreferences.Editor对象
                SharedPreferences.Editor editer = sharedPreferences.edit();
                String userNo = registerEntity.getData().getUserNo();
                editer.putString("userNo",userNo);
                editer.putString("token",registerEntity.getData().getToken());
                editer.putInt("state",registerEntity.getData().getStatus());
                editer.putString("nickName",registerEntity.getData().getNickName());
                editer.putString("avatar",registerEntity.getData().getAvatar());
                editer.putString("followNum",registerEntity.getData().getFollowNum()+"");
                editer.putString("followerSize",registerEntity.getData().getFollowerSize()+"");
                editer.putString("userLever",registerEntity.getData().getUserLever()+"");
                editer.commit();
                BaseApplication.IMLogion();
                finish();

        }

        @Override
        public void onError(Object object) {
            ToastUtils.showLong(object.toString());

        }
    };

    private String path;



    /**
     * 登录
     * @return
     */
    private boolean Login() {
        if (!etPhone.getText().toString().equals("") || !etPwd.getText().toString().equals("")) {
            if (!BaseApplication.isMobileNO(etPhone.getText().toString())) {
                ToastUtils.showLong("请输入正确的登录账号");
                return false;
            }
            if (etPwd.getText().toString().length() > 5 && etPwd.getText().toString().length() < 20) {
                return true;
            } else {
                ToastUtils.showLong("密码必须是6到20位");
                return false;
            }
        } else {
            ToastUtils.showLong("请输入登录账号或密码");
            return false;
        }
    }


    @Override
    public void AuthorizedSuccess() {

    }

    @Override
    public void AuthorizedFailure() {

    }
    //第三方登录成功
    @Override
    public void ThirdLoginSucces(ShareResponseEntity shareResponseEntity) {
        this.shareResponseEntity = shareResponseEntity;
        loginPresenter.onCreate();
        loginPresenter.getUserThreeLogin(ThreedLogintype+"",shareResponseEntity.getUserID());
        Log.e(LOGTAG,"请求参数: ThreedLogintype: "+ ThreedLogintype+ "   uid: "+shareResponseEntity.getUserID());
        loginPresenter.attachView(mLoginView);

    }

    @Override
    public void ThirdLoginFailure() {

    }

    @Override
    public void ShareItSuccess() {

    }

    @Override
    public void ShareItFailure() {

    }

    @Override
    public void ShareSDkError(String messageException) {
        ThreedLogintype = 0;
        ToastUtils.showLong(messageException);
    }
}
