package com.km2labs.android.spendview.member.list;

import com.km2labs.android.spendview.core.base.RecyclerItemView;
import com.km2labs.android.spendview.core.dagger.scope.ConfigPersistent;
import com.km2labs.android.spendview.core.mvp.MVPPresenterV2;
import com.km2labs.android.spendview.core.mvp.lce.MVPLCEView;
import com.km2labs.android.spendview.member.MemberModel;

import java.util.List;

import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;

/**
 * Created by : Subham Tyagi
 * Created on :  18/09/16.
 */

public interface MemberListContract {

    interface Presenter extends MVPPresenterV2<View> {

        void loadAllMembers();

        void loadAllMembersByExpenseBook(long expenseBookId);

        void deleteMemberFromExpenseBook(long memberId, long expenseBookId);
    }

    interface View extends MVPLCEView<List<RecyclerItemView>> {

    }


    @Subcomponent(modules = MemberListModule.class)
    @ConfigPersistent
    interface MemberListComponent {

        void inject(MemberListFragment memberListFragment);
    }

    @Module
    class MemberListModule {

        @Provides
        @ConfigPersistent
        public Presenter providePresenter(MemberModel memberModel) {
            return new MemberListFragmentPresenter(memberModel);
        }
    }
}
