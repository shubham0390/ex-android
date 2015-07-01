package com.mmt.shubh.expensemanager.database.content.contract;

/**
 * Created by Subham Tyagi,
 * on 30/Jun/2015,
 * 6:38 PM
 * TODO:Add class comment.
 */
public interface MemberExpenseBookContract extends BaseContract {

    String TABLE_NAME = "member_expense_book";

    String PATH = "Member/ExpenseBook";

    String MEMBER_KEY = "member_key";

    String EXPENSE_BOOK_KEY = "expense_book_key";
}
