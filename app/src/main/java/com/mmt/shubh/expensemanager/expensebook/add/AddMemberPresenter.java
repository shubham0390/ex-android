package com.mmt.shubh.expensemanager.expensebook.add;

import com.mmt.shubh.expensemanager.core.mvp.BasePresenter;
import com.mmt.shubh.expensemanager.expensebook.ExpenseBookModel;
import com.mmt.shubh.expensemanager.member.ContactsMetaData;
import com.mmt.shubh.expensemanager.core.mvp.MVPPresenter;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by subhamtyagi on 2/28/16.
 */
public class AddMemberPresenter extends BasePresenter<AddUpdateExpenseView> implements MVPPresenter<AddUpdateExpenseView> {

    ExpenseBookModel mExpenseBookModel;

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
