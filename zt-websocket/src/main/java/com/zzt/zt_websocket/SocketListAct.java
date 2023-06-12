package com.zzt.zt_websocket;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zzt.zt_websocket.javaws.JavaWsActivity;
import com.zzt.zt_websocket.netty.NettyActivity;
import com.zzt.zt_websocket.okhttp.OkhttpActivity;
import com.zzt.zt_websocket.okhttp.OkhttpActivity2;

/**
 * @author: zeting
 * @date: 2023/4/18
 *
 */
public class SocketListAct extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_list);

        findViewById(R.id.btn_01).setOnClickListener(v ->
                JavaWsActivity.start(v.getContext())
        );

        findViewById(R.id.btn_02).setOnClickListener(v ->
                NettyActivity.start(v.getContext())
        );

        findViewById(R.id.btn_03).setOnClickListener(v ->
                OkhttpActivity.start(v.getContext())
        );

        findViewById(R.id.btn_04).setOnClickListener(v ->
                OkhttpActivity2.start(v.getContext())
        );

    }
}
