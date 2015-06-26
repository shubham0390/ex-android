package com.mmt.shubh.expensemanager.database.content.contract;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public interface CategoryContract extends BaseColumns, BaseContract {
    String TABLE_NAME = "expense_category";
    String PATH_CATEGORY = "Category";

    int ID_COLUMN = 0;
    int NAME_COLUMN = 1;
    String CATEGORY_NAME = "category_name";

    public static final Uri CATEGORY_URI = new Uri.Builder()
            .scheme(ContentResolver.SCHEME_CONTENT)
            .authority(AUTHORITY)
            .path(PATH_CATEGORY)
            .build();

    Uri CATEGORY_EXPENSE_URI = CATEGORY_URI.buildUpon()
            .path(ExpenseContract.PATH_EXPENSE)
            .build();

}