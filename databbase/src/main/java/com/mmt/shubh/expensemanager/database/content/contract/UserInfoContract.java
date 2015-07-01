package com.mmt.shubh.expensemanager.database.content.contract;

import android.content.ContentResolver;
import android.net.Uri;

/**
 * Created by styagi on 5/31/2015.
 */
public interface UserInfoContract extends BaseContract {
    String PATH_USER = "User";

    int USER_ID_COLUMN = 0;
    int USER_NAME_COLUMN = 1;
    int USER_PASSWORD_COLUMN = 2;
    int USER_DISPLAY_NAME_COLUMN = 3;
    int USER_PROFILE_IMAGE_URL_COLUMN = 4;
    int USER_COVER_IMAGE_URL_COLUMN = 5;
    int USER_EMAIL_ADDRESS_COLUMN = 6;
    int USER_STATUS_COLUMN = 7;

    String TABLE_NAME = "user";

    String USER_NAME = "user_name";
    String USER_PASSWORD = "password";
    String USER_DISPLAY_NAME = "display_name";
    String USER_PROFILE_IMAGE_URL = "profile_image_url";
    String USER_COVER_IMAGE_URL = "cover_image_url";
    String USER_EMAIL_ADDRESS = "email_address";
    String USER_STATUS = "status";

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
