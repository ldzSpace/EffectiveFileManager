package com.ldzspace.baseeffective.bas.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @作者 liudazhi
 * @创建日期 2017/6/28
 */

public class ToastUtil {
    private static Toast mToast;
    private ToastUtil(){ throw new AssertionError("no instance"); }

    public static void show(Context context, String message, int duration){
        if (mToast == null) {
            mToast = Toast.makeText(context, message, duration);
        } else {
            mToast.setText(message);
        }
        mToast.show();
    }

    public static void showShortToast(Context context, String message){
        if (mToast == null) {
            mToast =  Toast.makeText(context, message, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(message);
        }
        mToast.show();
    }

    public static void showLongToast(Context context, String message){
        if (mToast == null) {
            mToast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        } else {
            mToast.setText(message);
        }
        mToast.show();
    }

    /**
     * 当toast转换,比如短toast转换成了长toast,先调用hideToast取消掉,在转换
     */
    public static void hideToast(){
        if (mToast != null) {
            mToast.cancel();
        }
    }
}
