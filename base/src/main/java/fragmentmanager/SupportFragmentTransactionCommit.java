package fragmentmanager;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * @作者 liudazhi
 * @创建日期 2017/6/27
 */

public class SupportFragmentTransactionCommit {

    private final List<FragmentTransaction> mTransactionList = new ArrayList<>();

    /**
     * commit a {@link android.support.v4.app.FragmentTransaction} from
     * {@link TransactionCommitter} safely
     *
     * @param committer committer we commit from
     * @param transaction transaction to commit
     * @return {@code true} if it's unsafe to commit now, it will be committed when
     * {@link #onResumed()} is called
     */
    public synchronized boolean safeCommit(@NonNull TransactionCommitter committer, @NonNull android.support.v4.app.FragmentTransaction transaction) {
        if (committer.isCommitterResumed()) {
            transaction.commit();
            return false;
        } else {
            mTransactionList.add(transaction);
            return true;
        }
    }

    /**
     * called at the {@code onResume()} life cycle method, e.g.
     * {@link android.app.Fragment#onResume()},
     * {@link android.support.v4.app.Fragment#onResume()},
     * {@link android.app.Activity#onResume()},
     * {@link android.support.v7.app.AppCompatActivity#onResumeFragments()},
     * it will commit pending transactions.
     *
     * @return {@code true} if pending transactions are committed.
     */
    public synchronized boolean onResumed() {
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
