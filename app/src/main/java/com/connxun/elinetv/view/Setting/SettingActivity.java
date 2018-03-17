package com.connxun.elinetv.view.Setting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.connxun.elinetv.R;
import com.connxun.elinetv.app.BaseApplication;
import com.connxun.elinetv.base.Dialog.ActionSheetDialog;
import com.connxun.elinetv.base.ui.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by connxun-16 on 2018/1/10.
 */

/**
 * 设置
 */
@ContentView(R.layout.activity_setting)
public class SettingActivity extends BaseActivity{

    @ViewInject(R.id.rl_setting_finish)
    RelativeLayout rlSettingFinish;
    @ViewInject(R.id.rl_setting_account_seccurity)
    RelativeLayout rlAccountSeccurity; //账号与安全
    @ViewInject(R.id.rl_setting_sweep)
    RelativeLayout rlSweep;

    @ViewInject(R.id.rl_setting_about_us)
    RelativeLayout rlAboutUs; //关于艺线
    @ViewInject(R.id.rl_setting_contact)
    RelativeLayout rlContact;



    @ViewInject(R.id.il_setting)
    LinearLayout ilSetting;
    @ViewInject(R.id.btn_user_out)
    Button btnUserOut;

    //类型
    int type;
    private ActionSheetDialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getIntExtra("type",2);
        initView();
    }

    private void initView() {
        if(type == 1){
            rlSweep.setVisibility(View.VISIBLE);
            ilSetting.setVisibility(View.GONE);
            btnUserOut.setVisibility(View.GONE);
        }else {
            rlSweep.setVisibility(View.GONE);
            ilSetting.setVisibility(View.VISIBLE);
            btnUserOut.setVisibility(View.VISIBLE);
        }
    }

    public void setOnClick() {

        rlSettingFinish.setOnClickListener(this);
        rlAccountSeccurity.setOnClickListener(this);
        btnUserOut.setOnClickListener(this);
        rlAboutUs.setOnClickListener(this);
        rlContact.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId())
        {
            case R.id.rl_setting_finish:
                finish();
                break;

            case R.id.rl_setting_account_seccurity:  //账号与安全
                startActivity(new Intent(this,AccountSeccurityActivity.class));
                break;

            case  R.id.btn_user_out:
                setDialogFinish();
                break;

            case R.id.rl_setting_about_us: //关于艺线
                startActivity(new Intent(this,AboutUsActivity.class));
                break;

            case R.id.rl_setting_contact: //联系我们
                startActivity(new Intent(this,ContactUsActivity.class));
                break;

        }
    }

    /**
     * 是否放弃视频
     */
    public void setDialogFinish(){
        mDialog = new ActionSheetDialog(this).builder();
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(true);
//        mDialog.setTitle("是否退出并更换账号");
        mDialog.addSheetItem("退出登录", ActionSheetDialog.SheetItemColor.Red, new ActionSheetDialog.OnSheetItemClickListener() {

            @Override
            public void onClick(int which) {
                SharedPreferences.Editor edit = BaseApplication.getUserSp().edit();
                edit.clear();
                edit.commit();
                finish();
            }
        }).show();
//        mDialog.addSheetItem("否", ActionSheetDialog.SheetItemColor.Red, new ActionSheetDialog.OnSheetItemClickListener() {
//            @Override
//            public void onClick(int which) {
//
//            }
//        }).show();
    }



}
