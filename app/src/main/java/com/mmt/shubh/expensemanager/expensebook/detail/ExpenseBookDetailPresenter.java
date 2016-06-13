package com.mmt.shubh.expensemanager.expensebook.detail;


import com.mmt.shubh.expensemanager.database.exception.EmptyDataException;
import com.mmt.shubh.expensemanager.expense.ExpenseFilter;
import com.mmt.shubh.expensemanager.expense.ExpenseListViewModel;
import com.mmt.shubh.expensemanager.expense.ExpenseModel;
import com.mmt.shubh.expensemanager.mvp.MVPAbstractPresenter;
import com.mmt.shubh.expensemanager.mvp.MVPPresenter;
import com.mmt.shubh.expensemanager.utils.DateUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;


public class ExpenseBookDetailPresenter extends MVPAbstractPresenter<ExpenseBookDetailView> implements
        MVPPresenter<ExpenseBookDetailView> {


    @Inject
    ExpenseModel mExpenseModel;

    @Inject
    public ExpenseBookDetailPresenter(ExpenseModel expenseModel) {
        mExpenseModel = expenseModel;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    public void loadExpenseByExpenseBookId(long expenseBoolId) {
        ExpenseFilter expenseFilter = new ExpenseFilter();
        expenseFilter.setExpenseBookId(expenseBoolId);
        expenseFilter.setTimeFilter(ExpenseFilter.TIME_FILTER_YEAR);
        mExpenseModel.loadExpenseWithFilter(expenseFilter)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mExpenseObserver);
    }

    private Observer<List<ExpenseListViewModel>> mExpenseObserver = new Observer<List<ExpenseListViewModel>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Timber.e(e.getMessage());
            e.printStackTrace();
            getView().showError(e, false);
        }

        @Override
        public void onNext(List<ExpenseListViewModel> expenses) {
            if (expenses == null || expenses.isEmpty()) {
                getView().showError(new EmptyDataException("No Expense Present"), false);
            } else
            createGraphData(expenses);
                getView().setData(expenses);
        }
    };
    private void createGraphData(List<ExpenseListViewModel> listViewModels) {
        Map<Integer, Double> mapData = new HashMap<>();
        for (ExpenseListViewModel expenseListViewModel : listViewModels) {

            long expenseTime = expenseListViewModel.getExpenseDateInMill();

            Double expenseAmount = expenseListViewModel.getExpenseAmount();
            Integer mnth = DateUtil.getMonthCount(expenseTime);

            if (mapData.containsKey(mnth)) {
                Double amount = mapData.get(mnth);
                amount += expenseAmount;
                mapData.put(mnth, amount);
            } else {
                mapData.put(mnth, expenseAmount);
            }
        }
        getView().setGraphData(mapData);
    }

}
