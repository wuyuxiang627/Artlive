package com.connxun.elinetv.view.user.MyPurse;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.connxun.elinetv.R;
import com.connxun.elinetv.app.BaseApplication;
import com.connxun.elinetv.base.ui.BaseActivity;
import com.connxun.elinetv.entity.JsonEntity;
import com.connxun.elinetv.presenter.Order.OrderPresenter;
import com.connxun.elinetv.util.ToastUtils;
import com.connxun.elinetv.view.user.ITestView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2018\3\9 0009.
 */

@ContentView(R.layout.activity_withdrawals)
public class WithdrawalsActivity extends BaseActivity {
    OrderPresenter orderPresenter = new OrderPresenter(this);
    @ViewInject(R.id.rl_finish)
    RelativeLayout rlFinish;

    @ViewInject(R.id.tv_title)
    TextView tvTitle;

    @ViewInject(R.id.tv_reight)
    TextView tvReiht;

    @ViewInject(R.id.tv_withdrawals_gold_num)
    TextView tvGoldNum;

    @ViewInject(R.id.et_withdrawals_money)
    EditText etMoney;

    @ViewInject(R.id.et_withdrawals_ali_number)
    EditText etAliNumber;

    @ViewInject(R.id.ed_withdrawals_user_name)
    EditText etUserName;

    @ViewInject(R.id.ed_withdrawals_phone)
    EditText etPhone;

    @ViewInject(R.id.btn_withdrawals_apply)
    Button btnApply;

    long withdrawalsMoney = 0;
    private long money;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText(R.string.withdraw);
        tvReiht.setText("提现记录");
        withdrawalsMoney = getIntent().getLongExtra("withdrawals_money",0);
        tvGoldNum.setText(withdrawalsMoney+"");
        long moneyHint = withdrawalsMoney/1000;
         money=  moneyHint * 100;
        etMoney.setHint("最多可提"+money+"元");
    }

    @Override
    public void setOnClick() {
        super.setOnClick();
        rlFinish.setOnClickListener(this);
        btnApply.setOnClickListener(this);
        tvReiht.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()){
            case R.id.rl_finish:
                finish();
                break;
            case R.id.btn_withdrawals_apply:
                if(JudgementCondition()){
                    long textMoney = Long.parseLong(etMoney.getText().toString())*10;
                    String number = etAliNumber.getText().toString();
                    String userName = etUserName.getText().toString();
                    String phone = etPhone.getText().toString();

                    orderPresenter.onCreate();
                    orderPresenter.getUserWithdrawals(textMoney+"",number,userName,phone);

                    orderPresenter.attachView(getUserWithdrawalsView);
                }
                break;
            case R.id.tv_reight:
                StartActivity(new Intent(this,PresentRecordActivity.class));
                break;

        }
    }

    public ITestView getUserWithdrawalsView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            JsonEntity jsonEntity = (JsonEntity) object;
            if(jsonEntity.getCode().equals("200")){
                ToastUtils.showLong("提现成功");
            }
        }

        @Override
        public void onError(Object object) {

        }
    };


    /**
     * 判断提现条件
     * @return
     */
    public boolean JudgementCondition(){
        if(etMoney.getText().toString().equals("")){
            ToastUtils.showLong("请输入提现金额");
            return  false;
        }
        if(money < 100 && money >Long.parseLong(etMoney.getText().toString())){
            ToastUtils.showLong("您的可提金币不足");
            return  false;
        }
        long whitMoney = Long.parseLong(etMoney.getText().toString());
        if(whitMoney % 100 != 0){
            ToastUtils.showLong("提现金额必须大于0的整百金额");
            return  false;
        }
        if(etAliNumber.getText().toString() == null || etAliNumber.getText().toString().equals("")){
            ToastUtils.showLong("请输入支付宝账号");
            return  false;
        }
        if(etUserName.getText().toString() == null || etUserName.getText().toString().equals("")){
            ToastUtils.showLong("请输入收款人姓名");
            return  false;
        }
        if(etPhone.getText().toString() == null || etPhone.getText().toString().equals("")){
            ToastUtils.showLong("请输入手机号");
            return  false;
        }
        if(!BaseApplication.isMobileNO(etPhone.getText().toString())){
            ToastUtils.showLong("手机号格式不对");
            return  false;
        }

        return true;
    }




}
