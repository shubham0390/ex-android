package com.km2labs.android.spendview.database.content.contract;

import android.content.ContentResolver;
import android.net.Uri;

/**
 * Created by Subham Tyagi,
 * on 30/Jun/2015,
 * 6:25 PM
 * TODO:Add class comment.
 */
public interface CardContract extends BaseContract {

    String TABLE_NAME = "cards";
    String PATH_CARDS = "Cards";

    String CARD_NO = "card_no";
    String BALANCE_AMOUNT = "balance_amount";
    String ACCOUNT_KEY = "account_key";

    Uri CARD_URI = new Uri.Builder()
            .scheme(ContentResolver.SCHEME_CONTENT)
            .authority(AUTHORITY)
            .path(PATH_CARDS)
            .build();
}
