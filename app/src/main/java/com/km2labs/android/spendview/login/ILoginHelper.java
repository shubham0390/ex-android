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

package com.km2labs.android.spendview.login;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by styagi on 6/4/2015.
 */
public interface ILoginHelper {

    enum Type {
        GOOGLE,
        FACEBOOK,
        TWITEER,
    }

    void setUp(Object object);
    void signIn(Activity activity);

    void signOut();

    void revokeAccess();

    Object getClient();

    void onActivityResult(int requestCode, int responseCode, Intent intent);
}
