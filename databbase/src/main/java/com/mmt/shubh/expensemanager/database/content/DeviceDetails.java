package com.mmt.shubh.expensemanager.database.content;

/**
 * Created by styagi on 6/5/2015.
 */
public class DeviceDetails {

    private String deviceUUID;

    private String gcmToken;

    private Account account;

    public DeviceDetails(Account account, String deviceUUID, String gcmToken) {
        this.account = account;
        this.deviceUUID = deviceUUID;
        this.gcmToken = gcmToken;
    }

    public Account getAccount() {
        return account;
    }

    public String getDeviceUUID() {
        return deviceUUID;
    }

    public String getGcmToken() {
        return gcmToken;
    }
}
