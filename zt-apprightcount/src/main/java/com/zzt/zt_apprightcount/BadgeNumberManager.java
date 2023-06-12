package com.zzt.zt_apprightcount;

import android.content.Context;

/**
 * @author: zeting
 * @date: 2023/4/10
 */
//public class BadgeNumberManager {
//
//    private Context mContext;
//
//    private BadgeNumberManager(Context context) {
//        mContext = context;
//    }
//
//    public static BadgeNumberManager from(Context context) {
//        return new BadgeNumberManager(context);
//    }
//
//    private static final BadgeNumberManager.Impl IMPL;
//
//    /**
//     * 设置应用在桌面上显示的角标数字
//     * @param number 显示的数字
//     */
//    public void setBadgeNumber(int number) {
//        IMPL.setBadgeNumber(mContext, number);
//    }
//
//    interface Impl {
//
//        void setBadgeNumber(Context context, int number);
//
//    }
//
//    static class ImplHuaWei implements Impl {
//
//        @Override
//        public void setBadgeNumber(Context context, int number) {
//            BadgeNumberManagerHuaWei.setBadgeNumber(context, number);
//        }
//    }
//
//    static class ImplVIVO implements Impl {
//
//        @Override
//        public void setBadgeNumber(Context context, int number) {
//            BadgeNumberManagerVIVO.setBadgeNumber(context, number);
//        }
//    }
//
//
//    static class ImplBase implements Impl {
//
//        @Override
//        public void setBadgeNumber(Context context, int number) {
//            //do nothing
//        }
//    }
//
//    static {
//        String manufacturer = Build.MANUFACTURER;
//        if (manufacturer.equalsIgnoreCase("Huawei")) {
//            IMPL = new ImplHuaWei();
//        } else if (manufacturer.equalsIgnoreCase("vivo")) {
//            IMPL = new ImplVIVO();
//        } else if (manufacturer.equalsIgnoreCase("XXX")) {
//            //其他品牌机型的实现类
//            IMPL = new ImplXXX();
//        } else {
//            IMPL = new ImplBase();
//        }
//    }
//}