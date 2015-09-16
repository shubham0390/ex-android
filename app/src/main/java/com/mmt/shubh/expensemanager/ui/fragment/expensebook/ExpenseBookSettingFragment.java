package com.mmt.shubh.expensemanager.ui.fragment.expensebook;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mmt.shubh.expensemanager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseBookSettingFragment extends Fragment {

    public ExpenseBookSettingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_expense_book_setting, container, false);
    }


}
