package com.ldzspace.baseeffective.base;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import di.BaseComponent;
import di.HasComponent;
import fragmentmanager.SupportFragmentTransactionCommit;
import fragmentmanager.TransactionCommitter;

/**
 * @作者 liudazhi
 * @创建日期 2017/6/8
 */

public abstract class BaseFragment<V extends MvpView, P extends BasePresenter<V>, C extends BaseComponent> extends MvpFragment implements TransactionCommitter{

    /** 用于安全提交 */
    private final SupportFragmentTransactionCommit mFragmentCommit = new SupportFragmentTransactionCommit();
    private volatile boolean mIsResume = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        C component = ((HasComponent<C>) getActivity()).getComponent();
        createPresenter();
    }

    /** 主动提交,而不是一定是onResume提交,我们知道BaseActivity也存在主动提交,*/
    public boolean safeFragmentCommit(FragmentTransaction transaction){
        return mFragmentCommit.safeCommit(this, transaction);
    }

    @Override
    public boolean isCommitterResumed() {
        return isResumed();
    }

    @Override
    public void onResume() {
        super.onResume();
        mFragmentCommit.onResumed();
    }
}
