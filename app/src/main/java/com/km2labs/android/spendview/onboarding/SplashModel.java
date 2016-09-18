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

package com.km2labs.android.spendview.onboarding;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.km2labs.android.spendview.core.dagger.scope.ConfigPersistent;
import com.km2labs.android.spendview.database.content.User;

import javax.inject.Inject;

import rx.Observable;

@ConfigPersistent
public class SplashModel {

    @Inject
    public SplashModel() {
    }

    public Observable<User> getUserInfo() {
        return Observable.create(subscriber -> {
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            if (firebaseUser != null) {
                User user = new User();
                user.setStatus(User.Status.LOGGED_IN);
                user.setEmail(firebaseUser.getEmail());
                user.setName(firebaseUser.getDisplayName());
                user.setProfileImageUrl(firebaseUser.getPhotoUrl().toString());
                subscriber.onNext(user);
                subscriber.onCompleted();
            }
        });
    }
}
