package com.connxun.elinetv.view.Setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;

import com.connxun.elinetv.R;
import com.connxun.elinetv.base.ui.BaseActivity;
import com.connxun.elinetv.view.user.Account.IllegalQueryActivity;
import com.connxun.elinetv.view.user.Account.RetrievePwdActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by connxun-16 on 2018/1/10.
 */

/**
 * 账号与安全
 */
@ContentView(R.layout.activity_account_seccurity)
public class AccountSeccurityActivity extends BaseActivity{

    @ViewInject(R.id.rl_account_seccurity_finish)
    RelativeLayout rlFinish;
    @ViewInject(R.id.rl_setting_forget_pwd)
    RelativeLayout rlForgetPwd;
    @ViewInject(R.id.rl_setting_lllegal_query)
    RelativeLayout rl_setting_lllegal_query;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setOnClick() {
        super.setOnClick();
        rlFinish.setOnClickListener(this);
        rlForgetPwd.setOnClickListener(this);
        rl_setting_lllegal_query.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId())
        {
            case R.id.rl_account_seccurity_finish:
                finish();
                break;
            case R.id.rl_setting_forget_pwd://忘记密码
                startActivity(new Intent(this, RetrievePwdActivity.class));
                break;
            case R.id.rl_setting_lllegal_query://违规查询
                startActivity(new Intent(this, IllegalQueryActivity.class));
                break;
        }
    }
}
