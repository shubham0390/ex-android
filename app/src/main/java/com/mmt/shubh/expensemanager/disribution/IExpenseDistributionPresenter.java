package com.mmt.shubh.expensemanager.disribution;

import com.mmt.shubh.expensemanager.mvp.MVPPresenter;
import com.mmt.shubh.expensemanager.mvp.MVPView;

/**
 * Created by Subham Tyagi,
 * on 15/Sep/2015,
 * 4:59 PM
 * TODO:Add class comment.
 */
public interface IExpenseDistributionPresenter<V extends MVPView> extends MVPPresenter<V>{

    void loadDistributionData();
}
