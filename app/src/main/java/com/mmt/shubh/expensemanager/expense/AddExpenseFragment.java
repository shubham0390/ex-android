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
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mmt.shubh.expensemanager.Constants;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.database.content.Account;
import com.mmt.shubh.expensemanager.database.content.Expense;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.expensebook.ExpenseBookListDialog;
import com.mmt.shubh.expensemanager.mvp.SupportMVPFragment;
import com.mmt.shubh.expensemanager.settings.UserSettings;
import com.mmt.shubh.expensemanager.ui.view.AutoResizeEditText;
import com.mmt.shubh.expensemanager.utils.DateUtil;

import org.parceler.Parcels;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.temporal.ChronoField;

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
    AutoResizeEditText amountEditText;

    /*@Bind(R.id.keyboard)
    KeyboardView keyboard;*/

    @Bind(R.id.amount_edit_text_continer)
    LinearLayout container;

    @Bind(R.id.action_date)
    TextView actionDate;

    @Bind(R.id.action_expense_book)
    TextView actionExpenseBook;

    @Bind(R.id.action_documents)
    TextView actionImage;

    @Bind(R.id.action_comment)
    TextView actionComment;

    @Bind(R.id.bottom_sheet)
    NestedScrollView mBottomSheet;

    @Bind(R.id.friend_bottom_sheet)
    NestedScrollView mFriendsBottomSheet;

    private BottomSheetBehavior mBottomSheetBehavior;
    private BottomSheetBehavior mFriendBottomSheetBehavior;

    private ExpenseBookListDialog mExpenseBookListDialog;


    private Expense mExpense;
    private ExpenseBook mExpenseBook;


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
        mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);
        mFriendBottomSheetBehavior = BottomSheetBehavior.from(mFriendsBottomSheet);
    }

    private void setupDefault() {
        setExpenseBookDetail(UserSettings.getInstance().getPersonalExpenseBook());
        setDate(DateUtil.getCurrentTimeInMilli());
    }

    private void setupExpense() {
        mTitleEditText.setText(mExpense.getExpenseName());
        amountEditText.setText(mExpense.getExpenseAmount() + "");
        setDate(mExpense.getExpenseDate());
        mPresenter.getExpenseBook(mExpense.getExpenseBookId());
    }

    private void setDate(long date) {
        actionDate.setText(DateUtil.getLocalizedDate(date));
    }

    private void setupViews() {
        amountEditText.setEnabled(true);
        amountEditText.setFocusableInTouchMode(true);
        amountEditText.setFocusable(true);
        amountEditText.setEnableSizeCache(false);
        amountEditText.setCursorVisible(false);
        amountEditText.setMovementMethod(null);
        amountEditText.setTextIsSelectable(true);
       /* keyboard.setupEditText(amountEditText);
        keyboard.setVisibility(View.GONE);
        amountEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                keyboard.setVisibility(View.VISIBLE);
            } else {
                keyboard.setVisibility(View.GONE);
            }
        });*/

    }


    public void onAccountClick() {

    }

    @OnClick(R.id.category_image)
    public void onCategoryClick() {

    }

    @OnClick(R.id.action_date)
    public void onDateActionClick() {
        LocalDateTime dateTime = LocalDateTime.now();
        new DatePickerDialog(getContext(), (view, year, monthOfYear, dayOfMonth) -> {
        LocalDateTime localDateTime = LocalDateTime.of(year,monthOfYear,dayOfMonth,dateTime.getHour(),dateTime.getSecond());
            setDate(DateUtil.toMilliSeconds(localDateTime));
        },dateTime.getYear(),dateTime.getMonthValue(),dateTime.getDayOfMonth()).show();
    }

    @OnClick(R.id.action_expense_book)
    public void onExpenseActionClick() {
        mPresenter.getExpenseBookByMemberId(UserSettings.getInstance().getUserId());
    }

    @OnClick(R.id.action_camera)
    public void onCameraActionClick() {
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } else {
            Toast.makeText(getContext(), R.string.no_camera_error_message, Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.action_document)
    public void onDucmentsClick() {

        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        Toast.makeText(getContext(), "No implemented yet ", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.action_documents)
    public void onBillCkick() {
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @OnClick(R.id.action_comment)
    public void onCommentActionClick() {

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

    @Override
    public void onEmptyTitleError() {

    }

    @Override
    public void onEmptyAccountError() {

    }

    @Override
    public void onEmptyAmountError() {

    }

    @Override
    public void onExpenseBookListLoad(List<ExpenseBook> expenseBookList) {
        mExpenseBookListDialog = new ExpenseBookListDialog(getContext());
        mExpenseBookListDialog.setExpenseBookList(expenseBookList);
        mExpenseBookListDialog.setItemSelectListener(this::setExpenseBookDetail);
        mExpenseBookListDialog.show();
    }

    private void setExpenseBookDetail(ExpenseBook expenseBook) {
        mExpenseBook = expenseBook;
        actionExpenseBook.setText(expenseBook.getName());
    }

    @Override
    public void onAccountListLoad(List<Account> data) {

    }

    @Override
    public void onExpenseBookLoad(ExpenseBook expenseBook) {
        setExpenseBookDetail(expenseBook);
    }
}
