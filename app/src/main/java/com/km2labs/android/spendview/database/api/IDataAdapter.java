package com.km2labs.android.spendview.database.api;

import android.support.annotation.WorkerThread;

import java.util.List;

import rx.Observable;

/**
 * Created by styagi on 5/28/2015.
 */
public interface IDataAdapter<T> {
    @WorkerThread
    T create(T t);

    @WorkerThread
    List<T> create(List<T> tList);

    @WorkerThread
    T update(T t);

    @WorkerThread
    long delete(long id);

    @WorkerThread
    int deleteAll();

    @WorkerThread
    T get(long id);

    @WorkerThread
    Observable<List<T>> getAll();


}
