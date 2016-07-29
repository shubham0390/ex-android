package com.km2labs.android.spendview.database.content.contract;

import android.provider.BaseColumns;

public interface MemberContract extends BaseColumns, BaseContract {
    String TABLE_NAME = "members";
    String MEMBER_USER_NAME = "user_name";
    String MEMBER_NAME = "name";
    String MEMBER_EMAIL = "email";
    String MEMBER_PHONE_NUMBER = "phone_number";
    String MEMBER_IMAGE_URI = "profile_image_uri";
    String MEMBER_COVER_IMAGE_URL = "cover_image_uri";
}