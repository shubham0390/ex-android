package com.mmt.shubh.expensemanager.expense;

import android.text.TextUtils;

import com.mmt.shubh.expensemanager.database.content.Expense;
import com.mmt.shubh.expensemanager.database.content.ModelFactory;
import com.mmt.shubh.expensemanager.mvp.MVPAbstractPresenter;
import com.mmt.shubh.expensemanager.mvp.MVPPresenter;

import java.util.Map;

/**
 * Created by subhamtyagi on 3/12/16.
 */
public class AddExpensePresenter extends MVPAbstractPresenter<AddExpenseView> implements MVPPresenter<AddExpenseView> {

    private ExpenseModel mExpenseModel;

    public AddExpensePresenter(ExpenseModel expenseModel) {
        mExpenseModel = expenseModel;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    public void addExpense(String title, double amount, long accountId, long categoryId,
                           long date,long ownerKey) {

        if (TextUtils.isEmpty(title)) {
            getView().showEmptyTitleError();
            return;
        }

        if (amount < 1) {
            getView().showEmptyAmountError();
        }
        Expense expense = ModelFactory.getExpense();
        expense.setAccountKey(accountId);
        expense.setExpenseAmount(amount);
        expense.setExpenseName(title);
        expense.setExpenseDate(date);
        expense.setExpenseCategoryId(categoryId);
        expense.setOwnerId(ownerKey);
        // TODO: 3/12/16 load private expense book in usersettings and get here from there
        expense.setExpenseBookId(1);
        mExpenseModel.createExpense(expense);

    }
    public void addExpense(String title, double amount, long accountId, long categoryId,
                           long date, long ownerKey, Map<Long,Double> memberMap) {

        if (TextUtils.isEmpty(title)) {
            getView().showEmptyTitleError();
            return;
        }

        if (amount < 1) {
            getView().showEmptyAmountError();
        }
        Expense expense = ModelFactory.getExpense();
        expense.setAccountKey(accountId);
        expense.setExpenseAmount(amount);
        expense.setExpenseName(title);
        expense.setExpenseDate(date);
        expense.setExpenseCategoryId(categoryId);
        expense.setOwnerId(ownerKey);
        expense.setMemberMap(memberMap);
        // TODO: 3/12/16 load private expense book in usersettings and get here from there
        expense.setExpenseBookId(1);
        mExpenseModel.createExpense(expense);

    }

}
