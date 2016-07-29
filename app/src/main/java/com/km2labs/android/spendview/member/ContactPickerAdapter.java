package com.km2labs.android.spendview.member;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.km2labs.shubh.expensemanager.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Umang Chamaria
 */
public class ContactPickerAdapter extends RecyclerView.Adapter<ContactPickerAdapter.ViewHolder> {
    private List<ContactsMetaData> mContactNames;
    private SparseBooleanArray mSelectedContacts;

    public ContactPickerAdapter(List<ContactsMetaData> contactNames) {
        this.mContactNames = new ArrayList<>(contactNames);
        mSelectedContacts = new SparseBooleanArray();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contacts_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ContactsMetaData metaData = mContactNames.get(position);
        holder.mContactName.setText(metaData.getContactName());
        String contactPhotoURI = metaData.getContactPhotoURI();
        if (!TextUtils.isEmpty(contactPhotoURI)) {
            holder.mContactImage.setImageURI(Uri.parse(contactPhotoURI));
        } else {
            holder.mContactImage.setImageResource(R.mipmap.ic_launcher);
        }
        holder.mContactChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactPickerAdapter.this.updateSelectedItem(((CheckBox) v).isChecked(), position);
            }
        });
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSelectedItem(holder.mContactChecked.isChecked(), position);
            }
        });
    }

    private void updateSelectedItem(boolean value, int position) {
        if (value) {
            if (!mSelectedContacts.get(position, false)) {
                mSelectedContacts.put(position, true);
            }
        } else {
            mSelectedContacts.delete(position);
        }
    }

    @Override
    public int getItemCount() {
        return mContactNames.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<Integer>(mSelectedContacts.size());
        for (int i = 0; i < mSelectedContacts.size(); i++) {
            items.add(mSelectedContacts.keyAt(i));
        }
        return items;
    }

    public void animateTo(List<ContactsMetaData> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<ContactsMetaData> filteredContactList) {
        for (int i = mContactNames.size() - 1; i >= 0; i--) {
            final ContactsMetaData contact = mContactNames.get(i);
            if (!filteredContactList.contains(contact)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<ContactsMetaData> filteredContactsList) {
        for (int i = 0, count = filteredContactsList.size(); i < count; i++) {
            final ContactsMetaData contact = filteredContactsList.get(i);
            if (!mContactNames.contains(contact)) {
                addItem(i, contact);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<ContactsMetaData> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final ContactsMetaData model = newModels.get(toPosition);
            final int fromPosition = mContactNames.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public ContactsMetaData removeItem(int position) {
        final ContactsMetaData contact = mContactNames.remove(position);
        notifyItemRemoved(position);
        return contact;
    }

    public void addItem(int position, ContactsMetaData model) {
        mContactNames.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final ContactsMetaData model = mContactNames.remove(fromPosition);
        mContactNames.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        @BindView(R.id.contact_image)
        ImageView mContactImage;
        @BindView(R.id.contact_name)
        TextView mContactName;
        @BindView(R.id.contact_selected)
        CheckBox mContactChecked;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }
}
