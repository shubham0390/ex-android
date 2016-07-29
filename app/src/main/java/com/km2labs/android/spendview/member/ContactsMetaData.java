package com.km2labs.android.spendview.member;

/**
 * @author Umang Chamaria
 */
public class ContactsMetaData {
    private String mContactName;
    private String mContactId;
    private String mContactPhotoURI;

    public ContactsMetaData(String contactName, String contactId, String contactPhotoURI) {
        this.mContactName = contactName;
        this.mContactId = contactId;
        this.mContactPhotoURI = contactPhotoURI;
    }

    public String getContactName() {
        return mContactName;
    }

    public void setContactName(String mContactName) {
        this.mContactName = mContactName;
    }

    public String getContactId() {
        return mContactId;
    }

    public void setContactId(String mContactId) {
        this.mContactId = mContactId;
    }

    public String getContactPhotoURI() {
        return mContactPhotoURI;
    }

    public void setContactPhotoURI(String mContactPhotoURI) {
        this.mContactPhotoURI = mContactPhotoURI;
    }
}
