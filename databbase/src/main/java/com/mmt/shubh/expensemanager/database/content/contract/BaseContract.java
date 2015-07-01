package com.mmt.shubh.expensemanager.database.content.contract;

import android.provider.BaseColumns;

/**
 * Created by Subham Tyagi,
 * on 01/Jul/2015,
 * 10:57 AM
 * TODO:Add class comment.
 */
public interface BaseContract extends BaseColumns {

    String AUTHORITY = "com.mmt.shubh.expensemanager.expenseprovider";
    long NO_CONTENT = -1;

    String ID_SELECTION = _ID + " = ?";


}
