package com.km2labs.android.spendview.database.content.contract;

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

    // All classes share this
    String RECORD_ID = "_id";

    String[] COUNT_COLUMNS = new String[]{"count(*)"};
    /**
     * This projection can be used with any of the EmailContent classes, when all you need
     * is a list of id's.  Use ID_PROJECTION_COLUMN to access the row data.
     */
    String[] ID_PROJECTION = new String[]{
            RECORD_ID
    };

    String PARAMETER_LIMIT = "limit";

}
