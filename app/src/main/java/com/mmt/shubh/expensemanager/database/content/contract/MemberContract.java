package com.mmt.shubh.expensemanager.database.content.contract;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public interface MemberContract extends BaseColumns, BaseContract {
    String TABLE_NAME = "members";
    /* public static final Uri MEMBER_GROUP_URI = Uri.withAppendedPath(CONTENT_URI, ContentPath.PATH_MEMBER_EXPENSE_BOOK);*/
    String PATH_MEMBER = "member";

    String MEMBER_USER_NAME = "user_name";
    String MEMBER_NAME = "name";
    String MEMBER_EMAIL = "email";
    String MEMBER_PHONE_NUMBER = "phone_number";
    String MEMBER_IMAGE_URI = "profile_image_uri";
    String MEMBER_COVER_IMAGE_URL = "cover_image_uri";


    Uri MEMBER_URI = new Uri.Builder()
            .scheme(ContentResolver.SCHEME_CONTENT)
            .authority(AUTHORITY)
            .path(PATH_MEMBER)
            .build();

    Uri MEMBER_EXPENSE_BOOK_URI = MEMBER_URI.buildUpon()
            .appendPath(ExpenseBookContract.PATH_EXPENSE_BOOK)
            .build();


   /* String[] PROJECTION_MEMBER_EXPENSE_BOOK = new String[]{
            "SUM(" + ExpenseContract.TABLE_NAME + "." + ExpenseContract.EXPENSE_AMOUNT + ") AS " + MEMBER_TOTAL_EXPENSE
            , ExpenseContract.TABLE_NAME + "." + ExpenseContract.MEMBER_KEY
            , ExpenseContract.TABLE_NAME + "." + ExpenseContract.EXPENSE_BOOK_KEY
            , "STRFTIME(\"%m-%Y\"," + ExpenseContract.EXPENSE_DATE + ") AS 'month_year'"
            , ExpenseBookContract.TABLE_NAME + "." + ExpenseBookContract._ID
            , ExpenseBookContract.TABLE_NAME + "." + ExpenseBookContract.EXPENSE_BOOK_NAME
            , ExpenseBookContract.TABLE_NAME + "." + ExpenseBookContract.EXPENSE_BOOK_TYPE
            , ExpenseBookContract.TABLE_NAME + "." + ExpenseBookContract.EXPENSE_BOOK_PROFILE_IMAGE_URI
    };

    String EXPNESE_EXPNSE_BOOK_JOINED_TABLE_NAME = ExpenseContract.TABLE_NAME
            + " INNER JOIN "
            + ExpenseBookContract.TABLE_NAME
            + " ON "
            + ExpenseBookContract.TABLE_NAME + "." + ExpenseBookContract._ID
            + " = "
            + ExpenseContract.TABLE_NAME + "." + ExpenseContract.EXPENSE_BOOK_KEY;

    String SELECTION_EXPENSE_MEMBER_KEY_AND_DATE =
            ExpenseContract.TABLE_NAME + "." + ExpenseContract.MEMBER_KEY
                    + " =? AND STRFTIME(\"%Y\","
                    + ExpenseContract.TABLE_NAME + "." + ExpenseContract.EXPENSE_DATE + ") =?";
*/

}