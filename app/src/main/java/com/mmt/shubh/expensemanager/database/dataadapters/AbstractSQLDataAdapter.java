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

/**
 * Created by subhamtyagi on 2/24/16.
 */
public abstract class AbstractSQLDataAdapter<T> {

    protected BriteDatabase mBriteDatabase;

    protected String mTableName;

    public AbstractSQLDataAdapter(String tableName, BriteDatabase briteDatabase) {
        mTableName = tableName;
        mBriteDatabase = briteDatabase;
    }

    public abstract T parseCursor(Cursor cursor);

    public abstract ContentValues toContentValues(T t);

    public List<ContentValues> toContentValues(List<T> list) {
        List<ContentValues> contentValues = new ArrayList<>();
        for (T t : list) {
            contentValues.add(toContentValues(t));
        }
        return contentValues;
    }

    public Observable<T> create(T t) {
        return Observable.create(subscriber -> {
            long id = mBriteDatabase.insert(mTableName, toContentValues(t));
            setTaskId(t, id);
            subscriber.onNext(t);
            subscriber.onCompleted();
        });
    }

    protected abstract void setTaskId(T t, long id);


    public Observable<T> update(T t) {
        return Observable.create(subscriber -> {
        });
    }

    public Observable<Long> delete(long id) {
        return Observable.create(subscriber -> {
            Selection selection = new Selection(BaseColumns._ID, Selection.EQUAL, id);
            mBriteDatabase.delete(mTableName, selection.build());
            subscriber.onNext(id);
            subscriber.onCompleted();
        });
    }


    public Observable<Boolean> deleteAll() {
        return Observable.create(subscriber -> {
            mBriteDatabase.delete(mTableName, null);
            subscriber.onNext(true);
            subscriber.onCompleted();
        });
    }


    public Observable<T> get(long id) {
        return Observable.create(subscriber -> {
            Selection selection = new Selection(BaseColumns._ID, Selection.EQUAL, id);
            QueryBuilder queryBuilder = new QueryBuilder();
            queryBuilder.addFrom(mTableName)
                    .addProjection(null).addSelection(selection);
            mBriteDatabase.createQuery(mTableName, queryBuilder.build()).subscribe(query -> {
                subscriber.onNext(parseCursor(query.run()));
            });
        });
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
            Selection selection = new Selection(column, Selection.EQUAL, value);
            QueryBuilder queryBuilder = new QueryBuilder();
            queryBuilder.addFrom(mTableName)
                    .addProjection(null).addSelection(selection);
            mBriteDatabase.createQuery(mTableName, queryBuilder.build()).subscribe(query -> {
                subscriber.onNext(parseCursor(query.run()));
            });
        });
    }
}
