package com.zzt.zt_apprightcount

import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

/**
 * @author: zeting
 * @date: 2023/6/28
 *
 */
class AAA : AppCompatActivity() {
    val TAG = AAA::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.aaa)
        initView()
    }

    private fun initView() {
        var nowTime: Long = 10
        if (nowTime in 1..10) {
            Log.d(TAG, ">>>>>> nowtime 1:$nowTime")
        }

        if (nowTime in 10..20) {
            Log.d(TAG, ">>>>>> nowtime 2:$nowTime")
        }

        if (nowTime in 10 until 20) {
            Log.d(TAG, ">>>>>> nowtime 3:$nowTime")
        }

        if (nowTime in 1 until 10) {
            Log.d(TAG, ">>>>>> nowtime 4:$nowTime")
        }


        for (k in 1..10) {
            Log.d(TAG, ">>>>>> k 1:$k")
        }

        for (k in 10..20) {
            Log.d(TAG, ">>>>>> k 2:$k")
        }

        for (k in 10 until 20) {
            Log.d(TAG, ">>>>>> k 3:$k")
        }

        for (k in 1 until 10) {
            Log.d(TAG, ">>>>>> k 4:$k")
        }

        for (k in 20 downTo 1) {
            Log.d(TAG, ">>>>>> k 5:$k")
        }

        var a = 1.rangeTo(10)  //升序区间
        var b = 10.downTo(1)  //降序区间
        var c = b.reversed()  //翻转区间
        var d = b.step(3)  //步长


        a.forEach {
            Log.d(TAG, ">>>>>> a:$it ")
        }

        b.forEach {
            Log.d(TAG, ">>>>>> b:$it ")
        }

        c.forEach {
            Log.d(TAG, ">>>>>> c:$it ")
        }

        d.forEach {
            Log.d(TAG, ">>>>>> d:$it ")
        }

    }

}