/*
 * Copyright 2015 Hannes Dorfmann.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mmt.shubh.expensemanager.core.mvp.lce;


/**
 * A base view state implementation for {@link LCEViewState} (Loading-Content-Error)
 *
 * @param <M> the data / model type
 * @param <V> the type of the view
 * @author Hannes Dorfmann
 * @since 1.0.0
 */
public abstract class AbsLceViewState<M, V extends MVPLCEView<M>> implements LCEViewState<M, V> {

    /**
     * The current viewstate. Used to identify if the view is/was showing loading, error, or content.
     */
    protected int currentViewState;

    protected boolean pullToRefresh;

    protected Throwable exception;

    protected M mLoadedData;

    @Override
    public void setStateShowContent(M loadedData) {
        currentViewState = STATE_SHOW_CONTENT;
        this.mLoadedData = loadedData;
        exception = null;
    }

    @Override
    public void setStateShowError(Throwable e, boolean pullToRefresh) {
        currentViewState = STATE_SHOW_ERROR;
        exception = e;
        this.pullToRefresh = pullToRefresh;
        if (!pullToRefresh) {
            mLoadedData = null;
        }
        // else, don't clear loaded data, because of pull to refresh where previous data may
        // be displayed while showing error
    }

    @Override
    public void setStateShowLoading(boolean pullToRefresh) {
        currentViewState = STATE_SHOW_LOADING;
        this.pullToRefresh = pullToRefresh;
        exception = null;

        if (!pullToRefresh) {
            mLoadedData = null;
        }
        // else, don't clear loaded data, because of pull to refresh where previous data
        // may be displayed while showing error
    }

    @Override
    public void apply(V view, boolean retained) {
        switch (currentViewState) {
            case STATE_SHOW_CONTENT:
                showContent(view);
                break;
            case STATE_SHOW_LOADING:
                showLoading(view, retained);
                break;
            case STATE_SHOW_ERROR:
                showError(view);
        }

    }

    private void showContent(V view) {
        view.setData(mLoadedData);
        view.showContent();
    }

    private void showError(V view) {
        boolean ptr = pullToRefresh;
        Throwable e = exception;
        if (pullToRefresh) {
            showContent(view);
        }
        view.showError(e, ptr);
    }

    private void showLoading(V view, boolean retained) {
        boolean ptr = pullToRefresh;
        if (pullToRefresh) {
            showContent(view);
        }
        if (retained) {
            view.showLoading(ptr);
        } else {
            view.loadData(ptr);
        }
    }

}