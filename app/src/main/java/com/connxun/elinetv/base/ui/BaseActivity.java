package com.connxun.elinetv.base.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.connxun.elinetv.R;
import com.connxun.elinetv.app.BaseApplication;
import com.connxun.elinetv.base.Dialog.lz_LoadingDialog;
import com.connxun.elinetv.observer.INotifyListener;
import com.connxun.elinetv.observer.NotifyListenerManager;
import com.connxun.elinetv.observer.NotifyObject;
import com.connxun.elinetv.view.user.login.LoginActivity;

import org.xutils.x;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity的基类
 */
public class BaseActivity extends FragmentActivity implements View.OnClickListener,INotifyListener {

    private static final String TAG = "BaseActivity";

    private static Handler handler;

    private lz_LoadingDialog mWaitDlg; //加载框
    public static BaseActivity context;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        //隐藏状态栏
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
//            local LayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
//        }


        x.view().inject(this);
        context = this;
        setOnClick();
        registerListener();

    }
    public final Context getContext(){
        return this;
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= 23
                && getApplicationInfo().targetSdkVersion >= 23) {
            if (isNeedCheck) {
                checkPermissions(needPermissions);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //关闭等待框
        dismissWaitingDialog();
    }
    public void setOnClick(){

    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterListener();
    }


    /**
     * 跳转指定activity
     *
     * @param intent
     */
    public void isLogin_startActivity(Intent intent) {
        if (isLogin()) {
            //跳转到登录界面
            startActivity(new Intent(BaseApplication.getContext(), LoginActivity.class));
            overridePendingTransition(R.anim.left_enteranim,R.anim.right_exitanim);
        } else {
            startActivity(intent);
            overridePendingTransition(R.anim.left_enteranim,R.anim.right_exitanim);
        }
    }

    public void StartActivity(Intent intent){
        startActivity(intent);
        overridePendingTransition(R.anim.left_enteranim,R.anim.right_exitanim);
    }



    /**
     * 跳转指定activity-携带请求码
     *
     * @param intent      intent对象
     * @param requestcode 返回值
     */
    public void isLogin_startActivityForResult(Intent intent, int requestcode) {
        if (isLogin()) {
            //跳转登录界面

        } else {
            startActivityForResult(intent, requestcode);
            overridePendingTransition(R.anim.left_enteranim,R.anim.right_exitanim);
        }
    }


    /**
     * 是否已经登录
     *
     * @return
     */
    private boolean isLogin() {
       String userNo =  BaseApplication.getUserSp().getString("userNo","");
       String token = BaseApplication.getUserSp().getString("token","");
       if(TextUtils.isEmpty(userNo) && TextUtils.isEmpty(token)){
           return  true;
       }else {
           return false;

       }

    }


    /**
     * 显示加载框
     */
    public void showDialog() {
        if (isDialogShowing()) {
            return;

        } else {
            if (!isNetworkAvailable(this)) {
                Toast.makeText(this, "请检查您的网络", Toast.LENGTH_SHORT).show();
            } else {
                mWaitDlg = new lz_LoadingDialog(this);
                mWaitDlg.show();
            }
        }
    }


    /**
     * 是否正显示加载框
     *
     * @return
     */
    public boolean isDialogShowing() {
        return mWaitDlg != null && mWaitDlg.isShowing();
    }


    /**
     * @return void
     * @Title: dismissWaitingDialog
     * @Description: 关闭等待Dialog
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


    @Override
    public void onClick(View view) {

    }

    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
    };

    public static final int PERMISSON_REQUESTCODE = 0;

    /**
     * 判断是否需要检测，防止不停的弹框
     */
    public boolean isNeedCheck = true;

    /**
     *
     * @param permissions
     * @since 2.5.0
     *
     */
    private void checkPermissions(String... permissions) {
        try {
            if (Build.VERSION.SDK_INT >= 23
                    && getApplicationInfo().targetSdkVersion >= 23) {
                List<String> needRequestPermissonList = findDeniedPermissions(permissions);
                if (null != needRequestPermissonList
                        && needRequestPermissonList.size() > 0) {
                    String[] array = needRequestPermissonList.toArray(new String[needRequestPermissonList.size()]);
                    Method method = getClass().getMethod("requestPermissions", new Class[]{String[].class,
                            int.class});

                    method.invoke(this, array, PERMISSON_REQUESTCODE);
                }
            }
        } catch (Throwable e) {
        }
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     * @since 2.5.0
     *
     */
    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<String>();
        if (Build.VERSION.SDK_INT >= 23
                && getApplicationInfo().targetSdkVersion >= 23){
            try {
                for (String perm : permissions) {
                    Method checkSelfMethod = getClass().getMethod("checkSelfPermission", String.class);
                    Method shouldShowRequestPermissionRationaleMethod = getClass().getMethod("shouldShowRequestPermissionRationale",
                            String.class);
                    if ((Integer)checkSelfMethod.invoke(this, perm) != PackageManager.PERMISSION_GRANTED
                            || (Boolean)shouldShowRequestPermissionRationaleMethod.invoke(this, perm)) {
                        needRequestPermissonList.add(perm);
                    }
                }
            } catch (Throwable e) {

            }
        }
        return needRequestPermissonList;
    }

    /**
     * 检测是否所有的权限都已经授权
     * @param grantResults
     * @return
     * @since 2.5.0
     *
     */
    public boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @TargetApi(23)
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] paramArrayOfInt) {
        if (requestCode == PERMISSON_REQUESTCODE) {
            if (!verifyPermissions(paramArrayOfInt)) {
                showMissingPermissionDialog();
                isNeedCheck = false;
            }
        }
    }

    /**
     * 显示提示信息
     *
     * @since 2.5.0
     *
     */
    public void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.notifyTitle);
        builder.setMessage(R.string.notifyMsg);

        // 拒绝, 退出应用
        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

        builder.setPositiveButton(R.string.setting,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                });

        builder.setCancelable(false);

        builder.show();
    }

    /**
     *  启动应用的设置
     *
     * @since 2.5.0
     *
     */
    private void startAppSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    protected final Handler getHandler() {
        if (handler == null) {
            handler = new Handler(getMainLooper());
        }
        return handler;
    }




    @Override
    public void notifyUpdate(NotifyObject obj) {

    }

    @Override
    public void registerListener() {
        NotifyListenerManager.getInstance().registerListener(this);
    }

    @Override
    public void unRegisterListener() {
        NotifyListenerManager.getInstance().unRegisterListener(this);
    }
}
