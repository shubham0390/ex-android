package com.airwatch.databbase;

import android.test.AndroidTestCase;

import com.mmt.shubh.expensemanager.database.content.Expense;
import com.mmt.shubh.expensemanager.database.dataadapters.ExpenseSqlDataAdapter;

/**
 * Created by styagi on 5/31/2015.
 */
public class TestExpenseSQLDataAdapter extends AndroidTestCase implements TestDataAdapter {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ExpenseSqlDataAdapter dataAdapter = new ExpenseSqlDataAdapter();
    }


    @Override
    public void testCreate() {
    Expense expense =  new Expense();

    }

    @Override
    public void testUpdate() {

    }

    @Override
    public void testDelete() {

    }

    @Override
    public void testDeleteById() {

    }

    @Override
    public void testGet() {

    }

    @Override
    public void testGetAll() {

    }
}
