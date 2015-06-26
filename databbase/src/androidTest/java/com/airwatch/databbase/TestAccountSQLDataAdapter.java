package com.airwatch.databbase;

import android.test.AndroidTestCase;

import com.mmt.shubh.expensemanager.database.content.Account;
import com.mmt.shubh.expensemanager.database.dataadapters.AccountSQLDataAdapter;

/**
 * Created by styagi on 5/31/2015.
 */
public class TestAccountSQLDataAdapter extends AndroidTestCase /*implements TestDataAdapter*/ {

    private AccountSQLDataAdapter mDataAdapter;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mDataAdapter = new AccountSQLDataAdapter(getContext());
    }

  /*  @Override
    public void testCreate() {
        Account account = new Account();
        account.setUserName("shubham");
        account.setUserPassword("shhubham");
        account.setStatus(Account.Status.ACTIVE);
        long result = mDataAdapter.create(account);
        assertTrue(result > 0);

    }

    @Override
    public void testUpdate() {
        Account account = new Account();
        account.setUserName("shubham");
        account.setUserPassword("shhubham");
        account.setStatus(Account.Status.ACTIVE);
        long id = mDataAdapter.create(account);
        account.setUserName("shubham1");
        mDataAdapter.update(account);
        assertEquals(account.getUserName(), mDataAdapter.get(id).getUserName());
    }

    @Override
    public void testDelete() {
        Account account = new Account();
        account.setUserName("shubham");
        account.setUserPassword("shhubham");
        account.setStatus(Account.Status.ACTIVE);
        long id = mDataAdapter.create(account);
        mDataAdapter.delete(account);
    }

    @Override
    public void testDeleteById() {
        Account account = new Account();
        account.setUserName("shubham");
        account.setUserPassword("shhubham");
        account.setStatus(Account.Status.ACTIVE);
        long id = mDataAdapter.create(account);
        mDataAdapter.delete(id);
    }

    @Override
    public void testGet() {
        Account account = new Account();
        account.setUserName("shubham");
        account.setUserPassword("shhubham");
        account.setStatus(Account.Status.ACTIVE);
        long result = mDataAdapter.create(account);
        Account account1 = mDataAdapter.get(result);
        assertEquals(account.getUserName(), account1.getUserName());
    }

    @Override
    public void testGetAll() {
        Account account = new Account();
        account.setUserName("shubham");
        account.setUserPassword("shhubham");
        account.setStatus(Account.Status.ACTIVE);
        long result = mDataAdapter.create(account);
        Account account1 = new Account();
        account.setUserName("shubham");
        account.setUserPassword("shhubham");
        account.setStatus(Account.Status.ACTIVE);
        mDataAdapter.create(account);
        assertTrue(mDataAdapter.getAll().size() == 2);
    }*/

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        mDataAdapter.deleteAll();
    }
}
