package com.mmt.shubh.expensemanager.service.rest.service;

import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.database.content.Member;

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
