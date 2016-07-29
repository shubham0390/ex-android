package com.km2labs.android.spendview.expense;

import android.text.TextUtils;

import com.km2labs.android.spendview.core.mvp.BasePresenter;
import com.km2labs.android.spendview.core.mvp.MVPPresenter;
import com.km2labs.android.spendview.database.content.Expense;
import com.km2labs.android.spendview.settings.UserSettings;
import com.km2labs.android.spendview.database.content.ModelFactory;

import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by subhamtyagi on 3/12/16.
 */
public class AddExpensePresenter extends BasePresenter<AddExpenseView> implements MVPPresenter<AddExpenseView> {

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
                           long date, long ownerKey) {

        if (TextUtils.isEmpty(title)) {
            getView().onEmptyTitleError();
            return;
        }

        if (amount < 1) {
            getView().onEmptyAmountError();
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
                           long date, long ownerKey, Map<Long, Double> memberMap) {

        if (TextUtils.isEmpty(title)) {
            getView().onEmptyTitleError();
            return;
        }

        if (amount < 1) {
            getView().onEmptyAmountError();
        }
        Expense expense = ModelFactory.getExpense();
        expense.setAccountKey(accountId);
        expense.setExpenseAmount(amount);
        expense.setExpenseName(title);
        expense.setExpenseDate(date);
        expense.setExpenseCategoryId(categoryId);
        expense.setOwnerId(ownerKey);
        expense.setMemberMap(memberMap);
        expense.setExpenseBookId(UserSettings.getInstance().getPersonalExpenseBook().getId());
        mExpenseModel.createExpense(expense);
    }



    private void showError(Throwable throwable) {
        Timber.tag(throwable.getMessage());
    }

    public void getAllAccountByMemberId(long memberId) {
        mExpenseModel.getAllAccount(memberId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> getView().onAccountListLoad(data));
    }


}
