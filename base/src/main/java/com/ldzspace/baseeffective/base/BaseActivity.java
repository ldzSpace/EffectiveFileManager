package com.ldzspace.baseeffective.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import di.BaseComponent;
import di.HasComponent;
import fragmentmanager.SupportFragmentTransactionCommit;
import fragmentmanager.TransactionCommitter;

/**
 * @作者 liudazhi
 * @创建日期 2017/6/8
 */

public class BaseActivity<C extends BaseComponent> extends AppCompatActivity implements HasComponent<C>,TransactionCommitter {

    /** 用于安全提交 */
    private final SupportFragmentTransactionCommit mFragmentCommit = new SupportFragmentTransactionCommit();
    private volatile boolean mIsResume = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        mIsResume = true;
    }

    public boolean safeFragmentCommit(@NonNull FragmentTransaction transaction){
        return mFragmentCommit.safeCommit(this, transaction);
    }

    @Override
    public boolean isCommitterResumed() {
        return mIsResume;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIsResume = false;
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        mIsResume = true;
        mFragmentCommit.onResumed();
    }

    public void safeStartActivity(){

    }

    @Override
    public C getComponent() {
        return null;
    }
}
