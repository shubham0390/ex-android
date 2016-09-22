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

package com.km2labs.android.spendview.expensebook.add;


import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.km2labs.android.spendview.core.dagger.component.ConfigPersistentComponentV2;
import com.km2labs.android.spendview.core.mvp.MVPFragmentV3;
import com.km2labs.android.spendview.expensebook.ExpenseBookComponent;
import com.km2labs.android.spendview.expensebook.ExpensebookModule;
import com.km2labs.android.spendview.member.ContactPickerAdapter;
import com.km2labs.android.spendview.member.ContactsMetaData;
import com.km2labs.android.spendview.utils.RxEventBus;
import com.km2labs.expenseview.android.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import timber.log.Timber;

/**
 */
public class AddMembersFragment extends MVPFragmentV3<AddMemberPresenter> implements SearchView.OnQueryTextListener, AddUpdateExpenseView {

    public static final String ARG_EXPENSEBOOK_NAME = "Arg:AddMemberFragment:Expensebook:Name";
    public static final String ARG_EXPENSEBOOK_OWNER_ID = "Arg:AddMemberFragment:Expensebook:OwnerId";
    private static final int REQUEST_CONTACTS = 1;


    @BindView((R.id.contacts_list))
    RecyclerView mContactsList;

    private ContactPickerAdapter mContactPickerAdapter;

    private List<ContactsMetaData> mContactsMetaDataList;

    private String mName;
    private String mOwnerId;

    public static Fragment getInstance(String name, String ownerId) {
        Fragment fragment = new Fragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_EXPENSEBOOK_NAME, name);
        bundle.putString(ARG_EXPENSEBOOK_OWNER_ID, ownerId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View getFragmentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_add_members_to_expense_book, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mName = bundle.getString(ARG_EXPENSEBOOK_NAME);
            mOwnerId = bundle.getString(ARG_EXPENSEBOOK_OWNER_ID);
        }
        setupRecyclerView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected <T> T createComponent(ConfigPersistentComponentV2 plus) {
        return (T) plus.plus(new ExpensebookModule());
    }

    @Override
    public <T> void injectDependency(@Nullable Bundle t) {
        ExpenseBookComponent component = getComponent(t);
        component.inject(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_next) {
            Log.d("Selected contacts", mContactPickerAdapter.getSelectedItems().toString());
            mPresenter.addMembersToExpenseBook(getContext(), mContactsMetaDataList,
                    mContactPickerAdapter.getSelectedItems(), mName, mOwnerId);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_add_members_to_expense_book, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private void setupRecyclerView() {
        mContactsList.setLayoutManager(new LinearLayoutManager(mContactsList.getContext()));
        if (ActivityCompat.checkSelfPermission(getActivity(), "android.permission.READ_CONTACTS") != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{"android.permission.READ_CONTACTS"}, REQUEST_CONTACTS);
        } else {
            readContacts();
            mContactPickerAdapter = new ContactPickerAdapter(mContactsMetaDataList);
            mContactsList.setAdapter(mContactPickerAdapter);
        }
    }

    @Override
    public boolean onQueryTextChange(String query) {
        final List<ContactsMetaData> filteredContactList = filter(mContactsMetaDataList, query);
        mContactPickerAdapter.animateTo(filteredContactList);
        mContactsList.scrollToPosition(0);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContacts();
                    mContactPickerAdapter = new ContactPickerAdapter(mContactsMetaDataList);
                    mContactsList.setAdapter(mContactPickerAdapter);
                } else {
                    Timber.e("User denied contact permission");
                }
            }
        }
    }

    /**
     * read contacts from system's contact database
     */
    public void readContacts() {
        Cursor contactsCursor = getActivity().getContentResolver().query(ContactsContract
                .Contacts.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME);
        mContactsMetaDataList = new ArrayList<>();
        try {
            if (contactsCursor.getCount() > 0) {
                while (contactsCursor.moveToNext()) {
                    String id = contactsCursor.getString(contactsCursor.getColumnIndex
                            (ContactsContract.Contacts._ID));
                    String name = contactsCursor.getString(contactsCursor.getColumnIndex
                            (ContactsContract.Contacts.DISPLAY_NAME));
                    String photoURI = contactsCursor.getString(contactsCursor.getColumnIndex
                            (ContactsContract.Contacts.PHOTO_URI));
                    if (Integer.parseInt(contactsCursor.getString(contactsCursor.getColumnIndex
                            (ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                        mContactsMetaDataList.add(new ContactsMetaData(name, id, photoURI));
                    }
                }
            }
        } finally {
            if (contactsCursor != null) {
                contactsCursor.close();
            }
        }
    }

    /**
     * returns a list ContactsMetaData which match the query string
     *
     * @param contactList list of contacts to be searched in
     * @param query       string to be searched in
     * @return list of contacts containing the query string
     */
    @Nullable
    private List<ContactsMetaData> filter(List<ContactsMetaData> contactList, String query) {
        query = query.toLowerCase();

        final List<ContactsMetaData> filteredModelList = new ArrayList<>();
        for (ContactsMetaData contact : contactList) {
            final String name = contact.getContactName().toLowerCase();
            if (name.contains(query)) {
                filteredModelList.add(contact);
            }
        }
        return filteredModelList;
    }

    @Override
    public void onMemberAdd(Boolean v) {
        RxEventBus.getInstance().post(ExpenseBookAddUpdateActivity.ACTION_ADD_MEMBER_COMPLETE);
    }

    @Override
    public void onMemberAddError(Throwable e) {

    }
}