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

package com.enfle.spendview.core.util;

import java.util.Collection;
import java.util.Map;

/**
 * TODO: Add class description
 * <p>
 * <p>
 * Authored by : <A HREF="mailto:[SubhamTyagi@km2labs.com]">Subham Tyagi</A>
 * Created on  : 12/31/15 10:26 AM
 */
public class CollectionUtils {


    public static boolean isEmpty(Object[] array) {
        if (array == null || array.length <= 0) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(int[] array) {
        if (array == null || array.length <= 0) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(long[] array) {
        if (array == null || array.length <= 0) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Collection tasks) {
        return tasks == null || tasks.size() > 0;
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.size() <= 0;
    }
}
