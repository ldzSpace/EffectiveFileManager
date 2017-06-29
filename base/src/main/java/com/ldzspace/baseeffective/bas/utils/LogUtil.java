package com.ldzspace.baseeffective.bas.utils;

import android.util.Log;

/**
 * @作者 liudazhi
 * @创建日期 2017/6/28
 */

public class LogUtil {

    private static boolean isLogEnable = true;

    public static void disableLog() {
        isLogEnable = false;
    }


    public static void enableLog() {
        isLogEnable = true;
    }

    public static void d(String tag, String msg) {
        if (isLogEnable) {
            StackTraceElement stackTraceElement = java.lang.Thread.currentThread().getStackTrace()[3];
            Log.d(tag, rebuildMsg(stackTraceElement, msg));
        }
    }

    public static void i(String tag, String msg) {
        if (isLogEnable) {
            StackTraceElement stackTraceElement = java.lang.Thread.currentThread().getStackTrace()[3];
            Log.i(tag, rebuildMsg(stackTraceElement, msg));
        }
    }

    public static void v(String tag, String msg) {
        if (isLogEnable) {
            StackTraceElement stackTraceElement = java.lang.Thread.currentThread().getStackTrace()[3];
            Log.v(tag, rebuildMsg(stackTraceElement, msg));
        }
    }

    public static void w(String tag, String msg) {
        if (isLogEnable) {
            StackTraceElement stackTraceElement = java.lang.Thread.currentThread().getStackTrace()[3];
            Log.w(tag, rebuildMsg(stackTraceElement, msg));
        }
    }

    public static void e(String tag, String msg) {
        if (isLogEnable) {
            StackTraceElement stackTraceElement = java.lang.Thread.currentThread().getStackTrace()[3];
            Log.e(tag, rebuildMsg(stackTraceElement, msg));
        }
    }

    private static String rebuildMsg(StackTraceElement stackTraceElement, String msg) {
        StringBuffer sb = new StringBuffer();
        sb.append(stackTraceElement.getFileName());
        sb.append(" (");
        sb.append(stackTraceElement.getLineNumber());
        sb.append(") ");
        sb.append(stackTraceElement.getMethodName());
        sb.append(": ");
        sb.append(msg);
        return sb.toString();
    }


}
