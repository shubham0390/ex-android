package com.mmt.shubh.expensemanager.account;

import com.mmt.shubh.expensemanager.database.api.ExpenseDataAdapter;
import com.mmt.shubh.expensemanager.database.api.exceptions.AccountDataAdapter;
import com.mmt.shubh.expensemanager.expense.ExpenseListViewModel;
import com.mmt.shubh.expensemanager.mvp.MVPAbstractPresenter;
import com.mmt.shubh.expensemanager.mvp.MVPPresenter;
import com.mmt.shubh.expensemanager.utils.DateUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by subhamtyagi on 2/20/16.
 */
public class AccountDetailPresenter extends MVPAbstractPresenter<IAccountDetailView>
        implements MVPPresenter<IAccountDetailView> {

    private static final int MONTH = 1000 * 60 * 60 * 24 * 30;
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
                .subscribeOn(Schedulers.io()).subscribe(d -> {
                    getView().showExpense(d);
                    createGraphData(d);
                },
                e -> getView().showError(e, false));
    }

    private void createGraphData(List<ExpenseListViewModel> listViewModels) {
        Map<Long, Double> mapData = new HashMap<>();
        for (ExpenseListViewModel expenseListViewModel : listViewModels) {

            long expenseTime = expenseListViewModel.getExpenseDateInMill();

            Set<Long> keys = mapData.keySet();

            Iterator<Long> iterator = keys.iterator();
            Double expenseAmount = expenseListViewModel.getExpenseAmount();

            boolean isAdded = false;

            while (iterator.hasNext()) {

                long key = iterator.next();

                long timeDiff = DateUtil.getTimeDifference(key, expenseTime);

                if (timeDiff < MONTH) {
                    Double amount = mapData.get(key);
                    amount += expenseAmount;
                    mapData.put(key, amount);
                    isAdded = true;
                    break;
                }
            }

            if (!isAdded) {
                mapData.put(expenseTime, expenseAmount);
            }
        }
        getView().setData(mapData);
    }
}
