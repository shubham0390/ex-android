package com.mmt.shubh.expensemanager.database.content.contract;

/**
 * Created by Subham Tyagi,
 * on 30/Jun/2015,
 * 6:40 PM
 * TODO:Add class comment.
 */
public interface TransactionContract extends BaseContract {

    String TABLE_NAME = "account_transaction";
    String PATH = "Transaction";

    String TRANSACTION_NAME = "transaction_name";
    String TRANSACTION_AMOUNT = "transaction_amount";
    String TRANSACTION_DATE = "transaction_date";
    String TRANSACTION_TYPE = "transaction_type";
    String ACCOUNT_KEY = "account_key";
}
