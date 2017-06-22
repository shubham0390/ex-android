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

package com.enfle.spendview.database.exception;

/**
 * Created by Subham Tyagi,
 * on 06/Nov/2015,
 * 6:31 PM
 * TODO:Add class comment.
 */
public class EmptyDataException extends RuntimeException {

    public EmptyDataException() {
    }

    public EmptyDataException(String detailMessage) {
        super(detailMessage);
    }

    public EmptyDataException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public EmptyDataException(Throwable throwable) {
        super(throwable);
    }
}
