package com.mmt.shubh.expensemanager.database.content.contract;

import android.provider.BaseColumns;

/**
 * Created by styagi on 5/31/2015.
 */
public interface BaseContract extends BaseColumns {

    String AUTHORITY = "com.mmt.shubh.expensemanager.expenseprovider";
    long NO_CONTENT = -1;

    String ID_SELECTION = _ID + " = ?";


}
