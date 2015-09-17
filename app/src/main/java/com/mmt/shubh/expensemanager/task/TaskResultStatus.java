/*
 * Copyright (c) 2015 AirWatch, LLC. All rights reserved.
 *  This product is protected by copyright and intellectual property laws in  the United States and other countries
 *  as well as by international treaties.
 *  AirWatch products may be covered by one or more patents listed at
 *  http://www.vmware.com/go/patents.
 */

package com.mmt.shubh.expensemanager.task;

/**
 * Result status codes of {@link ITask}
 * Created by Umang Chamaria.
 */
public interface TaskResultStatus {

    int FETCH_SUCCESSFUL = 1;

    int FETCH_FAILED = 2;

    int NO_CONNECTIVITY = 3;

    int SDK_INITIALIZATION_SUCCESSFUL = 4;

    int SDK_INITIALIZATION_FAILURE_UNAUTHORIZED_ACCESS = 5;

    int SDK_INITIALIZATION_FAILURE_DEVICE_NOT_ENROLLED = 10;

    int SDK_INITIALIZATION_FAILURE_DEVICE_SERVICE_NOT_AVAILABLE = 11;

    int SDK_INITIALIZATION_FAILURE_UNKNOWN_ERROR = 12;

    String UNABLE_TO_FETCH_SETTINGS = "Unable to fetch settings";
}
