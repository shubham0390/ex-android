package com.km2labs.android.spendview.core.mvp.lce;


import com.km2labs.android.spendview.core.mvp.ViewState;

/**
 * An LCE (Loading-Content-Error) {@link ViewState} is a viewstate that can save and restore
 * loading, the content, or the error (exception).
 *
 * @param <D> the data / model type
 * @param <V> the type of the view
 * @author Hannes Dorfmann
 * @since 1.0.0
 */
public interface LCEViewState<D, V extends MVPLCEView<D>> extends ViewState<V> {
    /**
     * Used as currentViewState to indicate that loading is currently displayed on screen
     */
    int STATE_SHOW_LOADING = 0;
    /**
     * Used as currentViewState to indicate that the content is currently displayed on
     * screen
     */
    int STATE_SHOW_CONTENT = 1;
    /**
     * Used as currentViewState to indicate that the error is currently displayed on screen
     */
    int STATE_SHOW_ERROR = -1;

    /**
     * Set the view state to showing content
     *
     * @param loadedData The content data that is currently dipslayed
     */
    void setStateShowContent(D loadedData);

    /**
     * Set the view state to showing the errorview
     *
     * @param e             The reason why the errorview is displayed on screen
     * @param pullToRefresh Was is a pull to refresh operation that has failed?
     */
    void setStateShowError(Throwable e, boolean pullToRefresh);

    /**
     * Set the state to show loading
     *
     * @param pullToRefresh Was it a pull to refresh operation?
     */
    void setStateShowLoading(boolean pullToRefresh);
}