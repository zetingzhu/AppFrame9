package com.zzt.zt_hook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zzt.appframe9.BuildConfig;

public class MainActivity extends AppCompatActivity {

    BuildConfig cc ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}