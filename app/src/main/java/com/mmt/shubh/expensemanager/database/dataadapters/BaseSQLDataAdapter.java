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

package com.mmt.shubh.expensemanager.database.dataadapters;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;

import com.mmt.shubh.expensemanager.database.DatabaseUtility;
import com.mmt.shubh.expensemanager.database.provider.ProviderUnavailableException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by STyagi on 3/20/14.
 */
public abstract class BaseSQLDataAdapter<T> {

    public static final String PARAMETER_LIMIT = "limit";

    // All classes share this
    public static final String RECORD_ID = "_id";
    /**
     * This projection can be used with any of the EmailContent classes, when all you need
     * is a list of id's.  Use ID_PROJECTION_COLUMN to access the row data.
     */
    public static final String[] ID_PROJECTION = new String[]{
            RECORD_ID
    };
    public static final String[] COUNT_COLUMNS = new String[]{"count(*)"};
    // Newly created objects get this id
    public static final int NOT_SAVED = -1;
    // The id of the Content
    public long mRecordId = NOT_SAVED;
    // The base Uri that this piece of content came from
    public Uri mBaseUri;
    // Lazily initialized uri for this Content
    private Uri mUri = null;

    protected Context mContext;

    public BaseSQLDataAdapter(Uri baseUri,Context context) {
        mContext = context;
        mBaseUri = baseUri;
    }

    /**
     * Restore a subclass of ExpenseContent from the database
     *
     * @param context           the caller's context
     * @param klass             the class to restore
     * @param contentUri        the content uri of the ExpenseContent subclass
     * @param contentProjection the content projection for the ExpenseContent subclass
     * @param id                the unique id of the object
     * @return the instantiated object
     */
    public static <T extends BaseSQLDataAdapter> T restoreContentWithId(Context context,
                                                                        Class<T> klass, Uri contentUri, String[] contentProjection, long id) throws IllegalArgumentException {
        long token = Binder.clearCallingIdentity();
        if (context == null) {
            throw new IllegalArgumentException("Application Context cannot be null");
        }
        Uri u = ContentUris.withAppendedId(contentUri, id);
        Cursor c = context.getContentResolver().query(u, contentProjection, null, null, null);
        if (c == null) throw new ProviderUnavailableException();
        try {
            if (c.moveToFirst()) {
                return getContent(c, klass);
            } else {
                return null;
            }
        } finally {
            c.close();
            Binder.restoreCallingIdentity(token);
        }
    }

    /**
     * Restore a subclass of ExpenseContent from the database
     *
     * @param <T>
     * @param context           the caller's context
     * @param klass             the class to restore
     * @param contentUri        the content uri of the ExpenseContent subclass
     * @param contentProjection the content projection for the ExpenseContent subclass
     * @return the instantiated object
     */
    public static <T extends BaseSQLDataAdapter> List<T> restoreContent(Context context,
                                                                        Class<T> klass, Uri contentUri, String[] contentProjection) {
        List<T> list = new ArrayList<T>();
        Cursor c = context.getContentResolver().query(contentUri, contentProjection, null, null, null);
        if (c == null) throw new ProviderUnavailableException();
        try {
            while (c.moveToNext()) {
                list.add(getContent(c, klass));
            }
        } finally {
            c.close();
        }
        return list;
    }

    // The Content sub class must have a no-arg constructor
    static public <T extends BaseSQLDataAdapter> T getContent(Cursor cursor, Class<T> klass) {
        try {
            T content = klass.newInstance();
            content.mRecordId = cursor.getLong(0);
            content.restore(cursor, content);
            return content;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public int update(Context context, Uri baseUri, long id, ContentValues contentValues) {
        return context.getContentResolver()
                .update(ContentUris.withAppendedId(baseUri, id), contentValues, null, null);
    }

    static public int delete(Context context, Uri baseUri, long id) {
        return context.getContentResolver()
                .delete(ContentUris.withAppendedId(baseUri, id), null, null);
    }

    /**
     * Generic count method that can be used for any ContentProvider
     *
     * @param uri           the Uri for the provider query
     * @param selection     as with a query call
     * @param selectionArgs as with a query call
     * @return the number of items matching the query (or zero)
     */
    public int count(Uri uri, String selection, String[] selectionArgs) {
        return DatabaseUtility.getFirstRowLong(mContext,
                uri, COUNT_COLUMNS, selection, selectionArgs, null, 0, Long.valueOf(0)).intValue();
    }

    /**
     * Same as {@link BaseSQLDataAdapter#count(Uri)} (Context, Uri, String, String[])} without selection.
     */
    public int count(Uri uri) {
        return count(uri, null, null);
    }

    static public Uri uriWithLimit(Uri uri, int limit) {
        return uri.buildUpon().appendQueryParameter(BaseSQLDataAdapter.PARAMETER_LIMIT,
                Integer.toString(limit)).build();
    }

    // Write the Content into a ContentValues container
    // Write the Content into a ContentValues container
    public abstract ContentValues toContentValues(T t);

    // Read the Content from a ContentCursor
    public abstract void restore(Cursor cursor, T t);

    // The Uri is lazily initialized
    public Uri getUri() {
        if (mUri == null) {
            mUri = ContentUris.withAppendedId(mBaseUri, mRecordId);
        }
        return mUri;
    }

    public boolean isSaved() {
        return mRecordId != NOT_SAVED;
    }

    public Uri save(T t) {
        if (isSaved()) {
            throw new UnsupportedOperationException();
        }

        Uri res = mContext.getContentResolver().insert(mBaseUri, toContentValues(t));
        mRecordId = Long.parseLong(res.getLastPathSegment());
        return res;
    }

    public int update(ContentValues contentValues) {
        if (!isSaved()) {
            throw new UnsupportedOperationException();
        }
        return mContext.getContentResolver().update(getUri(), contentValues, null, null);
    }

    public int delete() {
        return mContext.getContentResolver().delete(getUri(), null, null);
    }

}
