package com.zzt.zt_filesample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Debug;
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

        getMemoryInfo();
    }


    /**
     * 获取内存信息
     */
    public void getMemoryInfo() {
        Log.d(TAG, "WSLru-Socket内存 最大可用内存:" + (Runtime.getRuntime().maxMemory() / (1024 * 1024)) + "M");
        Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
        Debug.getMemoryInfo(memoryInfo);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            String javaHeap = memoryInfo.getMemoryStat("summary.java-heap");
            String nativeHeap = memoryInfo.getMemoryStat("summary.native-heap");
            String code = memoryInfo.getMemoryStat("summary.code");
            String stack = memoryInfo.getMemoryStat("summary.stack");
            String graphics = memoryInfo.getMemoryStat("summary.graphics");
            String privateOther = memoryInfo.getMemoryStat("summary.private-other");
            String system = memoryInfo.getMemoryStat("summary.system");
            String swap = memoryInfo.getMemoryStat("summary.total-swap");
            int totalPss = memoryInfo.getTotalPss();

            Log.w(TAG, "WSLru-Socket内存 3 已使用内存: " + (totalPss / 1024));
            Log.w(TAG, "WSLru-Socket内存 3 javaHeap: " + ((Integer.parseInt(javaHeap) * 1.0) / 1024));
            Log.w(TAG, "WSLru-Socket内存 3 nativeHeap: " + ((Integer.parseInt(nativeHeap) * 1.0) / 1024));
            Log.w(TAG, "WSLru-Socket内存 3 code: " + ((Integer.parseInt(code) * 1.0) / 1024));
            Log.w(TAG, "WSLru-Socket内存 3 stack: " + ((Integer.parseInt(stack) * 1.0) / 1024));
            Log.w(TAG, "WSLru-Socket内存 3 graphics: " + ((Integer.parseInt(graphics) * 1.0) / 1024));
            Log.w(TAG, "WSLru-Socket内存 3 privateOther: " + ((Integer.parseInt(privateOther) * 1.0) / 1024));
            Log.w(TAG, "WSLru-Socket内存 3 system: " + ((Integer.parseInt(system) * 1.0) / 1024));
            Log.w(TAG, "WSLru-Socket内存 3 swap: " + ((Integer.parseInt(swap) * 1.0) / 1024));
        }
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
        Log.d(TAG, "目录 ： " + context.getExternalFilesDir(""));


        Log.d(TAG, "目录"
                + "\n 1:" + context.getFilesDir()
                + "\n 6:" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                + "\n 7:" + Environment.getExternalStorageDirectory()
                + "\n 8:" + context.getExternalMediaDirs()
        );

    }

    private void initView() {
    }
}