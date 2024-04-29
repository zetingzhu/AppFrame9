package com.zzt.zt_json;

import com.google.gson.Gson;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author: zeting
 * @date: 2022/3/4
 * 用于解析行情的
 */
public class HttpResponseQuotation<T> implements Serializable {

    private boolean success;
    private String desc;
    private int status;
    private T data;
    private String exceptionString;// 用于记录行情接口失败原因

    public boolean isSuccess() {
        return "OK".equals(desc);
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getExceptionString() {
        return exceptionString;
    }

    public void setExceptionString(String exceptionString) {
        this.exceptionString = exceptionString;
    }

    public static HttpResponseQuotation fromJson(String json, Type type) {
        Gson gson = new Gson();
        Type objectType = type(HttpResponseQuotation.class, type);
        return gson.fromJson(json, objectType);
    }

    static ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }
}
