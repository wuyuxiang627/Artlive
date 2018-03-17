package com.connxun.elinetv.view.user.Account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.connxun.elinetv.R;
import com.connxun.elinetv.app.BaseApplication;
import com.connxun.elinetv.base.ui.BaseActivity;
import com.connxun.elinetv.entity.JsonEntity;
import com.connxun.elinetv.entity.RegisterEntity;
import com.connxun.elinetv.presenter.user.LoginPresenter;
import com.connxun.elinetv.presenter.user.TestPresenter;
import com.connxun.elinetv.util.ToastUtils;
import com.connxun.elinetv.view.user.ITestView;
import com.connxun.elinetv.view.user.login.PhoneLoginActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by connxun-16 on 2018/1/9.
 */

/**
 * 手机号登录
 */
@ContentView(R.layout.activity_retrieve_pwd)
public class RetrievePwdActivity extends BaseActivity {
    public static RetrievePwdActivity retrievePwdActivity_instance = null;
    public static final int  RETRIEVEPW_CODE = 1001;
    @ViewInject(R.id.rl_retrieve_pwd_finish)
    RelativeLayout rlFinish;
    @ViewInject(R.id.et_retrieve_pwd_phone)
    EditText etPhone;
    @ViewInject(R.id.btn_retrieve_pwd_confirm)
    Button btnConfirm;

    JsonEntity jsonEntity;

    boolean byPhoneCode;//判断输入合格

    RegisterEntity registerEntity;
    private LoginPresenter loginPresenter = new LoginPresenter(this); //登录

    private TestPresenter testPresenter = new TestPresenter(this); //测试

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrievePwdActivity_instance = this;
        //控件初始化
        inintView();

    }

    private void inintView() {
    }

    /**
     *监听
     */
    public  void setOnClick() {
        //输入手机号
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(etPhone.getText().length() > 10){
                    btnConfirm.setBackgroundResource(R.drawable.btn_orange_twenty_one);
                    byPhoneCode = true;
                }else {
                    byPhoneCode = false;
                    btnConfirm.setBackgroundResource(R.drawable.btn_gray_twenty_one);
                }
            }
        });


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(byPhoneCode && BaseApplication.isMobileNO(etPhone.getText().toString())){



                    loginPresenter.onCreate();
                    loginPresenter.getpdatePwdcaptch(etPhone.getText().toString());
                    loginPresenter.attachView(mLoginView);
                }else {
                    ToastUtils.showLong("请输入正确的电话号码");
                }
            }
        });
        rlFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public ITestView mLoginView  = new ITestView() {

        @Override
        public void onSuccess(Object object) {
            Intent intent = new Intent(RetrievePwdActivity.this,PhoneLoginActivity.class);
            intent.putExtra("phone",etPhone.getText().toString());
            intent.putExtra("type",RETRIEVEPW_CODE);

            startActivity(intent);
        }

        @Override
        public void onError(Object object) {
            ToastUtils.showLong(object.toString());

        }
    };



}
