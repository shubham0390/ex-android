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


import com.enfle.spendview.database.content.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import timber.log.Timber;


public class GoogleProfileFetcher implements ProfileFetcher {

    private GoogleSignInAccount mGoogleSignInAccount;

    public GoogleProfileFetcher(GoogleSignInAccount googleSignInAccount) {
        mGoogleSignInAccount = googleSignInAccount;
        Timber.tag(getClass().getSimpleName());
    }

    @Override
    public User getUserProfileDetails() {
        User user = new User();
        if (mGoogleSignInAccount != null) {
            Timber.d("Fetching user profile from google");
            user.setName(mGoogleSignInAccount.getDisplayName());
            user.setProfileImageUrl(mGoogleSignInAccount.getPhotoUrl().toString());
            user.setEmail(mGoogleSignInAccount.getEmail());
        } else {
            Timber.d("Unable to load user information");
        }
        return user;
    }

    @Override
    public String getAuthenticationToken() {
        return mGoogleSignInAccount.getIdToken();
    }
}
