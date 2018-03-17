package com.connxun.elinetv.observer;

import android.app.Activity;
import android.os.Bundle;


import java.util.ArrayList;

/**
 * 广播Demo 类
 * Created by zhangran on 2017/8/28.
 */

public class TestActivity extends Activity implements INotifyListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerListener();//注册
    }

    /**
     * 其他类调用方法
     */
    private void used(){
        NotifyListenerManager.getInstance().notifyListener(TestActivity.class);//不传参数通知
       NotifyListenerManager.getInstance().notifyListener(new NotifyObject(1), TestActivity.class);//传参数通知
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterListener();//取消注册
    }

    @Override
    public void registerListener() {
        NotifyListenerManager.getInstance().registerListener(this);
    }

    @Override
    public void unRegisterListener() {
        NotifyListenerManager.getInstance().unRegisterListener(this);
    }

    /**
     * 接受通知回调方法
     * @param obj
     */
    @Override
    public void notifyUpdate(NotifyObject obj) {
        if(obj.what == 1){
            ArrayList list = (ArrayList) obj.list;

            // dothing
        }

        if(obj.str.equals("")){
            // dothing
        }
    }
}
