package com.mmt.shubh.expensemanager.database.dataadapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.mmt.shubh.expensemanager.database.content.ExpenseCategory;
import com.mmt.shubh.expensemanager.database.content.contract.CategoryContract;

import java.util.List;

import com.mmt.shubh.expensemanager.database.api.CategoryDataAdapter;

/**
 * Created by styagi on 5/28/2015.
 */
public class CategorySQLDataAdapter extends BaseSQLDataAdapter<ExpenseCategory> implements CategoryDataAdapter<ExpenseCategory>, CategoryContract {


    public CategorySQLDataAdapter(Context context) {
        super(CategoryContract.CATEGORY_URI,context);
    }

    public ContentValues toContentValues(ExpenseCategory expenseCategory) {
        ContentValues values = new ContentValues();
        values.put(CATEGORY_NAME, expenseCategory.getCategoryName());
        return values;
    }

    public void restore(Cursor cursor, ExpenseCategory expenseCategory) {
        expenseCategory.setId(cursor.getLong(ID_COLUMN));
        expenseCategory.setCategoryName(cursor.getString(NAME_COLUMN));
    }


    @Override
    public long create(ExpenseCategory expenseCategory) {
        super.save(expenseCategory);
        return 0 ;
    }

    @Override
    public int update(ExpenseCategory expenseCategory) {
        return update(expenseCategory);
    }

    @Override
    public int delete(ExpenseCategory expenseCategory) {
        return 0;
    }

    @Override
    public int delete(long id) {
        return 0;
    }

    @Override
    public int deleteAll() {
        return 0;
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
