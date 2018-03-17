package com.connxun.elinetv.wxapi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;


import com.connxun.elinetv.base.ui.BaseActivity;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;


public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;

	private static final String APP_ID = "wxeb6cf53ef1c1281b";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    	api = WXAPIFactory.createWXAPI(this, APP_ID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	public void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {

	}

	@Override
	public void onResp(BaseResp resp) {
		if(resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX){
			SharedPreferences sp = getSharedPreferences("OrderId",1);
			final String orderId = sp.getString("orderId","");
			if(resp.errCode == 0){
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
//					new GetPayOkInfoProtocol(WXPayEntryActivity.this,WXPayEntryActivity.this,orderId);
					}
				},1000);
			}else{
				Toast.makeText(this, "支付失败，请重试", Toast.LENGTH_SHORT).show();
				finish();
			}
		}
	}

//	//------------网络请求的4个方法--------------
//	@Override
//	public void onHttpStart (LZHttpProtocolHandlerBase protocol){
//		super.onHttpStart(protocol);
//	}
//
//	@Override
//	public void onHttpProgress ( long total, long current, boolean isUploading){
//		super.onHttpProgress(total, current, isUploading);
//	}
//
//	@Override
//	public void onHttpSuccess (LZHttpProtocolHandlerBase protocol){
//		super.onHttpSuccess(protocol);
//
//		//		网络通顺
//		if (protocol.isHttpSuccess()) {
////			网络通顺，服务器返回的信息正确
//			if (protocol.isProtocolSuccess()) {
////                post请求
//				if (protocol.getProtocolType() == ConfigProtocolType.GET_PAY_INFO) {
//
//					//				网络通顺，服务器返回的信息正确，没有数据
//					if (protocol.getProtocolType() == ConfigStaticType.ProtocolStatus.RESULT_SUCCESS_EMPTY) {
//
//					}
//					//网络通顺，服务器返回的信息正确，有数据
//					else {
//						//余额支付成功后查询
//						GetPayOkInfoProtocol handler = (GetPayOkInfoProtocol) protocol;
//						String json = handler.getJson();
//						Log.e("微信","支付==返回"+json);
//						try {
//							JSONObject jsonObject = new JSONObject(json.toString());
//							int code = jsonObject.getInt("code");
//							if(code == 1){
//								JSONObject data = jsonObject.getJSONObject("data");
//								Intent intent = new Intent(this,DepositPayOkActivity.class);
//								intent.putExtra("cash",data.getInt("cash"));
//								intent.putExtra("payChannel",data.getString("payChannel"));
//								intent.putExtra("phone",data.getString("phone"));
//								intent.putExtra("cycle",data.getString("cycle"));
//								intent.putExtra("orderId",data.getString("orderId"));
//								intent.putExtra("explain",data.getString("explain"));
//								startActivity(intent);
//							}
//						} catch (JSONException e) {
//							e.printStackTrace();
//						}
//					}
//				}
//			}
//		}
//	}
//	@Override
//	public void onHttpFailure (Exception except, String msg){
//		super.onHttpFailure(except, msg);
//	}
}