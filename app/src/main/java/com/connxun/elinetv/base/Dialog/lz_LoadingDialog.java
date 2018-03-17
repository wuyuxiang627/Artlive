package com.connxun.elinetv.base.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.widget.TextView;

import com.connxun.elinetv.R;


/**
 * 自定义加载进度对话框
 * Created by Administrator on 2016-10-28.
 */

public class lz_LoadingDialog extends Dialog {
    private TextView tv_text;

    public lz_LoadingDialog(Context context) {
        super(context);
        /**设置对话框背景透明*/
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.wait_dialog);
        tv_text = (TextView) findViewById(R.id.tv_text);
        setCanceledOnTouchOutside(false);
    }

    /**
     * 为加载进度个对话框设置不同的提示消息
     *
     * @param message 给用户展示的提示信息
     * @return build模式设计，可以链式调用
     */
    public lz_LoadingDialog setMessage(String message) {
        tv_text.setText(message);
        return this;
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
