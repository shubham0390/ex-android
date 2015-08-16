package com.mmt.shubh.expensemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;

import com.mmt.shubh.expensemanager.database.content.Member;
import com.mmt.shubh.expensemanager.database.content.contract.ExpenseBookContract;
import com.mmt.shubh.expensemanager.database.content.contract.MemberContract;
import com.mmt.shubh.expensemanager.debug.Logger;

import java.util.List;

/**
 * @author uchamaria
 */
public class CreateExpenseBook {
    private final String TAG = CreateExpenseBook.class.getSimpleName();
    private Context mContext;
    private List<ContactsMetaData> mContactsList;
    private List<Integer> mSelectedContacts;
    private Bundle mExpenseBookInfo;

    public CreateExpenseBook(Context context, List<ContactsMetaData> contactsList, List<Integer>
            selectedContacts,
                             Bundle expenseBookInfo) {
        this.mContext = context;
        this.mContactsList = contactsList;
        this.mSelectedContacts = selectedContacts;
        this.mExpenseBookInfo = expenseBookInfo;
    }

    /**
     * creates new expense book
     */
    public void create() {
        Logger.debug(TAG, "entered create()");
        saveExpenseBookDetails();
        saveMemberDetails();
        Logger.debug(TAG, "exiting create()");
    }

    /**
     * creates a new expense book entry in the database
     */
    private void saveExpenseBookDetails() {
        Logger.debug(TAG, "entered saveExpenseBookDetails()");
        String expenseBookName = mExpenseBookInfo.getString(Constants.EXTRA_GROUP_NAME);
        String expenseBookImageURI = mExpenseBookInfo.getString(Constants.EXTRA_GROUP_IMAGE_URI);
        String expenseBookDescription = mExpenseBookInfo.getString(Constants.EXTRA_GROUP_DESCRIPTION);
        ContentValues values = new ContentValues();
        values.put(ExpenseBookContract.EXPENSE_BOOK_NAME, expenseBookName);
        values.put(ExpenseBookContract.EXPENSE_BOOK_TYPE, "public");
        values.put(ExpenseBookContract.EXPENSE_BOOK_PROFILE_IMAGE_URI, expenseBookImageURI);
        values.put(ExpenseBookContract.EXPENSE_BOOK_DESCRIPTION, expenseBookDescription);
        mContext.getContentResolver().insert(ExpenseBookContract.EXPENSE_BOOK_URI, values);
        Logger.debug(TAG, "exiting saveExpenseBookDetails()");
    }

    /**
     * save members and their details of the newly created expense book
     */
    private void saveMemberDetails() {
        Logger.debug(TAG, "entered saveMemberDetails()");
        for (int selectedIndex : mSelectedContacts) {
            String contactId = mContactsList.get(selectedIndex).getContactId();
            Member memberDetails = fetchContactDetails(contactId);
            saveMemberDetailsToDB(memberDetails);

        }
        Logger.debug(TAG, "exiting saveMemberDetails()");
    }

    /**
     * fetch name, contact number, email-id for a contact-id.
     * @param contactId id of the contact whose details are required.
     * @return Member object with all member details
     */
    private Member fetchContactDetails(String contactId) {
        Logger.debug(TAG, "entered fetchContactDetails()");
        Cursor contactsCursor = mContext.getContentResolver().query(ContactsContract
                        .Contacts.CONTENT_URI, null, ContactsContract.Contacts._ID + "?=", new
                        String[]{contactId}, ContactsContract.Contacts.DISPLAY_NAME);
        Member member = new Member();
        try {
            if (contactsCursor.moveToFirst()) {
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
                    String phoneNo = phoneNumberCursor.getString(phoneNumberCursor.getColumnIndex
                            (ContactsContract.CommonDataKinds.Phone.NUMBER));
                    String email = phoneNumberCursor.getString(phoneNumberCursor.getColumnIndex
                            (ContactsContract.CommonDataKinds.Email.ADDRESS));
                member.setMemberName(name);
                member.setMemberEmail(email);
                member.setProfilePhotoUrl(photoURI);
                member.setMemberPhoneNumber(phoneNo);
            }
        } finally {
            if (contactsCursor != null) {
                contactsCursor.close();
            }
        }
        Logger.debug(TAG, "exiting fetchContactDetails()");
        return member;

    }

    /**
     * save member details to database
     * @param memberDetails member details to be saved
     */
    private void saveMemberDetailsToDB(Member memberDetails){
        Logger.debug(TAG, "entering saveMemberDetailsToDB()");
        ContentValues values = new ContentValues();
        values.put(MemberContract.MEMBER_NAME, memberDetails.getMemberName());
        values.put(MemberContract.MEMBER_PHONE_NUMBER, memberDetails.getMemberPhoneNumber());
        values.put(MemberContract.MEMBER_EMAIL, memberDetails.getMemberEmail());
        values.put(MemberContract.MEMBER_IMAGE_URI, memberDetails.getProfilePhotoUrl());
        mContext.getContentResolver().insert(MemberContract.MEMBER_URI, values);
        Logger.debug(TAG, "exiting saveMemberDetailsToDB()");
    }
}
