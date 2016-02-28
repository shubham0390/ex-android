package com.mmt.shubh.expensemanager.cash;

import com.mmt.shubh.expensemanager.dagger.scope.ActivityScope;
import com.mmt.shubh.expensemanager.dagger.component.MainComponent;

import dagger.Component;

/**
 * Created by Subham Tyagi,
 * on 07/Sep/2015,
 * 2:29 PM
 * TODO:Add class comment.
 */
@ActivityScope
@Component(
        dependencies =
                {
                        MainComponent.class
                },
        modules = {
                CashActivityModule.class
        }
)
public interface CashActivityComponent {

    void inject(CashActivity cashActivity);

    void inject(CashListFragment cashListFragment);

}
