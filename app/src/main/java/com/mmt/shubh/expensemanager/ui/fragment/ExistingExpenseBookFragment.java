package com.mmt.shubh.expensemanager.ui.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.ui.activity.AddExpenseBookActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExistingExpenseBookFragment extends Fragment {

    private RecyclerView mExpenseBookList;

    public ExistingExpenseBookFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_existing_expense_book, container, false);
        mExpenseBookList = (RecyclerView) view.findViewById(R.id.expense_book_list);
        setupRecyclerView();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_expense_book, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_expense) {
            Intent intent = new Intent(getActivity().getApplicationContext(),
                    AddExpenseBookActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView() {
        mExpenseBookList.setLayoutManager(new LinearLayoutManager(mExpenseBookList.getContext()));
    }

}
