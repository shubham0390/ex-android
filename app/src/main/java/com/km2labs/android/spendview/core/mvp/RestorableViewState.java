
package com.km2labs.android.spendview.core.mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * A ViewState that is parcelable. Activities can only use this kind of ViewState, because saving
 * the ViewState in a bundle as Parcelable during screen orientation changes (from portrait to
 * landscape or vice versa) is the only way to do that for activities
 *
 * @author Hannes Dorfmann
 * @since 1.0.0
 */
public interface RestorableViewState<V extends MVPView> extends ViewState<V> {

    /**
     * Saves this ViewState to the outgoing bundle.
     * This will typically be called in {@link android.app.Activity#onSaveInstanceState(Bundle)}
     * or in  {@link android.app.Fragment#onSaveInstanceState(Bundle)}
     *
     * @param out The bundle where the viewstate should be stored in
     */
    void saveInstanceState(@NonNull Bundle out);

    /**
     * Restores the viewstate that has been saved before with {@link #saveInstanceState(Bundle)}
     *
     * @param in the bundle to read the data from
     * @return null, if view state could not be restored or the restore viestate instance. Typically
     * this method will return <code>this</code>.
     */
    RestorableViewState<V> restoreInstanceState(Bundle in);
}