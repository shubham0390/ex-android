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

import com.km2labs.android.spendview.database.content.Device;
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
    Long addDevice(@Query("memberId") long memberId, Device device);

    @PUT("/device")
    Device updateDevice(@Query("emailId") String emailId, Device device);

    @DELETE("/members/device")
    String deleteDevice(@Query("deviceUUID") String detailsUUID,
                        @Query("emailId") String emailId);

    @GET("/members/device")
    List<Device> getMemberDevices(@Query("memberId") long memberId);
}
