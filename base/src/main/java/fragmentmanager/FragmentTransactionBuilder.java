package fragmentmanager;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.IdRes;
import android.text.TextUtils;

/**
 * @作者 liudazhi
 * @创建日期 2017/6/27
 * @描述:
 * 对android.app.Fragment的事件操作
 */

public class FragmentTransactionBuilder {

    private FragmentManager mFragmentManager;
    private FragmentTransaction mTransaction;

    private FragmentTransactionBuilder(FragmentManager fm, FragmentTransaction transaction){
        this.mFragmentManager = fm;
        this.mTransaction = transaction;
    }

    public FragmentTransactionBuilder transaction(FragmentManager fm) {
        return new FragmentTransactionBuilder(fm,fm.beginTransaction());
    }

    public FragmentTransactionBuilder add(Fragment fragment, String tag) throws IllegalStateException{
        checkTag(tag);
        checkTagNotExist(mFragmentManager, tag);
        mTransaction.add(fragment, tag);
        return this;
    }

    public FragmentTransactionBuilder add(Fragment fragment, String tag, String name) throws IllegalStateException{
        checkTag(tag);
        checkTag(name);
        checkTagNotExist(mFragmentManager, tag);
        mTransaction.add(fragment, tag).addToBackStack(name);
        return this;
    }

    /**
     *
     * @param id  第一次添加Framgent,是需要id的,因为需要知道挂在哪个容器下
     * @param fragment
     * @param tag
     * @return
     * @throws IllegalStateException
     */
    public FragmentTransactionBuilder add(@IdRes int id, Fragment fragment, String tag) throws IllegalStateException{
        checkId(id);
        checkTag(tag);
        checkIdNotExist(mFragmentManager, id);
        checkTagNotExist(mFragmentManager, tag);
        mTransaction.add(id,fragment,tag);
        return this;
    }

    /**
     *
     * @param id
     * @param fragment
     * @param tag
     * @param name
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalStateException
     */
    public FragmentTransactionBuilder add(@IdRes int id, Fragment fragment, String tag, String name)
            throws IllegalArgumentException, IllegalStateException {
        checkId(id);
        checkTag(tag);
        checkTag(name);
        checkIdNotExist(mFragmentManager, id);
        checkTagNotExist(mFragmentManager, tag);
        mTransaction.add(id, fragment, tag).addToBackStack(name);
        return this;
    }

    public FragmentTransactionBuilder remove(@IdRes int id) throws IllegalStateException{
        checkId(id);
        checkIdExist(mFragmentManager, id);
        mTransaction.remove(mFragmentManager.findFragmentById(id));
        return this;
    }

    public FragmentTransactionBuilder remove(String tag) throws IllegalStateException{
        checkTag(tag);
        checkTagExist(tag);
        mTransaction.remove(mFragmentManager.findFragmentByTag(tag));
        return this;
    }

    /**
     *
     * @param id activity的中的Fragment的容器id
     * @param fragment
     * @param tag
     * @return
     * @throws IllegalStateException
     */
    public FragmentTransactionBuilder replace(@IdRes int id, Fragment fragment, String tag) throws IllegalStateException {
        checkTag(tag);
        checkId(id);
        checkIdExist(mFragmentManager, id);
        Fragment old = mFragmentManager.findFragmentById(id);
        if (!TextUtils.equals(old.getTag(), tag)) {
            checkTagNotExist(mFragmentManager, tag);
        }
        mTransaction.replace(id,fragment,tag);
        return this;
    }

    public void checkTagExist(String tag) throws IllegalStateException{
        if (mFragmentManager.findFragmentByTag(tag) == null) {
            throw new IllegalStateException("fragment with tag " + tag + " not exist!");
        }
    }

    private void checkTag(String tag){
        if (TextUtils.isEmpty(tag)){
            throw new IllegalStateException("tag is empty");
        }
    }

    private void checkIdExist(FragmentManager fragmentManager, @IdRes int id) throws IllegalStateException{
        if (mFragmentManager.findFragmentById(id) == null) {
            throw new IllegalStateException("fragment with tag " + id + " not exist!");
        }
    }

    private void checkId(@IdRes int id) throws IllegalArgumentException {
        if (id <= 0) {
            throw new IllegalArgumentException("id: " + id + " <= 0");
        }
    }

    private void checkTagNotExist(FragmentManager fragmentManager, String tag)
            throws IllegalStateException {
        if (fragmentManager.findFragmentByTag(tag) != null) {
            throw new IllegalStateException("fragment with tag "
                    + tag
                    + " already exist: "
                    + fragmentManager.findFragmentByTag(tag));
        }
    }

    private void checkIdNotExist(FragmentManager fragmentManager, @IdRes int id)
            throws IllegalStateException {
        if (fragmentManager.findFragmentById(id) != null) {
            throw new IllegalStateException("fragment with id "
                    + id
                    + " already exist: "
                    + fragmentManager.findFragmentById(id));
        }
    }
}
