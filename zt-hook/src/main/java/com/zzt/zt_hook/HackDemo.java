package com.zzt.zt_hook;

/**
 * @author: zeting
 * @date: 2023/5/11
 */

import android.util.Log;

import com.utils.library.log.LogA;

public class HackDemo {
    private int mIntField;
    private String mStr;
    private static String staticField = "dds";

    // 构造方法
    private HackDemo() {
        Log.d("dds", "constructor");
    }

    private HackDemo(int x) {
        mIntField = x;
        Log.d("dds", "constructor " + x);
    }

    private HackDemo(int x, String str) {
        mIntField = x;
        mStr = str;
        Log.d("dds", "constructor " + x);
    }

    // 成员方法
    private int foo() {
        Log.d("dds", "method :foo");
        // 返回私有变量
        return mIntField;
    }

    private int foo(int type, String str) {
        Log.d("dds", "method :foo " + type + "," + str);
        return 7;
    }

    // 静态成员方法
    private static void bar() {
        Log.d("dds", "static method :bar");
    }

    private static int bar(int type) {
        Log.d("dds", "static method :bar " + type);
        return type;
    }

    private static void bar(int type, String name, Bean bean) {
        LogA.d("dds", "static method :bar type:%d,%s,%s", type, name, bean.toString());
    }

    // 打印静态成员变量
    public void printStaticField() {
        Log.d("dds_test", "printStaticField:" + staticField);
    }
}

