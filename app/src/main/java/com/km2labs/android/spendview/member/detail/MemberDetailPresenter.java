package com.km2labs.android.spendview.member.detail;

import com.km2labs.android.spendview.core.dagger.scope.ConfigPersistent;
import com.km2labs.android.spendview.core.mvp.BasePresenter;

import javax.inject.Inject;

/**
 * Created by Subham on 02/07/16.
 */
@ConfigPersistent
public class MemberDetailPresenter extends BasePresenter<MemberDetailContract.View> implements MemberDetailContract.Presenter {

    @Inject
    public MemberDetailPresenter() {
    }
}
