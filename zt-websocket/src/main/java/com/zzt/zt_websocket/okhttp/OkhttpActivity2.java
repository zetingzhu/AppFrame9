package com.zzt.zt_websocket.okhttp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.zzt.zt_websocket.R;
import com.zzt.zt_websocket.javaws.JavaWsActivity;

import java.io.IOException;

public class OkhttpActivity2 extends AppCompatActivity {
    private static final String TAG = "test-socket";

    WebSocketEcho mWebSocket;

    public static void start(Context context) {
        Intent starter = new Intent(context, OkhttpActivity2.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("OkHttp  2");


        Button viewById = findViewById(R.id.button_netty);
        viewById.setText("跳到 java-websocket");
        findViewById(R.id.button_netty).setOnClickListener(v -> {
            JavaWsActivity.start(this);
        });
        findViewById(R.id.button).setOnClickListener(v -> {
            try {
                mWebSocket.run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        findViewById(R.id.button2).setOnClickListener(v -> {
            if (mWebSocket != null) {
                mWebSocket.close(1000, "Connection closed");
            }
        });
        findViewById(R.id.button3).setOnClickListener(v -> {
            if (mWebSocket != null) {
                String sendValidate = "{\"type\":\"connect\",\"p\":{\"t\":1681716116110,\"auth\":\"64800f9f714c5918510dae85694ef4b2\",\"uuid\":\"881174193DA10094\"}}";
                mWebSocket.sendMessage(sendValidate);
            }
        });

        findViewById(R.id.button4).setOnClickListener(v -> {
            if (mWebSocket != null) {
                String sendValidate = "{\"type\":\"rtc\",\"p\":{\"codes\":\"XTREND|EURUSD,XTREND|ADAUSD,XTREND|AAPL,XTREND|LTCUSD,XTREND|EURUSD,XTREND|XAUUSD,XTREND|GBPUSD\"}}";
                mWebSocket.sendMessage(sendValidate);
            }
        });

    }


}