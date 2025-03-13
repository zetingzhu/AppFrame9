package com.example.act

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.view.MTextInputLayoutV2
import com.example.zt_inputtext.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val customPasswordEditText: MTextInputLayoutV2 =
            findViewById(R.id.customPasswordEditText)
        customPasswordEditText.setOnPasswordChangeListener { password -> // 处理密码变化事件
            println("当前输入的密码是: $password")
        }

        var btn_error: Button = findViewById(R.id.btn_error)
        btn_error.setOnClickListener {
            customPasswordEditText.setErrorText("错误日志11111")
        }
    }
}