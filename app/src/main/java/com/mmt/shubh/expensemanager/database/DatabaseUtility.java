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

package com.mmt.shubh.expensemanager.database;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.mmt.shubh.expensemanager.database.content.contract.ExpenseContract;
import com.mmt.shubh.expensemanager.database.dataadapters.BaseSQLDataAdapter;

public class DatabaseUtility {

    private static final CursorGetter<Long> LONG_GETTER = new CursorGetter<Long>() {
        @Override
        public Long get(Cursor cursor, int column) {
            return cursor.getLong(column);
        }
    };

    /**
     * {@link #getFirstRowColumn} for a Long with null as a default value.
     */
    public static Long getFirstRowLong(Context context, Uri uri, String[] projection,
                                       String selection, String[] selectionArgs, String sortOrder, int column) {
        return getFirstRowColumn(context, uri, projection, selection, selectionArgs,
                sortOrder, column, null, LONG_GETTER);
    }

    /**
     * {@link #getFirstRowColumn} for a Long with a provided default value.
     */
    public static Long getFirstRowLong(Context context, Uri uri, String[] projection,
                                       String selection, String[] selectionArgs, String sortOrder, int column,
                                       Long defaultValue) {
        return getFirstRowColumn(context, uri, projection, selection, selectionArgs,
                sortOrder, column, defaultValue, LONG_GETTER);
    }

    /**
     * @return a generic in column {@code column} of the first result row, if the query returns at
     * least 1 row.  Otherwise returns {@code defaultValue}.
     */
    public static <T extends Object> T getFirstRowColumn(Context context, Uri uri,
                                                         String[] projection, String selection, String[] selectionArgs, String sortOrder,
                                                         int column, T defaultValue, CursorGetter<T> getter) {
        // Use PARAMETER_LIMIT to restrict the query to the single row we need
        uri = buildLimitOneUri(uri);
        Cursor c = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                sortOrder);
        if (c != null) {
            try {
                if (c.moveToFirst()) {
                    return getter.get(c, column);
                }
            } finally {
                c.close();
            }
        }
        return defaultValue;
    }

    /**
     * @return if {@code original} is to the EmailProvider, add "?limit=1".  Otherwise just returns
     * {@code original}.
     * <p/>
     * Other providers don't support the limit param.  Also, changing URI passed from other apps
     * can cause permission errors.
     */
    /* package */
    static Uri buildLimitOneUri(Uri original) {
        if ("content".equals(original.getScheme()) &&
                ExpenseContract.AUTHORITY.equals(original.getAuthority())) {
            return BaseSQLDataAdapter.uriWithLimit(original, 1);
        }
        return original;
    }

    public interface CursorGetter<T> {
        T get(Cursor cursor, int column);
    }
}
