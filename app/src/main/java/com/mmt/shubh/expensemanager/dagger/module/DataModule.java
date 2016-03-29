package com.mmt.shubh.expensemanager.dagger.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.mmt.shubh.expensemanager.ExpenseApplication;
import com.mmt.shubh.expensemanager.dagger.module.api.IDataModule;
import com.mmt.shubh.expensemanager.database.DatabaseHelper;
import com.mmt.shubh.expensemanager.database.api.ExpenseBookDataAdapter;
import com.mmt.shubh.expensemanager.database.api.ExpenseDataAdapter;
import com.mmt.shubh.expensemanager.database.api.MemberDataAdapter;
import com.mmt.shubh.expensemanager.database.api.MemberExpenseDataAdapter;
import com.mmt.shubh.expensemanager.database.api.TransactionDataAdapter;
import com.mmt.shubh.expensemanager.database.api.UserInfoDataAdapter;
import com.mmt.shubh.expensemanager.database.api.exceptions.AccountDataAdapter;
import com.mmt.shubh.expensemanager.database.dataadapters.AccountSQLDataAdapter;
import com.mmt.shubh.expensemanager.database.dataadapters.ExpenseBookSQLDataAdapter;
import com.mmt.shubh.expensemanager.database.dataadapters.ExpenseSqlDataAdapter;
import com.mmt.shubh.expensemanager.database.dataadapters.MemberExpenseSQLDataAdapter;
import com.mmt.shubh.expensemanager.database.dataadapters.MemberSQLDataAdapter;
import com.mmt.shubh.expensemanager.database.dataadapters.TransactionSQLDataAdapter;
import com.mmt.shubh.expensemanager.database.dataadapters.UserInfoSQLDataAdapter;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.schedulers.Schedulers;
import timber.log.Timber;

@Module
public class DataModule implements IDataModule {

    private static final String PREFERENCE_NAME = "expense_manager";

    private Context mContext;

    private BriteDatabase mBriteDatabase;

    public DataModule(ExpenseApplication app) {
        mContext = app.getApplicationContext();
    }

    @Override
    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences() {
        return mContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    public AccountDataAdapter provideAccountDataAdapter() {
        return new AccountSQLDataAdapter(getBriteDatabase());
    }

    @Singleton
    @Provides
    public ExpenseDataAdapter provideExpenseDataAdapter() {
        return new ExpenseSqlDataAdapter(getBriteDatabase());
    }

    @Provides
    @Singleton
    public TransactionDataAdapter provideTransactionDataAdapter() {
        return new TransactionSQLDataAdapter(getBriteDatabase());
    }

    @Provides
    @Singleton
    public MemberExpenseDataAdapter provideMemberExpenseDataAdapter() {
        return new MemberExpenseSQLDataAdapter(getBriteDatabase());
    }

    @Provides
    @Singleton
    public MemberDataAdapter provideMemberDataAdapter() {
        return new MemberSQLDataAdapter(getBriteDatabase());
    }

    @Provides
    @Singleton
    ExpenseBookDataAdapter provideExpenseBookDataAdapter() {
        return new ExpenseBookSQLDataAdapter(mContext, getBriteDatabase());
    }

    @Provides
    @Singleton
    public UserInfoDataAdapter provideUserInfoDataAdapter() {
        return new UserInfoSQLDataAdapter(getBriteDatabase());
    }

    @Override
    @Provides
    @Singleton
    public BriteDatabase provideBriteDatabase() {
        return getBriteDatabase();
    }

    private BriteDatabase getBriteDatabase() {
        if (mBriteDatabase == null) {
            SqlBrite sqlBrite = SqlBrite.create(message -> {
                Timber.tag("SQLBrite");
                Timber.d(message);
            });

            mBriteDatabase = sqlBrite.wrapDatabaseHelper(new DatabaseHelper(mContext), Schedulers.io());
        }
        return mBriteDatabase;
    }
}
