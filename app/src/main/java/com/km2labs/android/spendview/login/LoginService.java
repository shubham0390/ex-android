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

import com.km2labs.android.spendview.login.beans.LoginRequest;
import com.km2labs.android.spendview.login.beans.LoginResponse;
import com.km2labs.android.spendview.login.beans.SignupRequest;
import com.km2labs.android.spendview.login.beans.SignupResponse;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Subham Tyagi on 30/07/16.
 */

public interface LoginService {

    @POST("/user")
    Observable<SignupResponse> signup(@Body SignupRequest user);

    @POST("/user/login")
    Observable<LoginResponse> login(@Body LoginRequest loginRequest);
}
