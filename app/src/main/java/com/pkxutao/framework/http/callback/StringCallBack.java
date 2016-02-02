package com.pkxutao.framework.http.callback;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by pkxut on 2016/2/1.
 */
public abstract class StringCallBack extends HttpCallBack {

    public abstract void onSuccess(String reseponse);

    public abstract void onFailure(Request request, Exception e);

    @Override
    public void onHandleFailure(Request request, Exception e) {
        onFailure(request, e);
    }

    @Override
    public void onHandleSuccess(Request request, Response response, String responseStr) {
        onSuccess(responseStr);
    }
}
