package com.mmt.shubh.expensemanager.service.rest;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by styagi on 6/5/2015.
 */
public class RestClient {

    private static final String BASE_URL = "your base url";

    private static long SIZE_OF_CACHE = 10 * 1024 * 1024; // 10 MB

    private RestService apiService;

    private static RestClient mRestClient;

    private static RestClient getRestClient(Context context) {
        if (mRestClient == null) {
            synchronized (mRestClient) {
                if (mRestClient == null) {
                    mRestClient = new RestClient(context);
                }
            }
        }
        return mRestClient;
    }

    private RestClient(Context context) {

        // Create Cache
        Cache cache = null;
        cache = new Cache(new File(context.getCacheDir(), "http"), SIZE_OF_CACHE);

        // Create OkHttpClient
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setCache(cache);
        okHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(30, TimeUnit.SECONDS);

        // Add Cache-Control Interceptor
        okHttpClient.networkInterceptors().add(mCacheControlInterceptor);
        // Create Executor
        Executor executor = Executors.newCachedThreadPool();

        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new ItemTypeAdapterFactory())
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .setClient(new OkClient(okHttpClient))
                .setExecutors(executor, executor)
                .setRequestInterceptor(new SessionRequestInterceptor())
                .setConverter(new GsonConverter(gson))
                .build();

        apiService = restAdapter.create(RestService.class);
    }

    public RestService getRestService() {
        return apiService;
    }

    private static final Interceptor mCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            // Add Cache Control only for GET methods
            if (request.method().equals("GET")) {
                if (ConnectivityHelper.isNetworkAvailable()) {
                    // 1 day
                    request.newBuilder()
                            .header("Cache-Control", "only-if-cached")
                            .build();
                } else {
                    // 4 weeks stale
                    request.newBuilder()
                            .header("Cache-Control", "public, max-stale=2419200")
                            .build();
                }
            }

            Response response = chain.proceed(request);

            // Re-write response CC header to force use of cache
            return response.newBuilder()
                    .header("Cache-Control", "public, max-age=86400") // 1 day
                    .build();
        }
    };
}
