package com.pkxutao.framework.http.callback;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.$Gson$Types;
import com.pkxutao.framework.util.LogUtil;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 回调Bean实体接口
 * Created by pkxutao on 16/1/1.
 */
public abstract class EntityCallBack<T> extends HttpCallBack{
    Type mType;

    public boolean mIsShowTip = false;

    public abstract void onSuccess(T entity);

    public abstract void onFailure(Request request, Exception e);

    @Override
    public void onHandleFailure(Request request, Exception e) {
        onFailure(request, e);
    }

    @Override
    public void onHandleSuccess(Request request, Response response, String responseStr) {
        T entity = null;
        try {
            entity = new Gson().fromJson(responseStr, mType);
        }catch (JsonSyntaxException e){
            e.printStackTrace();
        }
        //这一层可以预先处理一些统一的需求，例如：判断是否是业务逻辑错误而toast erroMsg

        if (entity == null){
            onFailure(request, new JsonSyntaxException("解析失败"));
        }else {
            onSuccess(entity);
        }
    }

    public EntityCallBack() {
        this.mType = getSuperclassTypeParameter(getClass());
    }

    public EntityCallBack showTip(){
        mIsShowTip = true;
        return this;
    }

    /**
     * Returns the type fr
     * om super class's type parameter in {@link $Gson$Types#canonicalize
     * canonical form}.
     */
    public static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }
}
