package com.zzt.flow

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

/**
 * @author: zeting
 * @date: 2024/7/15
 * Flow 工具
 */
class ZFlowUtil {
    val TAG = ZFlowUtil::class.java.simpleName
    private var mainScope: CoroutineScope? = MainScope()



    fun onCancel() {
        mainScope?.cancel()
    }


    interface FlowCallback {

        fun doBack(): Any?

        fun uiView(any: Any?)

    }

    fun runBlocking(
        doInBackground: FlowCallback?,
    ) {
        mainScope?.launch {
            flow {
                try {
                    Log.d(TAG, "flow doBack:${currentCoroutineContext()}")
                    emit(doInBackground?.doBack())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    e.printStackTrace()
                }
                .collect { value ->
                    Log.d(TAG, "flow uiView:${currentCoroutineContext()}")
                    doInBackground?.uiView(value)
                }

        }
    }

}