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
import android.provider.BaseColumns;

import com.mmt.shubh.expensemanager.database.content.contract.AccountContract;
import com.mmt.shubh.expensemanager.database.content.contract.CategoryContract;
import com.mmt.shubh.expensemanager.database.content.contract.ExpenseBookContract;
import com.mmt.shubh.expensemanager.database.content.contract.ExpenseContract;
import com.mmt.shubh.expensemanager.database.content.contract.MemberContract;

public final class ExpenseDataEntity {

    private ExpenseDataEntity() {

    }

    /**
     * Class hold User table details
     */
    public static abstract class AccountEntity implements AccountContract {

        public static void createAccountTable(SQLiteDatabase database) {
            String createTable =
                    "CREATE TABLE " + TABLE_NAME
                            + "( "
                            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + USER_NAME + " TEXT , "
                            + USER_PASSWORD + " TEXT , "
                            + USER_STATUS + " TEXT NOT_NULL, "
                            + USER_DISPLAY_NAME + " TEXT NOT_NULL, "
                            + USER_EMAIL_ADDRESS + " TEXT NOT_NULL, "
                            + USER_PROFILE_IMAGE_URL + " TEXT , "
                            + USER_COVER_IMAGE_URL + " TEXT NOT_NULL " +
                            " );";
            database.execSQL(createTable);
        }
    }

    /**
     * Class hold the ExpenseBook table details
     */
    public static abstract class ExpenseBookEntity implements ExpenseBookContract {


        public static void createExpenseBookTable(SQLiteDatabase database) {
            String createTable = "CREATE TABLE " + TABLE_NAME
                    + " ( "
                    + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + EXPENSE_BOOK_NAME + " TEXT NOT NULL, "
                    + EXPENSE_BOOK_TYPE + " TEXT NOT NULL, "
                    + EXPENSE_BOOK_DESCRIPTION + " TEXT , "
                    + EXPENSE_BOOK_PROFILE_IMAGE + " BLOB , "
                    + USER_KEY + " INTEGER, "
                    + TOTAL_EXPENSE + " TEXT NOT NULL, "
                    + " FOREIGN KEY( " + USER_KEY + ")" +
                    " REFERENCES " + AccountEntity.TABLE_NAME + " (" + AccountEntity._ID + "));";
            database.execSQL(createTable);
        }

    }


    /**
     * class hold the Member table details
     */
    public static abstract class MemberEntity implements MemberContract {
       ;

        public static void createMemberTable(SQLiteDatabase database) {
            String createTable = "CREATE TABLE " + TABLE_NAME +
                    " ( "
                    + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + MEMBER_NAME + " TEXT NOT NULL, "
                    + MEMBER_EMAIL + " TEXT NOT NULL, "
                    + MEMBER_IMAGE_URI + " TEXT" +
                    ");";
            database.execSQL(createTable);
        }
    }


    /**
     * Class hold the EXPENSE table details
     */
    public static abstract class ExpenseEntity implements ExpenseContract {

        public static void createExpenseTable(SQLiteDatabase database) {
            String createTable = "CREATE TABLE " + TABLE_NAME
                    + " ( "
                    + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + EXPENSE_AMOUNT + " INTEGER NOT NULL, "
                    + EXPENSE_DATE + " TEXT NOT NULL, "
                    + EXPENSE_DESCRIPTION + " TEXT, "
                    + EXPENSE_NAME + " TEXT, "
                    + EXPENSE_PLACE + " TEXT NOT NULL, "
                    + MEMBER_KEY + " INTEGER, "
                    + EXPENSE_BOOK_KEY + " INTEGER, "
                    + CATEGORY_KEY + " INTEGER, "
                    + " FOREIGN KEY( " + MEMBER_KEY
                    + ") REFERENCES " + MemberEntity.TABLE_NAME + " (" + MemberEntity._ID + ") ,"
                    + " FOREIGN KEY( " + EXPENSE_BOOK_KEY
                    + ") REFERENCES " + ExpenseBookEntity.TABLE_NAME + " (" + ExpenseBookEntity._ID + ") ,"
                    + " FOREIGN KEY( " + CATEGORY_KEY
                    + ") REFERENCES " + CategoryEntity.TABLE_NAME + " (" + CategoryEntity._ID + ")"
                    + ");";
            database.execSQL(createTable);
        }
    }


    public static abstract class CategoryEntity implements CategoryContract {


        public static void createCategoryTable(SQLiteDatabase database) {
            String createTable = "CREATE TABLE " + TABLE_NAME
                    + "( "
                    + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + CATEGORY_NAME + " TEXT NOT NULL" + ");";
            database.execSQL(createTable);
        }
    }

    public static class MemberExpenseBookEntity implements MemberExpenseBookColumns {
        public static final String TABLE_NAME = "members_expense_book";

        public static void createMemberExpenseBookTable(SQLiteDatabase database) {
            String createTable = "CREATE TABLE " + TABLE_NAME +
                    " ( "
                    + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + MEMBER_KEY + " INTEGER NOT NULL, "
                    + EXPENSE_BOOK_KEY + " INTEGER, "
                    + TOTAL_EXPENSE + " TEXT NOT NULL, "
                    + "FOREIGN KEY (" + MEMBER_KEY + ") "
                    + "REFERENCES " + MemberEntity.TABLE_NAME + "( " + MemberEntity._ID + ")"
                    + "FOREIGN KEY (" + EXPENSE_BOOK_KEY + ") "
                    + "REFERENCES " + ExpenseBookEntity.TABLE_NAME + "( " + ExpenseBookEntity._ID + ")" +
                    ");";
            database.execSQL(createTable);
        }

    }

    public interface MemberExpenseBookColumns extends BaseColumns {
        String MEMBER_KEY = "member_key";
        String EXPENSE_BOOK_KEY = "expense_book_key";
        String TOTAL_EXPENSE = "total_expense";
    }

   /* public static class MemberExpenseEntity implements MemberExpenseBookColumns {

        public static final String TABLE_NAME = "members_expensebook";

        public static void createMemberExpenseTable(SQLiteDatabase database) {
            String createTable = "CREATE TABLE " + TABLE_NAME +
                    " ( "
                    +_ID
                    + MEMBER_KEY + " TEXT NOT NULL, "
                    + EXPENSE_BOOK_KEY + " INTEGER, "
                    + "FOREIGN KEY (" + MEMBER_KEY + ") " + "REFERENCES " + MemberEntity.TABLE_NAME + "( " + MemberEntity._ID + ")"
                    + "FOREIGN KEY (" + EXPENSE_BOOK_KEY + ") " + "REFERENCES " + ExpenseEntity.TABLE_NAME + "( " + ExpenseEntity._ID + ")" +
                    ");";
            database.execSQL(createTable);
        }
    }*/

  /*  public interface MemberExpenseColumns extends BaseColumns {

        String MEMBER_KEY = "member_key";
        String EXPENSE_KEY = "expense_key";
    }*/
}
