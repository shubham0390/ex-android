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

import com.km2labs.android.spendview.database.content.Member;
import com.km2labs.android.spendview.database.content.ExpenseBook;

import java.util.List;

import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Query;

/**
 * Created by Subham Tyagi
 * On 6/9/2015.
 * <p>
 * TODO: Add class comments
 */

public interface ExpenseBookResource {

    @POST("/expensebook")
    void createExpenseBook(ExpenseBook expenseBook);

    @PUT("/expensebook")
    void updateExpenseBook(ExpenseBook expenseBook);

    @GET("/expensebook")
    ExpenseBook getExpenseBookDetails(@Query("expenseBookId") String clientId);

    @GET("/expensebook")
    List<ExpenseBook> getExpenseBookList(String memberEmailId);

    @GET("/expensebook")
    List<ExpenseBook> getExpenseBookList(long memberId);


    @DELETE("/expensebook")
    void deleteExpenseBook(@Query("expenseBookId") String clientId);


    @POST("/expensebook/member")
    void addMember(Member member, @Query("expenseBookId") String clientId);

    @POST("/expensebook/member")
    void addMembers(List<Member> memberList, @Query("expenseBookId") String clientId);

    @DELETE("/expensebook/member")
    void deleteMember(@Query("expenseBookId") String clientId, @Query("memberEmailId") String memberEmailId);
}
