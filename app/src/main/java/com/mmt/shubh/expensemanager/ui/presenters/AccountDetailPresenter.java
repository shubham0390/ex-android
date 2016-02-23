package com.mmt.shubh.expensemanager.ui.presenters;

import com.mmt.shubh.expensemanager.database.api.ExpenseDataAdapter;
import com.mmt.shubh.expensemanager.database.api.exceptions.AccountDataAdapter;
import com.mmt.shubh.expensemanager.ui.mvp.MVPAbstractPresenter;
import com.mmt.shubh.expensemanager.ui.mvp.MVPPresenter;
import com.mmt.shubh.expensemanager.ui.viewmodel.ExpenseListViewModel;
import com.mmt.shubh.expensemanager.ui.views.IAccountDetailView;

import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by subhamtyagi on 2/20/16.
 */
public class AccountDetailPresenter extends MVPAbstractPresenter<IAccountDetailView>
        implements MVPPresenter<IAccountDetailView> {

    private AccountDataAdapter mAccountDataAdapter;

    private ExpenseDataAdapter mExpenseDataAdapter;

    @Inject
    public AccountDetailPresenter(AccountDataAdapter accountDataAdapter, ExpenseDataAdapter dataAdapter) {
        mAccountDataAdapter = accountDataAdapter;
        mExpenseDataAdapter = dataAdapter;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    public void loadExpenseByAccountId(long id) {
        mExpenseDataAdapter.getExpenseByAccountId(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Observer<List<ExpenseListViewModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<ExpenseListViewModel> listViewModels) {
                getView().showExpense(listViewModels);
                for (ExpenseListViewModel expenseListViewModel : listViewModels)
                    expenseListViewModel.getExpenseDateInMill();
            }
        });
    }
}
