package com.mmt.shubh.expensemanager.database.content.contract;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public interface MemberContract extends BaseColumns, BaseContract {
    public static final String TABLE_NAME = "members";
    /* public static final Uri MEMBER_GROUP_URI = Uri.withAppendedPath(CONTENT_URI, ContentPath.PATH_MEMBER_EXPENSE_BOOK);*/
    String PATH_MEMBER = "member";

    int MEMBER_ID_COLUMN = 0;
    int MEMBER_NAME_COLUMN = 1;
    int MEMBER_EMAIL_COLUMN = 2;
    int MEMBER_GROUP_KEY_COLUMN = 3;


    String MEMBER_NAME = "member_name";
    String MEMBER_EMAIL = "member_email";
    String EXPENSE_BOOK_KEY = "group_key";
    String MEMBER_IMAGE_URI = "profile_image_uri";

    Uri MEMBER_URI = new Uri.Builder()
            .scheme(ContentResolver.SCHEME_CONTENT)
            .authority(AUTHORITY)
            .path(PATH_MEMBER)
            .build();

    Uri MEMBER_EXPENSE_BOOK_URI = MEMBER_URI.buildUpon()
            .path(ExpenseBookContract.PATH_EXPENSE_BOOK)
            .build();
}