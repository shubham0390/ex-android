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

package com.km2labs.android.spendview.database.dataadapters;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.km2labs.android.spendview.core.QueryBuilder;
import com.km2labs.android.spendview.core.Select;
import com.km2labs.android.spendview.core.Selection;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;


public abstract class BaseSQLDataAdapter<T> {

    protected BriteDatabase mBriteDatabase;

    protected String mTableName;

    public BaseSQLDataAdapter(String tableName, BriteDatabase briteDatabase) {
        mTableName = tableName;
        mBriteDatabase = briteDatabase;
    }

    public abstract T parseCursor(Cursor cursor);

    public abstract ContentValues toContentValues(T t);

    protected abstract void setId(T t, long id);

    public List<ContentValues> toContentValues(List<T> list) {
        List<ContentValues> contentValues = new ArrayList<>();
        for (T t : list) {
            contentValues.add(toContentValues(t));
        }
        return contentValues;
    }

    public T create(T t) {
        long id = mBriteDatabase.insert(mTableName, toContentValues(t));
        setId(t, id);
        return t;

    }

    public List<T> create(List<T> ts) {

        try (BriteDatabase.Transaction transaction = mBriteDatabase.newTransaction()) {
            for (T t : ts) {
                long id = mBriteDatabase.insert(mTableName, toContentValues(t));
                setId(t, id);
            }
            transaction.markSuccessful();

        }
        return ts;
    }


    public T update(T t) {
        return null;
    }

    public long delete(long id) {
        Selection selection = new Selection(BaseColumns._ID, Selection.EQUAL, id);
        return mBriteDatabase.delete(mTableName, selection.build());
    }


    public int deleteAll() {
        return mBriteDatabase.delete(mTableName, null);
    }


    public T get(long id) {

        String s = new Select().all().from(mTableName).where(" _Id ").equql(id).toString();
        mBriteDatabase.createQuery(mTableName, s).map(query -> {
            T t = null;
            Cursor cursor = query.run();
            try {
                while (cursor.moveToNext()) {
                    t = parseCursor(cursor);
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            return t;
        });
        return null;
    }


    public Observable<List<T>> getAll() {
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder.addFrom(mTableName)
                .addProjection(null);
        return mBriteDatabase.createQuery(mTableName, queryBuilder.build()).mapToList(this::parseCursor);
    }

    public Observable<List<T>> getResultByColumn(String column, long value) {
        return getResultByColumn(column, String.valueOf(value));
    }

    public Observable<List<T>> getResultByColumn(String column, String value) {
        Selection selection = new Selection(column, Selection.EQUAL, value);
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder.addFrom(mTableName)
                .addProjection(null).addSelection(selection);
        return mBriteDatabase.createQuery(mTableName, queryBuilder.build()).mapToList(this::parseCursor);
    }

    public Observable<T> getSingleResultByColumn(String column, long value) {
        return getSingleResultByColumn(column, String.valueOf(value));
    }

    public Observable<T> getSingleResultByColumn(String column, String value) {
        return Observable.create(subscriber -> {
            String query = new Select().all().from(mTableName).where(column).equql(value).toString();
            mBriteDatabase.createQuery(mTableName, query).map(query1 -> {
                Cursor cursor = query1.run();
                return parseCursor(cursor);
            });
        });
    }
}
