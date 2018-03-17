package com.connxun.elinetv.view.user.MyChat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;

import com.connxun.elinetv.R;
import com.connxun.elinetv.base.ui.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2018\3\5 0005.
 */

/**
 * 我的聊天
 */
@ContentView(R.layout.activity_my_chat)
public class MyChatActivity extends BaseActivity {
    @ViewInject(R.id.rl_my_chat_finish)
    RelativeLayout rlFinish; //返回


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
            case R.id.rl_my_chat_finish:
                finish();
                break;
        }

    }
}
