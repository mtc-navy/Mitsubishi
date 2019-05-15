package io.mtc.common.utils;

import java.util.List;

public class ListUtils {

    /**
     * List判空
     *
     * @param value
     * @return
     */
    public static boolean isEmpty(List<?> value) {
        return value == null || value.isEmpty();
    }

    /**
     * List判不空
     *
     * @param value
     * @return
     */
    public static boolean isNotEmpty(List<?> value) {
        return !isEmpty(value);
    }

    /**
     * List判空，返回Y/N
     *
     * @param value
     * @return 返回Y/N
     */
    public static String isEmptyYorN(List<?> value) {
        return isEmpty(value) ? "Y" : "N";
    }

    /**
     * List判空，返回Y/N
     *
     * @param value
     * @return 返回Y/N
     */
    public static String isNOTEmptyYorN(List<?> value) {
        return isNotEmpty(value) ? "Y" : "N";
    }
}
