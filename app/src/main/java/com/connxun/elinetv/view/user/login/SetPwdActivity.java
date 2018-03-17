package com.connxun.elinetv.view.user.login;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.connxun.elinetv.R;
import com.connxun.elinetv.app.BaseApplication;
import com.connxun.elinetv.base.ui.BaseActivity;
import com.connxun.elinetv.base.util.LogUtil;
import com.connxun.elinetv.entity.RegisterEntity;
import com.connxun.elinetv.presenter.user.RegisterPresenter;
import com.connxun.elinetv.util.ToastUtils;
import com.connxun.elinetv.view.user.Account.RetrievePwdActivity;
import com.connxun.elinetv.view.user.ITestView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.regex.Pattern;

/**
 * Created by connxun-16 on 2018/1/9.
 */

@ContentView(R.layout.actiivty_set_pwd)
public class SetPwdActivity extends BaseActivity {

    public static final int  RETRIEVEPW_CODE = 1001; //找回密码

    @ViewInject(R.id.et_login_set_pwd)
    EditText etSetPwd;
    @ViewInject(R.id.et_login_set_pwd_two)
    EditText etSetPwdTwo;
    @ViewInject(R.id.btn_login_set_pwd_confirm)
    Button btnSetPwdConfirm;
    @ViewInject(R.id.rl_login_set_pwd_title)
    RelativeLayout rlFinish;
    @ViewInject(R.id.tv_login_set_pwd_title)
    TextView tvTitle;
    @ViewInject(R.id.rl_login_set_pwd_two)
    RelativeLayout rlSetPwdTwo;
    @ViewInject(R.id.rl_login_set_pwd)
    RelativeLayout rlSetPwd;




    boolean byPwd;//密码是否符合格式
    String phone;
    String code;
    RegisterEntity registerEntity;

    private RegisterPresenter registerPresenter = new RegisterPresenter(this);//注册
    private int layoutCode = 0 ;//页面模式;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutCode = getIntent().getIntExtra("type",0);
        phone = getIntent().getStringExtra("phone");
        code = getIntent().getStringExtra("code");

        inintView();

        setOnClickLister();
    }

    private void inintView() {
        switch (layoutCode) {
            case 0:
                tvTitle.setText(R.string.set_Pwd_title);
                rlSetPwdTwo.setVisibility(View.GONE);
                break;

            case RETRIEVEPW_CODE:
                tvTitle.setText(R.string.security_center_text);
                rlSetPwdTwo.setVisibility(View.VISIBLE);
                break;

        }

    }




    private void setOnClickLister() {
        rlFinish.setOnClickListener(this);
        btnSetPwdConfirm.setOnClickListener(this);
        //输入密码
        etSetPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(layoutCode == 0){
                    if(matchPwd(etSetPwd.getText().toString())){
                        btnSetPwdConfirm.setBackgroundResource(R.drawable.btn_orange_twenty_one);
                        byPwd = true;
                    }else {
                        btnSetPwdConfirm.setBackgroundResource(R.drawable.btn_gray_twenty_one);
                        byPwd = false;
                    }
                }else {
                    if(matchPwd(etSetPwd.getText().toString())&&matchPwd(etSetPwdTwo.getText().toString())){
                        btnSetPwdConfirm.setBackgroundResource(R.drawable.btn_orange_twenty_one);
                        byPwd = true;
                    }else {
                        btnSetPwdConfirm.setBackgroundResource(R.drawable.btn_gray_twenty_one);
                        byPwd = false;
                    }

                }

            }
        });
        etSetPwdTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(layoutCode == 0){
                    if(matchPwd(etSetPwd.getText().toString())){
                        btnSetPwdConfirm.setBackgroundResource(R.drawable.btn_orange_twenty_one);
                        byPwd = true;
                    }else {
                        btnSetPwdConfirm.setBackgroundResource(R.drawable.btn_gray_twenty_one);
                        byPwd = false;
                    }
                }else {
                    if(matchPwd(etSetPwd.getText().toString())&&matchPwd(etSetPwdTwo.getText().toString())){
                        btnSetPwdConfirm.setBackgroundResource(R.drawable.btn_orange_twenty_one);
                        byPwd = true;
                    }else {
                        btnSetPwdConfirm.setBackgroundResource(R.drawable.btn_gray_twenty_one);
                        byPwd = false;
                    }

                }

            }
        });
    }

    /**
     * 判断密码
     * @param pwd
     * @return
     */
    public boolean matchPwd(String pwd){
        boolean ispwd = false;
        Pattern z1_ = Pattern.compile("^(?=.*?[a-z])(?=.*?[0-9])[a-zA-Z0-9_]{8,20}$");
        //判断是否都成立，都包含返回true
       return ispwd = z1_.matcher(pwd).matches();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId())
        {
            case R.id.rl_login_set_pwd_title:
                finish();
                break;
            case R.id.btn_login_set_pwd_confirm:

                if(byPwd){
                    if(layoutCode == 0){
                        // 开始注册
                        registerPresenter.onCreate();
                        registerPresenter.getUserRegister(phone,
                                code,etSetPwd.getText().toString());
                        registerPresenter.attachView(mRegisterView);
                    }else {
                        if(!etSetPwd.getText().toString().equals(etSetPwdTwo.getText().toString())){
                           ToastUtils.showLong("密码输入不一致,请重新输入!");
                           return;
                        }
                        registerPresenter.onCreate();
                        registerPresenter.getUserUpdatePWD(phone,
                                code,etSetPwd.getText().toString());
                        registerPresenter.attachView(mUserUpdatePWD);


                    }

                }
                break;
        }
    }


    private ITestView mUserUpdatePWD = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            ToastUtils.showLong("修改成功");
            finish();
            PhoneLoginActivity.PhoneLoginActivity_instance.finish();
            RetrievePwdActivity.retrievePwdActivity_instance.finish();
        }

        @Override
        public void onError(Object object) {
            ToastUtils.showLong("修改失败");
        }
    };


    /**
     * 注册返回结果
     */
    private ITestView mRegisterView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            registerEntity = (RegisterEntity) object;
            LogUtil.Log("SetPwdActivity",LogUtil.LOG_E,registerEntity.toString());
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
            editer.commit();
            finish();

            LoginActivity.loginActivity_instance.finish();
            PhoneLoginActivity.PhoneLoginActivity_instance.finish();
        }

        @Override
        public void onError(Object object) {
            ToastUtils.showLong(object.toString());
        }
    };


}
