package com.km2labs.android.spendview.core.dagger.component;

import com.km2labs.android.spendview.core.dagger.module.ActivityModule;
import com.km2labs.android.spendview.core.dagger.scope.ConfigPersistent;

import dagger.Component;


@ConfigPersistent
@Component(dependencies = MainComponent.class)
public interface ConfigPersistentComponent {

    ActivityComponent activityComponent(ActivityModule activityModule);
}
