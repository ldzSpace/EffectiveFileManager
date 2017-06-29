package com.ldzspace.baseeffective.bas.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;
import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.ldzspace.baseeffective.bas.activityManager.StartActivityDelegate;
import com.ldzspace.baseeffective.bas.di.BaseComponent;
import com.ldzspace.baseeffective.bas.di.HasComponent;
import com.ldzspace.baseeffective.bas.fragmentmanager.SupportFragmentTransactionCommit;
import com.ldzspace.baseeffective.bas.fragmentmanager.TransactionCommitter;
import com.ldzspace.baseeffective.bas.netState.NetChangeObserver;
import com.ldzspace.baseeffective.bas.netState.NetStateReceiver;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @作者 liudazhi
 * @创建日期 2017/6/8
 *  泛型详解,泛型只在编译时期有效,在类名后面声明泛型BaseFragment<V extends MvpView, P extends BasePresenter<V>, C extends BaseComponent>
 *      而使用的时候,该类如果需要什么样的类型就给什么样的类型 ,比如MvpFragment<V, P>,mvpFragment需要两种类型,我们
 *      就传入两种类型
 */

public abstract class BaseFragment<V extends MvpView, P extends BasePresenter<V>, C extends BaseComponent> extends MvpFragment<V, P> implements TransactionCommitter{

    /** 用于安全提交 */
    private final SupportFragmentTransactionCommit mFragmentCommit = new SupportFragmentTransactionCommit();
    private boolean mHasStartBusinessed = false;
    protected C mComponent;
    protected Unbinder mBinder;
    protected Context mContext;
    private NetChangeObserver mNetChangeObserver = new NetChangeObserver(){
        @Override
        public void onNetConnected(String netType) {
            super.onNetConnected(netType);
            onNetworkConnected(netType);
        }

        @Override
        public void onNetDisConnect() {
            super.onNetDisConnect();
            onNetworkDisConnected();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        // 从宿主activity中获取component,宿主activity实现了HasComponent的接口,我们会在实现方法返回component
        mComponent = ((HasComponent<C>) mContext).getComponent();
        injectDependencies(mComponent);
        // 交给子类创建Presenter
        createPresenter();
        // 注册观察者
        NetStateReceiver.registerObserver(mNetChangeObserver);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutRes(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
    }

    @Override
    public void onStart() {
        super.onStart();
        // 在onStart内部开启业务,只允许在onStart方法中执行一次startBusiness方法,只要fragment不销毁,就只会执行一次
        if (!mHasStartBusinessed) {
            mHasStartBusinessed = true;
            startBusiness();
        }
        // 判断是否开启EventBus
        if (isBindEventBusFragment()) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mFragmentCommit.onResumed();
    }

    @Override
    public void onStop() {
        super.onStop();
        // 注销eventBus
        if (isBindEventBusFragment()) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unBindView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 在prensenter内部定义了销毁方法
        presenter.onDestroy();
        NetStateReceiver.unRegisterObserver(mNetChangeObserver);
    }

    /** 主动提交,而不是一定是onResume提交,我们知道BaseActivity也存在主动提交,*/
    public boolean safeFragmentCommit(FragmentTransaction transaction){
        return mFragmentCommit.safeCommit(this, transaction);
    }

    /**
     * 开启一个新的activity
     * @param intent
     */
    protected boolean startActivitySafely(Intent intent){
        return StartActivityDelegate.startActivitySafely(this, intent);
    }

    /**
     * 开启有返回值得activity
     * @param intent
     * @param code
     */
    protected boolean startActivityForResultSafely(Intent intent, int code){
        return StartActivityDelegate.startActivityForResultSafely(this, intent, code);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 使用com.vanniktech:onactivityresult库,漂亮的处理onActivityResult,使用注解完成处理
        // ActivityResult.onResult(requestCode, resultCode, data).into(this);
    }

    @Override
    public boolean isCommitterResumed() {
        return isResumed();
    }

    /**
     * 绑定butternife
     * @param view
     */
    protected void bindView(View view){
        if (autoBind()) mBinder = ButterKnife.bind(view);
    }

    /***
     * 解绑butternife
     */
    protected void unBindView(){
        if (autoBind()) mBinder.unbind();
    }

    /**
     * 子类复写的话可以不用自动绑定
     * @return
     */
    protected  boolean autoBind(){
        return true;
    }

    /**
     * 提供给子Fragment将自身注册到component中
     * @param component
     */
    protected abstract void injectDependencies(C component);

    /**
     * 提供给子Fragment填充布局
     * @return
     */
    protected abstract int getLayoutRes();

    /**
     * 开启业务
     */
    protected abstract void startBusiness();

    /**
     * 网络连接
     */
    protected abstract void onNetworkConnected(String type);

    /**
     * 网络断开
     */
    protected abstract void onNetworkDisConnected();

    /**
     * 是否绑定eventBus
     * @return
     */
    protected abstract boolean isBindEventBusFragment();
}
