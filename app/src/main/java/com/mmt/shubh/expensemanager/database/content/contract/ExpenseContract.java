package com.mmt.shubh.expensemanager.database.content.contract;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public interface ExpenseContract extends BaseColumns, BaseContract {
    String TABLE_NAME = "expenses";

    String PATH_EXPENSE = "EXPENSE";
    String PATH_YEAR = "YEAR";
    String PATH_MONTH = "MONTH";

    int COLUMN_EXPENSE_ID = 0;
    int COLUMN_EXPENSE_AMOUNT = 1;
    int COLUMN_EXPENSE_DATE = 2;
    int COLUMN_EXPENSE_NAME = 3;
    int COLUMN_EXPENSE_PLACE = 4;
    int COLUMN_EXPENSE_DESCRIPTION = 5;
    int COLUMN_EXPENSE_BOOK = 6;
    int COLUMN_CATEGORY_KEY = 7;

    String EXPENSE_AMOUNT = "expense_amount";
    String EXPENSE_DATE = "expense_date";
    String EXPENSE_NAME = "expense_name";
    String EXPENSE_PLACE = "expense_place";
    String EXPENSE_DESCRIPTION = "expense_description";
    String EXPENSE_BOOK_KEY = "expense_book_key";
    String CATEGORY_KEY = "category_key";
    String TRANSACTION_KEY = "transaction_key";
    String OWNER_KEY = "owner_key";

    Uri EXPENSE_URI = new Uri.Builder()
            .scheme(ContentResolver.SCHEME_CONTENT)
            .authority(AUTHORITY)
            .path(PATH_EXPENSE)
            .build();

    Uri EXPENSE_MEMBER_URI = EXPENSE_URI.buildUpon()
            .path(MemberContract.PATH_MEMBER)
            .build();

    /**
     * Give all expense for a particular member , Expense Book and current year.
     * add Member Id and Expense book id ad query parameter.
     */
    Uri EXPENSE_MEMBER_ID_EXPENSE_BOOK_ID_BY_MONTH_FOR_CURRENT_YEAR = EXPENSE_URI.buildUpon()
            .appendPath(MemberContract.PATH_MEMBER)
            .appendPath(ExpenseBookContract.PATH_EXPENSE_BOOK)
            .appendPath(PATH_MONTH)
            .appendPath(PATH_YEAR)
            .build();
    String COLUMN_MONTH = "month";

    String[] PROJECTION_EXPENSE_SUM = {"SUM( "
            + ExpenseContract.EXPENSE_AMOUNT
            + ") AS" +
            "STRFTIME(\"%m\", " + ExpenseContract.EXPENSE_DATE + ") AS " + COLUMN_MONTH};

    String SELECTION_EXPENSE_WITH_DATE_MEMBER_EXPENSE_BOOK = ExpenseContract.EXPENSE_BOOK_KEY + "=? AND " +
            "STRFTIME(\"%Y\",expenses.expense_date) =?";

    String GROUP_BY_MONTH_AND_YEAR_MEMBER_EXEPENSE_BOOK = "STRFTIME(\"%m-%Y\","
            + ExpenseContract.TABLE_NAME + "." + ExpenseContract.EXPENSE_DATE + " ), "
            + ExpenseContract.TABLE_NAME + "." + ExpenseContract.EXPENSE_BOOK_KEY;
}