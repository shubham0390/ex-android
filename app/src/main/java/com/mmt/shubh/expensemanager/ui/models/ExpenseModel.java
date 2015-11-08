package com.mmt.shubh.expensemanager.ui.models;

import com.mmt.shubh.expensemanager.database.api.ExpenseDataAdapter;
import com.mmt.shubh.expensemanager.database.api.MemberExpenseDataAdapter;
import com.mmt.shubh.expensemanager.database.api.TransactionDataAdapter;
import com.mmt.shubh.expensemanager.database.api.exceptions.AccountDataAdapter;
import com.mmt.shubh.expensemanager.database.content.Expense;
import com.mmt.shubh.expensemanager.database.content.MemberExpense;
import com.mmt.shubh.expensemanager.database.content.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

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

    @Inject
    public ExpenseModel() {
    }

    public void createExpense(long accountId, Expense expense) {

        long transactionId = createTransaction(expense);

        long expenseID = mExpenseDataAdapter.create(expense);

        Map<Long, Double> doubleMap = expense.getMemberMap();

        List<MemberExpense> memberExpenses = new ArrayList<>();

        for (long memberId : doubleMap.keySet()) {
            MemberExpense memberExpense = new MemberExpense();
            memberExpense.setMemberKey(memberId);
            memberExpense.setExpenseKey(expenseID);
            double sharedAmount = getSharedAmount(expense.getDistrubtionType(), expense.getExpenseAmount(), doubleMap.size(), doubleMap.get(memberId));
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
        deductAmoountFromAccount(accountId,expense.getExpenseAmount());

    }

    public void deductAmoountFromAccount(long accountId, double amount) {
        double balanceAmount = mAccountDataAdapter.getAccountBalance(accountId);
        mAccountDataAdapter.updateAmount(accountId, balanceAmount - amount);
    }

    private long createTransaction(Expense expense) {
        Transaction transaction = new Transaction();
        transaction.setAmount(expense.getExpenseAmount());
        transaction.setName(expense.getExpenseName());
        transaction.setDate(expense.getExpenseDate());
        transaction.setType(Transaction.TYPE_DEBIT);
        return mTransactionDataAdapter.create(transaction);
    }

    private double getSharedAmount(int sharedType, double totalAmount, int memberCount, double sharedAmount) {

        switch (sharedType) {
            case Expense.DISTRIBUTION_EQUALLY:
                return totalAmount / memberCount;
            case Expense.DISTRIBUTION_UNEQUALY:
                return sharedAmount;
            case Expense.DISTRIBUTION_PERCENTAGE:
                return (totalAmount * sharedAmount) / 100;
            default:
                return totalAmount / memberCount;
        }
    }
}
