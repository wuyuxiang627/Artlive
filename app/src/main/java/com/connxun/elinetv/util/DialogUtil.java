package com.connxun.elinetv.util;

import android.content.Context;

import com.connxun.elinetv.base.Dialog.lz_LoadingDialog;

/**
 * Created by connxun-16 on 2018/2/7.
 */

public class DialogUtil {

    private static  lz_LoadingDialog mWaitDlg = null;

    /**
     * 弹出加载框
     * @param context
     */
    public static  void startWaitingDialog(Context context,String message) {
        if (mWaitDlg == null) {
            mWaitDlg = new lz_LoadingDialog(context).setMessage(message);
        }
        if (!mWaitDlg.isShowing()) {
            mWaitDlg.show();
        }
    }

    /**
     * 销毁加载框
     */
    public static void dismissWaitingDialog() {
        if (mWaitDlg != null && mWaitDlg.isShowing()) {
            mWaitDlg.dismiss();
            mWaitDlg = null;
        }
    }


}
