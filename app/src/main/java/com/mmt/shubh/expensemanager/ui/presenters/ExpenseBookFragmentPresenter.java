package com.mmt.shubh.expensemanager.ui.presenters;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.mmt.shubh.expensemanager.AddUpdateExpenseBookTask;
import com.mmt.shubh.expensemanager.Constants;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.debug.Logger;
import com.mmt.shubh.expensemanager.task.OnTaskCompleteListener;
import com.mmt.shubh.expensemanager.task.TaskProcessor;
import com.mmt.shubh.expensemanager.task.TaskResult;
import com.mmt.shubh.expensemanager.ui.mvp.MVPAbstractPresenter;
import com.mmt.shubh.expensemanager.ui.mvp.MVPPresenter;
import com.mmt.shubh.expensemanager.ui.views.IExpenseBookFragmentView;
import com.mmt.shubh.expensemanager.utils.Validator;

import org.parceler.Parcels;

/**
 * Created by Subham Tyagi,
 * on 11/Sep/2015,
 * 5:14 PM
 * TODO:Add class comment.
 */
public class ExpenseBookFragmentPresenter extends MVPAbstractPresenter<IExpenseBookFragmentView>
        implements MVPPresenter<IExpenseBookFragmentView>, OnTaskCompleteListener {

    private final String TAG = getClass().getName();

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
        Logger.debug(TAG, "task with action " + action + "complete");
        getView().hideProgress();
        if (taskResult.isSuccess()) {
            if (taskResult.getStatusCode() == AddUpdateExpenseBookTask.STATUS_CODE_UPDATE_SUCCESSFULLY) {
                Logger.debug(TAG, "Expense book updated successfully");
                getView().exit();
            } else {
                Logger.debug(TAG, "Expense book created successfully ,installing add member fragment");
                Bundle expenseBookInfo = new Bundle();
                expenseBookInfo.putParcelable(Constants.KEY_EXPENSE_BOOK, Parcels.wrap(taskResult.getResult()));
                getView().addMemberFragment(expenseBookInfo);
            }
        } else
            getView().showError((String) taskResult.getResult());
    }
}
