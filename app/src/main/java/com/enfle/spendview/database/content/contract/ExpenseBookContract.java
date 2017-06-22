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
import android.provider.BaseColumns;

public interface ExpenseBookContract extends BaseColumns, BaseContract {

    String TABLE_NAME = "expense_book";
    String PATH_EXPENSE_BOOK = "ExpenseBook";

    String EXPENSE_BOOK_NAME = "name";
    String EXPENSE_BOOK_TYPE = "expense_book_type";
    String EXPENSE_BOOK_DESCRIPTION = "description";
    String EXPENSE_BOOK_PROFILE_IMAGE_URI = "profile_image_uri";
    String EXPENSE_BOOK_CREATION_TIME = "creation_time";
    String OWNER_KEY = "owner_key";

    Uri EXPENSE_BOOK_URI = new Uri.Builder().scheme(ContentResolver.SCHEME_CONTENT).authority(AUTHORITY).path(PATH_EXPENSE_BOOK).build();

}