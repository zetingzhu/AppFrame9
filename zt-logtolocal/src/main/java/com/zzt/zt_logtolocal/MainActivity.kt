package com.zzt.zt_logtolocal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button).setOnClickListener { view ->
            LogcatLocalHelper.getInstance(applicationContext)
                .writeTextToFile("写入：" + System.currentTimeMillis())
        }
    }
}