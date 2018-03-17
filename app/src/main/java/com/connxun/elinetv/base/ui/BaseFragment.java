package com.connxun.elinetv.base.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.connxun.elinetv.R;
import com.connxun.elinetv.app.BaseApplication;
import com.connxun.elinetv.base.Dialog.lz_LoadingDialog;
import com.connxun.elinetv.view.user.login.LoginActivity;

import org.xutils.x;

/**
 * Fragment的基类
 */

public class BaseFragment extends Fragment implements View.OnClickListener{
    static View view;
    static int layouts;

    private static final String TAG = "BaseFragment";

    private lz_LoadingDialog mWaitDlg; //加载框


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static void setView(int layout){
        layouts =  layout;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(layouts,null);
        x.view().inject(this, view);
        setOnClick();
        return view;
    }

    /**
     * 点击事件
     */
    public void setOnClick(){

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 跳转指定activity
     *
     * @param intent
     */
    public void isLogin_startActivity(Intent intent) {
        if (isLogin()) {
            //跳转到登录界面
            startActivity(new Intent(getContext(), LoginActivity.class));
            getActivity().overridePendingTransition(R.anim.left_enteranim,R.anim.right_exitanim);
        } else {
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.left_enteranim,R.anim.right_exitanim);
        }
    }

    public void StartActivity(Intent intent){
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.left_enteranim,R.anim.right_exitanim);
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
     * 跳转指定activity-携带请求码
     *
     * @param intent      intent对象
     * @param requestcode 返回值
     */
    public void isLogin_startActivityForResult(Intent intent, int requestcode) {
        if (isLogin()) {
            //跳转登录界面
            startActivity(new Intent(getContext(), LoginActivity.class));
        } else {
            startActivityForResult(intent, requestcode);
        }
    }

//    /**
//     * 是否登录
//     *
//     * @return
//     */
//    private boolean isLogin() {
//        if(TextUtils.isEmpty(BaseApplication.getUserNo())){
//            return false;
//        }
//        return true;
//    }


    /**
     * 显示加载框
     */
    public void showDialog() {
        if (isDialogShowing()) {
            return;
        } else {
            if (!isNetworkAvailable(getActivity())) {
                Toast.makeText(BaseApplication.getContext(), "请检查您的网络", Toast.LENGTH_SHORT).show();
            } else {
                mWaitDlg = new lz_LoadingDialog(getActivity());
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
}
