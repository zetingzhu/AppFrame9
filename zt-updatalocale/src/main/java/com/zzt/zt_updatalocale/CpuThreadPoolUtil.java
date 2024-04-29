package com.zzt.zt_updatalocale;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: zeting
 * @date: 2024/4/18
 * 创建一个 cpu 密集线程池，用来大量计算持仓
 */
public class CpuThreadPoolUtil {
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    private ExecutorService executorService;

    private static class InnerClass {
        private static final CpuThreadPoolUtil INSTANCE = new CpuThreadPoolUtil();
    }

    private CpuThreadPoolUtil() {
    }

    public static CpuThreadPoolUtil getInstance() {
        return InnerClass.INSTANCE;
    }

    private synchronized ExecutorService executorService() {
        if (executorService == null) {
            return new ThreadPoolExecutor(CPU_COUNT + 1, 2 * CPU_COUNT + 1,
                    30,
                    TimeUnit.SECONDS,
                    new LinkedBlockingQueue<Runnable>(20));
        }
        return executorService;
    }


    public void execute(Runnable runnable) {
        executorService().execute(runnable);

    }
}
