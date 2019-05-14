package io.mtc.common.utils;

import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Object对象处理
 */
public class ObjectUtil {

    /**
     * Object转换成String类型
     *
     * @param value
     * @return String
     */
    public static String toString(Object value) {
        return value == null ? null : String.valueOf(value);
    }

    /**
     * String类型NULL转空
     *
     * @param value
     * @return String
     */
    public static String nullToEmpty(String value) {
        return value == null ? "" : value;
    }

    /**
     * Object类型转换成Int类型
     *
     * @param value
     * @return
     */
    public static Integer toInteger(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Double) {
            return ((Double) value).intValue();
        }
        if (value == null) {
            return null;
        }
        if (isNumeric(String.valueOf(value))) {
            return Integer.parseInt(String.valueOf(value));
        } else {
            return null;
        }
    }

    /**
     * NULL值转换成Int型
     *
     * @param value
     * @return
     */
    public static Integer nullToZero(Integer value) {
        if (value == null) {
            return Integer.valueOf("0");
        }
        return value;
    }

    /**
     * Object类型转换成Date
     *
     * @param value
     * @return
     */
    public static Date toDate(Object value) {
        return value == null ? null : DateUtils.stringToDate(toString(value), "yyyy-MM-dd");
    }

    /**
     * 判断是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * Boolean类型转换成字符串
     *
     * @param value
     * @return Y/N
     */
    public static String boolToString(boolean value) {
        return value ? "Y" : "N";
    }

    /**
     * bool类型值返回是或者否
     *
     * @param value
     * @return
     */
    public static String boolToGBK(String value) {
        if (StringUtils.isEmpty(value)) {
            return "";
        } else if (value.equals("N")) {
            return "否";
        } else if (value.equals("Y")) {
            return "是";
        }
        return "";
    }

}
