package com.zzt.zt_json;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author: zeting
 * @date: 2022/3/4
 * 用于解析行情的
 */
public abstract class HttpCallbackQuotation<T> implements Callback {

    private static final String TAG = "HttpCallbackQuotation";


    private TypeReference<HttpResponseQuotation<T>> mClazz;

    public HttpCallbackQuotation() {
    }

    protected HttpCallbackQuotation(TypeReference<HttpResponseQuotation<T>> mClazz) {
        this.mClazz = mClazz;
    }

    private static Handler handler = new Handler(Looper.getMainLooper());

    public abstract void onResponse(HttpResponseQuotation<T> response);

    public void onResponseFastJson(HttpResponseQuotation<T> response) {
    }

    @Override
    public final void onResponse(Call call, Response response) {
        try {
//            StringBuffer strSb = new StringBuffer();
//            RequestBody requestBody = call.request().body();
//            if (requestBody != null) {
//                Buffer buffer = new Buffer();
//                requestBody.writeTo(buffer);
////                strSb.append(" >2.request().body():" + call.request().url() + "?" + buffer.readString(StandardCharsets.UTF_8));
//            }
//            strSb.append(" >3.response.code():" + response.code());
            if (response.code() == 200) {
                String resString = response.body().string();

//                strSb.append(" >4.response.body():" + resString);

                //start 打点
                long startNanos = System.nanoTime();
                Log.w("KLineVm", "kChart/v2 onResponse Gson start");

                Type type = getClass().getGenericSuperclass();
                if (type instanceof ParameterizedType)
                    type = ((ParameterizedType) type).getActualTypeArguments()[0];
                else
                    type = Object.class;
                HttpResponseQuotation<T> resObj = (HttpResponseQuotation<T>) HttpResponseQuotation.fromJson(resString, type);

                //计算耗时
                long lengthMillis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos);
                Log.w("KLineVm", "kChart/v2 onResponse Gson End " + "(" + lengthMillis + "ms)");

                if (resObj == null) {
                    if (shouldInMainThread() && handler != null) {
//                        handler.post(() -> onFailure(new RuntimeException(" >5.resObj null 1:" + strSb)));
                        handler.post(() -> onFailure(new RuntimeException(" >5.resObj null 1:")));
                    } else {
//                        onFailure(new RuntimeException(" >5.resObj null 2:" + strSb));
                        onFailure(new RuntimeException(" >5.resObj null 2:"));
                    }
                } else {
//                    resObj.setExceptionString(" >6.resObj success:" + strSb.toString());
                    if (shouldInMainThread() && handler != null) {
                        handler.post(() -> onResponse(resObj));
                        Log.w("KLineVm", "kChart/v2 onResponse End");
                    } else {
                        onResponse(resObj);
                    }
                }


                if (mClazz != null) {
                    //start 打点
                    long startNanosF = System.nanoTime();
                    Log.d("KLineVm", "      FastJson kChart/v2 onResponse start");
                    HttpResponseQuotation<T> mResponse = JSON.parseObject(resString, mClazz);
                    //计算耗时
                    long lengthMillisF = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanosF);
                    Log.d("KLineVm", "      FastJson kChart/v2 onResponse End " + "(" + lengthMillisF + "ms)");
                    onResponseFastJson(mResponse);
                }


            } else {
                if (shouldInMainThread() && handler != null) {
                    handler.post(() -> onFailure(new RuntimeException(" >7.response.code():" + response.code())));
                } else {
                    onFailure(new RuntimeException(" >7.response.code():" + response.code()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (shouldInMainThread() && handler != null) {
                handler.post(() -> onFailure(e));
            } else {
                onFailure(e);
            }
        }
    }

    @Override
    public final void onFailure(Call call, IOException e) {
        if (shouldInMainThread() && handler != null) {
            handler.post(() -> onFailure(e));
        } else {
            onFailure(e);
        }
    }

    public void onFailure(Exception e) {
        HttpResponseQuotation response = new HttpResponseQuotation();
        response.setSuccess(false);
//        try {
//            if (e != null) {
//                response.setExceptionString(" >8.Exception:" + GlideUrlLoaderUtil.getStackTrace(e));
//            }
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
        onResponse(response);
    }

    protected boolean shouldInMainThread() {
        return false;
    }
}
