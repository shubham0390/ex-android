package com.km2labs.android.spendview.home;

import com.km2labs.android.spendview.core.dagger.scope.ActivityScope;
import com.km2labs.android.spendview.core.dagger.component.MainComponent;


import dagger.Component;


@Component(
        dependencies = MainComponent.class)
@ActivityScope
public interface SummaryActivityComponent {

    void inject(SummaryActivity summaryActivity);
}
