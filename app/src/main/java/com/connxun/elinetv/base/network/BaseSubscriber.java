package com.connxun.elinetv.base.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.connxun.elinetv.app.BaseApplication;
import com.connxun.elinetv.base.Dialog.lz_LoadingDialog;
import com.connxun.elinetv.base.ui.BaseActivity;
import com.connxun.elinetv.util.ToastUtils;

import rx.Subscriber;

/**
 * Created by connxun-16 on 2018/1/30.
 */

public abstract class BaseSubscriber<T> extends Subscriber<T> {
    private BaseActivity context;
    private lz_LoadingDialog mWaitDlg = null;

    public BaseSubscriber(BaseActivity context) {
        this.context = context;
    }

    @Override
    public void onStart() {
        super.onStart();
        try{
            if(!isNetworkAvailable(context)){
                ToastUtils.showLong("当前网络不可用,请检查您的网络!!");
                // 一定好主动调用下面这一句
                onCompleted();
                return;
            }

            if(BaseApplication.isDialogShow){
                if(BaseApplication.isDialogShow){
                    startWaitingDialog(context);
                }
            }

            BaseApplication.isDialogShow = false;

        }catch (Exception e){

        }

    }

    @Override
    public void onCompleted() {
        try{
            dismissWaitingDialog();
        }catch (Exception e){

        }

    }

    @Override
    public void onError(Throwable e) {
        try{
            dismissWaitingDialog();
        }catch (Exception ex){
            dismissWaitingDialog();
        }

    }

    @Override
    public void onNext(T t) {
    }

    /**
     * 弹出加载框
     * @param context
     */
    public void startWaitingDialog(Context context) {
        if (mWaitDlg == null) {
            mWaitDlg = new lz_LoadingDialog(context).setMessage("正在加载...");
        }
        if (!mWaitDlg.isShowing()) {
            mWaitDlg.show();
        }
    }

    /**
     * 销毁加载框
     */
    public void dismissWaitingDialog() {
        if (mWaitDlg != null && mWaitDlg.isShowing()) {
            mWaitDlg.dismiss();
        }
    }

    //判断是否连接了网络
    public static boolean isNetworkAvailable(Context context) {


        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
        } else {
            //如果仅仅是用来判断网络连接
            //则可以使用 cm.getActiveNetworkInfo().isAvailable();
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;

    }
}
