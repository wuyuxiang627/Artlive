package com.connxun.elinetv.view.Setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.connxun.elinetv.R;
import com.connxun.elinetv.base.ui.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2018\3\8 0008.
 */

/**
 * 联系我们
 */
@ContentView(R.layout.activity_contact_us)
public class ContactUsActivity extends BaseActivity {

    @ViewInject(R.id.rl_finish)
    RelativeLayout rlFinish;

    @ViewInject(R.id.tv_title)
    TextView tvTitle;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

    }

    private void initView() {
        tvTitle.setText(R.string.setting_contact);
        rlFinish.setOnClickListener(this);

    }

    @Override
    public void setOnClick() {
        super.setOnClick();
        rlFinish.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId())
        {
            case R.id.rl_finish:
                finish();
                break;
        }


    }
}
