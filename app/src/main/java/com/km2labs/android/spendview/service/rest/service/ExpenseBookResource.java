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
