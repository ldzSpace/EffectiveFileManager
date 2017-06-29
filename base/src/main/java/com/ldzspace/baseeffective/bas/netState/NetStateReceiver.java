package com.ldzspace.baseeffective.bas.netState;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.ldzspace.baseeffective.bas.utils.LogUtil;
import com.ldzspace.baseeffective.bas.utils.NetUtil;

import java.util.ArrayList;

import static com.ldzspace.baseeffective.bas.utils.NetUtil.isNetworkAvailable;

/**
 * @作者 liudazhi
 * @创建日期 2017/6/28
 */

public class NetStateReceiver extends BroadcastReceiver {
    private final static String TAG = "NetStateReceiver";
    private final static String ANDROID_NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    public final static String CUSTOM_ANDROID_NET_CHANGE_ACTION = "com.ldzspace.baseeffective.net.conn.CONNECTIVITY_CHANGE";

    private static ArrayList<NetChangeObserver> mNetChangeObservers = new ArrayList<>();
    private static BroadcastReceiver mBroadcastReceiver;
    private boolean isNetAvailable;
    private String mNetType;

    /**
     * 创建广播实例
     * @return
     */
    private static BroadcastReceiver instance() {
        if (null == mBroadcastReceiver) {
            synchronized (NetStateReceiver.class) {
                if (null == mBroadcastReceiver) {
                    mBroadcastReceiver = new NetStateReceiver();
                }
            }
        }
        return mBroadcastReceiver;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase(ANDROID_NET_CHANGE_ACTION) || intent.getAction().equalsIgnoreCase(CUSTOM_ANDROID_NET_CHANGE_ACTION)) {
            if (isNetworkAvailable(context)) {
                LogUtil.i(TAG, "<--- network connected --->");
                isNetAvailable = true;
                mNetType = NetUtil.getNetworkType(context);
            } else {
                LogUtil.i(TAG, "<--- network disconnected --->");
                isNetAvailable = false;
            }
            notifyObserver(context);
        }
    }

    /**
     * 注册广播
     * @param context
     */
    public static void registerNetworkStateReceiver(Context context){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CUSTOM_ANDROID_NET_CHANGE_ACTION);
        intentFilter.addAction(ANDROID_NET_CHANGE_ACTION);
        context.getApplicationContext().registerReceiver(instance(),intentFilter);
    }

    /**
     * 主动通知网络状态改变
     * @param mContext
     */
    public static void checkNetworkState(Context mContext) {
        Intent intent = new Intent();
        intent.setAction(CUSTOM_ANDROID_NET_CHANGE_ACTION);
        mContext.sendBroadcast(intent);
    }

    /**
     * 注销广播
     */
    public static void unRegisterNetworkStateReceiver(Context context){
        if (mBroadcastReceiver != null) {
            try {
                context.getApplicationContext().unregisterReceiver(mBroadcastReceiver);
            } catch (IllegalArgumentException e){
                LogUtil.d(TAG, e.getMessage());
            }
        }
    }

    /**
     * 唤醒观察者
     */
    private void notifyObserver(Context context) {
        if (!mNetChangeObservers.isEmpty()) {
            int size = mNetChangeObservers.size();
            for (int i = 0; i < size; i++) {
                NetChangeObserver observer = mNetChangeObservers.get(i);
                if (observer != null) {
                    if (isNetworkAvailable(context)) {
                        observer.onNetConnected(mNetType);
                    } else {
                        observer.onNetDisConnect();
                    }
                }
            }
        }
    }

    /**
     * 注册观察者
     * @param observer
     */
    public static void registerObserver(NetChangeObserver observer){
        mNetChangeObservers.add(observer);
    }

    /**
     * 注销观察者
     * @param observer
     */
    public static void unRegisterObserver(NetChangeObserver observer){
        if (mNetChangeObservers.contains(observer)) mNetChangeObservers.remove(observer);
    }
}
