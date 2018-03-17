package com.connxun.elinetv.view.user.MyPurse;

/**
 * Created by connxun-16 on 2018/3/2.
 */

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.connxun.elinetv.R;
import com.connxun.elinetv.adapter.Mind.MyPurseBoldAdapter;
import com.connxun.elinetv.app.BaseApplication;
import com.connxun.elinetv.base.ALiPay.PayResult;
import com.connxun.elinetv.base.Dialog.ActionSheetDialog;
import com.connxun.elinetv.base.ui.BaseActivity;
import com.connxun.elinetv.entity.Entity;
import com.connxun.elinetv.entity.EntityObject;
import com.connxun.elinetv.entity.JsonEntity;
import com.connxun.elinetv.entity.order.Order;
import com.connxun.elinetv.entity.order.UserVC;
import com.connxun.elinetv.entity.order.VC;
import com.connxun.elinetv.entity.order.WeChatEntity;
import com.connxun.elinetv.observer.NotifyObject;
import com.connxun.elinetv.presenter.Order.OrderPresenter;
import com.connxun.elinetv.util.ToastUtils;
import com.connxun.elinetv.view.user.ITestView;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * 我的钱包
 */
@ContentView(R.layout.activity_mypurse)
public class MyPurseActivity extends BaseActivity{
    private OrderPresenter presenter = new OrderPresenter(this);

    @ViewInject(R.id.rlv_myPurse_bold)
    RecyclerView rlvMyPurseBold; //金币列表
    @ViewInject(R.id.tv_available_gold_num)
    TextView tvGoldNum;
    @ViewInject(R.id.rl_my_pursen_finish)
    RelativeLayout rlFinish;
    @ViewInject(R.id.tv_mind_reflect)
    TextView tvReflect;


    private Entity<VC> vcEntity; //金币列表
    private EntityObject<Order> orderEntityObject; //订单消息
    private JsonEntity jsonEntity;
    private VC vc;
    GridLayoutManager gridLayoutManager = null;
    MyPurseBoldAdapter myPurseBoldAdapter;

    private static final int SDK_PAY_FLAG = 1;

    private static int PayMentMethod = 1; //支付方式 1:支付宝 2:微信
    private EntityObject<WeChatEntity> weChatEntityEntityObject;
    private ActionSheetDialog mDialog;
    long withdrawals;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取金币列表
        presenter.onCreate();
        presenter.getVcList();
        presenter.attachView(getVcListView);

    }




    @Override
    public void setOnClick() {
        super.setOnClick();
        rlFinish.setOnClickListener(this);
        tvReflect.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId())
        {
            case R.id.rl_my_pursen_finish:
                this.finish();
            break;
            case R.id.tv_mind_reflect:

                if(userVC != null){
                     withdrawals = userVC.getData().getUsableBalance();
                }

                Intent intent = new Intent(this,WithdrawalsActivity.class);
                intent.putExtra("withdrawals_money",withdrawals);
                StartActivity(intent);
                break;
        }
    }


    @Override
    public void notifyUpdate(NotifyObject obj) {
        switch (obj.what)
        {
            case 1:
                if(BaseApplication.getUserNo() != null){
                    //生成订单
                    vc = (VC) obj.object;
                    setDialog(vc);

                }else {
                    ToastUtils.showLong(" 鬼知道你是哪个...,给我登录去...");
                }
                break;
        }

    }
    public void setDialog(final VC vc){
        mDialog = new ActionSheetDialog(this).builder();
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setTitle("支付方式");
        mDialog.addSheetItem("微信", ActionSheetDialog.SheetItemColor.green, new ActionSheetDialog.OnSheetItemClickListener() {

            @Override
            public void onClick(int which) {
                PayMentMethod = 2;
                presenter.onCreate();
                presenter.getOrderAdd("0","0","0",vc.getVcNo()+"");
                presenter.attachView(getOrderAddView);
            }
        });
        mDialog.addSheetItem("支付宝", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                PayMentMethod = 1;
                presenter.onCreate();
                presenter.getOrderAdd("0","0","0",vc.getVcNo()+"");
                presenter.attachView(getOrderAddView);

            }
        }).show();

    }


    private long userMoney;
    private EntityObject<UserVC> userVC;
    //礼物列表 UserVcVIew
    public ITestView UserVcVIew = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            userVC = (EntityObject<UserVC>) object;
            userMoney = userVC.getData().getUserBalance();
            tvGoldNum.setText(userMoney+" 金币 ");
        }

        @Override
        public void onError(Object object) {

        }
    };




    public ITestView getOrderAddView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            orderEntityObject = (EntityObject<Order>) object;
            String orderNo = orderEntityObject.getData().getOrderNo()+"";

            switch (PayMentMethod)
            {
                case 1: //支付宝
                    //生成支付宝订单
                    presenter.onCreate();
                    presenter.getOrderCreateAliyum(orderNo);
                    presenter.attachView(getOrderCreateAliyumView);

                    break;
                case 2:

                    //生成微信订单
                    presenter.onCreate();
                    presenter.getWxPayDoUnifiedOrder(orderNo);
                    presenter.attachView(getOrderCreateAliyumView);


                    break;
            }



        }

        @Override
        public void onError(Object object) {

        }
    };






    public ITestView getOrderCreateAliyumView = new ITestView() {
        @Override
        public void onSuccess(Object object) {

            switch (PayMentMethod)
            {
                case 1: //支付宝
                    jsonEntity = (JsonEntity) object;
                    SendAlipay(jsonEntity);

                    break;
                case 2:

                    weChatEntityEntityObject = (EntityObject<WeChatEntity>) object;
//                    ToastUtils.showLong("微信支付-成功");
                    weChatPay();
//                    //生成微信订单
//                    presenter.onCreate();
//                    presenter.getWxPayDoUnifiedOrder(orderNo);
//                    presenter.attachView(getOrderCreateAliyumView);


                    break;
            }



        }

        @Override
        public void onError(Object object) {

        }
    };
    public void weChatPay(){
//        ToastUtils.showLong("开始了");
        WeChatEntity weChatEntity = weChatEntityEntityObject.getData();
//            DepositPaymentModel depositPaymentModel = new DepositPaymentModel();
        IWXAPI mWxApi = WXAPIFactory.createWXAPI(this, weChatEntity.getAppid(), true);
        mWxApi.registerApp(weChatEntity.getAppid());
        /**weChatEntityEntityObject.getData()
         * 请求app服务器得到的回调结果
         */
        if (mWxApi != null) {
            PayReq req = new PayReq();
            req.appId = weChatEntityEntityObject.getData().getAppid();// 微信开放平台审核通过的应用APPID

            req.partnerId = weChatEntity.getPartnerid();// 微信支付分配的商户号
            req.prepayId = weChatEntity.getPrepayid();// 预支付订单号，app服务器调用“统一下单”接口获取
            req.nonceStr = weChatEntity.getNoncestr();// 随机字符串，不长于32位，服务器小哥会给咱生成
            req.timeStamp = weChatEntity.getTimestamp()+"";// 时间戳，app服务器小哥给出
            req.packageValue = weChatEntity.getWeChatPackage();// 固定值Sign=WXPay，可以直接写死，服务器返回的也是这个固定值
            req.sign = weChatEntity.getSign();
            mWxApi.sendReq(req);
//            ToastUtils.showLong("结束了");
        }
    }















    public void SendAlipay(JsonEntity JsonEntity){
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(MyPurseActivity.this);
                // 调用支付接口，获取支付结果
                String data = alipay.pay(jsonEntity.getData(), true);
                Message msg = mHandler.obtainMessage();
                msg.what = SDK_PAY_FLAG;
                msg.obj = data;
                mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }




    public ITestView getVcListView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            vcEntity = (Entity<VC>) object;
            presenter.onCreate();
            presenter.getUserUserVc();
            presenter.attachView(UserVcVIew);

            rlvMyPurseBold.setLayoutManager(new
                    StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
            myPurseBoldAdapter = new MyPurseBoldAdapter(MyPurseActivity.this,vcEntity.getData().getList());
            rlvMyPurseBold.setAdapter(myPurseBoldAdapter);
        }

        @Override
        public void onError(Object object) {

        }
    };


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
//                    Log.e("zhifu","支付"+msg.obj);
                    PayResult payResult = new PayResult((String) msg.obj);

                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();  //同步返回需要验证的信息


                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        //支付成功后查询
                        ToastUtils.showLong("支付成功");
                        presenter.onCreate();
                        presenter.getUserUserVc();
                        presenter.attachView(UserVcVIew);
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            ToastUtils.showLong("支付结果确认中");
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            ToastUtils.showLong("支付失败");
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };






}
