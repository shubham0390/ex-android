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
 * on 01/Jul/2015,
 * 10:57 AM
 * TODO:Add class comment.
 */
public interface AccountContract extends BaseContract {
    String PATH_ACCOUNT = "Account";

    String TABLE_NAME = "account";

    String ACCOUNT_NAME = "account_name";
    String ACCOUNT_TYPE = "account_type";
    String ACCOUNT_BALANCE = "account_balance";
    String ACCOUNT_NUMBER = "account_number";


    Uri ACCOUNT_URI = new Uri.Builder()
            .scheme(ContentResolver.SCHEME_CONTENT)
            .authority(AUTHORITY)
            .path(PATH_ACCOUNT)
            .build();


}
