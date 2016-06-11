package com.mmt.shubh.expensemanager.service.rest;

import android.content.Context;
import android.support.annotation.NonNull;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.mmt.shubh.expensemanager.service.network.ConnectivityHelper;
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

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Subham Tyagi,
 * on 22/Oct/2015,
 * 3:14 PM
 * TODO:Add class comment.
 */
public class RestClient {

    private static final String BASE_URL = "http://localhost:8080/expensemanager/rest";

    private final static long SIZE_OF_CACHE = 10 * 1024 * 1024; // 10 MB

    public RestClient() {

    }

    @NonNull
    public Retrofit getRetrofit(OkHttpClient okHttpClient) {
        // Create Executor
        Executor executor = Executors.newCachedThreadPool();

        Retrofit.Builder restAdapter = new Retrofit.Builder();
        restAdapter.baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .callbackExecutor(executor)
                .build();

        return restAdapter.build();
    }

    @NonNull
    public OkHttpClient getOkHttpClient(Context context) {
        // Create Cache
        Cache cache = new Cache(new File(context.getCacheDir(), "http"), SIZE_OF_CACHE);

        // Create OkHttpClient
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setCache(cache);
        okHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(30, TimeUnit.SECONDS);

        // Add Cache-Control Interceptor
        okHttpClient.networkInterceptors().add(mCacheControlInterceptor);
        okHttpClient.networkInterceptors().add(new StethoInterceptor());
        return okHttpClient;
    }

    private static final Interceptor mCacheControlInterceptor = chain -> {
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
    };
}
