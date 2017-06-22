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

package com.enfle.spendview.expensebook;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.enfle.spendview.core.view.CircleImageView;
import com.enfle.spendview.database.content.ExpenseBook;
import com.enfle.spendview.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Subham Tyagi,
 * on 10/Sep/2015,
 * 12:22 AM
 * TODO:Add class comment.
 */
public class ExpenseBookGridAdapter extends RecyclerView.Adapter<ExpenseBookGridAdapter.ExpenseBookViewHolder> {


    List<ExpenseBook> mExpenseBooks;
    private int mMode;

    public ExpenseBookGridAdapter() {
        mExpenseBooks = new ArrayList<>();
    }

    @Override
    public ExpenseBookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.grid_item_expense_book, parent, false);
        return new ExpenseBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExpenseBookViewHolder holder, int position) {
        holder.bindView(mExpenseBooks.get(position));
    }

    @Override
    public int getItemCount() {
        return mExpenseBooks.size();
    }

    @Override
    public long getItemId(int position) {
        return mExpenseBooks.get(position).getId();
    }

    public ExpenseBook getItem(int position) {
        return mExpenseBooks.get(position);
    }

    public void addData(List<ExpenseBook> expenseBooks) {
        mExpenseBooks.clear();
        mExpenseBooks.addAll(expenseBooks);
        notifyDataSetChanged();
    }

    public void setMode(int mode) {
        mMode = mode;
    }

    public void clear() {
        mExpenseBooks.clear();
    }

    class ExpenseBookViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.expense_book_title)
        TextView TitleTextView;

        @BindView(R.id.image)
        CircleImageView mProfileImage;


        public ExpenseBookViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(ExpenseBook expenseBook) {
            TitleTextView.setText(expenseBook.getName());
            ColorGenerator generator = ColorGenerator.MATERIAL;
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(String.valueOf(expenseBook.getName().charAt(0)), generator.getRandomColor());
//            mProfileImage.setImageDrawable(drawable);
            if (!TextUtils.isEmpty(expenseBook.getProfileImagePath())) {
                mProfileImage.loadImage(expenseBook.getProfileImagePath());
            }

            TitleTextView.setTypeface(null, Typeface.NORMAL);
        }

    }

}
