package com.zzt.zt_hook;

import com.zzt.zt_hook.hack.Hack;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
/**
 * @author: zeting
 * @date: 2023/5/11
 */
//public class aaa {
//    public aaa() {
//
//    }
//    //  私有构造方法
//    //        无参数构造方法
//
//    public void v1() {
//        //构造
//        Hack.HackedMethod0 constructor = Hack
//                .into("com.utils.dddemo.hack.HackDemo")
//                .constructor()
//                .withoutParams();
//        // check
//        assertNotNull(constructor);
//        // 调用
//        Object statically = constructor
//                .invoke()
//                .statically();
//        // check
//        assertNotNull(statically);
//    }
//
//    // 有参数构造方法
//    public void v2() {
//        // 构造
//        Hack.HackedMethod1 constructor1 = Hack
//                .into("com.utils.dddemo.hack.HackDemo")
//                .constructor()
//                .withParam(int.class);
//        // check
//        assertNotNull(constructor1);
//        // 调用
//        Object statically1 = constructor1
//                .invoke(1222)
//                .statically();
//        // check
//        assertNotNull(statically1);
//    }
//
//    //  私有成员方法. 无参数成员方法
//
//    public void v3() {
//        Hack.HackedMethod1 constructor1 = Hack
//                .into("com.utils.dddemo.hack.HackDemo")
//                .constructor()
//                .withParam(int.class);
//
//        // check
//        assertNotNull(constructor1);
//
//        // 构造方法
//        Object statically1 = constructor1.invoke(1222).statically();
//
//        // check
//        assertNotNull(statically1);
//
//        // 构造该方法
//        Hack.HackedMethod0 foo = Hack
//                .into("com.utils.dddemo.hack.HackDemo")
//                .method("foo")
//                .returning(int.class)
//                .withoutParams();
//
//        // check
//        assertNotNull(foo);
//
//        // check 返回值是否等于传入的值
//        assertEquals(1222, (int) foo.
//
//                invoke().
//
//                on(statically1));
//    }
//// 有参数，有返回值方法
//
//    public void v4() {
//        Hack.HackedMethod1 constructor1 = Hack
//                .into("com.utils.dddemo.hack.HackDemo")
//                .constructor()
//                .withParam(int.class);
//
//        assertNotNull(constructor1);
//
//        Object statically1 = constructor1.invoke(1222).statically();
//
//        assertNotNull(statically1);
//
//        Hack.HackedMethod2 foo = Hack
//                .into("com.utils.dddemo.hack.HackDemo")
//                .method("foo")
//                .returning(int.class)
//                .withParams(int.class, String.class);
//
//        assertNotNull(foo);
//
//        assertEquals(7, (int) foo.
//
//                invoke(11, "dds_test").
//
//                on(statically1));
//
//    }
//    //   私有静态方法 ,     无参数静态方法
//
//    public void v5() {
//        Hack.HackedMethod0 method = Hack
//                .into("com.utils.dddemo.hack.HackDemo")
//                .staticMethod("bar")
//                .withoutParams();
//
//        assertNotNull(method);
//        // 方法调用
//        method.invoke().
//
//                statically();
//    }
//    //有参数静态方法
//
//    public void v6() {
//        Hack.HackedMethod3 method
//                = Hack.into("com.utils.dddemo.hack.HackDemo")
//                .staticMethod("bar")
//                .throwing(IOException.class)
//                .withParams(int.class, String.class, Bean.class);
//
//        assertNotNull(method);
//        // 调用方法
//        method.invoke(-1, "xyz", new
//
//                Bean()).
//
//                statically();
//    }
//    // 下面重点来了，我们经常hook系统的方法都是hook的静态成员变量，那我们该如何修改静态成员变量的呢？
//
//    public void v7() {
//        // 静态成员变量
//        Hack.HackedTargetField field = Hack
//                .into("com.utils.dddemo.hack.HackDemo")
//                .staticField("staticField")
//                .ofType(String.class);
//        //设置新值
//        field.set("dds111111111");
//
//        //打印出新值
//        Hack.HackedMethod0 constructor = Hack
//                .into("com.utils.dddemo.hack.HackDemo")
//                .constructor()
//                .withoutParams();
//
//        assertNotNull(constructor);
//
//        Object statically = constructor.invoke().statically();
//        ((HackDemo) statically).
//
//                printStaticField();
//    }
//}
