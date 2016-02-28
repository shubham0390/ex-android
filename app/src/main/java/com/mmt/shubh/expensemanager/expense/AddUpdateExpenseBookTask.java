package com.mmt.shubh.expensemanager.expense;

import android.content.Context;

import com.mmt.shubh.expensemanager.TaskActions;
import com.mmt.shubh.expensemanager.settings.UserSettings;
import com.mmt.shubh.expensemanager.database.api.MemberDataAdapter;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.database.dataadapters.ExpenseBookSQLDataAdapter;
import com.mmt.shubh.expensemanager.database.dataadapters.MemberSQLDataAdapter;
import com.mmt.shubh.expensemanager.debug.Logger;
import com.mmt.shubh.expensemanager.task.AbstractTask;
import com.mmt.shubh.expensemanager.task.TaskResult;

/**
 * @author uchamaria
 */
public class AddUpdateExpenseBookTask extends AbstractTask<ExpenseBook> {

    public static final int STATUS_CODE_UPDATE_SUCCESSFULLY = 991;

    private final String TAG = getClass().getSimpleName();

    private ExpenseBook mExpenseBook;

    private ExpenseBookSQLDataAdapter mExpenseBookAdapter;

    private boolean isUpdate;

    public AddUpdateExpenseBookTask(Context context, ExpenseBook expenseBook, boolean isUpdate) {
        super(context);
        this.mContext = context;
        this.mExpenseBook = expenseBook;
        this.mExpenseBookAdapter = new ExpenseBookSQLDataAdapter(mContext);
        this.isUpdate = isUpdate;
    }

    /**
     * creates a new expense book entry in the database
     */
    private void saveExpenseBookDetails() {
        Logger.debug(TAG, "entered saveExpenseBookDetails()");

        mExpenseBook.setType("public");

        UserSettings userSettings = UserSettings.getInstance();
        MemberDataAdapter memberDataAdapter = new MemberSQLDataAdapter(mContext);
        mExpenseBook.setOwner(memberDataAdapter.get(userSettings.getUserInfo().getMemberKey()));

        mExpenseBook.setCreationTime(System.currentTimeMillis());

        mExpenseBookAdapter.create(mExpenseBook);
        Logger.debug(TAG, "exiting saveExpenseBookDetails()");
    }


    @Override
    public TaskResult execute() {
        Logger.debug(TAG, "entered execute");

        if (isUpdate) {
            Logger.debug(TAG,"Updating expense book");
            mExpenseBookAdapter.update(mExpenseBook);
            createTaskResult(true, STATUS_CODE_UPDATE_SUCCESSFULLY, mExpenseBook);
        } else {
            Logger.debug(TAG,"Creating new expense book");
            saveExpenseBookDetails();
            if (mExpenseBook.getId() >= 0) {
                createTaskResult(true, 1234, mExpenseBook);
            } else {
                createTaskResult(false, 1234, new Exception("Unable to create exception book"));
            }
            Logger.debug(TAG, "exiting execute");
        }
        return mTaskResult;
    }

    @Override
    public String getTaskAction() {
        return TaskActions.ACTION_CREATE_UPDATE_EXPENSE_BOOK;
    }
}
