package com.zyt.uxin.custonview.utiles;

import android.util.Log;


/**
 * 智能Log
 *
 * @author zoudong
 *         打开Log    -------->  adb shell setprop log.tag.url ERROR|DEBUG|WRAN|INFO|VERBOSE
 *         关闭Log    -------->  adb shell setprop log.tag.url false
 */
public class LogUtils {

    public static void e(String tag, String msg) {
            Log.e(tag, msg == null ? "null" : msg);
    }

    public static void d(String tag, String msg) {
            Log.d(tag, msg == null ? "null" : msg);
    }
}
