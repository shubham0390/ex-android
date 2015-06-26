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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DatabaseHelper extends SQLiteOpenHelper {

    /* Database Name */
    private static final String DATABASE_NAME = "Expense_manager.db";
    /**
     * Provide synchronization support for DB Access.
     */
    private static final Lock lock = new ReentrantLock();
    /* Database version */
    private static int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        ExpenseDataEntity.AccountEntity.createAccountTable(database);
        ExpenseDataEntity.ExpenseBookEntity.createExpenseBookTable(database);
        ExpenseDataEntity.MemberEntity.createMemberTable(database);
        ExpenseDataEntity.ExpenseEntity.createExpenseTable(database);
        ExpenseDataEntity.CategoryEntity.createCategoryTable(database);
//        ExpenseDataEntity.MemberExpenseEntity.createMemberExpenseTable(database);
        ExpenseDataEntity.MemberExpenseBookEntity.createMemberExpenseBookTable(database);

        addPrimaryDataToDatabase(database);

    }

    private void addPrimaryDataToDatabase(SQLiteDatabase database) {
      /*  database.execSQL("INSERT INTO " + ExpenseBookEntity.TABLE_NAME + " ("
                + ExpenseBookEntity.EXPENSE_BOOK_NAME + ") VALUES('ExpenseBook name 1')");

        database.execSQL("INSERT INTO " + ExpenseBookEntity.TABLE_NAME
                + " VALUES('ExpenseBook name 2')");*/

   /*     database.execSQL("INSERT INTO " + MemberEntity.TABLE_NAME + "("
                + MemberEntity.MEMBER_NAME + ", " + MemberEntity.MEMBER_EMAIL
                + ", " + MemberEntity.EXPENSE_BOOK_KEY
                + ") VALUES('Member name 1','name1@gmail.com', 1)");

        database.execSQL("INSERT INTO " + MemberEntity.TABLE_NAME + "("
                + MemberEntity.MEMBER_NAME + ", " + MemberEntity.MEMBER_EMAIL
                + ", " + MemberEntity.EXPENSE_BOOK_KEY
                + ") VALUES('Member name 2','name2@gmail.com', 2)");

        database.execSQL("INSERT INTO " + MemberEntity.TABLE_NAME + "("
                + MemberEntity.MEMBER_NAME + ", " + MemberEntity.MEMBER_EMAIL
                + ", " + MemberEntity.EXPENSE_BOOK_KEY
                + ") VALUES('Member name 3','name3@gmail.com', 1)");
*/
        database.execSQL("INSERT INTO "
                + ExpenseDataEntity.CategoryEntity.TABLE_NAME + "("
                + ExpenseDataEntity.CategoryEntity.CATEGORY_NAME
                + ") VALUES('HouseHold')");

        database.execSQL("INSERT INTO "
                + ExpenseDataEntity.CategoryEntity.TABLE_NAME + "("
                + ExpenseDataEntity.CategoryEntity.CATEGORY_NAME
                + ") VALUES('Food')");

        database.execSQL("INSERT INTO "
                + ExpenseDataEntity.CategoryEntity.TABLE_NAME + "("
                + ExpenseDataEntity.CategoryEntity.CATEGORY_NAME
                + ") VALUES('Entertainment')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // Nothing to do in first version

    }

    public void acquireAccess() {
        lock.lock();
    }

    public void releaseAccess() {
        lock.unlock();
    }

}
