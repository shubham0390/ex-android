package com.mmt.shubh.expensemanager.service.rest.service;

import com.mmt.shubh.expensemanager.database.content.DeviceDetails;
import com.mmt.shubh.expensemanager.database.content.Member;

import java.util.List;

import retrofit.http.DELETE;
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

public interface MemberRestService {

    @POST("/members")
    Observable<Member> registerMember(Member member);

    @PUT("/members")
    Observable<Member> updateMember(Member member);

    @GET("/members")
    Observable<Member> getMember(@Query("emailId") String emailId);

    @GET("/members/espensebook")
    Observable<List<Member>> getExpenseBookMembers(@Query("expenseBookId") long expenseBookId);


    @DELETE("/members")
    Observable<String> deleteMember(@Query("emailId") String emailId);

    @PUT("/device/{GCMToken}")
    Observable<String> updateGCMToken(@Path("GCMToken") String GCMToken,
                                      @Query("emailId") String emailId);

    @POST("/device")
    Observable<Long> addDevice(@Query("memberId") long memberId, DeviceDetails deviceDetails);

    @PUT("/device")
    Observable<DeviceDetails> updateDevice(@Query("emailId") String emailId, DeviceDetails deviceDetails);

    @DELETE("/members/device")
    Observable<String> deleteDevice(@Query("deviceUUID") String detailsUUID,
                                    @Query("emailId") String emailId);

    @GET("/members/device")
    Observable<List<DeviceDetails>> getMemberDevices(@Query("memberId") long memberId);
}
