package com.mmt.shubh.expensemanager.expensebook;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;

import com.mmt.shubh.expensemanager.ItemSelectListener;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;

import java.util.List;


public class ExpenseBookListDialog extends AppCompatDialog {

    private ExpenseBookListView mExpenseBookListView;

    private ExpenseBook mSelectedExpenseBook;

    private ItemSelectListener<ExpenseBook> mItemSelectListener;

    public ExpenseBookListDialog(Context context) {
        super(context);
        init(context);
    }

    public ExpenseBookListDialog(Context context, int theme) {
        super(context, theme);
        init(context);
    }

    protected ExpenseBookListDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context) {
        setContentView(R.layout.dialog_expense_book_list);
        setTitle(R.string.select_expense_book);
        mExpenseBookListView = (ExpenseBookListView) findViewById(R.id.expense_book_list);
    }


    public void setExpenseBookList(List<ExpenseBook> expenseBookList) {
        if (expenseBookList.size() <= 0) {
            mExpenseBookListView.showEmptyText(R.string.empty_expense_book);
        } else {
            mExpenseBookListView.setOnItemClickListener((parent, view, position, id) -> {
                mSelectedExpenseBook = mExpenseBookListView.getItemAtPosition(position);
                mItemSelectListener.onItemSelect(mSelectedExpenseBook);
                dismiss();
                return true;
            });
            mExpenseBookListView.addData(expenseBookList);
        }
    }

    public void setItemSelectListener(ItemSelectListener<ExpenseBook> mItemSelectListener) {
        this.mItemSelectListener = mItemSelectListener;
    }

    public ExpenseBook getSelectedItem() {
        return mSelectedExpenseBook;
    }
}
