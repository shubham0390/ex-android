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

package com.km2labs.android.spendview.service.rest.service;

import com.km2labs.android.spendview.database.content.Expense;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Subham Tyagi
 * On 6/10/2015.
 * <p>
 * TODO: Add class comments
 */
public interface ExpenseRestService {

    @POST("/expense")
    void createExpense();

    @PUT("/expense")
    void updateExpense();

    @PUT("/expense")
    void moveExpense(@Query("expenseId") long expenseId, @Query("expenseBookId") long newExpenseBookId);

    @GET("/expense")
    List<Expense> getAllExpenses(@Query("memberId") long memberId);

    @GET("/expense/{expenseBookId}")
    List<Expense> getExpenseList(@Path("expenseBookId") long expenseBookId);

    @GET("/expense/sync")
    List<Expense> syncExpenses(@Query("expenseBookId") long expenseBookId, @Query("syncId") long syncId);
}
