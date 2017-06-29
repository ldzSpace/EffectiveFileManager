package com.ldzspace.baseeffective.bas.helper.rx;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * @作者 liudazhi
 * @创建日期 2017/6/29
 */

public class RxDelegate {
    private CompositeSubscription mSubscriptionsToDestroy;
    private CompositeSubscription mSubscriptionsToStop;

    /**
     * 添加在onDestory销毁的subscription操作
     * @param subscription
     * @return
     */
    public synchronized boolean addSubscriptionDestory(Subscription subscription){
        if (mSubscriptionsToDestroy == null) {
            throw new IllegalStateException(" addUtilStop should be called between onCreate and onDestroy");
        }
        mSubscriptionsToDestroy.add(subscription);
        return true;
    }

    /**
     * 添加在onStop销毁的subscription操作
     * @param subscription
     * @return
     */
    public synchronized boolean addSubscriptionStop(Subscription subscription){
        if (mSubscriptionsToStop == null) {
            throw new IllegalStateException(" addUtilStop should be called between onStart and onStop");
        }
        mSubscriptionsToStop.add(subscription);
        return true;
    }

    /**
     * 移除操作
     * @param subscription
     */
    public synchronized void remove(Subscription subscription) {
        if (mSubscriptionsToDestroy == null && mSubscriptionsToStop == null) {
            throw new IllegalStateException("remove should not be called after onDestroy");
        }
        if (mSubscriptionsToStop != null) {
            mSubscriptionsToStop.remove(subscription);
        }
        if (mSubscriptionsToDestroy != null) {
            mSubscriptionsToDestroy.remove(subscription);
        }
    }


    public synchronized void onCreate() {
        if (mSubscriptionsToDestroy != null) {
            throw new IllegalStateException("onCreate called multiple times");
        }
        mSubscriptionsToDestroy = new CompositeSubscription();
    }

    public synchronized void onStart() {
        if (mSubscriptionsToStop != null) {
            throw new IllegalStateException("onStart called multiple times");
        }
        mSubscriptionsToStop = new CompositeSubscription();
    }

    public synchronized void onStop() {
        if (mSubscriptionsToStop == null) {
            throw new IllegalStateException("onStop called multiple times or onStart not called");
        }
        mSubscriptionsToStop.unsubscribe();
        mSubscriptionsToStop = null;
    }

    public synchronized void onDestroy() {
        if (mSubscriptionsToDestroy == null) {
            throw new IllegalStateException(
                    "onDestroy called multiple times or onCreate not called");
        }
        mSubscriptionsToDestroy.unsubscribe();
        mSubscriptionsToDestroy = null;
    }
}
