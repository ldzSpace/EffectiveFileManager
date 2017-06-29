package com.ldzspace.baseeffective.bas.base;

import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.ldzspace.base.R;
import com.ldzspace.baseeffective.bas.di.BaseComponent;
import com.ldzspace.baseeffective.bas.di.HasComponent;
import com.ldzspace.baseeffective.bas.fragmentmanager.SupportFragmentTransactionCommit;
import com.ldzspace.baseeffective.bas.fragmentmanager.TransactionCommitter;

import org.greenrobot.eventbus.EventBus;

/**
 * @作者 liudazhi
 * @创建日期 2017/6/8
 */

public abstract class BaseActivity<C extends BaseComponent> extends AppCompatActivity implements HasComponent<C>,TransactionCommitter {

    /**转场模式*/
    public enum TransitionMode { LEFT, RIGHT, TOP, BOTTOM, SCALE, FADE }
    /** 用于安全提交 */
    private final SupportFragmentTransactionCommit mFragmentCommit = new SupportFragmentTransactionCommit();
    private volatile boolean mIsResume = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        selectOverridePendingTransition();
        super.onCreate(savedInstanceState, persistentState);
        mIsResume = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 判断是否开启EventBus
        if (isBindEventBusActivity()) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 注销eventBus
        if (isBindEventBusActivity()) {
            EventBus.getDefault().register(this);
        }
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

    @Override
    public void finish() {
        super.finish();
        selectOverridePendingTransition();
    }

    @Override
    public C getComponent() {
        return null;
    }

    /**
     * 开启activity
     */
    public void safeStartActivity(){

    }

    /**
     * 添加activity之间的转换动画
     */
    public void selectOverridePendingTransition(){
        if (toggleOverridePendingTransition()) {
            switch (getOverridePendingTransitionMode()) {
                case LEFT:
                    overridePendingTransition(R.anim.left_in,R.anim.left_out);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.right_in,R.anim.right_out);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.top_in,R.anim.top_out);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.bottom_in,R.anim.bottom_out);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.scale_in,R.anim.scale_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    break;
            }
        }
    }

    /**
     * 设置状态栏透明
     * @param on
     */
    protected void setTranslucentStatus(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }
    /**
     * 提交Fragment事务
     * @param transaction
     * @return
     */
    public boolean safeFragmentCommit(@NonNull FragmentTransaction transaction){
        return mFragmentCommit.safeCommit(this, transaction);
    }

    @Override
    public boolean isCommitterResumed() {
        return mIsResume;
    }

    /**
     * 开启界面转换动画的开关
     * @return
     */
    protected abstract boolean toggleOverridePendingTransition();

    /**
     * 获取转场模式
     * @return
     */
    protected abstract TransitionMode getOverridePendingTransitionMode();

    /**
     * 选中是否绑定eventBus
     * @return
     */
    protected abstract boolean isBindEventBusActivity();
}
