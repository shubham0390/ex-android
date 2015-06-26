package com.mmt.shubh.expensemanager.database.content.contract;

import android.content.ContentResolver;
import android.net.Uri;

/**
 * Created by styagi on 5/31/2015.
 */
public interface AccountContract extends BaseContract {
    String PATH_USER = "Account";

    int USER_ID_COLUMN = 0;
    int USER_NAME_COLUMN = 1;
    int USER_PASSWORD_COLUMN = 2;
    int USER_DISPLAY_NAME_COLUMN = 3;
    int USER_PROFILE_IMAGE_URL_COLUMN = 4;
    int USER_COVER_IMAGE_URL_COLUMN = 5;
    int USER_EMAIL_ADDRESS_COLUMN = 6;
    int USER_STATUS_COLUMN = 7;

    String TABLE_NAME = "Account";

    String USER_NAME = "AccountName";
    String USER_PASSWORD = "Password";
    String USER_DISPLAY_NAME = "DisplayName";
    String USER_PROFILE_IMAGE_URL = "ProfileImage";
    String USER_COVER_IMAGE_URL = "CoverImage";
    String USER_EMAIL_ADDRESS = "EmailAddress";
    String USER_STATUS = "Status";

    String[] USER_PROJECTION = new String[]{
            _ID,
            USER_NAME,
            USER_PASSWORD,
            USER_DISPLAY_NAME,
            USER_PROFILE_IMAGE_URL,
            USER_COVER_IMAGE_URL,
            USER_EMAIL_ADDRESS,
            USER_STATUS
    };


    Uri ACCOUNT_URI = new Uri.Builder()
            .scheme(ContentResolver.SCHEME_CONTENT)
            .authority(AUTHORITY)
            .path(PATH_USER)
            .build();

}
