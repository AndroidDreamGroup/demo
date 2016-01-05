package com.pkxutao.framework.http.callback;

import com.squareup.okhttp.Request;

/**
 * Created by pkxutao on 16/1/1.
 */
public abstract class HttpCallBack {
    public abstract void onFailure(Request request, Exception e);
}
