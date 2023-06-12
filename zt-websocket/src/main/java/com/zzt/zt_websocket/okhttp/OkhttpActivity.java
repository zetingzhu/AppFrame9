package com.zzt.zt_websocket.okhttp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.zzt.zt_websocket.R;
import com.zzt.zt_websocket.javaws.JavaWsActivity;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public class OkhttpActivity extends AppCompatActivity {
    private static final String TAG = "test-socket";

    WebSocket mWebSocket;

    public static void start(Context context) {
        Intent starter = new Intent(context, OkhttpActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("OkHttp");

        Button viewById = findViewById(R.id.button_netty);
        viewById.setText("跳到 java-websocket");
        findViewById(R.id.button_netty).setOnClickListener(v -> {
            JavaWsActivity.start(this);
        });
        findViewById(R.id.button).setOnClickListener(v -> {
            okhttpConnect();
        });

        findViewById(R.id.button2).setOnClickListener(v -> {
            if (mWebSocket != null) {
                mWebSocket.close(1000, "Connection closed");
            }
        });
        findViewById(R.id.button3).setOnClickListener(v -> {
            if (mWebSocket != null) {
                String sendValidate = "{\"type\":\"connect\",\"p\":{\"t\":1681716116110,\"auth\":\"64800f9f714c5918510dae85694ef4b2\",\"uuid\":\"881174193DA10094\"}}";
                boolean send = mWebSocket.send(sendValidate);
                Log.d(TAG, "chenggong send 1:" + send );
            }
        });

        findViewById(R.id.button4).setOnClickListener(v -> {
            if (mWebSocket != null) {
                String sendValidate = "{\"type\":\"rtc\",\"p\":{\"codes\":\"XTREND|EURUSD,XTREND|ADAUSD,XTREND|AAPL,XTREND|LTCUSD,XTREND|EURUSD,XTREND|XAUUSD,XTREND|GBPUSD\"}}";
                boolean send = mWebSocket.send(sendValidate);
                Log.d(TAG, "chenggong send 2:" + send + " >");
            }
        });

    }


    public void okhttpConnect() {
        String wsUrl = "ws://test-quo-push-ws.yinyu.tech/ws";
        OkHttpClient mClient = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(30, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(30, TimeUnit.SECONDS)//设置连接超时时间
                .pingInterval(30, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(wsUrl)
                .build();
        mWebSocket = mClient.newWebSocket(request, new WsListener());
    }


}