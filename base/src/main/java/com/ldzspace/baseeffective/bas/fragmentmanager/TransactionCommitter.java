package com.ldzspace.baseeffective.bas.fragmentmanager;

/**
 *
 * @作者 liudazhi
 * @创建日期 2017/6/27
 * @描述  用于提交{@link android.app.FragmentTransaction} 或
 * {@link android.support.v4.app.FragmentTransaction} 表, 我们只在Activity 的Resumed方法的时候提交
 * 避免activity状态丢失的错误
 *
 */

public interface TransactionCommitter {
    /**
     * 判断是否在Resumed方法提交
     * @return
     */
    boolean isCommitterResumed();
}
