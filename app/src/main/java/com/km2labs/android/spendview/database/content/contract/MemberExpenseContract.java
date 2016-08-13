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

package com.km2labs.android.spendview.database.content.contract;

import android.content.ContentResolver;
import android.net.Uri;

/**
 * Created by Subham Tyagi,
 * on 30/Jun/2015,
 * 6:33 PM
 * TODO:Add class comment.
 */
public interface MemberExpenseContract extends BaseContract {

    String TABLE_NAME = "member_expense";

    String PATH = "Member/Expense";

    String EXPENSE_KEY = "expense_key";

    String MEMBER_KEY = "member_key";

    String SHARE_AMOUNT = "share_amount";

    String DEBIT_AMOUNT = "debit_amount";

    String BALANCE_AMOUNT = "balance_amount";

    Uri MEMBER_EXPENSE_URI = new Uri.Builder()
            .scheme(ContentResolver.SCHEME_CONTENT)
            .authority(AUTHORITY)
            .path(PATH)
            .build();

}
