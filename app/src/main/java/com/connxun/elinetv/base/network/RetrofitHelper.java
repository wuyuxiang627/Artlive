package com.connxun.elinetv.base.network;

/**
 * Created by connxun-16 on 2017/12/19.
 */

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.connxun.elinetv.app.BaseApplication;
import com.connxun.elinetv.base.util.LogUtil;
import com.connxun.elinetv.util.URLFactory;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit的初始化
 */
public class RetrofitHelper {

    private Context mContext;

    private static String CONTENT_TYPE = "application/x-www-form-urlencoded; charset=utf-8";

    private HashMap<String,String> hashMapBody;

    private static long DATE_TIME; //系统时间

    OkHttpClient client = new OkHttpClient();

    GsonConverterFactory factory = GsonConverterFactory.create(new GsonBuilder().create());

    private static RetrofitHelper instance = null;

    private static Retrofit mRetrofit = null;
    final String userNo = BaseApplication.userNo;
    final String token = BaseApplication.token;

    public RetrofitHelper(Context context) {
        mContext = context;
        init();

    }

    private void init() {
        resetApp();
    }

    private void resetApp() {

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {

                if(!TextUtils.isEmpty(BaseApplication.MD5sign)){

                    //获取时间
                    DATE_TIME =getDateTime();

                    Request original = chain.request();

                    //请求定制：添加请求头
                    Request.Builder requestBuilder = original.newBuilder()
                            .addHeader("Content-Type",CONTENT_TYPE)
                            .addHeader("userNo",BaseApplication.userNo);

                    hashMapBody = new HashMap();
                    //请求体定制：统一添加token参数
                    if(original.body() instanceof FormBody){
                        FormBody.Builder newFormBody = new FormBody.Builder();
                        FormBody oidFormBody = (FormBody) original.body();
                        for (int i = 0;i<oidFormBody.size();i++){
                            hashMapBody.put(oidFormBody.encodedName(i),oidFormBody.encodedValue(i));
                            newFormBody.addEncoded(oidFormBody.encodedName(i),oidFormBody.encodedValue(i));
                        }
                        requestBuilder.method(original.method(),newFormBody.build());
                    }
//                    //获取ASCLL编码排序字符串
//                    String asccllString =  AscllMap(hashMapBody);
//                    //添加token
//                    String sign = asccllString+"&key="+BaseApplication.token;
//                    //MD5加密
//                    String MD5sign =MD5(sign).toUpperCase();
                    //设置请求头
                    Request request = requestBuilder.addHeader("sign",BaseApplication.MD5sign).build();

                    Log.e("UserOpinionPresenter",BaseApplication.MD5sign+"MD5sign");
                    Log.e("UserOpinionPresenter",BaseApplication.token+"token");
                    return chain.proceed(request);
                }

                Request request = chain.request().newBuilder()
                        .header("Content-Type",CONTENT_TYPE)
                        .build();
                return chain.proceed(request);
            }
        });

        mRetrofit = new Retrofit.Builder()
                .baseUrl(URLFactory.SEVER_LOCAL)
                .client(client.build())
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }



    //获取系统时间
    public long getDateTime(){

        return new Date().getTime();
    }



    //ASCLL编码排序
    public String AscllMap(HashMap map){
        Collection<String> keyset= map.keySet();

        List list=new ArrayList<String>(keyset);

        Collections.sort(list);
        //这种打印出的字符串顺序和微信官网提供的字典序顺序是一致的
        String stringBuilder  = new String();
        for(int i=0;i<list.size();i++){
            stringBuilder = stringBuilder+list.get(i)+"="+map.get(list.get(i))+"&";
//            System.out.println(list.get(i)+"="+map.get(list.get(i)));
        }
        LogUtil.Log("helper",LogUtil.LOG_E,stringBuilder.substring(0,stringBuilder.length()-1));
        return stringBuilder.substring(0,stringBuilder.length()-1);
    }


    //MD5加密-pwd
    public static String MD5(String string)  {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;

        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b :bytes){
                String temp = Integer.toHexString(b & 0xff);
                if(temp.length() == 1){
                    temp = "0" +temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }






    /**
     * 获取helper对象
     * @param context
     * @return
     */
    public static RetrofitHelper getInstance(Context context){
        if (instance == null){
            instance = new RetrofitHelper(context);
        }
        return instance;
    }

    /**
     * 获取service 对象
     * @return
     */
    public static RetrofitService getServer(){
        return mRetrofit.create(RetrofitService.class);
    }



}
