package com.pkxutao.framework.http.callback;

/**
 * Created by pkxutao on 16/1/1.
 */
public abstract class EntityCallBack<T> extends HttpCallBack{
    public abstract void onSuccess(T entity);
}
