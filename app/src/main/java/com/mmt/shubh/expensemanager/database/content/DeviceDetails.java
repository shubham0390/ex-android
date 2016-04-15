package com.mmt.shubh.expensemanager.database.content;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import org.parceler.Parcel;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * Created by Subham Tyagi,
 * on 30/Sep/2015,
 * 5:48 PM
 * TODO:Add class comment.
 */
@Parcel(value = Parcel.Serialization.BEAN)
public class DeviceDetails {
    protected static final String PREFS_FILE = "device_id.xml";
    protected static final String PREFS_DEVICE_ID = "device_id";
    protected volatile static UUID uuid;
    private long id;
    private String deviceUUID;
    private String gcmToken;
    private UserInfo account;

    public DeviceDetails(){

    }

    public DeviceDetails(Context context) {
        if (uuid == null) {
            final SharedPreferences prefs = context
                    .getSharedPreferences(PREFS_FILE, 0);
            final String id = prefs.getString(PREFS_DEVICE_ID, null);
            if (id != null) {
                // Use the ids previously computed and stored in the
                // prefs file
                uuid = UUID.fromString(id);
            } else {
                final String androidId = Settings.Secure.getString(
                        context.getContentResolver(), Settings.Secure.ANDROID_ID);
                // Use the Android ID unless it's broken, in which case
                // fallback on deviceId,
                // unless it's not available, then fallback on a random
                // number which we store to a prefs file
                try {
                    if (!"9774d56d682e549c".equals(androidId)) {
                        uuid = UUID.nameUUIDFromBytes(androidId
                                .getBytes("utf8"));
                    } else {
                        final String deviceId = (
                                (TelephonyManager) context
                                        .getSystemService(Context.TELEPHONY_SERVICE))
                                .getDeviceId();
                        uuid = deviceId != null ? UUID
                                .nameUUIDFromBytes(deviceId
                                        .getBytes("utf8")) : UUID
                                .randomUUID();
                    }
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                // Write the value out to the prefs file
                prefs.edit()
                        .putString(PREFS_DEVICE_ID, uuid.toString())
                        .apply();
            }
        }
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
