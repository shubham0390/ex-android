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

package com.km2labs.android.spendview.service.rest;

import android.content.Context;
import android.support.annotation.NonNull;

import com.km2labs.android.spendview.service.network.ConnectivityHelper;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Subham Tyagi,
 * on 22/Oct/2015,
 * 3:14 PM
 * TODO:Add class comment.
 */
public class RestClient {

    private static final String BASE_URL = "http://localhost:8080/expensemanager/rest/";

    private final static long SIZE_OF_CACHE = 10 * 1024 * 1024; // 10 MB

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

      /*  // Add Cache-Control Interceptor
        okHttpClient.networkInterceptors().add(mCacheControlInterceptor);
        okHttpClient.networkInterceptors().add(new StethoInterceptor());*/
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
