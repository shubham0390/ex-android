/*
 * Copyright (c) 2016. . The Km2Labs Project
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.km2labs.android.spendview.expensebook;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.km2labs.android.spendview.core.dagger.scope.ConfigPersistent;
import com.km2labs.android.spendview.database.api.ExpenseBookDataAdapter;
import com.km2labs.android.spendview.database.api.MemberDataAdapter;
import com.km2labs.android.spendview.database.content.ExpenseBook;
import com.km2labs.android.spendview.database.content.Member;
import com.km2labs.android.spendview.debug.Logger;
import com.km2labs.android.spendview.member.ContactsMetaData;
import com.km2labs.android.spendview.settings.UserSettings;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Subham Tyagi,
 * on 01/Oct/2015,
 * 10:08 AM
 * TODO:Add class comment.
 */
@ConfigPersistent
public class ExpenseBookModel {

    private final String TAG = getClass().getName();

    private ExpenseBookDataAdapter mExpenseBookDataAdapter;

    private MemberDataAdapter mMemberDataAdapter;

    private Context mContext;

    @Inject
    public ExpenseBookModel(Context context, ExpenseBookDataAdapter dataAdapter, MemberDataAdapter memberDataAdapter) {
        mExpenseBookDataAdapter = dataAdapter;
        mContext = context;
        mMemberDataAdapter = memberDataAdapter;
    }

    public Observable<Boolean> addMemberToExpenseBook(List<ContactsMetaData> contactsMetaDataList,
                                                      List<Integer> selectedItems, long id) {
        return Observable.create(subscriber -> {
            saveMemberDetails(contactsMetaDataList, selectedItems, id);
            subscriber.onNext(true);
            subscriber.onCompleted();
        });

    }

    /**
     * save members and their details of the newly created expense book
     *
     * @param mContactsList
     * @param selectedItems
     * @param id
     */
    private void saveMemberDetails(List<ContactsMetaData> mContactsList, List<Integer> selectedItems, long id) {
        Logger.methodStart(TAG, "saveMemberDetails");
        List<Member> memberList = new ArrayList<>();

        for (int selectedIndex : selectedItems) {
            String contactId = mContactsList.get(selectedIndex).getContactId();
            Member memberDetails = fetchContactDetails(contactId);
            memberList.add(memberDetails);
        }

        saveMemberDetailsToDB(memberList);
        mExpenseBookDataAdapter.addMembers(memberList, id);

        Logger.methodEnd(TAG, "saveMemberDetails");
    }

    /**
     * fetch name, contact number, email-id for a contact-id.
     *
     * @param contactId id of the contact whose details are required.
     * @return Member object with all member details
     */
    private Member fetchContactDetails(String contactId) {
        Logger.methodStart(TAG, "fetchContactDetails");

        Cursor contactsCursor = mContext.getContentResolver().query(ContactsContract
                .Contacts.CONTENT_URI, null, ContactsContract.Contacts._ID + " ?=", new
                String[]{contactId}, ContactsContract.Contacts.DISPLAY_NAME);

        Member member = new Member();

        try {
            if (contactsCursor != null && contactsCursor.moveToFirst()) {
                String id = contactsCursor.getString(contactsCursor.getColumnIndex
                        (ContactsContract.Contacts._ID));
                String name = contactsCursor.getString(contactsCursor.getColumnIndex
                        (ContactsContract.Contacts.DISPLAY_NAME));
                String photoURI = contactsCursor.getString(contactsCursor.getColumnIndex
                        (ContactsContract.Contacts.PHOTO_URI));
                Cursor phoneNumberCursor = mContext.getContentResolver().query
                        (ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " " +
                                        "=?", new String[]{contactId}, null);
                int phoneNo = phoneNumberCursor.getInt(phoneNumberCursor.getColumnIndex
                        (ContactsContract.CommonDataKinds.Phone.NUMBER));
                String email = phoneNumberCursor.getString(phoneNumberCursor.getColumnIndex
                        (ContactsContract.CommonDataKinds.Email.ADDRESS));
                member.setMemberName(name);
                member.setMemberEmail(email);
                member.setProfilePhotoUrl(photoURI);
                member.setMemberPhoneNumber(String.valueOf(phoneNo));
            }
        } finally {
            if (contactsCursor != null) {
                contactsCursor.close();
            }
        }

        Logger.methodEnd(TAG, "fetchContactDetails");
        return member;

    }

    /**
     * save member details to database
     *
     * @param memberDetails member details to be saved
     */
    private void saveMemberDetailsToDB(List<Member> memberDetails) {
        Logger.methodStart(TAG, "saveMemberDetailsToDB()");
        mMemberDataAdapter.create(memberDetails);
        Logger.methodEnd(TAG, "saveMemberDetailsToDB()");
    }

    public Observable<ExpenseBook> addExpenseBook(ExpenseBook expenseBook) {
        return saveExpenseBookDetails(expenseBook);
    }

    /**
     * creates a new expense book entry in the database
     *
     * @param mExpenseBook
     */
    private Observable<ExpenseBook> saveExpenseBookDetails(ExpenseBook mExpenseBook) {
        Logger.debug(TAG, "entered saveExpenseBookDetails()");
        return Observable.create(subscriber -> {
            mExpenseBook.setType("public");

            UserSettings userSettings = UserSettings.getInstance();
            Member member = mMemberDataAdapter.get(Long.parseLong(userSettings.getUser().getServerId()));

            mExpenseBook.setCreationTime(System.currentTimeMillis());
            Logger.debug(TAG, "exiting saveExpenseBookDetails()");
            mExpenseBookDataAdapter.create(mExpenseBook);
            subscriber.onNext(mExpenseBook);
            subscriber.onCompleted();
        });

    }


    public Observable<List<ExpenseBook>> getAll() {
        return mExpenseBookDataAdapter.getAll();
    }

    public Observable<Member> loadExpenseBookOwner(long ownerId) {
        return Observable.create(subscriber -> {
            subscriber.onNext(mMemberDataAdapter.get(ownerId));
            subscriber.onCompleted();
        });
    }
}
