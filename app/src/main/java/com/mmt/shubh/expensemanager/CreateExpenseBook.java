package com.mmt.shubh.expensemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;

import com.mmt.shubh.expensemanager.database.api.MemberDataAdapter;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.database.content.Member;
import com.mmt.shubh.expensemanager.database.content.contract.MemberContract;
import com.mmt.shubh.expensemanager.database.dataadapters.ExpenseBookSQLDataAdapter;
import com.mmt.shubh.expensemanager.database.dataadapters.MemberSQLDataAdapter;
import com.mmt.shubh.expensemanager.debug.Logger;

import java.util.ArrayList;
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

    private ExpenseBookSQLDataAdapter mExpenseBookAdapter;

    public CreateExpenseBook(Context context, List<ContactsMetaData> contactsList, List<Integer>
            selectedContacts, Bundle expenseBookInfo) {

        this.mContext = context;
        this.mContactsList = contactsList;
        this.mSelectedContacts = selectedContacts;
        this.mExpenseBookInfo = expenseBookInfo;
        this.mExpenseBookAdapter = new ExpenseBookSQLDataAdapter(mContext);
    }

    /**
     * creates new expense book
     */
    public void create() {
        Logger.debug(TAG, "entered create()");
        ExpenseBook expenseBook = saveExpenseBookDetails();
        saveMemberDetails(expenseBook);
        Logger.debug(TAG, "exiting create()");
    }

    /**
     * creates a new expense book entry in the database
     */
    private ExpenseBook saveExpenseBookDetails() {
        Logger.debug(TAG, "entered saveExpenseBookDetails()");

        String expenseBookName = mExpenseBookInfo.getString(Constants.EXTRA_GROUP_NAME);
        String expenseBookImageURI = mExpenseBookInfo.getString(Constants.EXTRA_GROUP_IMAGE_URI);
        String expenseBookDescription = mExpenseBookInfo.getString(Constants.EXTRA_GROUP_DESCRIPTION);
        ExpenseBook expenseBook = new ExpenseBook();

        expenseBook.setName(expenseBookName);
        expenseBook.setProfileImagePath(expenseBookImageURI);
        expenseBook.setDescription(expenseBookDescription);
        expenseBook.setType("public");

        UserSettings userSettings = UserSettings.getInstance();
        MemberDataAdapter memberDataAdapter = new MemberSQLDataAdapter(mContext);
        expenseBook.setOwner(memberDataAdapter.get(userSettings.getUserInfo().getMemberKey()));

        expenseBook.setCreationTime(System.currentTimeMillis());

        mExpenseBookAdapter.create(expenseBook);
        Logger.debug(TAG, "exiting saveExpenseBookDetails()");

        return expenseBook;
    }

    /**
     * save members and their details of the newly created expense book
     * @param expenseBook
     */
    private void saveMemberDetails(ExpenseBook expenseBook) {
        Logger.debug(TAG, "entered saveMemberDetails()");
        List<Member> memberList = new ArrayList<>();
        for (int selectedIndex : mSelectedContacts) {
            String contactId = mContactsList.get(selectedIndex).getContactId();
            Member memberDetails = fetchContactDetails(contactId);
            memberList.add(memberDetails);

        }
        saveMemberDetailsToDB(memberList);
        Logger.debug(TAG, "exiting saveMemberDetails()");
        mExpenseBookAdapter.addMembers(memberList,expenseBook);
    }

    /**
     * fetch name, contact number, email-id for a contact-id.
     *
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
     *
     * @param memberDetails member details to be saved
     */
    private void saveMemberDetailsToDB(List<Member> memberDetails) {
        Logger.debug(TAG, "entering saveMemberDetailsToDB()");
        MemberSQLDataAdapter memberSQLDataAdapter =  new MemberSQLDataAdapter(mContext);
        memberSQLDataAdapter.create(memberDetails);
        Logger.debug(TAG, "exiting saveMemberDetailsToDB()");
    }
}
