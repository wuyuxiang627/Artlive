package com.connxun.elinetv.base.network;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;
import java.io.StringReader;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by connxun-16 on 2018/1/30.
 */

public class ResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private static final int FAILURE = 0;       // 失败 提示失败msg
    private static final int SUCCESS = 200

            ;       // 成功
    private static final int TOKEN_EXPIRE = 2;  // token过期
    private static final int SERVER_EXCEPTION = 3;  // 服务器异常

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    ResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String json = value.string();
        try {
            verify(json);
            return adapter.read(gson.newJsonReader(new StringReader(json)));
        } finally {
            value.close();
        }
    }

    private void verify(String json) {
//        Result result = gson.fromJson(json, Result.class);
//        if (result. != SUCCESS) {
//            switch (result.state) {
//                case FAILURE:
//                case SERVER_EXCEPTION:
//                    throw new ApiException(result.msg);
//                case TOKEN_EXPIRE:
//                    throw new TokenExpireException(result.msg);
//                default:
//                    throw new UndefinedStateException();
//            }
//        }
    }
}