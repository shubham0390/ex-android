package com.mmt.shubh.expensemanager.database.api;

import java.util.List;

/**
 * Created by styagi on 5/28/2015.
 */
public interface DataAdapter<T> {
    long create(T t);

    T update(T t);

    T delete(T t);

    T delete(long id);

    void deleteAll(Class<T> tClass);

    T get(long id);

    List<T> getAll();


}
