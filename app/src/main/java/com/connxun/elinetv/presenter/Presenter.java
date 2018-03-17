package com.connxun.elinetv.presenter;


import android.content.Intent;

import com.connxun.elinetv.view.View;

/**
 * Created by win764-1 on 2016/12/12.
 */

public interface Presenter {


    void onCreate();

    void onStop();

    void attachView(View view);

    void attachIncomingIntent(Intent intent);

}
