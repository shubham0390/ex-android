package com.enfle.spendview.member.list;

import com.enfle.spendview.core.RecyclerItemView;
import com.enfle.spendview.core.mvp.MVPPresenter;
import com.enfle.spendview.core.mvp.lce.MVPLCEView;

import java.util.List;

import dagger.Module;
import dagger.Subcomponent;

/**
 * Created by : Subham Tyagi
 * Created on :  18/09/16.
 */

public interface MemberListContract {

    interface Presenter extends MVPPresenter<View> {

        void loadAllMembers();

        void loadAllMembersByExpenseBook(long expenseBookId);

        void deleteMemberFromExpenseBook(long memberId, long expenseBookId);
    }

    interface View extends MVPLCEView<List<RecyclerItemView>> {

    }


    @Subcomponent(modules = MemberListModule.class)
    interface MemberListComponent {

        void inject(MemberListFragment memberListFragment);
    }

    @Module
    class MemberListModule {

//        @Provides
//        public Presenter providePresenter(MemberModel memberModel) {
//            return new MemberListFragmentPresenter(memberModel);
//        }
    }
}
