package com.mmt.shubh.expensemanager.ui.presenters;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.mmt.shubh.expensemanager.Constants;
import com.mmt.shubh.expensemanager.ui.mvp.MVPAbstractPresenter;
import com.mmt.shubh.expensemanager.ui.mvp.MVPPresenter;
import com.mmt.shubh.expensemanager.ui.views.IExpenseBookFragmentView;
import com.mmt.shubh.expensemanager.utils.Validator;

/**
 * Created by Subham Tyagi,
 * on 11/Sep/2015,
 * 5:14 PM
 * TODO:Add class comment.
 */
public class ExpenseBookFragmentPresenter extends MVPAbstractPresenter<IExpenseBookFragmentView>
        implements MVPPresenter<IExpenseBookFragmentView> {

    private Context mContext;

    public ExpenseBookFragmentPresenter(Context context) {
        mContext = context;
    }

    public void validateExpenseNameAndProceed(String expenseName, String mOutputFileUri, String expenseDescription) {
        if (TextUtils.isEmpty(expenseName)) {
            getView().showEmptyError();
            return;
        }
        if (Validator.expenseNameExist(mContext, expenseName)) {
            getView().showDuplicateExpenseBook();
            return;
        }

        Bundle expenseBookInfo = new Bundle();
        expenseBookInfo.putString(Constants.EXTRA_GROUP_NAME, expenseName);
        expenseBookInfo.putString(Constants.EXTRA_GROUP_IMAGE_URI, mOutputFileUri);
        expenseBookInfo.putString(Constants.EXTRA_GROUP_DESCRIPTION, expenseDescription);

        getView().addMemberFragment(expenseBookInfo);
    }


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }


}
