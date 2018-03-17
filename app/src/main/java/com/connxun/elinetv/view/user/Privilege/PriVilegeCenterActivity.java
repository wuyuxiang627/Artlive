package com.connxun.elinetv.view.user.Privilege;

/**
 * Created by Administrator on 2018\3\7 0007.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;

import com.connxun.elinetv.R;
import com.connxun.elinetv.base.ui.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * 特权中心
 */
@ContentView(R.layout.activity_privilege_center)
public class PriVilegeCenterActivity extends BaseActivity{

    @ViewInject(R.id.rl_privilege_center_finish)
    RelativeLayout rlFinish;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            case R.id.rl_privilege_center_finish:
                finish();
                break;
        }

    }
}
