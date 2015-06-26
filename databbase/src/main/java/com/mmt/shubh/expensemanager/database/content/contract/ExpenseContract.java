package com.mmt.shubh.expensemanager.database.content.contract;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public interface ExpenseContract extends BaseColumns, BaseContract {
    String TABLE_NAME = "exapnse";

    String PATH_EXPENSE = "EXPENSE";


    int COLUMN_EXPENSE_ID = 0;
    int COLUMN_EXPENSE_AMOUNT = 1;
    int COLUMN_EXPENSE_DATE = 2;
    int COLUMN_EXPENSE_NAME = 3;
    int COLUMN_EXPENSE_PLACE = 4;
    int COLUMN_EXPENSE_DESCRIPTION = 5;
    int COLUMN_EXPENSE_BOOK = 6;
    int COLUMN_CATEGORY_KEY = 7;
    int COLUMN_MEMBER_NAME = 8;

    String EXPENSE_AMOUNT = "expense_amount";
    String EXPENSE_DATE = "expense_date";
    String EXPENSE_NAME = "expense_name";
    String EXPENSE_PLACE = "expense_place";
    String EXPENSE_DESCRIPTION = "expense_description";
    String MEMBER_KEY = "member_key";
    String EXPENSE_BOOK_KEY = "group_key";
    String CATEGORY_KEY = "category_key";

    Uri EXPENSE_URI = new Uri.Builder()
            .scheme(ContentResolver.SCHEME_CONTENT)
            .authority(AUTHORITY)
            .path(PATH_EXPENSE)
            .build();

    Uri EXPENSE_MEMBER_URI = EXPENSE_URI.buildUpon()
            .path(MemberContract.PATH_MEMBER)
            .build();

}