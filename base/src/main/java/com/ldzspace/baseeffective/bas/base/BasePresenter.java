package com.ldzspace.baseeffective.bas.base;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.ldzspace.baseeffective.bas.helper.rx.RxDelegate;

import rx.Subscription;

/**
 * @作者 liudazhi
 * @创建日期 2017/6/8
 */

public abstract class BasePresenter<V extends MvpView> extends MvpBasePresenter<V> {
    private RxDelegate mRxDelegate;

    protected BasePresenter(){
        super();
        mRxDelegate = new RxDelegate();
        mRxDelegate.onCreate();
    }

    /**
     * 添加subscription, 防止Rx泄露
     * @param subscription
     * @return
     */
    public boolean addSubscriptionToStop(Subscription subscription){
        return mRxDelegate.addSubscriptionStop(subscription);
    }

    public boolean addSubscriptionToDestory(Subscription subscription){
        return mRxDelegate.addSubscriptionDestory(subscription);
    }

    /**
     * 删除subscription
     * @param subscription
     */
    public void removeSubscription(Subscription subscription){
         mRxDelegate.remove(subscription);
    }

    @Override
    public void attachView(V view) {
        super.attachView(view);
        mRxDelegate.onStart();
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        mRxDelegate.onStop();
    }

    public void onDestroy() {
        mRxDelegate.onDestroy();
    }
}
