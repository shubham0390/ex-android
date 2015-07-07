package com.mmt.shubh.expensemanager.database.dataadapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import api.CategoryDataAdapter;
import com.mmt.shubh.expensemanager.database.content.Category;
import com.mmt.shubh.expensemanager.database.content.contract.CategoryContract;

import java.util.List;

/**
 * Created by styagi on 5/28/2015.
 */
public class CategorySQLDataAdapter extends BaseSQLDataAdapter<Category> implements CategoryDataAdapter<Category>, CategoryContract {


    public CategorySQLDataAdapter(Context context) {
        super(context);
    }

    public ContentValues toContentValues(Category category) {
        ContentValues values = new ContentValues();
        values.put(CATEGORY_NAME, category.getCategoryName());
        return values;
    }

    public void restore(Cursor cursor, Category category) {
        category.setId(cursor.getLong(ID_COLUMN));
        category.setCategoryName(cursor.getString(NAME_COLUMN));
    }


    @Override
    public long create(Category category) {
        super.save(category);
        return 0 ;
    }

    @Override
    public int update(Category category) {
        return update(category);
    }

    @Override
    public int delete(Category category) {
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
    public Category get(long id) {
        return null;
    }

    @Override
    public List<Category> getAll() {
        return null;
    }
}
