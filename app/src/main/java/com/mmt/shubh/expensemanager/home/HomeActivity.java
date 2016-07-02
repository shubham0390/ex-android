package com.mmt.shubh.expensemanager.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.core.base.DrawerBaseActivity;
import com.mmt.shubh.expensemanager.core.dagger.component.ConfigPersistentComponent;
import com.mmt.shubh.expensemanager.expense.AddExpenseActivity;
import com.mmt.shubh.expensemanager.expensebook.add.ExpenseBookAddUpdateActivity;

import butterknife.BindView;

public class HomeActivity extends DrawerBaseActivity<HomePresenter> {

    @BindView(R.id.fab)
    FloatingActionButton mFab;

    @BindView(R.id.add_expense_fab)
    FloatingActionButton mAddExpenseFab;

    @BindView(R.id.add_expense_book_feb)
    FloatingActionButton mAddExpenseBookFab;

    private Animation fab_open, fab_close, rotate_forward, rotate_backward;

    private Boolean isFabOpen = false;
    private View.OnClickListener mOnClickListener = v -> {
        int id = v.getId();
        switch (id) {
            case R.id.fab:
                animateFAB();
                break;
            case R.id.add_expense_fab:
                Intent intent = new Intent(HomeActivity.this, AddExpenseActivity.class);
                startActivity(intent);
                break;
            case R.id.add_expense_book_feb:
                Intent intent1 = new Intent(HomeActivity.this, ExpenseBookAddUpdateActivity.class);
                startActivity(intent1);
                break;
        }
    };

    @Override
    protected void injectDependencies(ConfigPersistentComponent component) {
        /*component.activityComponent(new ActivityModule(this))
                .inject(this);*/
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeNavigationDrawer();
        setTitle(R.string.home_title);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        mFab.setOnClickListener(mOnClickListener);
        mAddExpenseFab.setOnClickListener(mOnClickListener);
        mAddExpenseBookFab.setOnClickListener(mOnClickListener);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_home;
    }


    public void animateFAB() {

        if (isFabOpen) {
            mFab.startAnimation(rotate_backward);
            mAddExpenseFab.startAnimation(fab_close);
            mAddExpenseBookFab.startAnimation(fab_close);
            mAddExpenseFab.setClickable(false);
            mAddExpenseBookFab.setClickable(false);
            isFabOpen = false;

        } else {
            mFab.startAnimation(rotate_forward);
            mAddExpenseFab.startAnimation(fab_open);
            mAddExpenseBookFab.startAnimation(fab_open);
            mAddExpenseFab.setClickable(true);
            mAddExpenseBookFab.setClickable(true);
            isFabOpen = true;
        }
    }
}
