package com.zzt.zt_bigdecimal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        try {
            String objStr = "1,122.33";
            double aDouble = Double.parseDouble(objStr);
            Log.d(TAG, "数字转换 1：" + aDouble);

//        objStr = objStr.replace(",", ".");
            double doubleValue = new BigDecimal(objStr).doubleValue();
            Log.d(TAG, "数字转换：" + doubleValue);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "数字转换：" + e);
        }
    }


}