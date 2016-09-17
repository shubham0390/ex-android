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

package com.km2labs.android.spendview.utils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Subham Tyagi on 07/07/16.
 */

public final class RxUtils {
    @SuppressWarnings("unchecked")
    private static final Observable.Transformer schedulersTransformer =
            new Observable.Transformer<Observable, Observable>() {
                @Override
                public Observable<Observable> call(Observable<Observable> observableObservable) {
                    return ((Observable) observableObservable).subscribeOn(Schedulers.computation())
                            .observeOn(Schedulers.computation());
                }
            };

    @SuppressWarnings("unchecked")
    private static final Observable.Transformer mainSchedulersTransformer =
            new Observable.Transformer<Observable, Observable>() {
                @Override
                public Observable<Observable> call(Observable<Observable> observableObservable) {
                    return ((Observable) observableObservable).subscribeOn(AndroidSchedulers.mainThread())
                            .observeOn(AndroidSchedulers.mainThread());
                }
            };

    @SuppressWarnings("unchecked")
    private static final Observable.Transformer schedulersMainIOTransformer =
            new Observable.Transformer<Observable, Observable>() {
                @Override
                public Observable<Observable> call(Observable<Observable> observableObservable) {
                    return ((Observable) observableObservable).subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread());
                }
            };

    @SuppressWarnings("unchecked")
    public static <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>) schedulersTransformer;
    }

    @SuppressWarnings("unchecked")
    public static <T> Observable.Transformer<T, T> applyMainSchedulers() {
        return (Observable.Transformer<T, T>) mainSchedulersTransformer;
    }

    public static <T> Observable.Transformer<T, T> applyMainIOSchedulers() {
        return (Observable.Transformer<T, T>) schedulersMainIOTransformer;
    }

}
