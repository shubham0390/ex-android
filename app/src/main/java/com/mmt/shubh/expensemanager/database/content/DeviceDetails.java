package com.mmt.shubh.expensemanager.database.content;

import org.parceler.Parcel;

import io.realm.DeviceDetailsRealmProxy;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Subham Tyagi,
 * on 30/Sep/2015,
 * 9:05 AM
 * TODO:Add class comment.
 */
@Parcel(implementations = { DeviceDetailsRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = { DeviceDetails.class })
public class DeviceDetails extends RealmObject{

    @PrimaryKey
    private long id;
    private String deviceUUID;

    private String gcmToken;

    private UserInfo account;

    public void setAccount(UserInfo account) {
        this.account = account;
    }

    public void setDeviceUUID(String deviceUUID) {
        this.deviceUUID = deviceUUID;
    }

    public void setGcmToken(String gcmToken) {
        this.gcmToken = gcmToken;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DeviceDetails() {
    }

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
