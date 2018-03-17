package com.connxun.elinetv.presenter.user;

import android.content.Context;
import android.content.Intent;

import com.connxun.elinetv.base.network.DataManager;
import com.connxun.elinetv.base.network.RetrofitService;
import com.connxun.elinetv.base.util.LogUtil;
import com.connxun.elinetv.entity.JsonEntity;
import com.connxun.elinetv.presenter.Presenter;
import com.connxun.elinetv.util.URLFactory;
import com.connxun.elinetv.view.View;
import com.connxun.elinetv.view.user.ITestView;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by connxun-16 on 2017/12/19.
 * 测试 presenter
 */

public class TestPresenter implements Presenter {
    String TAG = "TestPresenter";


    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private ITestView testView;
    private JsonEntity mjsonEntity;

    public TestPresenter (Context mContext){
        this.mContext = mContext;
    }


    @Override
    public void onCreate() {
        manager = new DataManager(mContext);
        mCompositeSubscription = new CompositeSubscription();
    }


    @Override
    public void onStop() {
        if (mCompositeSubscription.hasSubscriptions()){
            mCompositeSubscription.unsubscribe();
        }

    }

    @Override
    public void attachView(View view) {
        testView = (ITestView) view;
    }

    @Override
    public void attachIncomingIntent(Intent intent) {

    }


    public void getUserPhone(String phone){
        mCompositeSubscription.add(manager.getUserCaptch(phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonEntity>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.Log(TAG,LogUtil.LOG_E,"请求回来的参数： "+ mjsonEntity.toString());
                        if(mjsonEntity != null){
                            testView.onSuccess(mjsonEntity);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if(mjsonEntity != null)LogUtil.Log(TAG,LogUtil.LOG_E,"失败了请求回来的参数： "+ mjsonEntity.toString());
                        testView.onError("请求失败！！");
                    }

                    @Override
                    public void onNext(JsonEntity jsonEntity) {
                        mjsonEntity = jsonEntity; //赋值
                    }

                })
        );

////        [java] view plain copy
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public okhttp3.Response intercept(Chain chain) throws IOException {
//                        Request request = chain.request();
//                        Log.i("zzz", "request====111111111111111111111111111111");
//                        Log.i("zzz", "request====" + request.headers().toString());
//                        okhttp3.Response proceed = chain.proceed(request);
//                        Log.i("zzz", "proceed====" + proceed.headers().toString());
//                        return proceed;
//                    }
//                })
//
//                .build();
//
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(URLFactory.SEVER_LOCAL)
//                .client(okHttpClient)
//                //增加返回值为String的支持
////                .addConverterFactory(ScalarsConverterFactory.create())
//                //增加返回值为Gson的支持(以实体类返回)
//                .addConverterFactory(GsonConverterFactory.create())
//                //增加返回值为Oservable<T>的支持
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .build();
//
//        RetrofitService requestSerives = retrofit.create(RetrofitService.class);//这里采用的是Java的动态代理模式
//        Call<JsonEntity> call = requestSerives.getUserCaptch("13651320820");//传入我们请求的键值对的值
//
//        call.enqueue(new retrofit2.Callback<JsonEntity>() {
//            @Override
//            public void onResponse(Call<JsonEntity> call, Response<JsonEntity> response) {
//                Log.e("===","return:"+response.body().toString());
//                testView.onSuccess(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<JsonEntity> call, Throwable t) {
//                Log.e("===","失败了:"+t.toString());
//            }
//        });

    }




    public void getUserUserRegister(String captch){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .header("Content-Type","application/x-www-form-urlencoded")
                        .build();

                return chain.proceed(request);
            }
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLFactory.SEVER_LOCAL)
                .client(builder.build())
                //增加返回值为String的支持
//                .addConverterFactory(ScalarsConverterFactory.create())
                //增加返回值为Gson的支持(以实体类返回)
                .addConverterFactory(GsonConverterFactory.create())
                //增加返回值为Oservable<T>的支持
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        RetrofitService requestSerives = retrofit.create(RetrofitService.class);//这里采用的是Java的动态代理模式
//        Call<ResponseBody> call = requestSerives.getUserRegister("13651320820",captch,"ewqrewq","ewrqwe","北京","2017-10-10",1);//传入我们请求的键值对的值


//        call.enqueue(new retrofit2.Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Log.e("===","return:"+response.body().toString());
//                testView.onSuccess(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.e("===","失败了:"+t.toString());
//            }
//        });

    }


}
