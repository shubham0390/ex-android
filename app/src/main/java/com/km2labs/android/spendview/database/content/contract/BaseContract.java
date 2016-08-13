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

import android.provider.BaseColumns;

/**
 * Created by Subham Tyagi,
 * on 01/Jul/2015,
 * 10:57 AM
 * TODO:Add class comment.
 */
public interface BaseContract extends BaseColumns {

    String AUTHORITY = "com.mmt.shubh.expensemanager.expenseprovider";

    long NO_CONTENT = -1;

    String ID_SELECTION = _ID + " = ?";

    // All classes share this
    String RECORD_ID = "_id";

    String[] COUNT_COLUMNS = new String[]{"count(*)"};
    /**
     * This projection can be used with any of the EmailContent classes, when all you need
     * is a list of id's.  Use ID_PROJECTION_COLUMN to access the row data.
     */
    String[] ID_PROJECTION = new String[]{
            RECORD_ID
    };

    String PARAMETER_LIMIT = "limit";

}
