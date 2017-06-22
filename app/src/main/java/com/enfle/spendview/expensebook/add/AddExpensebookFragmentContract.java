package com.enfle.spendview.expensebook.add;

import com.enfle.spendview.core.mvp.MVPPresenter;
import com.enfle.spendview.core.mvp.MVPView;
import com.enfle.spendview.database.content.ExpenseBook;

/**
 * Created by : Subham Tyagi
 * Created on :  19/09/16.
 */

public interface AddExpensebookFragmentContract {

    interface Presenter extends MVPPresenter<View> {

        void validateExpenseNameAndProceed(String expenseName, String expenseDescription, boolean isUpdate);
    }

    interface View extends MVPView {
        void onEmptyError();

        void onDuplicateExpenseBook();

        void onExpenseBookAdded(ExpenseBook expenseBook);

        void onProgressUpdate();

        void onStopProgress();
    }
}
