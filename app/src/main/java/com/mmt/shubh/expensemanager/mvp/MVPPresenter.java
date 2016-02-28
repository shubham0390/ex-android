package com.mmt.shubh.expensemanager.mvp;

public interface MVPPresenter<V extends MVPView> {

    /**
     * Called when the presenter is resumed. After the initialization and when the presenter comes
     * from a pause state.
     */
    void resume();

    /**
     * Called when the presenter is paused.
     */
    void pause();

    /**
     * Set or attach the view to this presenter
     */
     void attachView(V view);

    /**
     * Will be called if the view has been destroyed. Typically this method will be invoked from
     * <code>Activity.detachView()</code> or <code>Fragment.onDestroyView()</code>
     */
     void detachView(boolean retainInstance);
}