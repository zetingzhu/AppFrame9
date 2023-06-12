package com.zzt.zt_apprightcount;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.core.app.NotificationCompat;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;

/**
 * @author: zeting
 * @date: 2023/3/13
 * 设置应用右上角角标
 */
public class AppRightUnreadCountUtil {

    public static int notificationId = 999;

    /**
     * 显示桌面角标
     */
    public static void showRightBadge(Context context, int number) {
        if (RomUtils.isHonor()) {
            showHonorBadgeNum(context, number);
        } else if (RomUtils.isHuawei()) {
            showHuaWeiBadge(context, number);
        } else if (RomUtils.isSamsung()) {
            showSamsungBadge(context, number);
            showNotificationBadge(context, number);
        } else if (RomUtils.isVivo()) {
            setVivoBadge(number, context);
        } else {
            showNotificationBadge(context, number);
        }
    }

    /**
     * 取消桌面角标
     */
    public static void cancelRightBadge(Context context, int number) {
        if (RomUtils.isHonor()) {
            showHonorBadgeNum(context, 0);
        } else if (RomUtils.isHuawei()) {
            showHuaWeiBadge(context, 0);
        } else if (RomUtils.isSamsung()) {
            showSamsungBadge(context, 0);
            cancelNotificationBadge(context, 0);
        } else if (RomUtils.isVivo()) {
            setVivoBadge(0, context);
        } else {
            cancelNotificationBadge(context, 0);
        }
    }

    /**
     * 显示vivo 角标
     *
     * @param count
     * @param context
     * @return
     */
    private static boolean setVivoBadge(int count, Context context) {
        try {
            String launcherClassName = getLauncherClassName(context);
            if (TextUtils.isEmpty(launcherClassName)) {
                return false;
            }
            Intent intent = new Intent("launcher.action.CHANGE_APPLICATION_NOTIFICATION_NUM");
            intent.putExtra("packageName", context.getPackageName());
            intent.putExtra("className", launcherClassName);
            intent.putExtra("notificationNum", count);
            context.sendBroadcast(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 显示华为角标
     */
    private static boolean showHuaWeiBadge(Context context, int number) {
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
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 显示荣耀角标
     *
     * @param context
     * @param number
     */
    public static void showHonorBadgeNum(Context context, int number) {
        String URI_OLD = "content://com.huawei.android.launcher.settings/badge/";
        String URI_NEW = "content://com.hihonor.android.launcher.settings/badge/";
        Uri uri = Uri.parse(URI_NEW);
        String type = context.getContentResolver().getType(uri);
        if (TextUtils.isEmpty(type)) {
            uri = Uri.parse(URI_OLD);
            type = context.getContentResolver().getType(uri);
            if (TextUtils.isEmpty(type)) {
                uri = null;
            }
        }
        try {
            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
            String launcherClassName = launchIntent.getComponent().getClassName();
            Bundle extra = new Bundle();
            extra.putString("package", context.getPackageName());
            extra.putString("class", launcherClassName);
            extra.putInt("badgenumber", number);
            if (uri != null) {
                context.getContentResolver().call(uri, "change_badge", null, extra);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void showSamsungDeskMark(Context context, int markCount) {
        try {
            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
            ComponentName launchComponent = launchIntent.getComponent();
            String launcherClassName = launchComponent.getClassName();
            Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
            intent.putExtra("badge_count", markCount);
            intent.putExtra("badge_count_package_name", context.getPackageName());
            intent.putExtra("badge_count_class_name", launcherClassName);
            context.sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static boolean setSamsungBadge(int count, Context context) {
        try {
            String launcherClassName = getLauncherClassName(context);
            if (TextUtils.isEmpty(launcherClassName)) {
                return false;
            }
            Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
            intent.putExtra("badge_count", count);
            intent.putExtra("badge_count_package_name", context.getPackageName());
            intent.putExtra("badge_count_class_name", launcherClassName);
            context.sendBroadcast(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 三星显示角标
     *
     * @param count
     * @param context
     * @return
     */
    private static boolean showSamsungBadge(Context context, int count) {
        try {
            String launcherClassName = getLauncherClassName(context);
            if (TextUtils.isEmpty(launcherClassName)) {
                return false;
            }
            Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
            intent.putExtra("badge_count", count);
            intent.putExtra("badge_count_package_name", context.getPackageName());
            intent.putExtra("badge_count_class_name", launcherClassName);
            context.sendBroadcast(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String getLauncherClassName(Context context) {
        ComponentName launchComponent = getLauncherComponentName(context);
        if (launchComponent == null) {
            return "";
        } else {
            return launchComponent.getClassName();
        }
    }

    private static ComponentName getLauncherComponentName(Context context) {
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        if (launchIntent != null) {
            return launchIntent.getComponent();
        } else {
            return null;
        }
    }

    /**
     * 通用展示角标
     *
     * @param context
     * @param count
     * @return
     */
    public static boolean showNotificationBadge(Context context, int count) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 8.0之后添加角标需要NotificationChannel
            NotificationChannel channel = new NotificationChannel("badge", "badge",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setShowBadge(true);
            notificationManager.createNotificationChannel(channel);
        }
        Intent intent = new Intent(context, MainActivity.class);
        String content = "您有" + count + "条未读消息";
        int code = content.hashCode();
        PendingIntent pendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getActivity(context, code, intent, PendingIntent.FLAG_IMMUTABLE);
        } else {
            pendingIntent = PendingIntent.getActivity(context, code, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        Notification notification = new NotificationCompat.Builder(context, "badge")
                .setContentTitle("应用角标")
                .setContentText(content)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setChannelId("badge")
                .setNumber(count)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL).build();

        // 小米
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            try {
                Field field = notification.getClass().getDeclaredField("extraNotification");
                Object extraNotification = field.get(notification);
                Method method = extraNotification.getClass().getDeclaredMethod("setMessageCount", int
                        .class);
                method.invoke(extraNotification, count);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        notificationManager.notify(notificationId, notification);
        return true;
    }

    public static boolean cancelNotificationBadge(Context context, int count) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 8.0之后添加角标需要NotificationChannel
            NotificationChannel channel = new NotificationChannel("badge", "badge",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setShowBadge(true);
            notificationManager.createNotificationChannel(channel);
        }
        //取消掉上一条通知消息
        notificationManager.cancel(notificationId);

//        Intent intent = new Intent(context, MainActivity.class);
//        String content = "您有" + count + "条未读消息";
//        int code = content.hashCode();
//        PendingIntent pendingIntent = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
//            pendingIntent = PendingIntent.getActivity(context, code, intent, PendingIntent.FLAG_IMMUTABLE);
//        } else {
//            pendingIntent = PendingIntent.getActivity(context, code, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        }
//        Notification notification = new NotificationCompat.Builder(context, "badge")
//                .setContentTitle("应用角标")
//                .setContentText(content)
//                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
//                .setSmallIcon(R.mipmap.ic_launcher_round)
//                .setAutoCancel(true)
//                .setContentIntent(pendingIntent)
//                .setChannelId("badge")
//                .setNumber(count)
//                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL).build();
//
//        // 小米
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
//            try {
//                Field field = notification.getClass().getDeclaredField("extraNotification");
//                Object extraNotification = field.get(notification);
//                Method method = extraNotification.getClass().getDeclaredMethod("setMessageCount", int
//                        .class);
//                method.invoke(extraNotification, count);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        notificationManager.notify(notificationId++, notification);
        return true;
    }

}
