package com.mmt.shubh.expensemanager.database.dataadapters;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.mmt.shubh.database.QueryBuilder;
import com.mmt.shubh.database.Selection;
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

    protected abstract void setTaskId(T t, long id);


    public List<ContentValues> toContentValues(List<T> list) {
        List<ContentValues> contentValues = new ArrayList<>();
        for (T t : list) {
            contentValues.add(toContentValues(t));
        }
        return contentValues;
    }

    public T create(T t) {
        long id = mBriteDatabase.insert(mTableName, toContentValues(t));
        setTaskId(t, id);
        return t;

    }

    public List<T> create(List<T> ts) {

        BriteDatabase.Transaction transaction = mBriteDatabase.newTransaction();
        try {
            for (T t : ts) {
                long id = mBriteDatabase.insert(mTableName, toContentValues(t));
                setTaskId(t, id);
            }
            transaction.markSuccessful();

        } finally {
            transaction.close();
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
        String s = "SELECT * FROM "
                + mTableName
                + " WHERE _id = " + id;
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

            String query = "SELECT * FROM " + mTableName + " WHERE " + column + " = " + value;
            mBriteDatabase.createQuery(mTableName, query).map(query1 -> {
                Cursor cursor = query1.run();
                return parseCursor(cursor);
            });
        });
    }
}
