package com.mmt.shubh.expensemanager.service.rest.service;

import com.mmt.shubh.expensemanager.database.content.Expense;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

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
    Observable<List<Expense>> getAllExpenses(@Query("memberId") long memberId);

    @GET("/expense/{expenseBookId}")
    Observable<List<Expense>> getExpenseList(@Path("expenseBookId") long expenseBookId);

    @GET("/expense/sync")
    Observable<List<Expense>> syncExpenses(@Query("expenseBookId") long expenseBookId, @Query("syncId") long syncId);
}
