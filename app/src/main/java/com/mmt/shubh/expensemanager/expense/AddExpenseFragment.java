package com.mmt.shubh.expensemanager.expense;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mmt.shubh.expensemanager.Constants;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.database.content.Account;
import com.mmt.shubh.expensemanager.database.content.Expense;
import com.mmt.shubh.expensemanager.mvp.SupportMVPFragment;
import com.mmt.shubh.expensemanager.ui.view.AutoResizeEditText;
import com.mmt.shubh.expensemanager.ui.view.BottomSheet;
import com.mmt.shubh.expensemanager.ui.view.BottomSheetAdapter;
import com.mmt.shubh.expensemanager.utils.DateUtil;

import org.parceler.Parcels;
import org.threeten.bp.LocalDateTime;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddExpenseFragment extends SupportMVPFragment<AddExpenseView, AddExpensePresenter> implements AddExpenseView {


    private static final int REQUEST_IMAGE_CAPTURE = 1;


    @Bind(R.id.expense_title)
    EditText mTitleEditText;

    @Bind(R.id.amount_edit_text)
    AutoResizeEditText mAmountEditText;

    /*@Bind(R.id.keyboard)
    KeyboardView keyboard;*/

    @Bind(R.id.amount_edit_text_continer)
    LinearLayout mContainer;

    @Bind(R.id.action_date)
    Button mActionDate;

    @Bind(R.id.action_receipt)
    Button mActionReceipt;

    @Bind(R.id.action_comment)
    Button mActionComment;

    @Bind(R.id.bill_bottom_sheet)
    BottomSheet mBottomSheet;

    private Expense mExpense;


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_add_expense;
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
        LocalDateTime dateTime = LocalDateTime.now();
        new DatePickerDialog(getContext(), (view, year, monthOfYear, dayOfMonth) -> {
            LocalDateTime localDateTime = LocalDateTime.of(year, monthOfYear, dayOfMonth, dateTime.getHour(), dateTime.getSecond());
            setDate(DateUtil.toMilliSeconds(localDateTime));
        }, dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth()).show();
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
    protected void injectDependencies(MainComponent mainComponent) {
        DaggerAddExpenseActivityComponent.builder()
                .mainComponent(mainComponent)
                .addExpenseFragmentModule(new AddExpenseFragmentModule())
                .build()
                .inject(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
        }
    }
}
