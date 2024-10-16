package com.zzt.flow

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

/**
 * @author: zeting
 * @date: 2021/11/9
 * 看看协程能不能实现轮询 ， 加上 Lifecycle 可以实现生命周期结束时候，循环自动停止
 */
class LoopCoroutineUtil {
    val TAG = LoopCoroutineUtil::class.java.simpleName
    private var mCountdownJob: Job? = null

    /**
     * java 轮询开始
     */
    fun startLoopJava(
            timeMillis: Long,
            doInBackground: () -> Any?,
            onPostExecute: (Any?) -> Unit,
            lifecycle: Lifecycle
    ) {
        // lifecycle 不能为空
        if (lifecycle == null) {
            throw RuntimeException("lifecycle cannot be empty")
        }
        val coroutineScope = lifecycle.coroutineScope
        mCountdownJob = coroutinesCyclic(timeMillis, coroutineScope, doInBackground, onPostExecute)
    }

    /**
     * java 取消轮询
     */
    fun cancelLoopJava() {
        mCountdownJob?.cancel()
    }

    /**
     * kotlin 开始轮询
     *
     * lifecycle ： viewModelScope 或者 lifecycleScope
     *
     */
    fun startLoop(
            timeMillis: Long,
            doInBackground: () -> Any?,
            onPostExecute: (Any?) -> Unit,
            lifecycle: CoroutineScope
    ) {
        mCountdownJob = coroutinesCyclic(timeMillis, lifecycle, doInBackground, onPostExecute)
    }

    /**
     * kotlin 取消轮询
     */
    fun cancelLoop() {
        mCountdownJob?.cancel()
    }

    /**
     * 循环的协程
     */
    private fun coroutinesCyclic(
            timeMillis: Long,
            scope: CoroutineScope,
            doInBackground: () -> Any?,
            onPostExecute: (Any?) -> Unit,
    ): Job? {
        return flow {
            while (true) {
                try {
                    emit(doInBackground.invoke())
//                    Log.i(TAG, ">>>>>  循环线程 ${Thread.currentThread().name}  ${timeMillis}")
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.i(TAG, ">>>>> 异步操作异常 ${Thread.currentThread().name}")
                }
                delay(timeMillis)
            }
        }.flowOn(Dispatchers.IO)
                .onEach {
                    try {
                        //                Log.i(TAG, ">>>>> 结果执行线程  ${Thread.currentThread().name}")
                        onPostExecute.invoke(it)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.i(TAG, ">>>>> 同步操作异常 ${Thread.currentThread().name}")
                    }
                }
                .flowOn(Dispatchers.Main)
                .onStart {
//                Log.i(TAG, ">>>>>  开始 ${Thread.currentThread().name}   ")
                }
                .catch { ex ->
//                Log.i(TAG, ">>>>>定时内部异常 ${Thread.currentThread().name}   ")
                    ex.printStackTrace()
                }.onCompletion {
                    Log.i(TAG, ">>>>> 定时结束了 ${Thread.currentThread().name}   ")
                }
                .launchIn(scope)
    }
}