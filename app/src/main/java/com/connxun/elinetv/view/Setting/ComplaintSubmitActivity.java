package com.connxun.elinetv.view.Setting;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.connxun.elinetv.R;
import com.connxun.elinetv.base.ui.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_complaint_submit)
public class ComplaintSubmitActivity extends BaseActivity {



    @ViewInject(R.id.rl_complaint_submit)
    RelativeLayout rl_complaint_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initview();

    }

    private void initview() {
        rl_complaint_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

    switch (view.getId()){
        case R.id.rl_complaint_submit:
            finish();
            break;
    }
    }
}
