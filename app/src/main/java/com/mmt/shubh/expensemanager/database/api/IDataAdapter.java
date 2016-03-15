package com.mmt.shubh.expensemanager.database.api;

import android.support.annotation.WorkerThread;

import java.util.List;

import rx.Observable;

/**
 * Created by styagi on 5/28/2015.
 */
public interface IDataAdapter<T> {
    @WorkerThread
    Observable<T> create(T t);

    @WorkerThread
    Observable<List<T>> create(List<T> tList);

    @WorkerThread
    Observable<T> update(T t);

    @WorkerThread
    Observable<Long> delete(long id);

    @WorkerThread
    Observable<Boolean> deleteAll();

    @WorkerThread
    Observable<T> get(long id);

    @WorkerThread
    Observable<List<T>> getAll();


}
