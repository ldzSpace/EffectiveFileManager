package com.ldzspace.baseeffective.bas.activityManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

/**
 * @作者 liudazhi
 * @创建日期 2017/6/28
 * @描述: 开启activity代理类
 */

public class StartActivityDelegate {

    private StartActivityDelegate(){
        // noInstance
    }

    /**
     * 开启新的Activity
     * @param fragment
     * @param intent
     * @return
     */
    public static boolean startActivitySafely(@NonNull Fragment fragment, Intent intent){
        return startActivitySafely(fragment, intent, null);
    }

    public static boolean startActivitySafely(@NonNull Fragment fragment, Intent intent, Bundle options){
        if (isIntentSafely(fragment.getActivity().getPackageManager(),intent)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                fragment.startActivity(intent, options);
            } else {
                fragment.startActivity(intent);
            }
            return true;
        }
        return false;
    }

    public static boolean startActivityForResultSafely(@NonNull Fragment fragment, @NonNull Intent intent, int requestCode){
        return startActivityForResultSafely(fragment, intent, requestCode, null);
    }

    public static boolean startActivityForResultSafely(@NonNull Fragment fragment, @NonNull Intent intent, int requestCode, Bundle options){
        if (isIntentSafely(fragment.getActivity().getPackageManager(),intent)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                fragment.startActivityForResult(intent, requestCode, options);
            } else {
                fragment.startActivityForResult(intent, requestCode);
            }
            return true;
        }
        return false;
    }

    /**
     * 从android.app.Fragment 开启activity
     *
     * @param fragment fragment we start from
     * @param intent intent to start
     * @return {@code true} if we start it safely, {@code false} if it's unsafe so we didn't start
     * it
     */
    public static boolean startActivitySafely(@NonNull android.app.Fragment fragment,
                                              @NonNull Intent intent) {
        return startActivitySafely(fragment, intent, null);
    }

    /**
     * 从android.app.Fragment 开启activity
     *
     * @param fragment fragment we start from
     * @param intent intent to start
     * @param options options used to start activity
     * @return {@code true} if we start it safely, {@code false} if it's unsafe so we didn't start
     * it
     */
    public static boolean startActivitySafely(@NonNull android.app.Fragment fragment,
                                              @NonNull Intent intent, Bundle options) {
        if (isIntentSafely(fragment.getActivity().getPackageManager(), intent)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                fragment.startActivity(intent, options);
            } else {
                fragment.startActivity(intent);
            }
            return true;
        }
        return false;
    }

    /**
     * 从android.app.Fragment开启需要返回结果的activity
     *
     * @param fragment fragment we start from
     * @param intent intent to start
     * @param requestCode request code
     * @return {@code true} if we start it safely, {@code false} if it's unsafe so we didn't start
     * it
     */
    public static boolean startActivityForResultSafely(@NonNull android.app.Fragment fragment,
                                                       @NonNull Intent intent, int requestCode) {
        return startActivityForResultSafely(fragment, intent, requestCode, null);
    }

    /**
     * 从android.app.Fragment开启需要返回结果的activity
     *
     * @param fragment fragment we start from
     * @param intent intent to start
     * @param requestCode request code
     * @param options options used to start activity
     * @return {@code true} if we start it safely, {@code false} if it's unsafe so we didn't start
     * it
     */
    public static boolean startActivityForResultSafely(@NonNull android.app.Fragment fragment,
                                                       @NonNull Intent intent, int requestCode, Bundle options) {
        if (isIntentSafely(fragment.getActivity().getPackageManager(), intent)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                fragment.startActivityForResult(intent, requestCode, options);
            } else {
                fragment.startActivityForResult(intent, requestCode);
            }
            return true;
        }
        return false;
    }

    /**
     * 从 Context开启activity
     *
     * @param context context we start from
     * @param intent intent to start
     * @return {@code true} if we start it safely, {@code false} if it's unsafe so we didn't start
     * it
     */
    public static boolean startActivitySafely(@NonNull Context context, @NonNull Intent intent) {
        return startActivitySafely(context, intent, null);
    }

    /**
     * 从 Context开启activity
     *
     * @param context context we start from
     * @param intent intent to start
     * @param options options used to start activity
     * @return {@code true} if we start it safely, {@code false} if it's unsafe so we didn't start
     * it
     */
    public static boolean startActivitySafely(@NonNull Context context, @NonNull Intent intent, Bundle options) {
        if (isIntentSafely(context.getPackageManager(), intent)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                context.startActivity(intent, options);
            } else {
                context.startActivity(intent);
            }
            return true;
        }
        return false;
    }

    /**
     *从 Activity开启另一个有结果的activity
     *
     * @param activity activity we start from
     * @param intent intent to start
     * @param requestCode request code
     * @return {@code true} if we start it safely, {@code false} if it's unsafe so we didn't start
     * it
     */
    public static boolean startActivityForResultSafely(@NonNull Activity activity, @NonNull Intent intent, int requestCode) {
        return startActivityForResultSafely(activity, intent, requestCode, null);
    }

    /**
     * 从 Activity开启另一个有结果的activity
     *
     * @param activity activity we start from
     * @param intent intent to start
     * @param requestCode request code
     * @param options options used to start activity
     * @return {@code true} if we start it safely, {@code false} if it's unsafe so we didn't start
     * it
     */
    public static boolean startActivityForResultSafely(@NonNull Activity activity, @NonNull Intent intent, int requestCode, Bundle options) {
        if (isIntentSafely(activity.getPackageManager(), intent)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                activity.startActivityForResult(intent, requestCode, options);
            } else {
                activity.startActivityForResult(intent, requestCode);
            }
            return true;
        }
        return false;
    }

    /**
     * 判断当前的Intent是否是合法的
     * @param pm
     * @param intent
     * @return
     */
    private static boolean isIntentSafely(PackageManager pm ,Intent intent){
        return !pm.queryIntentActivities(intent, 0).isEmpty();
    }
}
