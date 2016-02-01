package com.pkxutao.framework.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;

import com.pkxutao.framework.http.callback.HttpCallBack;
import com.pkxutao.framework.util.LogUtil;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by pkxutao on 16/1/1.
 */
public class HttpUtil {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String HOST = "http://appapi.estay.com";
    private OkHttpClient _okHttpClient;
    private Context _context;
    Handler _callBackHandler;
    private boolean _isShowTip = false;//网络请求失败时是否自动弹出tip提示
    HttpUtil _httpUtil;

    public HttpUtil(Context context) {
        this._context = context;
        _httpUtil = this;
        _callBackHandler = new Handler(context.getMainLooper());
    }

    public HttpUtil(Context context, OkHttpClient _okHttpClient) {
        this._context = context;
        _httpUtil = this;
        _callBackHandler = new Handler(context.getMainLooper());
        this._okHttpClient = _okHttpClient;
    }


    private OkHttpClient getClient() {
        if (_okHttpClient == null) {
            _okHttpClient = new OkHttpClient();
            _okHttpClient.setReadTimeout(15, TimeUnit.SECONDS);
            _okHttpClient.setCookieHandler(new CookieManager(
                    new PersistentCookieStore(_context.getApplicationContext()),
                    CookiePolicy.ACCEPT_ALL));
        }
        return _okHttpClient;
    }

    /**
     * get异步请求
     *
     * @param url      自定义url
     * @param callBack 回调接口
     */
    public void get(String url, String methodName, Params params, HttpCallBack callBack) {
        Request request = buildGetRequest(url, methodName, params);
        request(request, callBack);
    }

    /**
     * get同步请求
     *
     * @param url 自定义url
     * @return
     * @throws IOException
     */
    public Response get(String url, String methodName, Params params) throws IOException {
        Request request = buildGetRequest(url, methodName, params);
        Response response = getClient().newCall(request).execute();
        return response;
    }

    /**
     * get同步请求
     *
     * @param url      自定义url
     * @param params
     * @param callBack 回调接口  @throws IOException
     */
    public void get(String url, Params params, HttpCallBack callBack) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        request(request, callBack);
    }

    /**
     * post异步请求，使用默认URL
     *
     * @param paramses 请求参数
     * @param callBack 回调接口
     */
    public void post(Params paramses, String methodName, HttpCallBack callBack) {
        Request request = buildJsonPostRequest(HOST, methodName, paramses);
        request(request, callBack);
    }

    /**
     * post异步请求，使用自定义url
     *
     * @param url      自定义url
     * @param paramses 请求参数
     * @param callBack 回调接口
     */
    public void post(String url, String methodName, Params paramses, HttpCallBack callBack) {
        Request request = buildJsonPostRequest(url, methodName, paramses);
        request(request, callBack);
    }

    /**
     * post异步请求
     *
     * @param jsonObject Json对象
     * @param callBack   回调接口
     */
    public void post(String methodName, JSONObject jsonObject, HttpCallBack callBack) {
        Request request = buildJsonPostRequest(HOST, methodName, jsonObject);
        request(request, callBack);
    }

    /**
     * post异步请求，参数为Key value拼接
     *
     * @param url      自定义url
     * @param params   请求参数
     * @param callBack 回调接口
     */
    public void postWithKV(String url, String methodName, Params params, HttpCallBack callBack) {
        Request request = buildPostRequest(url, methodName, params);
        request(request, callBack);
    }

    /**
     * 上传文件(file)
     *
     * @param url      自定义url
     * @param files    文件数组
     * @param fileKeys 文件对应的key数组
     * @param params   请求参数
     * @param callBack 回调接口
     */
    public void post(String url, String methodName, File[] files,
                     String[] fileKeys, Params params, HttpCallBack callBack) {
        Request request = buildMultipartFormRequest(url, methodName, files, fileKeys, params);
//        request(request, callBack, entity);
    }

    /**
     * 上传文件(字节)
     *
     * @param url      自定义url
     * @param bytes    文件(字节)数组
     * @param fileKeys 文件对应的key数组
     * @param params   请求参数
     * @param callBack 回调接口
     */
    public void post(String url, String methodName, List<byte[]> bytes,
                     String[] fileKeys, Params params, HttpCallBack callBack) {
        Request request = buildMultipartFormRequest(url, methodName, bytes, fileKeys, params);
//        request(request, callBack);
    }

    /**
     * post同步请求
     *
     * @param url      自定义url
     * @param paramses 请求参数
     * @return
     * @throws IOException
     */
    public Response post(String url, String methodName, Params paramses) throws IOException {
        Request request = buildJsonPostRequest(url, methodName, paramses);
        Response response = getClient().newCall(request).execute();
        return response;
    }


    /**
     * 创建以json对象为参数的request对象
     *
     * @param url
     * @param paramses
     * @return
     */
    private Request buildJsonPostRequest(String url, String methodName, Params paramses) {
        if (paramses == null) {
            paramses = new Params();
        }
        JSONObject jsonObject = new JSONObject();
        try {

            for (Params.Param param : paramses.getList()) {
                jsonObject.put(param.key, param.value);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(JSON, jsonObject.toString());
        return new Request.Builder()
                .url(url + "/" + methodName)
                .post(requestBody)
                .build();
    }

    private Request buildJsonPostRequest(String url, String methodName, JSONObject jsonObject) {
        if (jsonObject == null) {
            jsonObject = new JSONObject();
        }
        RequestBody requestBody = RequestBody.create(JSON, jsonObject.toString());
        return new Request.Builder()
                .url(url + "/" + methodName)
                .post(requestBody)
                .build();
    }

    /**
     * 创建以key、value键值对为参数的request对象
     *
     * @param url
     * @param paramses
     * @return
     */
    private Request buildPostRequest(String url, String methodName, Params paramses) {
        if (paramses == null) {
            paramses = new Params();
        }
        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (Params.Param param : paramses.getList()) {
            builder.add(param.key, String.valueOf(param.value));
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url + "/" + methodName)
                .post(requestBody)
                .build();
    }

    private Request buildGetRequest(String url, String methodName, Params paramses) {
        if (paramses == null) {
            paramses = new Params();
        }
        String params = "";
        for (Params.Param param : paramses.getList()) {
            params += "/" + param.key + "=" + param.value;
        }
        return new Request.Builder()
                .url(url + "/" + methodName + params)
                .build();
    }

    /**
     * 创建上传文件的request对象
     *
     * @param url      自定义url
     * @param bytes    内容数组list
     * @param fileKeys 文件对应的key数组
     * @param params   请求参数
     * @return
     */
    private Request buildMultipartFormRequest(String url, String methodName, List<byte[]> bytes,
                                              String[] fileKeys, Params params) {
        if (params == null) {
            params = new Params();
        }

        MultipartBuilder builder = new MultipartBuilder()
                .type(MultipartBuilder.FORM);

        if (params == null) {
            params = new Params();
        }
        JSONObject jsonObject = new JSONObject();
        try {

            for (Params.Param param : params.getList()) {
                jsonObject.put(param.key, param.value);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody tempBody = RequestBody.create(JSON, jsonObject.toString());
        builder.addPart(tempBody);


//        for (Params.Param param : params.getList()) {
//            builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + param.key + "\""),
//                    RequestBody.create(null, String.valueOf(param.value)));
//        }
        if (bytes != null) {
            RequestBody fileBody = null;
            for (int i = 0; i < bytes.size(); i++) {
                fileBody = RequestBody.create(MediaType.parse(guessMimeType("")), bytes.get(i));

                builder.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + fileKeys[i] + "\"; filename=\"" + "1.jpg" + "\""),
                        fileBody);
            }
        }

        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url + "/" + methodName)
                .post(requestBody)
                .build();
    }


    /**
     * 创建上传文件的request对象
     *
     * @param url      自定义url
     * @param files    文件数组
     * @param fileKeys 文件对应的key数组
     * @param params   请求参数
     * @return
     */
    private Request buildMultipartFormRequest(String url, String methodName, File[] files,
                                              String[] fileKeys, Params params) {
        if (params == null) {
            params = new Params();
        }

        MultipartBuilder builder = new MultipartBuilder()
                .type(MultipartBuilder.FORM);

        for (Params.Param param : params.getList()) {
            builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + param.key + "\""),
                    RequestBody.create(null, String.valueOf(param.value)));
        }
        if (files != null) {
            RequestBody fileBody = null;
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                String fileName = file.getName();
                fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);

                builder.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + fileKeys[i] + "\"; filename=\"" + fileName + "\""),
                        fileBody);
            }
        }

        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url + "/" + methodName)
                .post(requestBody)
                .build();
    }

    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }


    /**
     * 开始请求
     *
     * @param request
     * @param callBack
     */
    private void request(final Request request, final HttpCallBack callBack) {
//        if (_isShowTip && !isConnected(_context)) {
////            callBack.onFail(request, new NoNetException("网络错误，请设置网络"));
//            Tip.show(_context, "网络错误，请设置网络");
//            return;
//        }
        OkHttpClient okHttpClient = getClient();
        final Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
                         @Override
                         public void onFailure(final Request request, final IOException e) {
                             _callBackHandler.post(new Runnable() {
                                 @Override
                                 public void run() {
                                     callBack.onHandleFailure(request, e);
                                 }
                             });
                         }

                         @Override
                         public void onResponse(final Response response) throws IOException {
                             _callBackHandler.post(new Runnable() {
                                 @Override
                                 public void run() {
                                     callBack.onHandleSuccess(request, response);
                                     LogUtil.e("tag","---------------------");
                                 }
                             });
                         }
                     }

        );
    }


    public static boolean isConnected(Context context) {
        if (null == context) {
            return false;
        }
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connMgr) {
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            return (null != networkInfo && networkInfo.isConnected());
        }

        return false;
    }

    public void cancel() {

    }

}
