package com.zzt.zt_flexboxlayout;

import android.animation.FloatEvaluator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.zzt.zt_flexboxlayout.view.SlidingMenu;

/**
 * @author: zeting
 * @date: 2024/10/12
 * <p>
 * 1. 根布局必须是 DrawerLayout
 * 2. 第二个布局（菜单）必须设置 android:layout_gravity="left" or "right"
 */
public class MSlidingLayoutAct extends AppCompatActivity {
    private static final String TAG = MSlidingLayoutAct.class.getSimpleName();

    public static void start(Context context) {
        Intent starter = new Intent(context, MSlidingLayoutAct.class);
        context.startActivity(starter);
    }

    private Button button, button2, button3;
    private SlidingMenu drawer_layout;
    private View vContentBg;

    /**
     * 透明度估值器
     */
    private FloatEvaluator mAlphaEvaluator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_m_sliding_layout);
        initView();
    }

    private void initView() {
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        drawer_layout = findViewById(R.id.drawer_layout);
        vContentBg = findViewById(R.id.content_bg);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer_layout.openMenu();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer_layout.closeMenu();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer_layout.closeMenu();
            }
        });


        //创建估值器
        mAlphaEvaluator = new FloatEvaluator();
        //------------ 重点：设置侧滑菜单的状态切换监听 ------------
        drawer_layout.setOnMenuStateChangeListener(new SlidingMenu.OnMenuStateChangeListener() {
            @Override
            public void onMenuOpen() {
                Log.d(TAG, "菜单打开");
                //让黑色遮罩，禁用触摸
                vContentBg.setClickable(true);
            }

            @Override
            public void onSliding(float fraction) {
                Log.d(TAG, "菜单拽托中，百分比：" + fraction);
                //设定最小、最大透明度值
                float startValue = 0;
                float endValue = 0.55f;
                //估值当前的透明度值，并设置
                Float value = mAlphaEvaluator.evaluate(fraction, startValue, endValue);
                vContentBg.setAlpha(value);
            }

            @Override
            public void onMenuClose() {
                Log.d(TAG, "菜单关闭");
                //让黑色遮罩，恢复触摸
                vContentBg.setClickable(false);
            }
        });
    }
}
