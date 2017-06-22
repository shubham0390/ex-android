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


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.enfle.spendview.core.mvp.MVPFragment;
import com.enfle.spendview.core.view.AutoResizeEditText;
import com.enfle.spendview.core.view.BottomSheet;
import com.enfle.spendview.core.view.BottomSheetAdapter;
import com.enfle.spendview.database.content.Account;
import com.enfle.spendview.database.content.Expense;
import com.enfle.spendview.utils.Constants;
import com.enfle.spendview.utils.DateUtil;
import com.enfle.spendview.R;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddExpenseFragment extends MVPFragment<AddExpensePresenter> implements AddExpenseView {

    private static final int REQUEST_IMAGE_CAPTURE = 1;


    @BindView(R.id.expense_title)
    EditText mTitleEditText;

    @BindView(R.id.amount_edit_text)
    AutoResizeEditText mAmountEditText;

    /*@BindView(R.id.keyboard)
    KeyboardView keyboard;*/

    @BindView(R.id.amount_edit_text_continer)
    LinearLayout mContainer;

    @BindView(R.id.action_date)
    Button mActionDate;

    @BindView(R.id.action_receipt)
    Button mActionReceipt;

    @BindView(R.id.action_comment)
    Button mActionComment;

    @BindView(R.id.bill_bottom_sheet)
    BottomSheet mBottomSheet;

    private Expense mExpense;

    @Override
    protected View getFragmentView(@NonNull LayoutInflater inflater, ViewGroup container) {
        return null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
        Bundle bundle = getArguments();
        if (bundle != null) {
            Parcelable parcelable = bundle.getParcelable(Constants.EXTRA_EXPENSE);
            if (parcelable != null)
                mExpense = Parcels.unwrap(parcelable);
            setupExpense();
        } else {
            setupDefault();
        }
    }

    private void setupDefault() {
        setDate(DateUtil.getCurrentTimeInMilli());
    }

    private void setupExpense() {
        mTitleEditText.setText(mExpense.getExpenseName());
        mAmountEditText.setText(mExpense.getExpenseAmount() + "");
        setDate(mExpense.getExpenseDate());
    }

    private void setDate(long date) {
        mActionDate.setText(DateUtil.getLocalizedDate(date));
    }

    private void setupViews() {
        mAmountEditText.setEnabled(true);
        mAmountEditText.setFocusableInTouchMode(true);
        mAmountEditText.setFocusable(true);
        mAmountEditText.setEnableSizeCache(false);
        mAmountEditText.setCursorVisible(false);
        mAmountEditText.setMovementMethod(null);
        mAmountEditText.setTextIsSelectable(true);
        mBottomSheet.setAdapter(new BottomSheetAdapter<>(getResources().getStringArray(R.array.bill_bottom_sheet_menu)));
        mBottomSheet.setLayoutBehaviour(BottomSheetBehavior.from(mBottomSheet));
        mBottomSheet.setItemsSelectListener(position -> {
            switch (position) {
                case 0:
                    onCameraActionClick();
                    break;
                case 1:
                    onDocumentsClick();

            }
        });
       /* keyboard.setupEditText(mAmountEditText);
        keyboard.setVisibility(View.GONE);
        mAmountEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                keyboard.setVisibility(View.VISIBLE);
            } else {
                keyboard.setVisibility(View.GONE);
            }
        });*/

    }

    @OnClick(R.id.category_image)
    public void onCategoryClick() {

    }

    @OnClick(R.id.action_date)
    public void onDateActionClick() {
    }

    public void onCameraActionClick() {
        mBottomSheet.hide();
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } else {
            Toast.makeText(getContext(), R.string.error_message_no_camera, Toast.LENGTH_SHORT).show();
        }
    }

    public void onDocumentsClick() {
        Toast.makeText(getContext(), "No implemented yet ", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.action_receipt)
    public void onActionReceiptClick() {
        mBottomSheet.show();
    }

    @OnClick(R.id.action_comment)
    public void onActionCommentClick() {

    }

    @Override
    public void onEmptyTitleError() {
        mTitleEditText.setError(getString(R.string.error_empty_expense_name));
    }

    @Override
    public void onEmptyAccountError() {

    }

    @Override
    public void onEmptyAmountError() {
        mAmountEditText.setError(getString(R.string.error_empty_expense_amount));
    }

    @Override
    public void onAccountListLoad(List<Account> data) {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
        }
    }
}