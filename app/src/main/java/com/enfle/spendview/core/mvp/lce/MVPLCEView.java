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

package com.enfle.spendview.core.mvp.lce;

import com.enfle.spendview.core.mvp.MVPView;

public interface MVPLCEView<M> extends MVPView {

    /**
     * Display a loading view while loading data in background.
     * <b>The loading view must have the id = R.id.mLoadingView</b>
     *
     * @param pullToRefresh true, if pull-to-refresh has been invoked loading.
     */
    void showLoading(boolean pullToRefresh);

    /**
     * Show the content view.
     * <p>
     * <b>The content view must have the id = R.id.mContentView</b>
     */
    void showContent();

    /**
     * Show the error view.
     * <b>The error view must be a TextView with the id = R.id.mErrorView</b>
     *
     * @param e             The Throwable that has caused this error
     * @param pullToRefresh true, if the exception was thrown during pull-to-refresh, otherwise
     *                      false.
     */
    void showError(Throwable e, boolean pullToRefresh);

    /**
     * The data that should be displayed with {@link #showContent()}
     */
    void setData(M data);

    /**
     * Load the data. Typically invokes the presenter method to load the desired data.
     * <p>
     * <b>Should not be called from presenter</b> to prevent infinity loops. The method is declared
     * in
     * the views interface to add support for view state easily.
     * </p>
     *
     * @param pullToRefresh true, if triggered by a pull to refresh. Otherwise false.
     */
    void loadData(boolean pullToRefresh);

}