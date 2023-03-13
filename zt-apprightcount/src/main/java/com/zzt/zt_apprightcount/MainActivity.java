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
import android.os.Build;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHuaWeiDeskMark(v.getContext(), 10);
            }
        });
    }


    /**
     * 显示华为角标
     */
    private void showHuaWeiDeskMark(Context context, int number) {
        try {
            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
            String launcherClassName = launchIntent.getComponent().getClassName();
            Bundle extra = new Bundle();
            extra.putString("package", context.getPackageName());
            extra.putString("class", launcherClassName);
            extra.putInt("badgenumber", number);
            context.getContentResolver().call(
                    Uri.parse("content://com.huawei.android.launcher.settings/badge/"),
                    "change_badge",
                    null,
                    extra);
        } catch (Exception e) {
            e.printStackTrace();
        }
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