package com.mmt.shubh.expensemanager.database.content.contract;

import android.provider.BaseColumns;

public interface CategoryContract extends BaseColumns, BaseContract {
    String TABLE_NAME = "expense_category";
    String PATH_CATEGORY = "Category";

    int ID_COLUMN = 0;
    int NAME_COLUMN = 1;
    String CATEGORY_NAME = "category_name";
    String CATEGORY_TYPE = "category_type";
    String CATEGORY_IMAGE_NAME = "category_image";
}