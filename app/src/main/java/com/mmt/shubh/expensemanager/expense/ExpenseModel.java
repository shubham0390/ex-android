package com.mmt.shubh.expensemanager.expense;

import com.mmt.shubh.expensemanager.core.dagger.scope.ConfigPersistent;
import com.mmt.shubh.expensemanager.database.api.ExpenseBookDataAdapter;
import com.mmt.shubh.expensemanager.database.api.ExpenseDataAdapter;
import com.mmt.shubh.expensemanager.database.api.MemberDataAdapter;
import com.mmt.shubh.expensemanager.database.api.MemberExpenseDataAdapter;
import com.mmt.shubh.expensemanager.database.api.TransactionDataAdapter;
import com.mmt.shubh.expensemanager.database.api.exceptions.AccountDataAdapter;
import com.mmt.shubh.expensemanager.database.content.Account;
import com.mmt.shubh.expensemanager.database.content.Expense;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.database.content.MemberExpense;
import com.mmt.shubh.expensemanager.database.content.Transaction;
import com.mmt.shubh.expensemanager.debug.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import timber.log.Timber;

/**
 * Created by Subham Tyagi,
 * on 07/Nov/2015,
 * 5:17 PM
 * TODO:Add class comment.
 */
@ConfigPersistent
public class ExpenseModel {
    private String LOG_TAG = getClass().getName();

    private ExpenseDataAdapter mExpenseDataAdapter;
    private MemberExpenseDataAdapter mMemberExpenseDataAdapter;
    private TransactionDataAdapter mTransactionDataAdapter;
    private AccountDataAdapter mAccountDataAdapter;
    private MemberDataAdapter mMemberDataAdapter;
    private ExpenseBookDataAdapter mExpenseBookDataAdapter;

    @Inject
    public ExpenseModel(ExpenseDataAdapter expenseDataAdapter,
                        MemberExpenseDataAdapter memberDataAdapter,
                        TransactionDataAdapter transactionDataAdapter,
                        AccountDataAdapter accountDataAdapter,
                        MemberDataAdapter DataAdapter, ExpenseBookDataAdapter expenseBookDataAdapter) {
        mExpenseDataAdapter = expenseDataAdapter;
        mMemberExpenseDataAdapter = memberDataAdapter;
        mTransactionDataAdapter = transactionDataAdapter;
        mAccountDataAdapter = accountDataAdapter;
        mMemberDataAdapter = DataAdapter;
        mExpenseBookDataAdapter = expenseBookDataAdapter;
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
        mExpenseDataAdapter.create(expense);
        addExpenseForMember(expense);
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
        mMemberExpenseDataAdapter.create(memberExpenses);
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
        mTransactionDataAdapter.create(transaction);
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


    public Observable<List<ExpenseBook>> loadAllExpenseBookForMember(long memberId) {
        return mExpenseBookDataAdapter.getByMemberId(memberId);
    }

    public Observable<List<Account>> getAllAccount(long memberId) {
        return mAccountDataAdapter.getAccountByMember(memberId);
    }

    public Observable<ExpenseBook> getExpenseBook(long expenseBookId) {
        return Observable.create(subscriber -> {
            subscriber.onNext(mExpenseBookDataAdapter.get(expenseBookId));
        });

    }
}
