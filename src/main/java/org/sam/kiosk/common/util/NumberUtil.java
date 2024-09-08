package org.sam.kiosk.common.util;

import java.text.DecimalFormat;

public class NumberUtil {

    private static final DecimalFormat decimalFormat = new DecimalFormat("###,###원");

    private NumberUtil() {}

    /**
     * 정수인지 확인한다.
     * */
    public static boolean isInteger(String str) {
        return str != null && str.matches("^-?\\d+$");
    }

    public static String toMoneyFormat(int amount) {
        return decimalFormat.format(amount);
    }

}
