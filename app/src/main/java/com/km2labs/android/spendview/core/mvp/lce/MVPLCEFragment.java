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

package com.km2labs.android.spendview.core.mvp.lce;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.km2labs.android.spendview.core.mvp.MVPFragment;
import com.km2labs.android.spendview.core.mvp.MVPPresenter;
import com.km2labs.android.spendview.core.mvp.ViewState;
import com.km2labs.spendview.android.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A {@link MVPFragment} that implements {@link MVPLCEView} which gives you 3 options:
 * <ul>
 * <li>Display a loading view: A view with <b>R.id.mLoadingView</b> must be specified in your
 * inflated xml layout</li>
 * <li>Display a error view: A <b>TextView</b> with <b>R.id.mErrorView</b> must be declared in your
 * inflated xml layout</li>
 * <li>Display content view: A view witjh <b>R.id.mContentView</b> must be specified in your
 * inflated
 * xml layout</li>
 * </ul>
 *
 * @param <CV> The type of the content view with the id = R.id.mContentView. Can be any kind of
 *             android view widget like ListView, RecyclerView, ScrollView or a simple layout like Framelayout
 *             etc. (everything that extends from android.view.View)
 * @param <M>  The underlying data model that will be displayed with this view
 * @param <V>  The View interface that must be implemented by this view. You can use {@link
 *             MVPLCEView}, but if you want to add more methods you have to provide your own view interface
 *             that
 *             extends from {@link MVPLCEView}
 * @param <P>  The type of the Presenter. Must extend from {@link MVPPresenter}
 * @author Hannes Dorfmann
 * @since 1.0.0
 */
public abstract class MVPLCEFragment<CV extends View, M, V extends MVPLCEView<M>, P extends MVPPresenter<V>>
        extends MVPFragment<P> implements MVPLCEView<M> {

    @BindView(R.id.loadingView)
    protected View mLoadingView;

    @BindView(R.id.contentView)
    protected CV mContentView;

    @BindView(R.id.errorView)
    protected TextView mErrorView;


    /**
     * The viewstate will be instantiated by calling {@link #createViewState()} in {@link
     * #onViewCreated(View, Bundle)}. Don't instantiate it by hand.
     */
    protected LCEViewState<M, V> mViewState;

    /**
     * A flag that indicates if the viewstate tires to restore the view right now.
     */
    private boolean restoringViewState = false;

    /**
     * Create the view state object of this class
     */
    public abstract LCEViewState<M, V> createViewState();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mViewState = createViewState();
    }

    @CallSuper
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mLoadingView == null) {
            throw new NullPointerException(
                    "Loading view is null! Have you specified a loading view in your layout xml file?"
                            + " You have to give your loading View the id R.id.mLoadingView");
        }

        if (mContentView == null) {
            throw new NullPointerException(
                    "Content view is null! Have you specified a content view in your layout xml file?"
                            + " You have to give your content View the id R.id.mContentView");
        }

        if (mErrorView == null) {
            throw new NullPointerException(
                    "Error view is null! Have you specified a content view in your layout xml file?"
                            + " You have to give your error View the id R.id.mErrorView");
        }

    }


    @Override
    public void showLoading(boolean pullToRefresh) {
        if (!pullToRefresh) {
            animateLoadingViewIn();
        }
        mViewState.setStateShowLoading(pullToRefresh);
        // otherwise the pull to refresh widget will already display a loading animation
    }

    /**
     * Override this method if you want to provide your own animation for showing the loading view
     */
    protected void animateLoadingViewIn() {
        LCEAnimator.showLoading(mLoadingView, mContentView, mErrorView);
    }

    @Override
    public void showContent() {
        mViewState.setStateShowContent(getData());
        animateContentViewIn();
    }

    /**
     * Called to animate from loading view to content view
     */
    protected void animateContentViewIn() {
        LCEAnimator.showContent(mLoadingView, mContentView, mErrorView);
    }

    /**
     * Get the error message for a certain Exception that will be shown on {@link
     * #showError(Throwable, boolean)}
     */
    protected abstract String getErrorMessage(Throwable e, boolean pullToRefresh);

    /**
     * The default behaviour is to display a toast message as light error (i.e. pull-to-refresh
     * error).
     * Override this method if you want to display the light error in another way (like crouton).
     */
    protected void showLightError(String msg) {
        if (isRestoringViewState()) {
            return; // Do not display toast again while restoring viewstate
        }
        if (getActivity() != null) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    public void setRestoringViewState(boolean restoringViewState) {
        this.restoringViewState = restoringViewState;
    }

    public boolean isRestoringViewState() {
        return restoringViewState;
    }

    public void onViewStateInstanceRestored(boolean instanceStateRetained) {
        // Not needed in general. override it in subclass if you need this callback
    }

    public void onNewViewStateInstance() {
        loadData(false);
    }


    /**
     * Called if the error view has been clicked. To disable clicking on the mErrorView use
     * <code>mErrorView.setClickable(false)</code>
     */
    @OnClick(R.id.errorView)
    protected void onErrorViewClicked() {
        loadData(false);
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        mViewState.setStateShowError(e, pullToRefresh);
        String errorMsg = getErrorMessage(e, pullToRefresh);

        if (pullToRefresh) {
            showLightError(errorMsg);
        } else {
            mErrorView.setText(errorMsg);
            animateErrorViewIn();
        }
    }

    /**
     * Animates the error view in (instead of displaying content view / loading view)
     */
    protected void animateErrorViewIn() {
        LCEAnimator.showErrorView(mLoadingView, mContentView, mErrorView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mLoadingView = null;
        mContentView = null;
        mErrorView = null;
    }

    public LCEViewState<M, V> getViewState() {
        return mViewState;
    }

    public void setViewState(LCEViewState<M, V> viewState) {
        this.mViewState = (LCEViewState<M, V>) viewState;
    }

    /**
     * Get the data that has been set before in {@link #setData(Object)}
     * <p>
     * <b>It's necessary to return the same data as set before to ensure that {@link ViewState} works
     * correctly</b>
     * </p>
     *
     * @return The data
     */
    public abstract M getData();
}