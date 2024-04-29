package com.zzt.zt_json;



import android.util.Log;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author: zeting
 * @date: 2024/4/10
 */
public abstract class FastJsonCallback<T> implements Callback {
    private static final String TAG = FastJsonCallback.class.getSimpleName();

    private TypeReference<HttpResponseQuotation<T>> mClazz;

    protected FastJsonCallback(TypeReference<HttpResponseQuotation<T>> mClazz) {
        this.mClazz = mClazz;
    }

    public abstract void onResponse(HttpResponseQuotation<T> response);

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response.isSuccessful()) {
            if (response.body() != null) {
                //start 打点
                long startNanos = System.nanoTime();
                Log.d("KLineVm", "      FastJson kChart/v2 onResponse start");
                String body = response.body().string();
                HttpResponseQuotation<T> mResponse = JSON.parseObject(body, mClazz);
                //计算耗时
                long lengthMillis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos);
                Log.d("KLineVm", "      FastJson kChart/v2 onResponse End "  + "(" + lengthMillis + "ms)");
                onResponse(mResponse);
            }
        }
    }
}