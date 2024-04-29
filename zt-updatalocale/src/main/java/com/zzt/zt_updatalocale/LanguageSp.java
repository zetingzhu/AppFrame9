package com.zzt.zt_updatalocale;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceActivity;

/**
 * @author: zeting
 * @date: 2024/4/16
 */
public class LanguageSp {

    public static final String PRE_SETTING_NAME = "preferencesetting_default";

    public static void setLanguage(Context context, String language) {
        if (context != null) {
            SharedPreferences sp = context.getSharedPreferences(PRE_SETTING_NAME, PreferenceActivity.MODE_PRIVATE);
            sp.edit().putString("language", language).apply();
        }
    }

    public static String getLanguage(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PRE_SETTING_NAME, PreferenceActivity.MODE_PRIVATE);
        return sp.getString("language", "");
    }
}
