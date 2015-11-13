package com.mmt.shubh.expensemanager.ui.models;

import android.content.Context;

import com.mmt.shubh.expensemanager.database.api.ExpenseDataAdapter;
import com.mmt.shubh.expensemanager.database.api.MemberExpenseDataAdapter;
import com.mmt.shubh.expensemanager.database.api.TransactionDataAdapter;
import com.mmt.shubh.expensemanager.database.api.exceptions.AccountDataAdapter;
import com.mmt.shubh.expensemanager.database.content.Expense;
import com.mmt.shubh.expensemanager.database.content.MemberExpense;
import com.mmt.shubh.expensemanager.database.content.Transaction;
import com.mmt.shubh.expensemanager.database.dataadapters.AccountSQLDataAdapter;
import com.mmt.shubh.expensemanager.database.dataadapters.ExpenseSqlDataAdapter;
import com.mmt.shubh.expensemanager.database.dataadapters.MemberExpenseSQLDataAdapter;
import com.mmt.shubh.expensemanager.database.dataadapters.TransactionSQLDataAdapter;
import com.mmt.shubh.expensemanager.debug.Logger;
import com.mmt.shubh.expensemanager.ui.fragment.ExpenseFilter;
import com.mmt.shubh.expensemanager.ui.viewmodel.ExpenseListViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Subham Tyagi,
 * on 07/Nov/2015,
 * 5:17 PM
 * TODO:Add class comment.
 */
public class ExpenseModel {
    private String LOG_TAG = getClass().getName();

    @Inject
    ExpenseDataAdapter mExpenseDataAdapter;

    @Inject
    MemberExpenseDataAdapter mMemberExpenseDataAdapter;

    @Inject
    TransactionDataAdapter mTransactionDataAdapter;

    @Inject
    AccountDataAdapter mAccountDataAdapter;

    @Inject
    public ExpenseModel(Context context) {
        mExpenseDataAdapter = new ExpenseSqlDataAdapter(context);
        mMemberExpenseDataAdapter = new MemberExpenseSQLDataAdapter(context);
        mTransactionDataAdapter = new TransactionSQLDataAdapter(context);
        mAccountDataAdapter = new AccountSQLDataAdapter(context);
    }

    public void createExpense(long accountId, Expense expense) {
        Logger.methodStart(LOG_TAG, "createExpense");
        long transactionId = createTransaction(expense);

        long expenseID = mExpenseDataAdapter.create(expense);

        Map<Long, Double> doubleMap = expense.getMemberMap();

        List<MemberExpense> memberExpenses = new ArrayList<>();

        for (long memberId : doubleMap.keySet()) {

            double sharedAmount = getSharedAmount(expense.getDistrubtionType(),
                    expense.getExpenseAmount(), doubleMap.size(), doubleMap.get(memberId));

            MemberExpense memberExpense = new MemberExpense();

            memberExpense.setMemberKey(memberId);
            memberExpense.setExpenseKey(expenseID);
            memberExpense.setShareAmount(sharedAmount);

            if (memberId == expense.getOwnerId()) {
                memberExpense.setDebitAmount(expense.getExpenseAmount());
            } else {
                memberExpense.setDebitAmount(0);
            }

            memberExpense.setBalanceAmount(expense.getExpenseAmount() - sharedAmount);
            memberExpenses.add(memberExpense);
        }
        expense.setTransactionKey(transactionId);
        mMemberExpenseDataAdapter.create(memberExpenses);
        //deductAmountFromAccount(accountId,expense.getExpenseAmount());
        Logger.methodStart(LOG_TAG, "createExpense");
    }

    public void deductAmountFromAccount(long accountId, double amount) {
        double balanceAmount = mAccountDataAdapter.getAccountBalance(accountId);
        mAccountDataAdapter.updateAmount(accountId, balanceAmount - amount);
    }

    private long createTransaction(Expense expense) {
        Logger.methodStart(LOG_TAG, "createTransaction");
        Transaction transaction = new Transaction();
        transaction.setAmount(expense.getExpenseAmount());
        transaction.setName(expense.getExpenseName());
        transaction.setDate(expense.getExpenseDate());
        transaction.setType(Transaction.TYPE_DEBIT);
        transaction.setAccountKey(1);
        long id = mTransactionDataAdapter.create(transaction);
        Logger.methodEnd(LOG_TAG, "createTransaction");
        return id;
    }

    private double getSharedAmount(int sharedType, double totalAmount, int memberCount, double sharedAmount) {
        Logger.methodStart(LOG_TAG, "getSharedAmount");
        double sharedAmountC;
        switch (sharedType) {
            case Expense.DISTRIBUTION_EQUALLY:
                sharedAmountC = totalAmount / memberCount;
                break;
            case Expense.DISTRIBUTION_UNEQUALY:
                sharedAmountC = sharedAmount;
                break;
            case Expense.DISTRIBUTION_PERCENTAGE:
                sharedAmountC = (totalAmount * sharedAmount) / 100;
                break;
            default:
                sharedAmountC = totalAmount / memberCount;
        }
        Logger.methodEnd(LOG_TAG, "getSharedAmount");
        return sharedAmountC;
    }

    public Observable<List<ExpenseListViewModel>> loadExpenseWithFilter(long memberId) {
        return Observable.create(subscriber -> {
            List<ExpenseListViewModel> expenses = mExpenseDataAdapter.getExpenseByMemberId(memberId);
            subscriber.onNext(expenses);
            subscriber.onCompleted();
        });
    }

    public Observable<List<ExpenseListViewModel>> getAllExpenseForExpenseBook(long expenseBookId) {
        return Observable.create((Subscriber<? super List<ExpenseListViewModel>> subscriber) -> {
            List<ExpenseListViewModel> expenses = mExpenseDataAdapter.getExpenseByExpenseBookId(expenseBookId);
            subscriber.onNext(expenses);
            subscriber.onCompleted();
        });
    }

    public Observable<List<ExpenseListViewModel>> loadExpenseWithFilter(ExpenseFilter filter) {
        return Observable.create(subscriber -> {
            List<ExpenseListViewModel> expenses = mExpenseDataAdapter.getExpenseByMemberId(filter);
            subscriber.onNext(expenses);
            subscriber.onCompleted();
        });

    }
}
