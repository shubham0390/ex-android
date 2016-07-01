package com.mmt.shubh.expensemanager.database.dataadapters;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import com.mmt.shubh.expensemanager.database.api.CategoryDataAdapter;
import com.mmt.shubh.expensemanager.database.content.ExpenseCategory;
import com.mmt.shubh.expensemanager.database.content.ModelFactory;
import com.mmt.shubh.expensemanager.database.content.contract.CategoryContract;
import com.squareup.sqlbrite.BriteDatabase;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by styagi on 5/28/2015.
 */
@Singleton
public class CategorySQLDataAdapter extends BaseSQLDataAdapter<ExpenseCategory> implements CategoryDataAdapter, CategoryContract {

    @Inject
    public CategorySQLDataAdapter(BriteDatabase briteDatabase) {
        super(TABLE_NAME, briteDatabase);
    }

    @Override
    public ExpenseCategory parseCursor(Cursor cursor) {
        ExpenseCategory expenseCategory = ModelFactory.getCategory();
        expenseCategory.setId(cursor.getLong(ID_COLUMN));
        expenseCategory.setCategoryName(cursor.getString(NAME_COLUMN));
        return expenseCategory;
    }

    public ContentValues toContentValues(ExpenseCategory expenseCategory) {
        ContentValues values = new ContentValues();
        if (!TextUtils.isEmpty(expenseCategory.getCategoryName())) {
            values.put(CATEGORY_NAME, expenseCategory.getCategoryName());
        }
        if (!TextUtils.isEmpty(expenseCategory.getCategoryType())) {
            values.put(CATEGORY_TYPE, expenseCategory.getCategoryType());
        }
        if (!TextUtils.isEmpty(expenseCategory.getCategoryImageName())) {
            values.put(CATEGORY_IMAGE_NAME, expenseCategory.getCategoryImageName());
        }
        return values;
    }


    @Override
    protected void setTaskId(ExpenseCategory expenseCategory, long id) {
        expenseCategory.setId(id);
    }
}
