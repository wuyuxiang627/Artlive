package com.connxun.elinetv.view.user;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.connxun.elinetv.R;
import com.connxun.elinetv.app.BaseApplication;
import com.connxun.elinetv.base.ui.BaseActivity;
import com.connxun.elinetv.entity.JsonEntity;
import com.connxun.elinetv.entity.RegisterEntity;
import com.connxun.elinetv.presenter.user.LoginPresenter;
import com.connxun.elinetv.presenter.user.RegisterPresenter;
import com.connxun.elinetv.presenter.user.TestPresenter;
import com.connxun.elinetv.presenter.user.UserinfoPresenter;

import org.xutils.x;

/**
 * Created by connxun-16 on 2017/12/19.
 */

public class TestaActivity extends BaseActivity {

    /**控件*/
    Button buttonHttpStart; //开始请求
    Button buttonHttpRegister; //注册
    Button buttonHttpLogin;//登录
    Button buttonHttpUserinfo;//获取用户信息
    TextView textViewText; //显示
    TextView textRegister;
    TextView textLogin;//登录
    TextView textUserInfo;//用户信息
    EditText edUserPhone;//用户手机号




    /**参数*/
    JsonEntity jsonEntity;
    RegisterEntity registerEntity;


    private TestPresenter testPresenter = new TestPresenter(this); //测试
    private RegisterPresenter registerPresenter = new RegisterPresenter(this);//注册
    private LoginPresenter loginPresenter = new LoginPresenter(this); //登录
    private UserinfoPresenter userinfoPresenter = new UserinfoPresenter(this);//获取用户的个人信息


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        x.view().inject(this);

        intView(); //控件初始化

    }

    private void intView() {
        buttonHttpStart = findViewById(R.id.btn_http_start);
        textViewText = findViewById(R.id.text);
        buttonHttpRegister = findViewById(R.id.btn_http_regist);
        textRegister = findViewById(R.id.text_register);
        buttonHttpLogin = findViewById(R.id.btn_http_login);
        textLogin = findViewById(R.id.text_login);
        edUserPhone = findViewById(R.id.et_user_login_phone);
        buttonHttpUserinfo = findViewById(R.id.btn_http_userinfo);
        textUserInfo = findViewById(R.id.text_userinfo);

        buttonHttpStart.setOnClickListener(this);
        buttonHttpRegister.setOnClickListener(this);
        buttonHttpLogin.setOnClickListener(this);
        buttonHttpUserinfo.setOnClickListener(this);

    }




    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId())
        {
            case R.id.btn_http_start:
                //开始起请求

//                //请求数据
                testPresenter.onCreate();
                testPresenter.getUserPhone(edUserPhone.getText().toString());
                testPresenter.attachView(mBookView);

                break;

            case R.id.btn_http_regist: //注册
//                registerPresenter.onCreate();
//                registerPresenter.getUserRegister(edUserPhone.getText().toString(),"wuyuxiang",
//                        textViewText.getText().toString(),"1342","bj","1998-06-27",1);
//                registerPresenter.attachView(mRegisterView);

                break;

            case R.id.btn_http_login:
                loginPresenter.onCreate();
                loginPresenter.getUserLogin(edUserPhone.getText().toString(),textViewText.getText().toString());
                loginPresenter.attachView(mLoginView);

                break;

            case R.id.btn_http_userinfo:
                userinfoPresenter.onCreate();
                userinfoPresenter.getUserUserinfo();
                userinfoPresenter.attachView(mLoginView);
                break;

        }
    }

    /**
     * 注册返回结果
     */
    private ITestView mRegisterView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            registerEntity = (RegisterEntity) object;
            textRegister.setText(registerEntity.getData().toString());
            SharedPreferences.Editor  editor = BaseApplication.getEditor();
            editor.putString("userNo",registerEntity.getData().getUserNo());
            editor.putString("token",registerEntity.getData().getToken());
            editor.putString("nickName",registerEntity.getData().getNickName());
            editor.putInt("state",registerEntity.getData().getStatus());
            editor.commit();
        }

        @Override
        public void onError(Object object) {
            textRegister.setText(object.toString());
        }
    };





    /**
     * 获取验证码
     */
    private ITestView mBookView = new ITestView() {

        @Override
        public void onSuccess(Object object) {
            jsonEntity = (JsonEntity) object;
            textViewText.setText(jsonEntity.getData());
        }

        @Override
        public void onError(Object object) {
            textViewText.setText(object.toString());
        }
    };

    /**
     * 登录
     */
    private ITestView mLoginView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            registerEntity = (RegisterEntity) object;
            textLogin.setText(registerEntity.getData().toString());
            BaseApplication.setToken(registerEntity.getData().getToken());
            BaseApplication.setUserNo(registerEntity.getData().getUserNo());
            SharedPreferences.Editor  editor = BaseApplication.getEditor();
            editor.putString("userNo",registerEntity.getData().getUserNo());
            editor.putString("token",registerEntity.getData().getToken());
            editor.putInt("state",registerEntity.getData().getStatus());
            editor.putString("nickName",registerEntity.getData().getNickName());
            editor.commit();
        }

        @Override
        public void onError(Object object) {
            textLogin.setText(object.toString());
        }
    };

    /**
     * 获取用户数据
     */
    private ITestView mUserinfoView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            registerEntity = (RegisterEntity) object;
            textUserInfo.setText(registerEntity.getData().toString());
            BaseApplication.setToken(registerEntity.getData().getToken());
            BaseApplication.setUserNo(registerEntity.getData().getUserNo());
        }

        @Override
        public void onError(Object object) {
            textUserInfo.setText(object.toString());
        }
    };

}
