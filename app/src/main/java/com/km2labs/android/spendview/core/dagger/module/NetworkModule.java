package com.km2labs.android.spendview.core.dagger.module;

import android.content.Context;

import com.km2labs.android.spendview.service.rest.RestClient;
import com.km2labs.android.spendview.service.rest.service.MemberRestService;
import com.km2labs.android.spendview.core.dagger.module.api.INetworkModule;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Retrofit;

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

}
