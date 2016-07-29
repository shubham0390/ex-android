package com.km2labs.android.spendview.core.util;

import java.util.Collection;
import java.util.Map;

/**
 * TODO: Add class description
 * <p>
 * <p>
 * Authored by : <A HREF="mailto:[SubhamTyagi@km2labs.com]">Subham Tyagi</A>
 * Created on  : 12/31/15 10:26 AM
 */
public class CollectionUtil {


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
