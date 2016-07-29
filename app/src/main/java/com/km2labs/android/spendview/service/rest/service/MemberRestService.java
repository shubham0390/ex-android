package com.km2labs.android.spendview.service.rest.service;

import com.km2labs.android.spendview.database.content.DeviceDetails;
import com.km2labs.android.spendview.database.content.Member;

import java.util.List;

import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Subham Tyagi
 * On 6/10/2015.
 * <p>
 * TODO: Add class comments
 */

public interface MemberRestService {

    @POST("/members")
    Member registerMember(Member member);

    @PUT("/members")
    Member updateMember(Member member);

    @GET("/members")
    Member getMember(@Query("emailId") String emailId);

    @GET("/members/espensebook")
    List<Member> getExpenseBookMembers(@Query("expenseBookId") long expenseBookId);


    @DELETE("/members")
    String deleteMember(@Query("emailId") String emailId);

    @PUT("/device/{GCMToken}")
    String updateGCMToken(@Path("GCMToken") String GCMToken,
                          @Query("emailId") String emailId);

    @POST("/device")
    Long addDevice(@Query("memberId") long memberId, DeviceDetails deviceDetails);

    @PUT("/device")
    DeviceDetails updateDevice(@Query("emailId") String emailId, DeviceDetails deviceDetails);

    @DELETE("/members/device")
    String deleteDevice(@Query("deviceUUID") String detailsUUID,
                        @Query("emailId") String emailId);

    @GET("/members/device")
    List<DeviceDetails> getMemberDevices(@Query("memberId") long memberId);
}
