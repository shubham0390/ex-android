package com.mmt.shubh.expensemanager.database.dataadapters;

import android.content.Context;

import com.mmt.shubh.expensemanager.database.api.ExpenseDataAdapter;
import com.mmt.shubh.expensemanager.database.content.ExpenseCategory;
import com.mmt.shubh.expensemanager.database.content.Expense;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.database.content.Member;
import com.mmt.shubh.expensemanager.database.content.contract.ExpenseContract;

import java.util.List;

/**
 * Created by styagi on 5/28/2015.
 */
public class ExpenseRealmDataAdapter extends AbsRealmDataAdapter<Expense> implements ExpenseDataAdapter<Expense>, ExpenseContract {


    public ExpenseRealmDataAdapter(Context context) {
        super(context);
    }

    private ExpenseCategory getCategory(long aLong) {
        return null;
    }

    private ExpenseBook getExpenseBook(long aLong) {
        return null;
    }

    private List<Member> getMemberList(long aLong) {
        return null;
    }

    @Override
    public long create(Expense expense) {
        save(expense);
        return 0;
    }

    @Override
    public Expense delete(Expense expense) {
        return null;
    }

    @Override
    public Expense delete(long id) {
        return null;
    }


    @Override
    public Expense get(long id) {
        return null;
    }

    @Override
    public List<Expense> getAll() {
        return null;
    }


}
