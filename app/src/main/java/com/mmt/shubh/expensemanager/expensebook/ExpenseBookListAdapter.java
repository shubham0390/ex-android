package com.mmt.shubh.expensemanager.expensebook;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Subham Tyagi,
 * on 10/Sep/2015,
 * 12:22 AM
 * TODO:Add class comment.
 */
public class ExpenseBookListAdapter extends RecyclerView.Adapter<ExpenseBookListAdapter.ExpenseBookViewHolder> {


    List<ExpenseBook> mExpenseBooks;

    public ExpenseBookListAdapter(List<ExpenseBook> expenseBooks) {
        mExpenseBooks = expenseBooks;
    }

    @Override
    public ExpenseBookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_expense_book, parent, false);
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

    static class ExpenseBookViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.expense_book_title)
        TextView TitleTextView;

        @Bind(R.id.expense_book_type)
        TextView mTypeTextView;

        @Bind(R.id.expense_book_description)
        TextView mDescriptionTextView;

        @Bind(R.id.image)
        ImageView mProfileImage;


        public ExpenseBookViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(ExpenseBook expenseBook) {
            TitleTextView.setText(expenseBook.getName());
            mTypeTextView.setText(expenseBook.getType());
            mDescriptionTextView.setText(expenseBook.getDescription());
            ColorGenerator generator = ColorGenerator.MATERIAL;
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(String.valueOf(expenseBook.getName().charAt(0)), generator.getRandomColor());
            mProfileImage.setImageDrawable(drawable);
        }
    }

    public void addData(List<ExpenseBook> expenseBooks) {
        mExpenseBooks = expenseBooks;
        notifyDataSetChanged();
    }
}
