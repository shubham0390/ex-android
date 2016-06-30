package com.mmt.shubh.expensemanager.expense;

import android.widget.TextView;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.core.mvp.MVPFragment;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.expensebook.ExpenseBookListDialog;
import com.mmt.shubh.expensemanager.settings.UserSettings;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;


public class SharedFragment extends MVPFragment<SharedExpenseFragmentPresenter>
        implements SharedExpenseView {

    @Bind(R.id.action_expense_book)
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
