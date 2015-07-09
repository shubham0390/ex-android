/*
 * Copyright (c) 2014. The MMT group Project
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.mmt.shubh.expensemanager.database;

import android.database.sqlite.SQLiteDatabase;

import com.mmt.shubh.expensemanager.database.content.UserInfo;
import com.mmt.shubh.expensemanager.database.content.contract.AccountContract;
import com.mmt.shubh.expensemanager.database.content.contract.CardContract;
import com.mmt.shubh.expensemanager.database.content.contract.CategoryContract;
import com.mmt.shubh.expensemanager.database.content.contract.ExpenseBookContract;
import com.mmt.shubh.expensemanager.database.content.contract.ExpenseContract;
import com.mmt.shubh.expensemanager.database.content.contract.MemberContract;
import com.mmt.shubh.expensemanager.database.content.contract.MemberExpenseBookContract;
import com.mmt.shubh.expensemanager.database.content.contract.MemberExpenseContract;
import com.mmt.shubh.expensemanager.database.content.contract.TransactionContract;
import com.mmt.shubh.expensemanager.database.content.contract.UserInfoContract;

public final class ExpenseDDL {


    public static void createUserTable(SQLiteDatabase database) {
        String createTable =
                "CREATE TABLE " + UserInfoContract.TABLE_NAME
                        + "( "
                        + UserInfoContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + UserInfoContract.USER_NAME + " TEXT , "
                        + UserInfoContract.USER_PASSWORD + " TEXT , "
                        + UserInfoContract.USER_STATUS + " TEXT NOT_NULL, "
                        + UserInfoContract.USER_DISPLAY_NAME + " TEXT NOT_NULL, "
                        + UserInfoContract.USER_EMAIL_ADDRESS + " TEXT NOT_NULL, "
                        + UserInfoContract.USER_PROFILE_IMAGE_URL + " TEXT , "
                        + UserInfoContract.USER_COVER_IMAGE_URL + " TEXT NOT_NULL " +
                        " );";
        database.execSQL(createTable);
    }

    /**
     * Class hold account table details
     */

    public static void createAccountTable(SQLiteDatabase database) {
        String createTable =
                "CREATE TABLE " + AccountContract.TABLE_NAME
                        + "( "
                        + AccountContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + AccountContract.ACCOUNT_NAME + " TEXT NOT_NULL, "
                        + AccountContract.ACCOUNT_BALANCE + " INTEGER "
                        + " );";
        database.execSQL(createTable);
    }

    /**
     * Class hold account table details
     */

    public static void createCardTable(SQLiteDatabase database) {
        String createTable =
                "CREATE TABLE " + CardContract.TABLE_NAME
                        + "( "
                        + CardContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + CardContract.CARD_NO + " TEXT NOT_NULL, "
                        + CardContract.BALANCE_AMOUNT + " INTEGER "
                        + "FOREIGN KEY (" + CardContract.ACCOUNT_KEY + ") " + "REFERENCES " + AccountContract.TABLE_NAME + "( " + AccountContract._ID + ")"
                        + " );";
        database.execSQL(createTable);
    }

    /**
     * Class hold the ExpenseBook table details
     */


    public static void createExpenseBookTable(SQLiteDatabase database) {
        String createTable = "CREATE TABLE " + ExpenseBookContract.TABLE_NAME
                + " ( "
                + ExpenseBookContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ExpenseBookContract.EXPENSE_BOOK_NAME + " TEXT NOT NULL, "
                + ExpenseBookContract.EXPENSE_BOOK_TYPE + " TEXT NOT NULL, "
                + ExpenseBookContract.EXPENSE_BOOK_DESCRIPTION + " TEXT , "
                + ExpenseBookContract.EXPENSE_BOOK_PROFILE_IMAGE + " TEXT  "
                + ")";
        database.execSQL(createTable);
    }

    /**
     * class hold the Member table details
     */

    public static void createMemberTable(SQLiteDatabase database) {
        String createTable = "CREATE TABLE " + MemberContract.TABLE_NAME +
                " ( "
                + MemberContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MemberContract.MEMBER_NAME + " TEXT NOT NULL, "
                + MemberContract.MEMBER_EMAIL + " TEXT NOT NULL, "
                + MemberContract.MEMBER_IMAGE_URI + " TEXT, "
                + MemberContract.MEMBER_USER_NAME + " TEXT,"
                + MemberContract.MEMBER_COVER_IMAGE_URL + " TEXT" +
                ");";
        database.execSQL(createTable);
    }


    /**
     * Class hold the EXPENSE table details
     */

    public static void createExpenseTable(SQLiteDatabase database) {
        String createTable = "CREATE TABLE " + ExpenseContract.TABLE_NAME
                + " ( "
                + ExpenseContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ExpenseContract.EXPENSE_AMOUNT + " INTEGER NOT NULL, "
                + ExpenseContract.EXPENSE_DATE + " TEXT NOT NULL, "
                + ExpenseContract.EXPENSE_DESCRIPTION + " TEXT, "
                + ExpenseContract.EXPENSE_NAME + " TEXT, "
                + ExpenseContract.EXPENSE_PLACE + " TEXT NOT NULL, "
                + ExpenseContract.EXPENSE_BOOK_KEY + " INTEGER, "
                + ExpenseContract.CATEGORY_KEY + " INTEGER, "
                + ExpenseContract.TRANSACTION_KEY + " INTEGER, "
                + " FOREIGN KEY( " + ExpenseContract.EXPENSE_BOOK_KEY + ") REFERENCES " + ExpenseBookContract.TABLE_NAME + " (" + ExpenseBookContract._ID + ") ,"
                + " FOREIGN KEY( " + ExpenseContract.CATEGORY_KEY + ") REFERENCES " + CategoryContract.TABLE_NAME + " (" + CategoryContract._ID + ")"
                + " FOREIGN KEY( " + ExpenseContract.TRANSACTION_KEY + ") REFERENCES " + TransactionContract.TABLE_NAME + " (" + TransactionContract._ID + ")"
                + ");";
        database.execSQL(createTable);
    }


    public static void createCategoryTable(SQLiteDatabase database) {
        String createTable = "CREATE TABLE " + CategoryContract.TABLE_NAME
                + "( "
                + CategoryContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CategoryContract.CATEGORY_NAME + " TEXT NOT NULL" + ");";
        database.execSQL(createTable);
    }

    public static void createMemberExpenseBookTable(SQLiteDatabase database) {
        String createTable = "CREATE TABLE " + MemberExpenseBookContract.TABLE_NAME +
                " ( "
                + MemberExpenseBookContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MemberExpenseBookContract.MEMBER_KEY + " INTEGER NOT NULL, "
                + MemberExpenseBookContract.EXPENSE_BOOK_KEY + " INTEGER, "
                + "FOREIGN KEY (" + MemberExpenseBookContract.MEMBER_KEY + ") "
                + "REFERENCES " + MemberContract.TABLE_NAME + "( " + MemberContract._ID + ")"
                + "FOREIGN KEY (" + MemberExpenseBookContract.EXPENSE_BOOK_KEY + ") "
                + "REFERENCES " + ExpenseBookContract.TABLE_NAME + "( " + ExpenseBookContract._ID + ")" +
                ");";
        database.execSQL(createTable);
    }


    public static void createMemberExpenseTable(SQLiteDatabase database) {
        String createTable = "CREATE TABLE " + MemberExpenseContract.TABLE_NAME +
                " ( "
                + MemberExpenseContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MemberExpenseContract.MEMBER_KEY + " INTEGER NOT NULL, "
                + MemberExpenseContract.EXPENSE_KEY + " INTEGER, "
                + MemberExpenseContract.SHARE_AMOUNT + " INTEGER NOT NULL,"
                + MemberExpenseContract.DEBIT_AMOUNT + " INTEGER NOT NULL,"
                + MemberExpenseContract.BALANCE_AMOUNT + " INTEGER NOT NULL, "
                + "FOREIGN KEY (" + MemberExpenseContract.MEMBER_KEY + ") " + "REFERENCES " + MemberContract.TABLE_NAME + "( " + MemberContract._ID + ")"
                + "FOREIGN KEY (" + MemberExpenseContract.EXPENSE_KEY + ") " + "REFERENCES " + ExpenseContract.TABLE_NAME + "( " + ExpenseContract._ID + ")" +
                ");";
        database.execSQL(createTable);
    }

    public static void createTransactionTable(SQLiteDatabase database) {
        String createTable = "CREATE TABLE " + TransactionContract.TABLE_NAME +
                " ( "
                + TransactionContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TransactionContract.TRANSACTION_NAME + " INTEGER NOT NULL, "
                + TransactionContract.TRANSACTION_AMOUNT + " INTEGER, "
                + TransactionContract.TRANSACTION_TYPE + " TEXT NOT NULL,"
                + TransactionContract.TRANSACTION_DATE + " INTEGER NOT NULL,"
                + TransactionContract.ACCOUNT_KEY + " INTEGER NOT NULL ,"
                + "FOREIGN KEY (" + TransactionContract.ACCOUNT_KEY+ ") " + "REFERENCES " + AccountContract.TABLE_NAME + "( " + AccountContract._ID + ")" +
                ");";
        database.execSQL(createTable);
    }

}
