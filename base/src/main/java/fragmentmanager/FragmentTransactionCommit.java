package fragmentmanager;

import android.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * @作者 liudazhi
 * @创建日期 2017/6/27
 */

public class FragmentTransactionCommit {

    private List<FragmentTransaction> mTransactionList = new ArrayList<>();

    /**
     * 在onResume()提交commit,否者添加到链表,下次onResume中提交
     * @param transactionCommitter
     * @param transaction
     * @return
     */
    public synchronized boolean safeCommit(TransactionCommitter transactionCommitter, FragmentTransaction transaction){
        if (transactionCommitter.isCommitterResumed()) {
            transaction.commit();
            return true;
        } else {
            mTransactionList.add(transaction);
            return false;
        }
    }

    public synchronized  boolean onResume(){
        if (!mTransactionList.isEmpty()) {
            for (FragmentTransaction mTransaction : mTransactionList) {
                mTransaction.commit();
            }
            mTransactionList.clear();
            return true;
        }
        return false;
    }
}
