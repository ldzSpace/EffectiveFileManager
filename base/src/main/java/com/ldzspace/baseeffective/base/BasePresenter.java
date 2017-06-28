package com.ldzspace.baseeffective.base;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * @作者 liudazhi
 * @创建日期 2017/6/8
 */

public class BasePresenter<V extends MvpView> extends MvpBasePresenter<V> {
    @Override
    public void attachView(V view) {

    }

    @Override
    public void detachView(boolean retainInstance) {

    }
}
