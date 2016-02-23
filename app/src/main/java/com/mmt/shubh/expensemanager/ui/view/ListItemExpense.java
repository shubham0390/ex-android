package com.mmt.shubh.expensemanager.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.ui.viewmodel.ExpenseListViewModel;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by subhamtyagi on 2/20/16.
 */
public class ListItemExpense extends LinearLayout {

    CircleImageView mProfileImageView;
    ImageView mAccountTypeImageView;
    TextView mCategoryNameTextView;
    TextView mExpenseTitleTextView;
    TextView mAccountNameTextView;
    TextView mExpenseDateTextView;
    TextView mMemberNameTextView;
    TextView mExpenseAmountTextView;
    TextView mExpenseBookNameTextView;

    public ListItemExpense(Context context) {
        super(context);
        init(context);
    }

    public ListItemExpense(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ListItemExpense(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ListItemExpense(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_expense, this, true);
        mProfileImageView = (CircleImageView) findViewById(R.id.list_image_icon);
        mCategoryNameTextView = (TextView) findViewById(R.id.category_name);
        mExpenseTitleTextView = (TextView) findViewById(R.id.expense_title);
        mExpenseDateTextView = (TextView) findViewById(R.id.expense_date);
        mMemberNameTextView = (TextView) findViewById(R.id.member_name);
        mExpenseAmountTextView = (TextView) findViewById(R.id.expense_amount);
        mExpenseBookNameTextView = (TextView) findViewById(R.id.expense_book);
        mAccountNameTextView = (TextView) findViewById(R.id.account_name);

        if (isInEditMode()) {
            return;
        }
        //ButterKnife.bind(view);
    }

    public void setExpense(ExpenseListViewModel expense) {
        mProfileImageView.setImageResource(expense.getCategoryImage());
        mCategoryNameTextView.setText(expense.getCategoryName());
        mExpenseTitleTextView.setText(expense.getExpenseTitle());
        mExpenseDateTextView.setText(expense.getExpenseDate());
        mMemberNameTextView.setText(expense.getMemberName());
        mExpenseAmountTextView.setText(expense.getFormatedExpenseAmount());
        mExpenseBookNameTextView.setText(expense.getCategoryName());
        mAccountNameTextView.setText(expense.getAccountName());
        //mAccountTypeImageView.setImageResource(expense.getAccountType());
    }
}
