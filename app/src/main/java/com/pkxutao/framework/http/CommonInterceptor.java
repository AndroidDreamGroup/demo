package com.pkxutao.framework.http;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 *
 * Created by pkxut on 2016/3/14.
 */
public class CommonInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request newRequest;
        //统一添加header
        newRequest = request.newBuilder()
                .addHeader("test", "test")
                .build();
        return chain.proceed(newRequest);
    }
}
