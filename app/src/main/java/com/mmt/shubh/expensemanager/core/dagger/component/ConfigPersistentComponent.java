package com.mmt.shubh.expensemanager.core.dagger.component;

import com.mmt.shubh.expensemanager.core.dagger.module.ActivityModule;
import com.mmt.shubh.expensemanager.core.dagger.scope.ConfigPersistent;
import com.mmt.shubh.expensemanager.login.LoginActivityComponent;
import com.mmt.shubh.expensemanager.login.LoginModule;

import dagger.Component;


@ConfigPersistent
@Component(dependencies = MainComponent.class)
public interface ConfigPersistentComponent {

    ActivityComponent activityComponent(ActivityModule activityModule);
}
