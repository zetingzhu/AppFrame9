package com.zzt.flow;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

/**
 * @author: zeting
 * @date: 2024/7/22
 */
public class DownloadThread extends HandlerThread {


    private DownloadThread mHandlerThread;//子线程
    private Handler mUIhandler;//主线程的Handler

    public void createThread() {
        //初始化，参数为线程的名字
        mHandlerThread = new DownloadThread("mHandlerThread");
        //调用start方法启动线程
        mHandlerThread.start();
        //初始化Handler，传递mHandlerThread内部的一个looper
        mUIhandler = new Handler(mHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                //判断mHandlerThread里传来的msg，根据msg进行主页面的UI更改
                switch (msg.what) {
                    case DownloadThread.TYPE_START:
                        //不是在这里更改UI哦，只是说在这个时间，你可以去做更改UI这件事情，改UI还是得在主线程。
                        Log.e(TAG, "4.主线程知道Download线程开始下载了...这时候可以更改主界面UI");
                        break;
                    case DownloadThread.TYPE_FINISHED:
                        Log.e(TAG, "7.主线程知道Download线程下载完成了...这时候可以更改主界面UI，收工");
                        break;
                    default:
                        break;
                }
                super.handleMessage(msg);
            }
        };
        //子线程注入主线程的mUIhandler，可以在子线程执行任务的时候，随时发送消息回来主线程
        mHandlerThread.setUIHandler(mUIhandler);
        //子线程开始下载
        mHandlerThread.startDownload();
    }

    public void closeThread() {
        //有2种退出方式
        mHandlerThread.quit();
        //mHandlerThread.quitSafely(); 需要API>=18
    }

    private static final String TAG = "DownloadThread";

    public static final int TYPE_START = 2;//通知主线程任务开始
    public static final int TYPE_FINISHED = 3;//通知主线程任务结束

    private Handler mUIHandler;//主线程的Handler

    public DownloadThread(String name) {
        super(name);
    }

    /*
     * 执行初始化任务
     * */
    @Override
    protected void onLooperPrepared() {
        Log.e(TAG, "onLooperPrepared: 1.Download线程开始准备");
        super.onLooperPrepared();
    }

    //注入主线程Handler
    public void setUIHandler(Handler UIhandler) {
        mUIHandler = UIhandler;
        Log.e(TAG, "setUIHandler: 2.主线程的handler传入到Download线程");
    }

    //Download线程开始下载
    public void startDownload() {
        Log.e(TAG, "startDownload: 3.通知主线程,此时Download线程开始下载");
        mUIHandler.sendEmptyMessage(TYPE_START);

        //模拟下载
        Log.e(TAG, "startDownload: 5.Download线程下载中...");
        SystemClock.sleep(2000);

        Log.e(TAG, "startDownload: 6.通知主线程,此时Download线程下载完成");
        mUIHandler.sendEmptyMessage(TYPE_FINISHED);
    }
}