package com.mmt.shubh.expensemanager.ui.presenters;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;

import com.mmt.shubh.expensemanager.dagger.scope.ActivityScope;
import com.mmt.shubh.expensemanager.database.api.exceptions.AccountDataAdapter;
import com.mmt.shubh.expensemanager.database.content.Account;
import com.mmt.shubh.expensemanager.database.content.CardDetails;
import com.mmt.shubh.expensemanager.database.exception.EmptyDataException;
import com.mmt.shubh.expensemanager.ui.mvp.MVPAbstractPresenter;
import com.mmt.shubh.expensemanager.ui.mvp.MVPPresenter;
import com.mmt.shubh.expensemanager.ui.mvp.lce.MVPLCEView;
import com.mmt.shubh.expensemanager.ui.viewmodel.AccountListViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Subham Tyagi,
 * on 04/Nov/2015,
 * 4:55 PM
 * TODO:Add class comment.
 */
@ActivityScope
public class AccountListPresenter extends MVPAbstractPresenter<MVPLCEView<List<AccountListViewModel>>>
        implements MVPPresenter<MVPLCEView<List<AccountListViewModel>>>, LoaderManager.LoaderCallbacks<List<AccountListViewModel>> {

    @Inject
    Context mContext;

    @Inject
    AccountDataAdapter mAccountDataAdapter;

    @Inject
    public AccountListPresenter() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }


    @Override
    public Loader<List<AccountListViewModel>> onCreateLoader(int id, Bundle args) {
        return new AccountListLoader(mContext, mAccountDataAdapter);
    }

    @Override
    public void onLoadFinished(Loader<List<AccountListViewModel>> loader, List<AccountListViewModel> data) {
        if (data == null || data.isEmpty()) {
            getView().showError(new EmptyDataException("No Data present"), false);
        } else {
            getView().setData(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<AccountListViewModel>> loader) {

    }

    private static class AccountListLoader extends AsyncTaskLoader<List<AccountListViewModel>> {

        private final AccountDataAdapter mAccountDataAdapter;

        public AccountListLoader(Context context, AccountDataAdapter accountDataAdapter) {
            super(context);
            mAccountDataAdapter = accountDataAdapter;
        }

        @Override
        public List<AccountListViewModel> loadInBackground() {

            List<AccountListViewModel> viewModels = new ArrayList<>();
            List<Account> accounts = mAccountDataAdapter.getAll();
            if (accounts != null) {
                for (Account account : accounts) {
                    AccountListViewModel model = new AccountListViewModel();
                    model.setAccountBalance(account.getAccountBalance());
                    model.setAccountName(account.getAccountName());
                    model.setAccountType(account.getType());
                    model.setAccountNumber(account.getAccountNumber());
                    // TODO: 9/19/2015 remove below code after testing
                    CardDetails card = new CardDetails();
                    card.setAccountKey("12345678");
                    card.setCardNo("xxxxxxxxxxx-4567");
                    card.setBalanceAmount(1234567L);
                    //card.se("Debit Card:-");
                    ArrayList<CardDetails> cards = new ArrayList<>();
                    cards.add(card);
                    model.setCardList(cards);
                    viewModels.add(model);
                }
            }
            return viewModels;
        }
    }

}
