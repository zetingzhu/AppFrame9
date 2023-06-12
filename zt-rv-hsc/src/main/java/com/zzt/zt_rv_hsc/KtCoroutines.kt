package com.zzt.zt_rv_hsc

import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

const val SUCCESS: Int = 200

fun requestData(sus : suspend () -> Unit): Job {
    return MainScope().launch { sus() }
}