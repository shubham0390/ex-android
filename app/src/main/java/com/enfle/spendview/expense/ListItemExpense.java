/*
 * Copyright (c) 2016. . The Km2Labs Project
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.enfle.spendview.expense;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enfle.spendview.core.view.CircleImageView;
import com.enfle.spendview.R;


public class ListItemExpense extends LinearLayout {

    CircleImageView mProfileImageView;
    TextView mCategoryNameTextView;
    TextView mExpenseTitleTextView;
    TextView mExpenseDateTextView;
    TextView mMemberNameTextView;
    TextView mExpenseAmountTextView;

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
        LayoutInflater.from(context).inflate(R.layout.list_item_expense, this, true);
        mProfileImageView = (CircleImageView) findViewById(R.id.list_image_icon);
        mCategoryNameTextView = (TextView) findViewById(R.id.category_name);
        mExpenseTitleTextView = (TextView) findViewById(R.id.expense_title);
        mExpenseDateTextView = (TextView) findViewById(R.id.expense_date);
        mMemberNameTextView = (TextView) findViewById(R.id.member_name);
        mExpenseAmountTextView = (TextView) findViewById(R.id.expense_amount);

        if (isInEditMode()) {
            return;
        }
    }

    public void setExpense(ExpenseListViewModel expense) {
        mExpenseTitleTextView.setText(expense.getExpenseTitle());
        mExpenseDateTextView.setText(expense.getExpenseDate());
        mExpenseAmountTextView.setText(expense.getFormatedExpenseAmount());

        mProfileImageView.setImageResource(expense.getCategoryImage());
        mCategoryNameTextView.setText(expense.getCategoryName());

        setMemberDetails(expense);
    }

    /*private void setExpenseBookDetails(ExpenseListViewModel expense) {
        mExpenseBookNameTextView.setText(expense.getExpenseBookName());
    }*/

    private void setMemberDetails(ExpenseListViewModel expense) {
        SpannableString spannableString = new SpannableString("Paid by " + expense.getMemberName() + " and 2 others in " + expense.getExpenseBookName());
        int startIndex = 8;
        int lastIndex = startIndex + expense.getMemberName().length();
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), startIndex, lastIndex, 0);
        startIndex = lastIndex + 4;
        lastIndex = startIndex + 2;
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), startIndex, lastIndex, 0);
        startIndex = lastIndex + 11;
        lastIndex = startIndex + expense.getExpenseBookName().length();
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), startIndex, lastIndex, 0);
        mMemberNameTextView.setText(spannableString);
    }

   /* private void setAccountDetails(ExpenseListViewModel expense) {
        mAccountNameTextView.setText(expense.getAccountName());
        //mAccountTypeImageView.setImageResource(expense.getAccountType());
    }*/
}
