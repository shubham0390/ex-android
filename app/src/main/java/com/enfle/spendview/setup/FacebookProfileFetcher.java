/*
 * Copyright (c) 2016. . The Km2Labs Project
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.enfle.spendview.setup;

import android.os.Handler;
import android.os.Looper;

import com.enfle.spendview.database.content.User;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;

import org.json.JSONException;
import org.json.JSONObject;

import timber.log.Timber;

/**
 * Created by Subham Tyagi,
 * on 25/Oct/2015,
 * 5:13 PM
 * TODO:Add class comment.
 */
public class FacebookProfileFetcher implements ProfileFetcher {

    private String mAuthenticationToken;

    private User mUser;

    public FacebookProfileFetcher(String token) {
        mUser = new User();
        mAuthenticationToken = token;
    }

    @Override
    public User getUserProfileDetails() {

        Profile profile = Profile.getCurrentProfile();

        mUser.setName(profile.getName());
        mUser.setProfileImageUrl(profile.getProfilePictureUri(512, 512).toString());

        GraphRequest graphRequest = new GraphRequest(AccessToken.getCurrentAccessToken(), "/" + Profile.getCurrentProfile().getId(),
                null, HttpMethod.GET, mCallback);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(graphRequest::executeAndWait);
        return mUser;
    }


    private GraphRequest.Callback mCallback = new GraphRequest.Callback() {
        @Override
        public void onCompleted(GraphResponse response) {
            Profile profile1 = Profile.getCurrentProfile();
            Timber.d(response.toString());
            try {
                JSONObject jsonObject = response.getJSONObject();
                String emailID = jsonObject.getString("email");
                mUser.setEmail(emailID);
                mUser.setProfileImageUrl(profile1.getProfilePictureUri(512, 512).toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public String getAuthenticationToken() {
        return mAuthenticationToken;
    }
}
