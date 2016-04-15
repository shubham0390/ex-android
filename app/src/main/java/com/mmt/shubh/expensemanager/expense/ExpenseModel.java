package com.mmt.shubh.expensemanager.expense;

import com.mmt.shubh.expensemanager.database.api.ExpenseDataAdapter;
import com.mmt.shubh.expensemanager.database.api.MemberDataAdapter;
import com.mmt.shubh.expensemanager.database.api.MemberExpenseDataAdapter;
import com.mmt.shubh.expensemanager.database.api.TransactionDataAdapter;
import com.mmt.shubh.expensemanager.database.api.exceptions.AccountDataAdapter;
import com.mmt.shubh.expensemanager.database.content.Expense;
import com.mmt.shubh.expensemanager.database.content.MemberExpense;
import com.mmt.shubh.expensemanager.database.content.Transaction;
import com.mmt.shubh.expensemanager.debug.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Subham Tyagi,
 * on 07/Nov/2015,
 * 5:17 PM
 * TODO:Add class comment.
 */
public class ExpenseModel {

    private ExpenseDataAdapter mExpenseDataAdapter;
    private MemberExpenseDataAdapter mMemberExpenseDataAdapter;
    private TransactionDataAdapter mTransactionDataAdapter;
    private AccountDataAdapter mAccountDataAdapter;
    private MemberDataAdapter mMemberDataAdapter;
    private String LOG_TAG = getClass().getName();

    @Inject
    public ExpenseModel(ExpenseDataAdapter expenseDataAdapter,
                        MemberExpenseDataAdapter memberDataAdapter,
                        TransactionDataAdapter transactionDataAdapter,
                        AccountDataAdapter accountDataAdapter, MemberDataAdapter DataAdapter) {
        mExpenseDataAdapter = expenseDataAdapter;
        mMemberExpenseDataAdapter = memberDataAdapter;
        mTransactionDataAdapter = transactionDataAdapter;
        mAccountDataAdapter = accountDataAdapter;
        mMemberDataAdapter = DataAdapter;
        Timber.tag(getClass().getName());
    }

    public Observable<Expense> createExpense(Expense expense) {
        return Observable.create(subscriber -> {

            createExpenses(expense);
        });
    }

    private void createExpenses(Expense expense) {
        Logger.methodStart(LOG_TAG, "createExpense");
        deductAmountFromAccount(expense.getAccountKey(), expense.getExpenseAmount());
        long transactionId = createTransaction(expense);
        expense.setTransactionKey(transactionId);
        mExpenseDataAdapter.create(expense)
                .observeOn(Schedulers.immediate())
                .subscribeOn(Schedulers.immediate())
                .subscribe(d -> {
                    addExpenseForMember(expense);

                }, e -> {
                    Timber.e("Unable to create expense.");
                });
    }

    private void addExpenseForMember(Expense expense) {
        Logger.methodStart(LOG_TAG, "addExpenseForMember");
        Map<Long, Double> doubleMap = expense.getMemberMap();
        long expenseID = expense.getId();
        List<MemberExpense> memberExpenses = new ArrayList<>();
        Iterator iterator = doubleMap.keySet().iterator();


        while (iterator.hasNext()) {
            int memberId = (int) iterator.next();
            double paidAmount = doubleMap.get(memberId);
            double sharedAmount = getSharedAmount(expense.getDistrubtionType(),
                    expense.getExpenseAmount(), doubleMap.size(), paidAmount);

            MemberExpense memberExpense = new MemberExpense();

            memberExpense.setMemberKey(memberId);
            memberExpense.setExpenseKey(expenseID);
            memberExpense.setShareAmount(sharedAmount);
            memberExpense.setDebitAmount(paidAmount);

            memberExpense.setBalanceAmount(paidAmount - sharedAmount);
            memberExpenses.add(memberExpense);
        }
        mMemberExpenseDataAdapter.create(memberExpenses)
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate())
                .subscribe(d -> {
                }, e -> {
                    Timber.e(e.getMessage());
                });
        Logger.methodEnd(LOG_TAG, "addExpenseForMember");
    }

    public void deductAmountFromAccount(long accountId, double amount) {
        Timber.i("Detecting money from accoun");
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
        transaction.setAccountKey(expense.getAccountKey());
        mTransactionDataAdapter.create(transaction)
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate())
                .subscribe(d -> {
                }, e -> {
                    Timber.e(e.getMessage());
                });
        Logger.methodEnd(LOG_TAG, "createTransaction");
        return transaction.getId();
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
        return mExpenseDataAdapter.getExpensesWithFilters(memberId);
    }

    public Observable<List<ExpenseListViewModel>> getAllExpenseForExpenseBook(final long expenseBookId) {
        return mExpenseDataAdapter.getExpenseByExpenseBookId(expenseBookId);
    }

    public Observable<List<ExpenseListViewModel>> loadExpenseWithFilter(final ExpenseFilter filter) {
        return mExpenseDataAdapter.getExpensesWithFilters(filter);

    }

    public AccountDataAdapter getAccountDataAdapter() {
        return mAccountDataAdapter;
    }

    public MemberDataAdapter getMemberDataAdapter() {
        return mMemberDataAdapter;
    }

    public Observable<List<Expense>> createExpense(List<Expense> expenses) {

        return Observable.create(subscriber -> {
            for (Expense expense : expenses) {
                createExpenses(expense);
            }
        });
    }


}
