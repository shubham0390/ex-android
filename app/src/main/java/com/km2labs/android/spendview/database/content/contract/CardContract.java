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
 * 6:25 PM
 * TODO:Add class comment.
 */
public interface CardContract extends BaseContract {

    String TABLE_NAME = "cards";
    String PATH_CARDS = "Cards";

    String CARD_NO = "card_no";
    String BALANCE_AMOUNT = "balance_amount";
    String ACCOUNT_KEY = "account_key";

    Uri CARD_URI = new Uri.Builder()
            .scheme(ContentResolver.SCHEME_CONTENT)
            .authority(AUTHORITY)
            .path(PATH_CARDS)
            .build();
}
