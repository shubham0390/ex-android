package com.mmt.shubh.expensemanager.mvp.lce;

/**
 * Created by Subham Tyagi,
 * on 21/Sep/2015,
 * 9:20 AM
 * TODO:Add class comment.
 */
public class LCEViewStateImpl<D, V extends MVPLCEView<D>> extends AbsLceViewState<D, V> {

    public int getCurrentState() {
        return currentViewState;
    }

    @Override
    public void apply(V view, boolean retained) {
        super.apply(view, retained);
        if (currentViewState == 0) {
            view.loadData(false);
        }
    }
}
