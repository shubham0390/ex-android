
package com.mmt.shubh.expensemanager.ui.mvp;

import android.support.v4.app.Fragment;

/**
 * A ViewState is, like the name suggests, responsible to store the views state. In other words:
 * The
 * view like an Activity or a Fragment stores his state, like "showing loading animation", showing
 * error view, etc. The goal is to have a views that can easily restore there state after screen
 * orientation changes (from portrait to landscape and vice versa) by using a ViewState and the
 * well
 * defined View interfaces. The idea is to call the same methods the presenter would call to
 * restore
 * the view's state.
 * <p>
 * While fragments can restore every data object if you use {@link Fragment#setRetainInstance(boolean)}
 * = true Activities can't do that.
 * Therefore Activities have to use {@link RestorableViewState}.
 * </p>
 *
 * @param <V> The type of the View (extends {@link MVPView}
 * @author Hannes Dorfmann
 * @since 1.0.0
 */
public interface ViewState<V extends MVPView> {

  /**
   * Called to apply this viewstate on a given view.
   *
   * @param view The {@link MVPView}
   * @param retained true, if the components like the viewstate and the presenter have been
   * retained
   * because the {@link Fragment#setRetainInstance(boolean)} has been set to true
   */
  public void apply(V view, boolean retained);
}