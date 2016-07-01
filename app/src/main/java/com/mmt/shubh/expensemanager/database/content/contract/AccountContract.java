package com.mmt.shubh.expensemanager.database.content.contract;

import android.content.ContentResolver;
import android.net.Uri;

/**
 * Created by Subham Tyagi,
 * on 01/Jul/2015,
 * 10:57 AM
 * TODO:Add class comment.
 */
public interface AccountContract extends BaseContract {
    String PATH_ACCOUNT = "Account";

    String TABLE_NAME = "account";

    String ACCOUNT_NAME = "account_name";
    String ACCOUNT_TYPE = "account_type";
    String ACCOUNT_BALANCE = "account_balance";
    String ACCOUNT_NUMBER = "account_number";


    Uri ACCOUNT_URI = new Uri.Builder()
            .scheme(ContentResolver.SCHEME_CONTENT)
            .authority(AUTHORITY)
            .path(PATH_ACCOUNT)
            .build();


}
