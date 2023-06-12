package com.zzt.zt_apprightcount;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import androidx.core.app.NotificationCompat;

/**
 * @author: zeting
 * @date: 2023/3/13
 */
public class SamsungRightBadge {
    public static int notificationId = 1;

    public static void setSamSungBadgeNum(Context context, int num) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true);

        mBuilder.setContentTitle("test");
        mBuilder.setTicker("test");
        mBuilder.setContentText("test");

        //点击set 后，app退到桌面等待3s看效果（有的launcher当app在前台设置未读数量无效）
        final Notification notification = mBuilder.build();
        sendBadgeNotification(notification, notificationId, context, num, num);
    }

    /**
     * 重置、清除Badge未读显示数<br/>
     *
     * @param context
     */
    public static void resetBadgeCount(Context context) {
        sendBadgeNotification(null, 0, context, 0, 0);
    }

    public static void sendBadgeNotification(Notification notification, int notifyID, Context context, int thisNotifyCount, int count) {
        if (count <= 0) {
            count = 0;
        } else {
            count = Math.max(0, Math.min(count, 99));
        }

        executeBadge(context, notification, notifyID, thisNotifyCount, count);
    }

    public static void executeBadge(Context context, Notification notification, int notificationId, int thisNotificationCount, int count) {

        String launcherClassName = getLauncherClassName(context);
        if (launcherClassName == null) {
            return;
        }
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", count);
        intent.putExtra("badge_count_package_name", context.getPackageName());
        intent.putExtra("badge_count_class_name", launcherClassName);
        context.sendBroadcast(intent);

        setNotification(notification, notificationId, context);
    }

    protected static void setNotification(Notification notification, int notificationId, Context context) {
        if (notification != null) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(notificationId, notification);
        }
    }

    protected static String getLauncherClassName(Context context) {
        PackageManager packageManager = context.getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        // To limit the components this Intent will resolve to, by setting an
        // explicit package name.
        intent.setPackage(context.getPackageName());
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        // All Application must have 1 Activity at least.
        // Launcher activity must be found!
        ResolveInfo info = packageManager
                .resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);

        // get a ResolveInfo containing ACTION_MAIN, CATEGORY_LAUNCHER
        // if there is no Activity which has filtered by CATEGORY_DEFAULT
        if (info == null) {
            info = packageManager.resolveActivity(intent, 0);
        }

        return info.activityInfo.name;
    }
}
