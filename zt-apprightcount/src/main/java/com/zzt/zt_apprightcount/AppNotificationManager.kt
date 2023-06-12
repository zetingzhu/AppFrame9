package com.zzt.zt_apprightcount

import android.content.Context
import android.os.Build
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.core.app.NotificationManagerCompat

/**
 * 在用户没开启通知栏权限时，用于跳转通知栏权限页面
 */
class AppNotificationManager {

    companion object {
        fun jumpNotificationSetting(context: Context) {
            val intent = Intent()
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                    intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                    intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                    context.startActivity(intent)
                }
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                    intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
                    intent.putExtra("app_package", context.packageName)
                    intent.putExtra("app_uid", context.applicationInfo.uid)
                    context.startActivity(intent)
                }
                else -> {
                    val intent = Intent()
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    intent.data = Uri.fromParts("package", context.packageName, null)
                    context.startActivity(intent)
                }
            }
        }


        /**
         * 系统层面通知开关有没有开启
         * Build.VERSION.SDK_INT >= 24
         * Build.VERSION.SDK_INT >= 19
         *
         * @param mContext
         * @return
         */
        fun checkNotifyPermission(mContext: Context): Boolean {
            var manager = NotificationManagerCompat.from(mContext);
            return manager.areNotificationsEnabled();
        }


    }
}