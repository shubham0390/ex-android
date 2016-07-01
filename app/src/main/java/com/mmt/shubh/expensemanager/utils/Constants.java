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
package com.mmt.shubh.expensemanager.utils;

public interface Constants {
    String LOG_TAG = "ExManager";

    /* Bundle Extra key*/
    String EXTRA_MEMBER_ID = "memberId";
    String EXTRA_MEMBER = "member";
    String EXTRA_DELETE_MEMBER = "deleteMember";

    String EXTRA_EXPENSE_BOOK = "expenseBook";
    String EXTRA_EXPENSE_BOOK_ID = "expenseBookId";

    String EXTRA_EXPENSE = "expense";
    String EXTRA_ACCOUNT = "account";

    String EXTRA_TYPE = "extraType";

    int ADD_MEMBER_FRAGMENT = 1;

    /*Intents Actions*/
    String ACTION_ADD_MEMBERS = "com.mmt.shubh.expensemanager.ACTION_ADD_MEMBERS";
    String ACTION_SELECT_CONTACT = "com.mmt.shubh.expense.ACTION_SELECT_CONTACT";
}
