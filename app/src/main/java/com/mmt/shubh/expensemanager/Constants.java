/*
 * Copyright (c) 2014. The MMT group Project
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.mmt.shubh.expensemanager;

public interface Constants {
    String LOG_TAG = "ExManager";

    /*Extra/Bundle keys*/
    String KEY_ITEM_ID = "itemId";
    String KEY_USER_ID = "userId";
    String KEY_EXPENSE_BOOK = "groupId";
    String KEY_MEMBER_ID = "memberId";
    String EXTRA_MEMBER = "member";
    String KEY_RESULT_CODE = "result_code";
    String KEY_REQUEST_CODE = "request_code";
    String ACTION_SELECT_CONTACT = "com.mmt.shubh.expense.ACTION_SELECT_CONTACT";
    String KEY_FRAGMENT_START_MODE = "expense_book_mode";
    String MODE_EDIT_DETAILS = "details";
    String MODE_NEW = "new";
    String KEY_MEMBER_NAME = "member_name";
    String EXTRA_MEMBER_ID = "memberId";
    String EXTRA_GROUP_NAME = "group_name";
    String EXTRA_GROUP_IMAGE_URI = "group_image_uri";
    String EXTRA_GROUP_DESCRIPTION = "group_image_uri";
    String EXTRA_DELETE_MEMBER = "delete_member";

    String CATEGORY_TYPE_CUSTOM = "custom";
    String CATEGORY_TPYE_DEFAULT = "default";
    String EXTRA_EXPENSE_BOOK_ID = "expense_book_id";


    int ADD_MEMBER_FRAGMENT = 1;

    /*Intents Actions*/
    String ACTION_ADD_MEMBERS = "com.mmt.shubh.expensemanager.ACTION_ADD_MEMBERS";
    String KEY_CONTENT_ID = "contentId";
    String EXTRA_ACCOUNT = "account";
    String EXTRA_TYPE = "extra_type";
}
