package com.mmt.shubh.expensemanager.database.content.contract;

import android.content.ContentResolver;
import android.net.Uri;

/**
 * Created by Subham Tyagi,
 * on 30/Jun/2015,
 * 6:38 PM
 * TODO:Add class comment.
 */
public interface MemberExpenseBookContract extends BaseContract {

    String TABLE_NAME = "member_expense_book";

    String PATH = "Member/ExpenseBook";

    String MEMBER_KEY = "member_key";

    String EXPENSE_BOOK_KEY = "expense_book_key";

    String STATUS = "status";


    Uri MEMBER_EXPENSE_BOOK_URI = new Uri.Builder()
            .scheme(ContentResolver.SCHEME_CONTENT)
            .authority(AUTHORITY)
            .path(PATH)
            .build();
}
