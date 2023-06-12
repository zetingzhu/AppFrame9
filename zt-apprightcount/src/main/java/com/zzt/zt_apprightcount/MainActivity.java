package com.zzt.zt_apprightcount;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    EditText et_number;
    private TextView tv_setting;

    private int sendCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_number = findViewById(R.id.et_number);
        tv_setting = findViewById(R.id.tv_setting);

        findViewById(R.id.btn_b0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppRightUnreadCountUtil.cancelRightBadge(MainActivity.this, 0);
            }
        });

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;
                if (!TextUtils.isEmpty(et_number.getText())) {
                    count = Integer.parseInt(String.valueOf(et_number.getText()));
                }
                AppRightUnreadCountUtil.showRightBadge(MainActivity.this, count);
            }
        });

        findViewById(R.id.btn_b2).setOnClickListener(v -> {

        });

        findViewById(R.id.btn_b3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SamsungU3.OpenNotification(MainActivity.this);
            }
        });

        findViewById(R.id.btn_b4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        OPPOV1.setOPPOBadge(22, MainActivity.this);
                        OPPOV1.setOPPOBadge2(33, MainActivity.this);
                        AppRightUnreadCountUtil.showNotificationBadge(MainActivity.this, 44);
                    }
                }, 5 * 1000);
            }
        });

        if (AppNotificationManager.Companion.checkNotifyPermission(this)) {
            tv_setting.setText("有通知权限");
        } else {
            tv_setting.setText("没有通知权限");
        }

        tv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppNotificationManager.Companion.jumpNotificationSetting(v.getContext());
            }
        });


        sendCount++;
        sendMsg();

    }

    public void sendMsg() {
        tv_setting.postDelayed(new Runnable() {
            @Override
            public void run() {
                AppRightUnreadCountUtil.showRightBadge(MainActivity.this, sendCount);
                sendMsg();
                Log.d(TAG, "发送的数量" + sendCount);
                sendCount++;
            }
        }, 5000);
    }


    public void showCount() {

        //显示数量
        int showNumber = 99;
        //小米11以下处理
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(this, "小米")
                .setContentTitle("小米测试")
                .setContentText("您有" + showNumber + "条新消息")
                .setLargeIcon(
                        BitmapFactory.decodeResource(
                                getResources(),
                                R.drawable.ic_launcher_background
                        )
                )
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setChannelId("xiaomi")
                .setNumber(showNumber)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL).build();
        //小米12处理
        Notification xiaomiNotification12 = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            xiaomiNotification12 = new Notification.Builder(MainActivity.this, "xiaomi")
                    .setSmallIcon(androidx.loader.R.drawable.notification_bg)
                    .setContentTitle("小米测试")
                    .setContentText("小米测试")
                    .setNumber(showNumber)
                    .build();
        }

    }
}