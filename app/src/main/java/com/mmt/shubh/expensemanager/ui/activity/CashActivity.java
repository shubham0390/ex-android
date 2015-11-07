package com.mmt.shubh.expensemanager.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.ui.dagger.component.CashActivityComponent;
import com.mmt.shubh.expensemanager.ui.dagger.component.DaggerCashActivityComponent;
import com.mmt.shubh.expensemanager.ui.dagger.module.CashActivityModule;
import com.mmt.shubh.expensemanager.ui.mvp.MVPActivity;

public class CashActivity extends MVPActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void injectDependencies(MainComponent mainComponent) {
        CashActivityComponent component = DaggerCashActivityComponent.builder()
                .cashActivityModule(new CashActivityModule())
                .mainComponent(mainComponent).build();
        component.inject(this);
    }
}
