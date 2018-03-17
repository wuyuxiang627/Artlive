package com.connxun.elinetv.view.Setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.connxun.elinetv.R;
import com.connxun.elinetv.base.ui.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_complaint)
public class ComplaintActivity extends BaseActivity {



    @ViewInject(R.id.rl_illegal_complaint)
    RelativeLayout rl_illegal_complaint;

    @ViewInject(R.id.btn_complaint)
    Button btn_complaint;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initview();


    }

    private void initview() {
       rl_illegal_complaint.setOnClickListener(this);
       btn_complaint.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        Intent intent = null;
    switch (view.getId()){
        case R.id.rl_illegal_complaint:
            finish();
            break;
        case R.id.btn_complaint:
           startActivity(new Intent(this,ComplaintSubmitActivity.class));
            break;
    }

    }
}
