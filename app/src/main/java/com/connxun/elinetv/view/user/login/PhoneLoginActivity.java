package com.connxun.elinetv.view.user.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.connxun.elinetv.R;
import com.connxun.elinetv.app.BaseApplication;
import com.connxun.elinetv.base.ui.BaseActivity;
import com.connxun.elinetv.entity.JsonEntity;
import com.connxun.elinetv.entity.RegisterEntity;
import com.connxun.elinetv.presenter.user.LoginPresenter;
import com.connxun.elinetv.presenter.user.TestPresenter;
import com.connxun.elinetv.util.ToastUtils;
import com.connxun.elinetv.view.user.ITestView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by connxun-16 on 2018/1/9.
 */

/**
 * 手机号登录/找回密码
 */
@ContentView(R.layout.activity_login_phone)
public class PhoneLoginActivity extends BaseActivity {
    public static PhoneLoginActivity PhoneLoginActivity_instance = null;

    public static final int  RETRIEVEPW_CODE = 1001; //找回密码

    @ViewInject(R.id.rl_phone_login_title)
    RelativeLayout rlFinish;
    @ViewInject(R.id.et_login_phone_phone)
    EditText etPhone;
    @ViewInject(R.id.et_login_phone_verification_code)
    EditText etVerifiCode;
    @ViewInject(R.id.tv_et_login_phone_verification_code_text)
    TextView tvVerifiCodeText;
    @ViewInject(R.id.tv_login_phone_verifi_code)
    TextView tvVerifiCode;
    @ViewInject(R.id.tv_login_title)
    TextView tvTitle;
    @ViewInject(R.id.tv_login_text)
    TextView tvText;
    @ViewInject(R.id.tv_login_retrievepwd_number)
    TextView tvPhoneNumber;
    @ViewInject(R.id.btn_login_confirm)
    Button btnConfirm;

    @ViewInject(R.id.view_login_retrievepwd_number)
    View view;


    TimeCounts time; //倒计时
    JsonEntity jsonEntity;

    boolean byPhoneCode;//判断输入合格


    private String retrievePwdPhone;
    private int layoutCode = 0;



    RegisterEntity registerEntity;
    private LoginPresenter loginPresenter = new LoginPresenter(this); //登录

    private TestPresenter testPresenter = new TestPresenter(this); //测试

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PhoneLoginActivity_instance = this;

        retrievePwdPhone = getIntent().getStringExtra("phone");
        layoutCode = getIntent().getIntExtra("type",0);


        //控件初始化
        inintView();

    }

    private void inintView() {
        switch (layoutCode)
        {
            case 0:
                tvTitle.setText(R.string.phone_login_title);
                tvVerifiCodeText.setVisibility(View.GONE);
                tvText.setVisibility(View.VISIBLE);
                btnConfirm.setText(R.string.confirm);
                etPhone.setVisibility(View.VISIBLE);
                tvPhoneNumber.setVisibility(View.GONE);
                view.setVisibility(View.GONE);
                break;

            case RETRIEVEPW_CODE:
                tvTitle.setText(R.string.security_center_text);
                tvText.setVisibility(View.GONE);
                tvVerifiCodeText.setVisibility(View.VISIBLE);
                btnConfirm.setText(R.string.ssdk_sms_btn_next);
                tvPhoneNumber.setVisibility(View.VISIBLE);
                view.setVisibility(View.VISIBLE);
                String aa = retrievePwdPhone;
                aa = aa.substring(0, 3) + "****" + aa.substring(7, 11);
                tvPhoneNumber.setHint(aa);
                String str="<font color='#999999'>当前绑定手机号：</font><font color='#FF7602'>"+aa+"</font>";
                tvPhoneNumber.setText(Html.fromHtml(str));
                break;

        }



        if(time != null){
            time.cancel();
        }
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
                if(etPhone.getText().length() > 10&&etVerifiCode.getText().length() >3){
                    btnConfirm.setBackgroundResource(R.drawable.btn_orange_twenty_one);
                    byPhoneCode = true;
                }else {
                    byPhoneCode = false;
                    btnConfirm.setBackgroundResource(R.drawable.btn_gray_twenty_one);
                }
                retrievePwdPhone = etPhone.getText().toString();


            }
        });

        //输入验证码
        etVerifiCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if((etPhone.getText().length() > 10&&etVerifiCode.getText().length() >3)||(etVerifiCode.getText().length() >3)){
                    byPhoneCode = true;
                    btnConfirm.setBackgroundResource(R.drawable.btn_orange_twenty_one);
                }else {
                    byPhoneCode = false;
                    btnConfirm.setBackgroundResource(R.drawable.btn_gray_twenty_one);
                }
            }
        });

        //获取验证码
        tvVerifiCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(BaseApplication.isMobileNO(retrievePwdPhone)){
                    time = new TimeCounts(60000, 1000);
                    time.start();
                    testPresenter.onCreate();
                    testPresenter.getUserPhone(retrievePwdPhone);
                    testPresenter.attachView(mBookView);
                }else {
                    ToastUtils.showLong("请填写正确的手机号");
                }

            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(byPhoneCode){
                    //手机验证码登录
                    loginPresenter.onCreate();
                    loginPresenter.getUserLogin(retrievePwdPhone,etVerifiCode.getText().toString());
                    loginPresenter.attachView(mLoginView);
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


    /**
     * 倒计时
     */
    class TimeCounts extends CountDownTimer {
        public TimeCounts(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            tvVerifiCode.setText("重新发送");
            tvVerifiCode.setVisibility(View.VISIBLE);
            tvVerifiCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    time.start();
                }
            });
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            tvVerifiCode.setClickable(false);//防止重复点击
            tvVerifiCode.setClickable(false);
            tvVerifiCode.setText("重新发送("+millisUntilFinished / 1000 + "s)");
        }
    }

    /**
     * 获取验证码
     */
    private ITestView mBookView = new ITestView() {

        @Override
        public void onSuccess(Object object) {
            jsonEntity = (JsonEntity) object;
            etVerifiCode.setText(jsonEntity.getData());
        }

        @Override
        public void onError(Object object) {
            ToastUtils.showLong(object.toString());
        }
    };


    /**
     * 登录
     */
    private ITestView mLoginView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            registerEntity = (RegisterEntity) object;
            switch (layoutCode)
            {
                case 0:
                    switch (registerEntity.getData().getStatus())
                    {
                        case 0: //正常登录
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
                            break;
                        case 1: //账号被封

                            break;
                        case 2: //未注册
                            Intent intent = new Intent(getBaseContext(),SetPwdActivity.class);
                            intent.putExtra("phone",etPhone.getText().toString());
                            intent.putExtra("code",etVerifiCode.getText().toString());
                            startActivity(intent);
                            break;

                    }

                    break;
                case RETRIEVEPW_CODE:
                    Intent intent = new Intent(getBaseContext(),SetPwdActivity.class);
                    intent.putExtra("type",layoutCode);
                    intent.putExtra("phone",retrievePwdPhone);
                    intent.putExtra("code",etVerifiCode.getText().toString());
                    startActivity(intent);
                    break;
            }

        }

        @Override
        public void onError(Object object) {
            ToastUtils.showLong(object.toString());
        }

    };



}
