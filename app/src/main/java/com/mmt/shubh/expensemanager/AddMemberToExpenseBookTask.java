package com.mmt.shubh.expensemanager;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.mmt.shubh.expensemanager.database.api.ExpenseBookDataAdapter;
import com.mmt.shubh.expensemanager.database.content.Member;
import com.mmt.shubh.expensemanager.database.dataadapters.ExpenseBookSQLDataAdapter;
import com.mmt.shubh.expensemanager.database.dataadapters.MemberSQLDataAdapter;
import com.mmt.shubh.expensemanager.debug.Logger;
import com.mmt.shubh.expensemanager.task.AbstractTask;
import com.mmt.shubh.expensemanager.task.TaskResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Subham Tyagi,
 * on 17/Sep/2015,
 * 9:47 PM
 * TODO:Add class comment.
 */
public class AddMemberToExpenseBookTask extends AbstractTask {

    private final String TAG = getClass().getSimpleName();

    private List<ContactsMetaData> mContactsList;
    private List<Integer> mSelectedContacts;
    private long mExpenseBookId;

    public AddMemberToExpenseBookTask(Context context, List<ContactsMetaData> contactsList,
                                      List<Integer> selectedContacts, long expenseBookId) {
        super(context);
        this.mContactsList = contactsList;
        this.mSelectedContacts = selectedContacts;
        mExpenseBookId = expenseBookId;
    }

    @Override
    public TaskResult execute() {
        saveMemberDetails();
        return null;
    }

    @Override
    public String getTaskAction() {
        return TaskActions.ACTION_ADD_MEMEBER_TO_EXPENSE_BOOK;
    }

    /**
     * save members and their details of the newly created expense book
     */
    private void saveMemberDetails() {

        Logger.debug(TAG, "entered saveMemberDetails()");
        List<Member> memberList = new ArrayList<>();

        for (int selectedIndex : mSelectedContacts) {
            String contactId = mContactsList.get(selectedIndex).getContactId();
            Member memberDetails = fetchContactDetails(contactId);
            memberList.add(memberDetails);
        }
        saveMemberDetailsToDB(memberList);
        Logger.debug(TAG, "exiting saveMemberDetails()");
        ExpenseBookDataAdapter dataAdapter = new ExpenseBookSQLDataAdapter(mContext);
        dataAdapter.addMembers(memberList, mExpenseBookId);
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
        MemberSQLDataAdapter memberSQLDataAdapter = new MemberSQLDataAdapter(mContext);
        memberSQLDataAdapter.create(memberDetails);
        Logger.debug(TAG, "exiting saveMemberDetailsToDB()");
    }
}
