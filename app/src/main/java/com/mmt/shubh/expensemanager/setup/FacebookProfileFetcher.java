package com.mmt.shubh.expensemanager.setup;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.mmt.shubh.expensemanager.database.content.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by styagi on 6/4/2015.
 */
public class FacebookProfileFetcher extends ProfileFetcher {

    private static final String TAG = FacebookProfileFetcher.class.getSimpleName();

    private UserInfo userInfo;

    @Override
    public UserInfo fetchUserAccountDetails(final Context context) {
        Profile profile = Profile.getCurrentProfile();
        userInfo = new UserInfo();

        userInfo.setDisplayName(profile.getName());
        userInfo.setProfilePhotoUrl(profile.getProfilePictureUri(512, 512).toString());
        userInfo.setEmailAddress("");
        userInfo.setStatus(UserInfo.Status.ACTIVE);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                new GraphRequest(
                        AccessToken.getCurrentAccessToken(),"/" + Profile.getCurrentProfile().getId(),
                        null,HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {
                                Profile profile = Profile.getCurrentProfile();
                                Log.d(TAG, response.toString());
                                try {
                                    JSONObject jsonObject = response.getJSONObject();
                                    String emailID = jsonObject.getString("email");
                                    userInfo.setEmailAddress(emailID);
                                    userInfo.setProfilePhotoUrl(profile.getProfilePictureUri(512,
                                            512).toString());
                                    update(context, userInfo);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                ).executeAsync();
            }
        });

        return userInfo;
    }
}
