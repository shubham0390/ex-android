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

package com.enfle.spendview.account;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.enfle.spendview.database.content.Account;
import com.enfle.spendview.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Subham Tyagi,
 * on 12/Jul/2015,
 * 10:39 PM
 * TODO:Add class comment.
 */

public class AddEditAccountFragment extends Fragment {

    @BindView(R.id.account_name)
    EditText mAccountName;

    @BindView(R.id.account_number)
    EditText mAccountNumber;

    @BindView(R.id.account_balance)
    EditText mAccountBalance;

    @BindView(R.id.bank)
    TextView mBank;

    @BindView(R.id.account_type)
    Spinner mAccountType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_account, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.button_submit)
    public void onSubmitClick() {
        if (!isEmpty()) {
            Account account = constructAccount();
        }
    }

    @OnClick(R.id.bank_container)
    public void onSelectBankClick() {
        /*Dialoge dialoge =  new Dialoge();
        dialoge.onCreateDialog(getActivity());*/
    }

    private Account constructAccount() {
        Account account = new Account();
        account.setAccountName(mAccountName.getText().toString());
        account.setAccountBalance(Integer.parseInt(mAccountBalance.getText().toString()));
        account.setAccountNumber(mAccountNumber.getText().toString());
        account.setBankName(mBank.getText().toString());
        account.setType(mAccountType.getSelectedItem().toString());
        return account;
    }

    private boolean isEmpty() {
        boolean isEmpty = false;
        if (TextUtils.isEmpty(mAccountName.getText().toString())) {
            isEmpty = true;
            mAccountName.setError(getString(R.string.account_name_empty_error));
        }
        if (TextUtils.isEmpty(mAccountNumber.getText().toString())) {
            isEmpty = true;
            mAccountNumber.setError(getString(R.string.account_number_empty_error));
        }
        if (TextUtils.isEmpty(mAccountBalance.getText().toString())) {
            isEmpty = true;
            mAccountBalance.setError(getString(R.string.account_balance_empty_error));
        }
        if (TextUtils.isEmpty(mBank.getText().toString())) {
            isEmpty = true;
            mBank.setError(getString(R.string.bank_empty_error));
        }


        return isEmpty;
    }
}
