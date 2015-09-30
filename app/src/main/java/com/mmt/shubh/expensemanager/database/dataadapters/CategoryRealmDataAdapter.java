package com.mmt.shubh.expensemanager.database.dataadapters;

import android.content.Context;

import com.mmt.shubh.expensemanager.database.api.CategoryDataAdapter;
import com.mmt.shubh.expensemanager.database.content.ExpenseCategory;
import com.mmt.shubh.expensemanager.database.content.contract.CategoryContract;

import java.util.List;

/**
 * Created by styagi on 5/28/2015.
 */
public class CategoryRealmDataAdapter extends AbsRealmDataAdapter<ExpenseCategory> implements CategoryDataAdapter<ExpenseCategory>, CategoryContract {


    public CategoryRealmDataAdapter(Context context) {
        super(context);
    }


    @Override
    public long create(ExpenseCategory expenseCategory) {
        super.save(expenseCategory);
        return 0 ;
    }

    @Override
    public ExpenseCategory delete(ExpenseCategory expenseCategory) {
        return null;
    }

    @Override
    public ExpenseCategory delete(long id) {
        return null;
    }



    @Override
    public ExpenseCategory get(long id) {
        return null;
    }

    @Override
    public List<ExpenseCategory> getAll() {
        return null;
    }
}
