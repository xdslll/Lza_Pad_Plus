package com.lza.pad.core.utils;

import com.lza.pad.lib.support.utils.ToastUtils;

/**
 * Created by xiads on 14-9-7.
 */
public class ToastUtilsSimplify {

    public static void show(String m) {
        ToastUtils.showShort(GlobalContext.getInstance(), m);
    }

    public static void show(int resId) {
        ToastUtils.showShort(GlobalContext.getInstance(), resId);
    }

    public static void showLong(String m) {
        ToastUtils.showLong(GlobalContext.getInstance(), m);
    }

    public static void showLong(int resId) {
        ToastUtils.showLong(GlobalContext.getInstance(), resId);
    }

    public static void test() {
        show("It's work!");
    }
}
