package com.mmt.shubh.expensemanager.database.api;

import java.util.List;

import rx.Observable;

/**
 * Created by styagi on 5/28/2015.
 */
public interface IDataAdapter<T> {
    Observable<T> create(T t);

    Observable<T> update(T t);

    Observable<Long> delete(long id);

    Observable<Boolean> deleteAll();

    Observable<T> get(long id);

    Observable<List<T>> getAll();


}
