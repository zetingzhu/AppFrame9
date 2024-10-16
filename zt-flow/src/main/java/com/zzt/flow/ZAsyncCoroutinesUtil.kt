package com.zzt.flow

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author: zeting
 * @date: 2024/7/22
 * 协程异步任务
 */
class ZAsyncCoroutinesUtil {
    interface AsyncCallback {
        /**
         * 异步执行
         * @return Any?
         */
        fun doBack(): Any?

        /**
         * 更新ui数据
         * @param any Any?
         */
        fun uiView(any: Any?)
    }

    fun asyncRun(callback: AsyncCallback?) {
        GlobalScope.launch {
            val doBackValue = callback?.doBack()
            withContext(Dispatchers.Main)
            {
                callback?.uiView(doBackValue)
            }
        }
    }

}