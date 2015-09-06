package com.mmt.shubh.expensemanager.ui.presenters;

import com.mmt.shubh.expensemanager.ui.mvp.MVPPresenter;
import com.mmt.shubh.expensemanager.ui.mvp.MVPView;

/**
 * Created by Subham Tyagi,
 * on 14/Aug/2015,
 * 5:26 PM
 * TODO:Add class comment.
 */
public interface ISignInPresenter<V extends MVPView> extends MVPPresenter<V> {
    void validateCredentials(String username, String password);
}
