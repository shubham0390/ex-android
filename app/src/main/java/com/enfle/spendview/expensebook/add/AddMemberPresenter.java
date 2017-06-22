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

package com.enfle.spendview.expensebook.add;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.enfle.spendview.core.dagger.scope.ConfigPersistent;
import com.enfle.spendview.core.mvp.BasePresenter;
import com.enfle.spendview.core.mvp.MVPPresenter;
import com.enfle.spendview.database.FirebaseDataManager;
import com.enfle.spendview.database.content.Member;
import com.enfle.spendview.member.ContactsMetaData;
import com.enfle.spendview.utils.RxUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

@ConfigPersistent
public class AddMemberPresenter extends BasePresenter<AddUpdateExpenseView>
        implements MVPPresenter<AddUpdateExpenseView> {

    FirebaseDataManager mFirebaseDataManager;

    @Inject
    public AddMemberPresenter(FirebaseDataManager firebaseDataManager) {
        mFirebaseDataManager = firebaseDataManager;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    public void addMembersToExpenseBook(Context context, List<ContactsMetaData> contactsMetaDataList, List<Integer> selectedItems, String name, String ownerId) {
        Map<String, Boolean> memberMap = new HashMap<>();
        for (int selectedIndex : selectedItems) {
            String contactId = contactsMetaDataList.get(selectedIndex).getContactId();
            Member member = fetchContactDetails(context, contactId);
            memberMap.put(member.getMemberPhoneNumber(), true);
            mFirebaseDataManager.getMember(member.getMemberPhoneNumber()).compose(RxUtils.applyMainIOSchedulers())
                    .subscribe(member2 -> {
                    }, throwable -> {
                        mFirebaseDataManager.createMember(member);
                    });
        }
        mFirebaseDataManager.addMemberToExpenseBook(memberMap, name, ownerId);
    }

    private Member fetchContactDetails(Context context, String contactId) {

        Cursor contactsCursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, ContactsContract.Contacts._ID + " ?=", new
                String[]{contactId}, ContactsContract.Contacts.DISPLAY_NAME);

        Member member = new Member();

        try {
            if (contactsCursor != null && contactsCursor.moveToFirst()) {
                String name = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String photoURI = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));

                Cursor phoneNumberCursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " " + "=?", new String[]{contactId}, null);
                int phoneNo = phoneNumberCursor.getInt(phoneNumberCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                String email = phoneNumberCursor.getString(phoneNumberCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
                member.setMemberName(name);
                member.setMemberEmail(email);
                member.setProfilePhotoUrl(photoURI);
                member.setMemberPhoneNumber(String.valueOf(phoneNo));
                member.setIsRegistered(false);
            }
        } finally {
            if (contactsCursor != null) {
                contactsCursor.close();
            }
        }
        return member;

    }
}
