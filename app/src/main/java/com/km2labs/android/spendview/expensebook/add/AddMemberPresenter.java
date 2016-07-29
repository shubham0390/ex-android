package com.km2labs.android.spendview.expensebook.add;

import com.km2labs.android.spendview.core.dagger.scope.ConfigPersistent;
import com.km2labs.android.spendview.core.mvp.MVPPresenter;
import com.km2labs.android.spendview.expensebook.ExpenseBookModel;
import com.km2labs.android.spendview.core.mvp.BasePresenter;
import com.km2labs.android.spendview.member.ContactsMetaData;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@ConfigPersistent
public class AddMemberPresenter extends BasePresenter<AddUpdateExpenseView>
        implements MVPPresenter<AddUpdateExpenseView> {

    ExpenseBookModel mExpenseBookModel;

    @Inject
    public AddMemberPresenter(ExpenseBookModel expenseBookModel) {
        mExpenseBookModel = expenseBookModel;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }


    public void addMembersToExpenseBook(List<ContactsMetaData> contactsMetaDataList, List<Integer> selectedItems, long id) {
        mExpenseBookModel.addMemberToExpenseBook(contactsMetaDataList, selectedItems, id)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(v -> getView().onMemberAdd(v), e -> getView().onMemberAddError(e));

    }


}
