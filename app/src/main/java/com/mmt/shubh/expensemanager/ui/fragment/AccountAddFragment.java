package com.mmt.shubh.expensemanager.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.mmt.shubh.expensemanager.R;

/**
 * Created by Subham Tyagi,
 * on 12/Jul/2015,
 * 10:39 PM
 * TODO:Add class comment.
 */
public class AccountAddFragment extends Fragment {

    private EditText mAccountName;
    private EditText mAccountNumber;
    private EditText mAccountBalance;
    private TextView mBank;
    private Spinner mAccountType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_account, container, false);

        mAccountName = (EditText) view.findViewById(R.id.account_name);
        mAccountNumber = (EditText) view.findViewById(R.id.account_number);
        mAccountBalance = (EditText) view.findViewById(R.id.account_balance);
        mBank = (TextView) view.findViewById(R.id.bank);
        mAccountType = (Spinner) view.findViewById(R.id.account_type);

        return view;
    }
}
