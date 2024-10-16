package com.zzt.zt_flexboxlayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

/**
 * @author: zeting
 * @date: 2024/10/12
 * <p>
 * 1. 根布局必须是 DrawerLayout
 * 2. 第二个布局（菜单）必须设置 android:layout_gravity="left" or "right"
 */
public class MDrawerLayoutAct extends AppCompatActivity {
    public static void start(Context context) {
        Intent starter = new Intent(context, MDrawerLayoutAct.class);
        context.startActivity(starter);
    }

    private Button button, button2, button3;
    private DrawerLayout drawer_layout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_m_drawer_layout);
        initView();
    }

    private void initView() {
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        drawer_layout = findViewById(R.id.drawer_layout);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer_layout.openDrawer(GravityCompat.START);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer_layout.closeDrawers();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer_layout.closeDrawers();
            }
        });
    }
}
