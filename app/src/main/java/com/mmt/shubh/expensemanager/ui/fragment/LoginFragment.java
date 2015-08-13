package com.mmt.shubh.expensemanager.ui.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.database.content.UserInfo;
import com.mmt.shubh.expensemanager.database.dataadapters.UserInfoSQLDataAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginFragment extends Fragment {

    @Bind(R.id.full_name_edit_text)
    EditText mFullNameEditText;

    @Bind(R.id.email_edit_text)
    EditText mEmailAddressEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        mEmailAddressEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (v.getId() == R.id.email_edit_text) {
                    onSubmit();
                    return true;
                }
                return false;
            }
        });
        return view;
    }


    @OnClick(R.id.button_submit)
    public void onSubmit() {
        if (!isViewsEmpty()) {
            UserInfoSQLDataAdapter sqlDataAdapter = new UserInfoSQLDataAdapter(getActivity().getApplicationContext());
            UserInfo.Builder builder = new UserInfo.Builder();
            builder.setDisplayName(mFullNameEditText.getText().toString());
            builder.setEmailAddress(mEmailAddressEditText.getText().toString());
            builder.setProfilePhotoUrl("");
            builder.setCoverPhotoUrl("");
            builder.setUserName("");
            builder.setStatus(UserInfo.Status.ACTIVE);
            sqlDataAdapter.create(builder.build());
        }
    }

    private boolean isViewsEmpty() {
        boolean isEmpty = false;
        if (TextUtils.isEmpty(mEmailAddressEditText.getText().toString()))
            isEmpty = true;
        if (TextUtils.isEmpty(mFullNameEditText.getText().toString()))
            isEmpty = true;

        return isEmpty;
    }


}
