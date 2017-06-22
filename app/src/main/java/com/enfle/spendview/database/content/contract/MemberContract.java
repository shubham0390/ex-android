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

package com.enfle.spendview.database.content.contract;

import android.provider.BaseColumns;

public interface MemberContract extends BaseColumns, BaseContract {
    String TABLE_NAME = "members";
    String MEMBER_USER_NAME = "user_name";
    String MEMBER_NAME = "name";
    String MEMBER_EMAIL = "email";
    String MEMBER_PHONE_NUMBER = "phone_number";
    String MEMBER_IMAGE_URI = "profile_image_uri";
    String MEMBER_COVER_IMAGE_URL = "cover_image_uri";
}