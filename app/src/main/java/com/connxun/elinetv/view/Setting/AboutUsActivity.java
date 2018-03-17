package com.connxun.elinetv.view.Setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;

import com.connxun.elinetv.R;
import com.connxun.elinetv.base.ui.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2018\3\8 0008.
 */

/**
 * 关于艺线
 */
@ContentView(R.layout.activity_about_us)
public class AboutUsActivity extends BaseActivity {

    @ViewInject(R.id.rl_about_us_finish)
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
            case R.id.rl_about_us_finish:
                finish();
                break;
        }


    }
}
