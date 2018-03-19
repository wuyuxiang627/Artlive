package com.connxun.elinetv.base.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;
/**
 * Created by Administrator on 2018\3\19 0019.
 */

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JIGUANG-Example";
    private static final int TYPE_NONE = 0;
    private static final int TYPE_NOTIFICATION_LIST = 1;
    private static final int TYPE_VIDEOROOM = 2;
    private static final int TYPE_TASK_LIST = 3;
    private static final int TYPE_WELLET = 4;
    private int type;
    private String videoId;

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        String extras = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        JSONObject jsonObject = null;

        try {
            if(extras!=null){
                jsonObject = new JSONObject(extras);
                if(jsonObject!=null){
                    type = Integer.parseInt(jsonObject.getString("type"));
                    videoId = jsonObject.getString("videoId");
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                //send the Registration Id to your server...
                //自定义消息
            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                processCustomMessage(context, bundle);

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                if(type == MyReceiver.TYPE_NOTIFICATION_LIST || type == MyReceiver.TYPE_TASK_LIST) {
//                    NotifyListenerManager.getInstance().notifyListener(new NotifyObject(1), Fragment_left.class);//传参数通知
//                    NotifyListenerManager.getInstance().notifyListener(new NotifyObject(10), Fragment_index_Gao.class);//传参数通知
                }

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {

                //打开自定义的Activity
                if(type == MyReceiver.TYPE_NONE){

                }else if(type == MyReceiver.TYPE_NOTIFICATION_LIST){
                    //发送广播通知显示红点
//                    NotifyListenerManager.getInstance().notifyListener(new NotifyObject(1), Fragment_left.class);
//                    NotifyListenerManager.getInstance().notifyListener(new NotifyObject(10), Fragment_index_Gao.class);
                    //type = 1,跳转至消息列表
//                    Intent i = new Intent(context, NewsActivity.class);
//                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//                    context.startActivity(i);

                }else if(type == MyReceiver.TYPE_VIDEOROOM){
                    //type = 2,跳转至视频间
//                    Intent i = new Intent(context, PlayVideo_copy.class);
//                    i.putExtra("videoId",videoId);
//                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//                    context.startActivity(i);
                }else if(type == MyReceiver.TYPE_TASK_LIST){
                    //type = 3,跳转至任务列表
//                    NotifyListenerManager.getInstance().notifyListener(new NotifyObject(1), Fragment_left.class);//传参数通知
//                    NotifyListenerManager.getInstance().notifyListener(new NotifyObject(10), Fragment_index_Gao.class);//传参数通知
//                    Intent i = new Intent(context, DailyTasksActivity.class);
//                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//                    context.startActivity(i);
                }else if(type == MyReceiver.TYPE_WELLET){
                    //type = 4,跳转至存钱罐
//                    Intent i = new Intent(context, SaveLabelActivity.class);
//                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//                    context.startActivity(i);
                }

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            } else {
            }
        } catch (Exception e){

        }
        type=0;
        videoId="";
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            }else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it =  json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " +json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//        NotifyListenerManager.getInstance().notifyListener(new NotifyObject(1), Fragment_left.class);//传参数通知
//        NotifyListenerManager.getInstance().notifyListener(new NotifyObject(10), Fragment_index_Gao.class);//传参数通知

    }
}
