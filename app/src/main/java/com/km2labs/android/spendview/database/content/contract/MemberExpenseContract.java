package com.km2labs.android.spendview.database.content.contract;

import android.content.ContentResolver;
import android.net.Uri;

/**
 * Created by Subham Tyagi,
 * on 30/Jun/2015,
 * 6:33 PM
 * TODO:Add class comment.
 */
public interface MemberExpenseContract extends BaseContract {

    String TABLE_NAME = "member_expense";

    String PATH = "Member/Expense";

    String EXPENSE_KEY = "expense_key";

    String MEMBER_KEY = "member_key";

    String SHARE_AMOUNT = "share_amount";

    String DEBIT_AMOUNT = "debit_amount";

    String BALANCE_AMOUNT = "balance_amount";

    Uri MEMBER_EXPENSE_URI = new Uri.Builder()
            .scheme(ContentResolver.SCHEME_CONTENT)
            .authority(AUTHORITY)
            .path(PATH)
            .build();

}
