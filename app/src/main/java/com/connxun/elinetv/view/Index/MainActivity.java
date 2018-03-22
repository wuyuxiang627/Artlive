package com.connxun.elinetv.view.Index;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.connxun.elinetv.R;
import com.connxun.elinetv.base.ui.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, HomePageActivity.class));
                finish();
            }
        }, 1000);
    }




    //是否第一次进入
    public boolean firstNext(){

        return false;
    }



}
