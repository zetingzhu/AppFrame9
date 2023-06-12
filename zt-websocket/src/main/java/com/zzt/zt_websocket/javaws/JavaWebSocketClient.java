package com.zzt.zt_websocket.javaws;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author: zeting
 * @date: 2023/4/17
 */
public class JavaWebSocketClient extends WebSocketClient {

    private static final String TAG = "test-socket  - java-websocket";

    public JavaWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake arg0) {
        Log.d(TAG, "------ MyWebSocket onOpen ------");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.d(TAG, "------ MyWebSocket onClose ------ code:" + code + " reason:" + reason + " remote:" + remote);
    }

    @Override
    public void onError(Exception arg0) {
        Log.d(TAG, "------ MyWebSocket onError ------");
    }

    @Override
    public void onMessage(ByteBuffer bytes) {
        super.onMessage(bytes);
        String message = StandardCharsets.UTF_8.decode(bytes).toString();
        Log.d(TAG, "-------- 接收到服务端数据 2： " + message + "--------");
    }

    @Override
    public void onMessage(String message) {
        Log.d(TAG, "-------- 接收到服务端数据 1： " + message + "--------");
    }
}
