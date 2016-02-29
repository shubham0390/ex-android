package com.mmt.shubh.expensemanager.mvp;

import android.support.annotation.Nullable;

import com.mmt.shubh.expensemanager.ExpenseApplication;
import com.mmt.shubh.expensemanager.dagger.component.MainComponent;

import java.lang.ref.WeakReference;

import timber.log.Timber;

public abstract class MVPAbstractPresenter<V extends MVPView> {
    protected String TAG = getClass().getName();
    private WeakReference<V> viewRef;

    public MVPAbstractPresenter() {
        Timber.tag(getClass().getName());
    }

    public void attachView(V view) {
        viewRef = new WeakReference<>(view);
        injectDependencies((MainComponent) ExpenseApplication.component());
        Timber.tag(getClass().getName());
    }

    /**
     * Get the attached view. You should always call {@link #isViewAttached()} to check if the view
     * is
     * attached to avoid NullPointerExceptions.
     *
     * @return <code>null</code>, if view is not attached, otherwise the concrete view instance
     */
    @Nullable
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

    public void detachView(boolean retainInstance) {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }

    protected void injectDependencies(MainComponent mainComponent) {

    }


}