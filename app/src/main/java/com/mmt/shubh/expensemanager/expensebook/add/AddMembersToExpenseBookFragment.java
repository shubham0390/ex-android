package com.mmt.shubh.expensemanager.expensebook.add;


import android.app.Fragment;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.mmt.shubh.expensemanager.core.mvp.MVPFragment;
import com.mmt.shubh.expensemanager.utils.Constants;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.core.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.member.ContactPickerAdapter;
import com.mmt.shubh.expensemanager.member.ContactsMetaData;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMembersToExpenseBookFragment extends MVPFragment<AddMemberPresenter>
        implements SearchView.OnQueryTextListener, AddUpdateExpenseView {

    private static final int REQUEST_CONTACTS = 1;

    @Bind((R.id.contacts_list))
    RecyclerView mContactsList;

    private ContactPickerAdapter mContactPickerAdapter;

    private List<ContactsMetaData> mContactsMetaDataList;

    private ExpenseBook mExpenseBook;

    public AddMembersToExpenseBookFragment() {
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_add_members_to_expense_book;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mExpenseBook = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_EXPENSE_BOOK));
        setupRecyclerView();
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
            mPresenter.addMembersToExpenseBook(mContactsMetaDataList, mContactPickerAdapter.getSelectedItems()
                    , mExpenseBook.getId());
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
        if (ActivityCompat.checkSelfPermission(getActivity(), "android.permission.READ_CONTACTS")
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{"android.permission.READ_CONTACTS"},
                    REQUEST_CONTACTS);

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
    protected void injectDependencies(MainComponent mainComponent) {
        DaggerExpenseBookUpdateActivityComponent.builder()
                //.moduleExpneseBookUpdate(new ModuleExpenseBookUpdate())
                .mainComponent(mainComponent)
                .build()
                .inject(this);
    }

    @Override
    public void onMemberAdd(Boolean v) {
        getActivity().finish();
    }

    @Override
    public void onMemberAddError(Throwable e) {

    }
}
