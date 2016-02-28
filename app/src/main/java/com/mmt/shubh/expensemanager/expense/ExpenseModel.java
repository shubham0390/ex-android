package com.mmt.shubh.expensemanager.expense;

import com.mmt.shubh.expensemanager.database.api.ExpenseDataAdapter;
import com.mmt.shubh.expensemanager.database.api.MemberExpenseDataAdapter;
import com.mmt.shubh.expensemanager.database.api.TransactionDataAdapter;
import com.mmt.shubh.expensemanager.database.api.exceptions.AccountDataAdapter;
import com.mmt.shubh.expensemanager.database.content.Expense;
import com.mmt.shubh.expensemanager.database.content.MemberExpense;
import com.mmt.shubh.expensemanager.database.content.Transaction;
import com.mmt.shubh.expensemanager.debug.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

/**
 * Created by Subham Tyagi,
 * on 07/Nov/2015,
 * 5:17 PM
 * TODO:Add class comment.
 */
public class ExpenseModel {

    @Inject
    ExpenseDataAdapter mExpenseDataAdapter;
    @Inject
    MemberExpenseDataAdapter mMemberExpenseDataAdapter;
    @Inject
    TransactionDataAdapter mTransactionDataAdapter;
    @Inject
    AccountDataAdapter mAccountDataAdapter;

    private String LOG_TAG = getClass().getName();

    @Inject
    public ExpenseModel(ExpenseDataAdapter expenseDataAdapter,
                        MemberExpenseDataAdapter memberDataAdapter,
                        TransactionDataAdapter transactionDataAdapter,
                        AccountDataAdapter accountDataAdapter) {
        mExpenseDataAdapter = expenseDataAdapter;
        mMemberExpenseDataAdapter = memberDataAdapter;
        mTransactionDataAdapter = transactionDataAdapter;
        mAccountDataAdapter = accountDataAdapter;
    }

    public void createExpense(long accountId, Expense expense) {
        Logger.methodStart(LOG_TAG, "createExpense");
        long transactionId = createTransaction(expense);
        mExpenseDataAdapter.create(expense)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Expense>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Expense expense) {
                        addExpenseForMember(expense);
                        expense.setTransactionKey(transactionId);
                    }
                });


        //deductAmountFromAccount(accountId,expense.getFormatedExpenseAmount());
        Logger.methodStart(LOG_TAG, "createExpense");
    }

    private void addExpenseForMember(Expense expense) {
        Map<Long, Double> doubleMap = expense.getMemberMap();
        long expenseID = expense.getId();
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
        mMemberExpenseDataAdapter.create(memberExpenses);
    }

    public void deductAmountFromAccount(long accountId, double amount) {
        double balanceAmount = 3000; //mAccountDataAdapter.getAccountBalance(accountId);
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

    public Observable<List<ExpenseListViewModel>> loadExpenseWithFilter(final long memberId) {
        return mExpenseDataAdapter.getExpenseByMemberId(memberId);
    }

    public Observable<List<ExpenseListViewModel>> getAllExpenseForExpenseBook(final long expenseBookId) {
        return mExpenseDataAdapter.getExpenseByExpenseBookId(expenseBookId);
    }

    public Observable<List<ExpenseListViewModel>> loadExpenseWithFilter(final ExpenseFilter filter) {
        return mExpenseDataAdapter.getExpenseByMemberId(filter);

    }

    public AccountDataAdapter getAccountDataAdapter() {
        return mAccountDataAdapter;
    }
}
