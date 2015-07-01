package com.mmt.shubh.expensemanager.database.content;

/**
 * Created by styagi on 6/5/2015.
 */
public class DeviceDetails {

    private String deviceUUID;

    private String gcmToken;

    private UserInfo account;

    public DeviceDetails(UserInfo account, String deviceUUID, String gcmToken) {
        this.account = account;
        this.deviceUUID = deviceUUID;
        this.gcmToken = gcmToken;
    }

    public UserInfo getAccount() {
        return account;
    }

    public String getDeviceUUID() {
        return deviceUUID;
    }

    public String getGcmToken() {
        return gcmToken;
    }
}
