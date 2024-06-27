package kr.co._29cm.homework.util;

public class NumberUtil {

    private NumberUtil() {}

    /**
     * 숫자인지 확인한다.
     * */
    public static boolean isInteger(String str) {
        return str != null && str.matches("^-?\\d+$");
    }

}
