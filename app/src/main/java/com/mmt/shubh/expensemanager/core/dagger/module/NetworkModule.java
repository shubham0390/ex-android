package com.mmt.shubh.expensemanager.core.dagger.module;

import android.content.Context;

import com.mmt.shubh.expensemanager.core.dagger.module.api.INetworkModule;
import com.mmt.shubh.expensemanager.service.rest.RestClient;
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
}
