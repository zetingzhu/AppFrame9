package com.zzt.zt_websocket.okhttp;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.Buffer;
import okio.ByteString;

/**
 * @author: zeting
 * @date: 2023/4/18
 */
public final class WebSocketEcho extends WebSocketListener {
    private static final String TAG = "test-socket-2";
    private final ExecutorService writeExecutor = Executors.newSingleThreadExecutor();
    WebSocket mWebSocket;

    public void run() throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(0, TimeUnit.MILLISECONDS)
                .build();

        Request request = new Request.Builder()
                .url("ws://echo.websocket.org")
                .build();

        mWebSocket = client.newWebSocket(request, this);

        // Trigger shutdown of the dispatcher's executor so this process can exit cleanly.
        client.dispatcher().executorService().shutdown();
    }

    public void close(int code, String reason) {
        if (mWebSocket != null) {
            mWebSocket.close(code, reason);
        }
    }

    public boolean sendMessage(String msg) {
        writeExecutor.execute(new Runnable() {
            @Override
            public void run() {
                if (mWebSocket != null) {
                    boolean send = mWebSocket.send(msg);
                }
            }
        });
        return false;
    }

    @Override
    public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
        super.onOpen(webSocket, response);
        Log.e(TAG, "连接成功！");

    }


    @Override
    public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        super.onClosing(webSocket, code, reason);
        Log.w(TAG, "-------- onClosing ： " + code + "--------" + reason);
        writeExecutor.shutdown();
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
        super.onFailure(webSocket, t, response);
        Log.w(TAG, "-------- onFailure： " + response + "--------");
        writeExecutor.shutdown();
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
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        super.onClosed(webSocket, code, reason);
        Log.w(TAG, "-------- onClosed ： " + code + "--------" + reason);
    }


}