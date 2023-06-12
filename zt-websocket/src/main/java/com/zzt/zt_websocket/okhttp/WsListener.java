package com.zzt.zt_websocket.okhttp;

import android.util.Log;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * @author: zeting
 * @date: 2023/4/17
 */
//监听事件，用于收消息，监听连接的状态
public class WsListener extends WebSocketListener {
    private static final String TAG = "test-socket - okhttp";

    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        super.onClosed(webSocket, code, reason);
        Log.w(TAG, "-------- onClosed ： " + code + "--------" + reason);
    }

    @Override
    public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        super.onClosing(webSocket, code, reason);
        Log.w(TAG, "-------- onClosing ： " + code + "--------" + reason);
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
        super.onFailure(webSocket, t, response);
        Log.w(TAG, "-------- onFailure： " + response + "--------");
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        super.onMessage(webSocket, text);
        Log.e(TAG, "客户端收到消息:" + text);
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
        super.onMessage(webSocket, bytes);
        Log.d(TAG, "-------- 接收到服务端数据 2： " + bytes + "--------");
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        super.onOpen(webSocket, response);
        Log.e(TAG, "连接成功！");
    }
}

