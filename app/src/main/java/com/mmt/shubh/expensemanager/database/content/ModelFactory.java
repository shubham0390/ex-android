package com.mmt.shubh.expensemanager.database.content;

/**
 * Created by subhamtyagi on 3/12/16.
 */
public class ModelFactory {


    public static Expense getExpense() {
        return new Expense();
    }

    public static UserInfo getUserInfo() {
        return new UserInfo();
    }

    public static MemberExpense getNewMemberExpense() {
        return new MemberExpense();
    }

    public static ExpenseCategory getCategory() {
        return new ExpenseCategory();
    }
}
