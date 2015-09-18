package com.mmt.shubh.expensemanager.ui.presenters;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;

import com.mmt.shubh.expensemanager.Constants;
import com.mmt.shubh.expensemanager.AddUpdateExpenseBookTask;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.task.OnTaskCompleteListener;
import com.mmt.shubh.expensemanager.task.TaskProcessor;
import com.mmt.shubh.expensemanager.task.TaskResult;
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
        implements MVPPresenter<IExpenseBookFragmentView>, OnTaskCompleteListener {

    private Context mContext;
    TaskProcessor mTaskProcessor;

    public ExpenseBookFragmentPresenter(Context context) {
        mContext = context;
        mTaskProcessor = TaskProcessor.getTaskProcessor();
        mTaskProcessor.setOnTaskCompleteListener(this);
    }

    public void validateExpenseNameAndProceed(String expenseName, String mOutputFileUri,
                                              String expenseDescription, boolean isUpdate) {
        if (TextUtils.isEmpty(expenseName)) {
            getView().showEmptyError();
            return;
        }
        if (Validator.expenseNameExist(mContext, expenseName) && !isUpdate) {
            getView().showDuplicateExpenseBook();
            return;
        }

        ExpenseBook expenseBook = new ExpenseBook();

        expenseBook.setName(expenseName);
        expenseBook.setProfileImagePath(mOutputFileUri);
        expenseBook.setDescription(expenseDescription);
        AddUpdateExpenseBookTask task = new AddUpdateExpenseBookTask(mContext, expenseBook, isUpdate);
        mTaskProcessor.execute(task);
        getView().showCreatingExpenseBookProgress();
    }


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }


    @Override
    public void onTaskComplete(String action, TaskResult taskResult) {
        getView().hideProgress();
        if (taskResult.isSuccess()) {
            if (taskResult.getStatusCode() == AddUpdateExpenseBookTask.STATUS_CODE_UPDATE_SUCCESSFULLY) {
                    getView().exit();
            } else {
                Bundle expenseBookInfo = new Bundle();
                expenseBookInfo.putParcelable(Constants.KEY_EXPENSE_BOOK, (Parcelable) taskResult.getResult());
                getView().addMemberFragment(expenseBookInfo);
            }
        } else
            getView().showError((String) taskResult.getResult());
    }
}
