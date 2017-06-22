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

package com.enfle.spendview.database.api;

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
