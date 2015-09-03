package com.mmt.shubh.expensemanager.dagger;

import android.app.Application;

import com.mmt.shubh.expensemanager.service.rest.RestClient;
import com.mmt.shubh.expensemanager.service.rest.RestService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Endpoint;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;

@Module
public class ApiModule {
    public static final String PRODUCTION_API_URL = "https://api.github.com";


    @Provides
    @Singleton
    RestClient provideClient(Application app) {
        return RestClient.getRestClient(app.getApplicationContext());
    }

    /**
     * Endpoint object is delivered by specific child module (Release or Debug)
     */
    @Provides
    @Singleton
    RestService provideRestAdapter(Application app) {
        return RestClient.getRestClient(app.getApplicationContext()).getRestService();
    }
}
