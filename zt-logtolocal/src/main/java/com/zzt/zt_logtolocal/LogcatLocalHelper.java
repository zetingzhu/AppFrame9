package com.zzt.zt_logtolocal;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: zeting
 * @date: 2023/5/26
 * 将日志文件写入本地
 */
public class LogcatLocalHelper {
    private static final String TAG = LogcatLocalHelper.class.getSimpleName();
    private static LogcatLocalHelper INSTANCE = null;
    private String PATH_LOGCAT;

    /**
     * 初始化目录
     */
    public void init(Context context) {
        //存储路径
        PATH_LOGCAT = context.getExternalCacheDir() + File.separator + "log" + File.separator;
        File file = new File(PATH_LOGCAT);
        if (!file.exists()) {
            file.mkdirs();
        }
        Log.d(TAG, "保存文件路径：" + file.getAbsolutePath());
    }

    /**
     * 单例的生成方法
     *
     * @param context
     * @return
     */
    public static LogcatLocalHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LogcatLocalHelper(context);
        }
        return INSTANCE;
    }

    private LogcatLocalHelper(Context context) {
        init(context);
        dirFile();
    }

    /**
     * 创建文件
     */
    public void dirFile() {
        try {
            File dirFile = new File(PATH_LOGCAT);
            while (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            File logFile = new File(dirFile.getAbsolutePath() + File.separator + getFileName() + ".txt");
            while (!logFile.exists()) {
                logFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写文件到本地
     *
     * @param text
     */
    public void writeTextToFile(String text) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File dirFile = new File(PATH_LOGCAT);
                    if (!dirFile.exists()) {
                        return;
                    }
                    File logFile = new File(dirFile.getAbsolutePath() + File.separator + getFileName() + ".txt");
                    if (!logFile.exists()) {
                        return;
                    }
                    // 每次写入时，都换行写
                    String strContent = text + "\r\n";
                    RandomAccessFile raf = new RandomAccessFile(logFile, "rwd");
                    raf.seek(logFile.length());
                    raf.write(strContent.getBytes());
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /**
     * 确定文件名的函数，通过时间来产生不同的名字
     *
     * @return
     */
    public String getFileName() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date(System.currentTimeMillis()));
        return date;
    }
}
