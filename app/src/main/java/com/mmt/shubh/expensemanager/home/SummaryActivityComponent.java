package com.mmt.shubh.expensemanager.home;

import com.mmt.shubh.expensemanager.core.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.core.dagger.scope.ActivityScope;


import dagger.Component;


@Component(
        dependencies = MainComponent.class)
@ActivityScope
public interface SummaryActivityComponent {

    void inject(SummaryActivity summaryActivity);
}
