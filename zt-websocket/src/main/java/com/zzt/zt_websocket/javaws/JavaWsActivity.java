package com.zzt.zt_websocket.javaws;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.zzt.zt_websocket.R;
import com.zzt.zt_websocket.netty.NettyActivity;

import org.java_websocket.enums.ReadyState;

import java.net.URI;

public class JavaWsActivity extends AppCompatActivity {
    private static final String TAG = "test-socket";
    JavaWebSocketClient myClient;

    public static void start(Context context) {
        Intent starter = new Intent(context, JavaWsActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("java_websocket");


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_NOTIFICATION_POLICY}, 100);
        }
        requestNotificationPermission(this);

        Button viewById = findViewById(R.id.button_netty);
        viewById.setText("跳到 netty");
        findViewById(R.id.button_netty).setOnClickListener(v -> {
            NettyActivity.start(this);
        });
        findViewById(R.id.button).setOnClickListener(v -> {
            wsConnect();
        });

        findViewById(R.id.button2).setOnClickListener(v -> {
            if (myClient != null) {
                myClient.close();
            }
        });
        findViewById(R.id.button3).setOnClickListener(v -> {
            if (myClient != null) {
                String sendValidate = "{\"type\":\"connect\",\"p\":{\"t\":1681716116110,\"auth\":\"64800f9f714c5918510dae85694ef4b2\",\"uuid\":\"881174193DA10094\"}}";
                myClient.send(sendValidate);
            }
        });

        findViewById(R.id.button4).setOnClickListener(v -> {
            if (myClient != null) {
                String sendValidate = "{\"type\":\"rtc\",\"p\":{\"codes\":\"XTREND|EURUSD,XTREND|ADAUSD,XTREND|AAPL,XTREND|LTCUSD,XTREND|EURUSD,XTREND|XAUUSD,XTREND|GBPUSD\"}}";
                myClient.send(sendValidate);
            }
        });

    }

    //2. 代码申请
    public static final String POST_NOTIFICATIONS = "android.permission.POST_NOTIFICATIONS";

    public static void requestNotificationPermission(Activity activity) {

        if (Build.VERSION.SDK_INT >= 33) {
            if (ActivityCompat.checkSelfPermission(activity, POST_NOTIFICATIONS) == PackageManager.PERMISSION_DENIED) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, POST_NOTIFICATIONS)) {
                    enableNotification(activity);
                } else {
                    ActivityCompat.requestPermissions(activity, new String[]{POST_NOTIFICATIONS}, 100);
                }
            }
        } else {
            boolean enabled = NotificationManagerCompat.from(activity).areNotificationsEnabled();
            if (!enabled) {
                enableNotification(activity);
            }
        }
    }

    public static void enableNotification(Context context) {
        try {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
            intent.putExtra(Settings.EXTRA_CHANNEL_ID, context.getApplicationInfo().uid);
            intent.putExtra("app_package", context.getPackageName());
            intent.putExtra("app_uid", context.getApplicationInfo().uid);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
            intent.setData(uri);
            context.startActivity(intent);
        }
    }

    public void wsConnect() {
        try {
            String wsUrl = "ws://test-quo-push-ws.yinyu.tech/ws";
            // 创建WebSocket客户端
            myClient = new JavaWebSocketClient(new URI(wsUrl));
            // 与服务端建立连接
            myClient.connect();
            while (!myClient.getReadyState().equals(ReadyState.OPEN)) {
                Log.d(TAG, "连接中。。。");
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}