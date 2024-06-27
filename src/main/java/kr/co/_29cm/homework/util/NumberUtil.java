package kr.co._29cm.homework.util;

public class NumberUtil {

    private NumberUtil() {}

    public static boolean isInteger(String str) {
        return str != null && str.matches("^-?\\d+$");
    }

}
