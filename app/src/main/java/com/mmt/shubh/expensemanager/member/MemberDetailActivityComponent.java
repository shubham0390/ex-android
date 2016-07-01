package com.mmt.shubh.expensemanager.member;

import com.mmt.shubh.expensemanager.core.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.core.dagger.scope.ActivityScope;

import dagger.Component;

/**
 * Created by subhamtyagi on 2/28/16.
 */
@Component(
        dependencies = MainComponent.class,
        modules = {
                ModuleMemberDetailActivity.class
        })
@ActivityScope
public interface MemberDetailActivityComponent {
    void inject(MemberDetailActivity memberDetailActivity);
}
