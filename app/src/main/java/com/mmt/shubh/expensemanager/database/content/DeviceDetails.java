package com.mmt.shubh.expensemanager.database.content;

import org.parceler.Parcel;

/**
 * Created by Subham Tyagi,
 * on 30/Sep/2015,
 * 5:48 PM
 * TODO:Add class comment.
 */
@Parcel(value = Parcel.Serialization.BEAN)
public class DeviceDetails {

    private long id;
    private String deviceUUID;
    private String gcmToken;
    private UserInfo account;

    public DeviceDetails() {
    }

    public DeviceDetails(UserInfo account, String deviceUUID, String gcmToken) {
        this.account = account;
        this.deviceUUID = deviceUUID;
        this.gcmToken = gcmToken;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserInfo getAccount() {
        return account;
    }

    public void setAccount(UserInfo account) {
        this.account = account;
    }

    public String getDeviceUUID() {
        return deviceUUID;
    }

    public void setDeviceUUID(String deviceUUID) {
        this.deviceUUID = deviceUUID;
    }

    public String getGcmToken() {
        return gcmToken;
    }

    public void setGcmToken(String gcmToken) {
        this.gcmToken = gcmToken;
    }
}
