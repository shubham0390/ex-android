/*
 * Copyright (c) 2016. . The Km2Labs Project
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.enfle.spendview.database.dataadapters;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import com.enfle.spendview.database.api.CategoryDataAdapter;
import com.enfle.spendview.database.content.ExpenseCategory;
import com.enfle.spendview.database.content.ModelFactory;
import com.enfle.spendview.database.content.contract.CategoryContract;
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
    protected void setId(ExpenseCategory expenseCategory, long id) {
        expenseCategory.setId(id);
    }
}
