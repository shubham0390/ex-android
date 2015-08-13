package com.mmt.shubh.expensemanager.ui.fragment;


import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
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

import com.mmt.shubh.expensemanager.ContactsMetaData;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.ui.adapters.ContactPickerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMembersToExpenseBookFragment extends Fragment implements SearchView
        .OnQueryTextListener {

    private final String TAG = AddMembersToExpenseBookFragment.class.getSimpleName();
    private RecyclerView mContactsList;
    private ContactPickerAdapter mContactPickerAdapter;
    private List<ContactsMetaData> mContactsMetaDataList;

    public AddMembersToExpenseBookFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_members_to_expense_book, container,
                false);
        mContactsList = (RecyclerView) view.findViewById(R.id.contacts_list);
        setupRecyclerView();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_next) {
            Log.d("Selected contacts", mContactPickerAdapter.getSelectedItems().toString());
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

    @Override
    public boolean onQueryTextChange(String query) {
        final List<ContactsMetaData> filteredContactList = filter(mContactsMetaDataList, query);
        mContactPickerAdapter.animateTo(filteredContactList);
        mContactsList.scrollToPosition(0);
        return true;
    }

    private void setupRecyclerView() {
        mContactsList.setLayoutManager(new LinearLayoutManager(mContactsList.getContext()));
        readContacts();
        mContactPickerAdapter = new ContactPickerAdapter(mContactsMetaDataList);
        mContactsList.setAdapter(mContactPickerAdapter);
    }

    public void readContacts() {
        Cursor contactsCursor = getActivity().getContentResolver().query(ContactsContract
                .Contacts.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME);
        mContactsMetaDataList = new ArrayList<>();
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
        if (contactsCursor != null) {
            contactsCursor.close();
        }
    }

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
}
