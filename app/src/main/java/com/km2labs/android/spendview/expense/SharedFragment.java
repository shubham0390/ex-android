package com.km2labs.android.spendview.expense;

import android.widget.TextView;

import com.km2labs.android.spendview.core.mvp.MVPFragment;
import com.km2labs.android.spendview.expensebook.ExpenseBookListDialog;
import com.km2labs.android.spendview.settings.UserSettings;
import com.km2labs.shubh.expensemanager.R;
import com.km2labs.android.spendview.database.content.ExpenseBook;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class SharedFragment extends MVPFragment<SharedExpenseFragmentPresenter>
        implements SharedExpenseView {

    @BindView(R.id.action_expense_book)
    TextView actionExpenseBook;
    private ExpenseBookListDialog mExpenseBookListDialog;

    private ExpenseBook mExpenseBook;


    @OnClick(R.id.action_expense_book)
    public void onExpenseActionClick() {
        mPresenter.getExpenseBookByMemberId(UserSettings.getInstance().getUserId());
    }

    private void setExpenseBookDetail(ExpenseBook expenseBook) {
        mExpenseBook = expenseBook;
        actionExpenseBook.setText(expenseBook.getName());
    }

    @Override
    public void onExpenseBookLoad(ExpenseBook expenseBook) {
        setExpenseBookDetail(expenseBook);
    }

    private void setupDefault() {
        mPresenter.getExpenseBook(0);
        setExpenseBookDetail(UserSettings.getInstance().getPersonalExpenseBook());
    }

    @Override
    public void onExpenseBookListLoad(List<ExpenseBook> expenseBookList) {
        mExpenseBookListDialog = new ExpenseBookListDialog(getContext());
        mExpenseBookListDialog.setExpenseBookList(expenseBookList);
        mExpenseBookListDialog.setItemSelectListener(this::setExpenseBookDetail);
        mExpenseBookListDialog.show();
    }


}
