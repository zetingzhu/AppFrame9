package com.zzt.zt_filesample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData(getBaseContext());
    }

    private void initData(Context context) {
        //1. 内部存储
        Log.d(TAG, "目录 ： " + Environment.getDataDirectory());         //获取内部存储的根目录，/data
        Log.d(TAG, "目录 ： " + context.getFilesDir());   // 获取应用在内部存储的file目录  /data/.../packageName/file
        Log.d(TAG, "目录 ： " + context.getCacheDir());    //获取应用在内部存储的cache目录   /data/.../packageName/cache

        //2. 外部存储,私有目录
        Log.d(TAG, "目录 ： " + context.getExternalCacheDir());
        Log.d(TAG, "目录 ： " + context.getExternalFilesDir(null));
        Log.d(TAG, "目录 ： " + context.getExternalFilesDir("abc"));
        Log.d(TAG, "目录 ： " + context.getExternalFilesDir(""));

        //3. 外部存储,公共目录
        Log.d(TAG, "目录 ： " + Environment.getExternalStorageDirectory());
    }

    private void initView() {
    }
}