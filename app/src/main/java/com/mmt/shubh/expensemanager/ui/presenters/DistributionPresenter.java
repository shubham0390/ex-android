package com.mmt.shubh.expensemanager.ui.presenters;

import com.mmt.shubh.expensemanager.ui.models.DistributionModel;
import com.mmt.shubh.expensemanager.ui.mvp.MVPAbstractPresenter;
import com.mmt.shubh.expensemanager.ui.mvp.lce.MVPLCEView;
import com.mmt.shubh.expensemanager.ui.presenters.api.IExpenseDistributionPresenter;

/**
 * Created by Subham Tyagi,
 * on 15/Sep/2015,
 * 5:05 PM
 * TODO:Add class comment.
 */
public class DistributionPresenter extends MVPAbstractPresenter<MVPLCEView<DistributionModel>>
        implements IExpenseDistributionPresenter<MVPLCEView<DistributionModel>> {

    @Override
    public void loadDistributionData() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }


}
