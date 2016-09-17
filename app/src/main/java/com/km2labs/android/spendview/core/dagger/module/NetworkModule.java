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

package com.km2labs.android.spendview.core.dagger.module;

import android.content.Context;

import com.km2labs.android.spendview.core.dagger.module.api.INetworkModule;
import com.km2labs.android.spendview.login.LoginService;
import com.km2labs.android.spendview.service.rest.RestClient;
import com.km2labs.android.spendview.service.rest.service.MemberRestService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Module
public class NetworkModule implements INetworkModule {

    @Provides
    @Singleton
    public RestClient provideRestClient() {
        return new RestClient();
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(Context context, RestClient restClient) {
        return restClient.getOkHttpClient(context);
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(RestClient restClient, OkHttpClient okHttpClient) {
        return restClient.getRetrofit(okHttpClient);
    }

    @Provides
    @Singleton
    public MemberRestService provideMemberRestService(Retrofit retrofit) {
        return retrofit.create(MemberRestService.class);
    }

    @Provides
    @Singleton
    public LoginService provideLoginService(Retrofit retrofit) {
        return retrofit.create(LoginService.class);
    }

}
