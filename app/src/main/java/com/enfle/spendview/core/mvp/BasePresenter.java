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

package com.enfle.spendview.core.mvp;


import java.lang.ref.WeakReference;

import timber.log.Timber;

public abstract class BasePresenter<V extends MVPView> {

    private WeakReference<V> viewRef;

    public BasePresenter() {
        Timber.tag(getClass().getName());
    }

    public void subcribe(V view) {
        viewRef = new WeakReference<>(view);
        Timber.tag(getClass().getName());
    }

    /**
     * Get the attached view. You should always call {@link #isViewAttached()} to check if the view
     * is
     * attached to avoid NullPointerExceptions.
     *
     * @return <code>null</code>, if view is not attached, otherwise the concrete view instance
     */
    protected V getView() {
        return viewRef == null ? null : viewRef.get();
    }

    /**
     * Checks if a view is attached to this presenter. You should always call this method before
     * calling {@link #getView()} to get the view instance.
     */
    protected boolean isViewAttached() {
        return viewRef != null && viewRef.get() != null;
    }

    public void unsubcribe(boolean retainInstance) {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }

    public void resume() {

    }

    public void pause() {

    }

    public void onError(Throwable throwable) {
        Timber.e(throwable, "error");
    }

  
}