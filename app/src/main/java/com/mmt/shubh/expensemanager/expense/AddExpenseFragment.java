package com.mmt.shubh.expensemanager.expense;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.mvp.MVPFragment;
import com.mmt.shubh.expensemanager.mvp.SupportMVPFragment;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddExpenseFragment extends SupportMVPFragment<AddExpenseView, AddExpensePresenter> {

    @Bind(R.id.name_text_view)
    EditText mTitleEditText;

    @Bind(R.id.amount_edit_text)
    AutoResizeEditText amountEditText;

    /*@Bind(R.id.keyboard)
    KeyboardView keyboard;*/

    @Bind(R.id.amount_edit_text_continer)
    LinearLayout container;

    /*@Bind(R.id.account)
    ChooserView mAccountChooserView;

    @Bind(R.id.category)
    ChooserView mCategoryChooserView;*/

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_add_expense;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setup();

    }

    private void setup() {
        amountEditText.setEnabled(true);
        amountEditText.setFocusableInTouchMode(true);
        amountEditText.setFocusable(true);
        amountEditText.setEnableSizeCache(false);
        amountEditText.setCursorVisible(false);
        amountEditText.setMovementMethod(null);
        amountEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);
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

    @Override
    protected void injectDependencies(MainComponent mainComponent) {
        DaggerAddExpenseActivityComponent.builder()
                .mainComponent(mainComponent)
                .addExpenseFragmentModule(new AddExpenseFragmentModule())
                .build()
                .inject(this);
    }
}
