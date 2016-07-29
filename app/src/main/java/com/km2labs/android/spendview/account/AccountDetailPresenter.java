package com.km2labs.android.spendview.account;

import com.km2labs.android.spendview.core.mvp.MVPPresenter;
import com.km2labs.android.spendview.database.content.contract.ExpenseContract;
import com.km2labs.android.spendview.expense.ExpenseListViewModel;
import com.km2labs.android.spendview.utils.DateUtil;
import com.km2labs.android.spendview.core.Selection;
import com.km2labs.android.spendview.core.mvp.BasePresenter;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AccountDetailPresenter extends BasePresenter<IAccountDetailView> implements MVPPresenter<IAccountDetailView> {


    AccountModel mAccountModel;

    @Inject
    public AccountDetailPresenter(AccountModel accountModel) {
        mAccountModel = accountModel;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    public void loadExpenseByAccountId(long id) {
        LocalDate currentDate = LocalDate.now();
        LocalDateTime localDate = LocalDateTime.of(currentDate.getYear(), 1, 1, 0, 0);
        long currentYearTimeInMilli = DateUtil.toMilliSeconds(localDate);
        Selection.Builder builder = new Selection.Builder();
        builder.appendSelection(ExpenseContract.ACCOUNT_KEY, Selection.EQUAL, id)
                .appendOperation(Selection.AND)
                .appendSelection(ExpenseContract.EXPENSE_DATE, Selection.GREATER, currentYearTimeInMilli);

        mAccountModel.getExpenses(builder.build())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(d -> {
                    getView().showExpense(d);
                    createGraphData(d);
                },
                e -> getView().showError(e, false));
    }

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
        getView().setData(mapData);
    }
}
