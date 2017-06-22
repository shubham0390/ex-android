/*
 * Copyright (c) 2016. . The Km2Labs Project
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.enfle.spendview.database.content.contract;

import android.content.ContentResolver;
import android.net.Uri;

/**
 * Created by Subham Tyagi,
 * on 30/Jun/2015,
 * 6:38 PM
 * TODO:Add class comment.
 */
public interface MemberExpenseBookContract extends BaseContract {

    String TABLE_NAME = "member_expense_book";

    String PATH = "Member/ExpenseBook";

    String MEMBER_KEY = "member_key";

    String EXPENSE_BOOK_KEY = "expense_book_key";

    String STATUS = "status";


    Uri MEMBER_EXPENSE_BOOK_URI = new Uri.Builder()
            .scheme(ContentResolver.SCHEME_CONTENT)
            .authority(AUTHORITY)
            .path(PATH)
            .build();
}
