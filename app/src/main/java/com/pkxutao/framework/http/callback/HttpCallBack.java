package com.pkxutao.framework.http.callback;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * 网络回调基类
 * Created by pkxutao on 16/1/1.
 */
public abstract class HttpCallBack {
    public abstract void onHandleFailure(Request request, Exception e);
    public abstract void onHandleSuccess(Request request, Response response, String responseStr);
}
